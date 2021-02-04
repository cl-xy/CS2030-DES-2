package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.function.Supplier;

public class Shop {
    private final int numServers;
    private final List<Server> serverList;
    private final RandomGenerator rng;
    private final List<ServerQueue> queueList;
    private final int maxQueueLength;

    /**
     * Initialise a shop 
     * @param numServers the number of servers in this shop
     * @param rng the random generator for this shop
     */
    public Shop(int numServers, RandomGenerator rng) {
        this.numServers = numServers;
        this.serverList = Stream.iterate(Server.freeServer(1), x -> Server.freeServer(x.getID() + 1)).limit(numServers).collect(Collectors.toList());
        this.rng = rng;
        this.queueList = new ArrayList<Integer>();
        for (int i = 0; i < numServers; i++) {
            this.queueList.add(0);
        }
        this.maxQueueLength = 1;
    }

    /**
     * Overloaded constructor to initialise a shop
     * @param serverList the list of servers in the shop
     * @param rng the random generator for this shop
     */
    public Shop(List<Server> serverList, RandomGenerator rng) {
        this.numServers = serverList.size();
        this.serverList = serverList;
        this.rng = rng;
        this.queueList = new ArrayList<Integer>();
        for (int i = 0; i < numServers; i++) {
            this.queueList.add(0);
        }
        this.maxQueueLength = 1;
    }

    /**
     * For level 3 testcases
     * Overloaded constructor to initialise a shop
     * @param numServers the number of servers in this shop
     */
    public Shop(int numServers) {
        this(numServers, new RandomGenerator(1, 1, 1, 1));
    }

    /**
     * For level 3 testcases
     * Overloaded constructor to initialise a shop
     * @param serverList the list of servers in this shop
     */
    public Shop(List<Server> serverList) {
        this(serverList, new RandomGenerator(1, 1, 1, 1));
    }

    public Shop(int numServers, RandomGenerator rng, int maxQueueLength) {
        this.numServers = numServers;
        this.serverList = Stream.iterate(Server.freeServer(1), x -> Server.freeServer(x.getID() + 1)).limit(numServers).collect(Collectors.toList());
        this.rng = rng;
        this.queueList = new ArrayList<Integer>();
        for (int i = 0; i < numServers; i++) {
            this.queueList.add(0);
        }
        this.maxQueueLength = maxQueueLength;
    }

    public Shop(List<Server> serverList, RandomGenerator rng, int maxQueueLength) {
        this.numServers = serverList.size();
        this.serverList = serverList;
        this.rng = rng;
        this.queueList = new ArrayList<Integer>();
        for (int i = 0; i < numServers; i++) {
            this.queueList.add(0);
        }
        this.maxQueueLength = maxQueueLength;
    }

    /**
     * To find a particular server that fulfils the predicate
     * @param pred the Predicate condition to fulfil
     * @return optional object of server if condition fulfilled, else empty optional object
     */
    public Optional<Server> find(Predicate<Server> pred) {
        return this.serverList.stream().filter(pred).findFirst();
    }

    //find first queue that has capacity
    public Optional<Integer> find(Predicate<Integer> pred) {
        return this.queueList.stream().filer(pred).findFirst();
    }

    /**
     * To replace a server in the shop with new server
     * @param server the new server
     * @return a new shop containing the replaced server
     */
    public Shop replace(Server server) {
        return new Shop(this.serverList.stream().map(x -> x.getID() == server.getID() ? server : x).collect(Collectors.toList()), this.rng);
    }

    /**
     * Getter method
     * @return this shop's rng.
     */
    public RandomGenerator getRng() {
        return this.rng;
    }

    public int getMQL() {
        return this.maxQueueLength;
    }

    public List<Integer> getQueueList() {
        return this.queueList;
    }

    @Override
    public String toString() {
        return this.serverList.toString();
    }
}
