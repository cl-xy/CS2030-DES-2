package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Creates a leave event when customer arrives, does not get served and leaves.
 * The `LeaveEvent` should support
 * (i) getting the time that customer leaves
 */
public class LeaveEvent extends Event {
    //private final Shop shop;

    /**
     * Initialises the LeaveEvent.
     * @param customer the current customer
     * @param shop the current shop
     */
    public LeaveEvent(Customer customer) {
        super(shop -> Pair.of(shop, new LeaveEvent(customer)), customer, customer.getArrTime());
        //this.shop = shop;
    }

    /**
     * Overriden toString method.
     * @return prints out the event in correct format
     */
    @Override
    public String toString() {
        Customer cust = this.getCustomer();
        return String.format("%.3f %s leaves", cust.getArrTime(), cust.getId());
    }
}
