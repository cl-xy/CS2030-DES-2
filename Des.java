package cs2030.simulator;

import java.util.stream.*;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
//import cs2030.simulator.RandomGenerator;
//import cs2030.simulator.RandomGenerator;

public class Des {
    private final RandomGenerator rng;
    //private final int seed;
    private final int numServers;
    private final int numCustomers;
    //private final double arrivalRate;
    //private final double serviceRate;
    private final Shop shop;
    //private final List<Customer> custList;

    /**
     * Constructor for level 3
     * @param seed the base seed for the RandomGenerator object
     * @param numServers the number of servers
     * @param numCustomers the number of customers
     * @param arrivalRate rate of customers arriving
     * @param serviceRate rate of serving the customers
     */
    public Des(int seed, int numServers, int numCustomers, double arrivalRate, double serviceRate) {
        this.rng = new RandomGenerator(seed, arrivalRate, serviceRate, 1);
        //this.seed = seed;
        this.numServers = numServers;
        this.numCustomers = numCustomers;
        //this.arrivalRate = arrivalRate;
        //this.serviceRate = serviceRate;
        this.shop = new Shop(numServers, rng);
    }
    
    public Des(int seed, int numServers, int maxQueueLength, int numCustomers, double arrivalRate, double serviceRate) {
        this.rng = new RandomGenerator(seed, arrivalRate, serviceRate, 1);
        //this.seed = seed;
        this.numServers = numServers;
        this.numCustomers = numCustomers;
        //this.arrivalRate = arrivalRate;
        //this.service
        this.shop = new Shop(numServers, rng, maxQueueLength);
    }

    private List<Customer> getCustomerList(int numCustomers) {
        List<Customer> list = new ArrayList<>();
        double arrivalTime = 0;
        for (int i = 1; i <= numCustomers; i++) {
            list.add(new Customer(i, arrivalTime));
            arrivalTime += rng.genInterArrivalTime();
        }
        return list;
    }

    /**
     * start running the des
     * @return priority queue of events in the final order
     */
    public PriorityQueue<Event> run() {
        PriorityQueue<Event> tempQueue = new PriorityQueue<>(new EventComparator());
        PriorityQueue<Event> finalQueue = new PriorityQueue<>(new EventComparator());
        List<Customer> custList = getCustomerList(numCustomers);
        for (int i = 0; i < custList.size(); i++) {
            Customer cust = custList.get(i);
            tempQueue.add(new ArriveEvent(cust));
        }
        
        Pair<Shop, Event> pair;
        Shop currShop = shop;

        while (!tempQueue.isEmpty()) {
            Event curr = tempQueue.poll();
            finalQueue.add(curr);
            //System.out.println(curr);
            if (curr instanceof ArriveEvent || curr instanceof ServeEvent || curr instanceof WaitEvent) {
                pair = curr.execute(currShop);
                currShop = pair.first();
                Event nextEvent = pair.second();
                //System.out.println(nextEvent);
                tempQueue.add(nextEvent);
            } else if (curr instanceof DoneEvent) {
                pair = curr.execute(currShop);
                currShop = pair.first();
            }
        }

        /*while (!finalQueue.isEmpty()) {
            System.out.println(finalQueue.poll());
        }*/
        return finalQueue;
    }
}
