package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;

/** 
 * Creates an event when customer is being served.
 * The `ServeEvent` should support 
 * (i) deciding the next event.
 */
public class ServeEvent extends Event {
    private final int selectedID;
    //private final Shop shop;
    
    /**
     * Initialises the ServeEvent for customers who arrive and get served immediately.
     * @param customer the current customer
     * @param shop the current shop
     * @param selectedID the identifier of selected server
     */
    public ServeEvent(Customer customer, int selectedID) {
        super(shop -> {
            //RandomGenerator rng = shop.getRng();
            double serviceTime = shop.getRng().genServiceTime();
            
            Server newServer = new Server(selectedID, false, false, customer.getArrTime() + serviceTime);
            Shop newShop = shop.replace(newServer);
            return Pair.of(newShop, new DoneEvent(customer, selectedID, customer.getArrTime() + serviceTime));
        }, customer, customer.getArrTime());
        //this.shop = shop;
        this.selectedID = selectedID;
    }
    
    /**
     * Overloaded constructor
     * Initialises the ServeEvent for customers who gets waited before getting served.
     * @param customer the current customer
     * @param shop the current shop
     * @param selectedID the identifier of selected server
     * @param eventTime the time that customer gets served
     */
    public ServeEvent(Customer customer, int selectedID, double eventTime) {
        super(shop -> {
            //RandomGenerator rng = shop.getRng();
            double serviceTime = shop.getRng().genServiceTime();
            Server newServer = new Server(selectedID, false, false, eventTime + serviceTime);
            Shop newShop = shop.replace(newServer);
            return Pair.of(newShop, new DoneEvent(customer, selectedID, eventTime + serviceTime));
        }, customer, eventTime);
        //this.shop = shop;
        this.selectedID = selectedID;
    }

    /**
     * Overriden toString method.
     * @return prints out the event in the correct format
     */
    @Override
    public String toString() {
        Customer cust = this.getCustomer();
        double time = this.getEventTime();
        return String.format("%.3f %s served by server %s", time, 
                cust.getId(), this.selectedID);        
    }
}
