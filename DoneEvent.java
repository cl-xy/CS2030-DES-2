package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Creates an event for customers who are done.
 * The `DoneEvent` supports (i) changing the state of selected server to available
 * (ii) getting the identifier of selected server
 */
public class DoneEvent extends Event {
    private final int selectedID;
    //private final Shop shop;

    /**
     * Initialises the DoneEvent.
     * @param customer the current customer
     * @param shop the current shop
     * @param selectedID the identifier of the server selected to serve the customer
     * @param eventTime the time that DoneEvent happens
     */
    public DoneEvent(Customer customer, int selectedID, double eventTime) {
        super(shop -> {
            Server curr = shop.find(s -> s.getID() == selectedID).get();
            Server newServer = new Server(selectedID, true, false, curr.getNextAvailTime());
            Shop newShop = shop.replace(newServer);
            return Pair.of(newShop, new DoneEvent(customer, selectedID, eventTime));
        }, customer, eventTime);
        //this.shop = shop;
        this.selectedID = selectedID;
    }
    
    /**
     * Overriden toString method.
     * @return prints out the DoneEvent in the correct format
     */
    @Override
    public String toString() {
        double time = this.getEventTime();
        Customer cust = this.getCustomer();
        return String.format("%.3f %s done serving by server %s", time, 
                cust.getId(), this.selectedID);
    }
}
