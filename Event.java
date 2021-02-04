package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.Optional;

/**
 * Creates an abstract class, acts as a parent class.
 * The `Event` class supports 
 * (i) getting the customer in the event
 * (ii) getting the server list
 * (iii) getting the customer's id
 * (iv) getting the customer's arrival time
 */
public abstract class Event {
    private final double eventTime;
    private final Customer customer;
    private final Function<Shop, Pair<Shop, Event>> func;

    /**
     * Returns another event
     * @param function the function to apply
     */
    public Event(Function<Shop, Pair<Shop, Event>> function, Customer customer, double eventTime) {
        this.func = function;
        this.customer = customer;
        this.eventTime = eventTime;
    }

    final Pair<Shop, Event> execute(Shop shop) {
        return this.func.apply(shop);
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public double getEventTime() {
        return this.eventTime;
    }
}
