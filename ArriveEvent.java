package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Creates an event when the customer arrives.
 * The `ArriveEvent` class supports (i) making the event happen 
 * (ii) deciding on the next event for this particular customer
 * (iii) storing the time of occurrence of event.
 */

public class ArriveEvent extends Event {
    /** Initialises ArriveEvent.
     * @param customer the customer who arrives
     */ 

    public ArriveEvent(Customer customer) {
        super(shop -> {
                Optional<Server> freeServer = shop.find(s -> s.isAvailable());
                int shopMQL = shop.getMQL();
                Optional<Integer> queueHasSpace = shop.find(q -> q < shopMQL);
                //Optional<Server> serverHasSpace = shop.find(s -> !s.getHasWaitCust());

                //if all servers occupied, all queues occupied, leave the shop
                if (freeServer.isEmpty() && queueHasSpace.isEmpty()) {
                    return Pair.of(shop, new LeaveEvent(customer));
                
                //if there is an avail server, serve the customer
                } else if (freeServer.isPresent()) {
                    Server server = freeServer.get();
                    return Pair.of(shop, new ServeEvent(customer, server.getID()));
                
                //if there is a queue with space
                } else if (queueHasSpace.isPresent()) {
                    //Server server = serverHasSpace.get();

                    return Pair.of(shop, new WaitEvent(customer, server.getID()));
                } else {
                    return null;
                }
        }, customer, customer.getArrTime());
    }

    /**
     * Override toString method.
     * @return prints out the arriveEvent in the correct format
     */
    @Override
    public String toString() {
        Customer cust = this.getCustomer();
        String custDetails = String.format("%.3f ", cust.getArrTime()) + cust.getId();
        return custDetails + " arrives";
    }
}
