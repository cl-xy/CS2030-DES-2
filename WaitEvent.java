package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Creates a event when the customer gets waited.
 * The `WaitEvent` class should support
 * (i) getting the time of event
 * (ii) getting the id of the selected server
 * (iii) making the event happen
 * (iv) updating the server list.
 */
public class WaitEvent extends Event {
    private final int selectedID;
    //private final Shop shop;

    /**
     * Initialises the WaitEvent. 
     * @param customer the current customer
     * @param shop the current shop
     * @param selectedID the id of selected server
     */
    public WaitEvent(Customer customer, int selectedID) {
        super(shop -> {
                Server curr = shop.find(s -> s.getID() == selectedID).get();
                Server newServer = new Server(selectedID, false, true, curr.getNextAvailTime());
                Shop newShop = shop.replace(newServer);
                return Pair.of(newShop, new ServeEvent(customer, selectedID, curr.getNextAvailTime()));
        }, customer, customer.getArrTime());
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
        return String.format("%.3f %s waits to be served by server %s", 
                cust.getArrTime(), cust.getId(), this.selectedID);
    }
}
