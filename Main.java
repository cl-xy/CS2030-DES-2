import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;

import cs2030.simulator.Customer;
import cs2030.simulator.Server;
import cs2030.simulator.Event;
import cs2030.simulator.ArriveEvent;
import cs2030.simulator.ServeEvent;
import cs2030.simulator.EventComparator;
import cs2030.simulator.WaitEvent;
import cs2030.simulator.DoneEvent;
import cs2030.simulator.LeaveEvent;
import cs2030.simulator.Pair;
import cs2030.simulator.Shop;
import cs2030.simulator.Des;

class Main {
    private String calcStats(PriorityQueue<Event> priorityQueue, int numCust) {
        List<Event> finalEvents = new ArrayList<Event>();

        double totalWaitingTime = 0;
        int numServed = 0;
        int numLeft = 0;

        while (!priorityQueue.isEmpty()) {
            finalEvents.add(priorityQueue.remove());
        }

        int currCustId = 1;
        double custArrTime = 0;
        double custServeTime = 0;
        for (int i = 0; i < finalEvents.size(); i++) {
            Event curr = finalEvents.get(i);
            if ((curr instanceof ArriveEvent) && (curr.getCustomer().getId() == currCustId)) {
                custArrTime = curr.getEventTime();

                for (int j = i + 1; j < finalEvents.size(); j++) {
                    Event next = finalEvents.get(j);

                    if ((next instanceof ServeEvent) && (next.getCustomer().getId() == currCustId)) {
                        custServeTime = next.getEventTime();
                        break;
                    }

                    if ((next instanceof LeaveEvent) && (next.getCustomer().getId() == currCustId)) {
                        custServeTime = custArrTime;
                        numLeft++;
                        break;
                    }
                }
                totalWaitingTime += custServeTime - custArrTime;
                currCustId++;
            }

        }

        numServed = numCust - numLeft;
        double avgWT;
        if (totalWaitingTime!= 0) {
            avgWT = totalWaitingTime / (double) numServed;
        } else {
            avgWT = 0;
        }
        return String.format("[%.3f %s %s]", avgWT, numServed, numLeft);
    }

    private void printPQ(PriorityQueue<Event> priorityQueue) {
        while (!priorityQueue.isEmpty()) {
            System.out.println(priorityQueue.remove());
        }
    }

    public static void main(String[] args) {
        if (args.length == 5) {
            int seed = Integer.valueOf(args[0]);
            int numServers = Integer.valueOf(args[1]);
            int numCustomers = Integer.valueOf(args[2]);
            double arrivalRate = Double.valueOf(args[3]);
            double serviceRate = Double.valueOf(args[4]);
            Des des = new Des(seed, numServers, numCustomers, arrivalRate, serviceRate);
        } else if (args.length == 6) {
            int seed = Integer.valueOf(args[0]);
            int numServers = Integer.valueOf(args[1]);
            int maxQueueLength = Integer.valueOf(args[2]);
            int numCustomers = Integer.valueOf(args[3]);
            double arrivalRate = Double.valueOf(args[4]);
            double serviceRate = Double.valueOf(args[5]);
            Des des = new Des(seed, numServers, maxQueueLength, numCustomers, arrivalRate, serviceRate);
        }
        PriorityQueue<Event> pq = des.run();       
        PriorityQueue<Event> pqForPrint = new PriorityQueue<Event>(pq);
        Main main = new Main();
        main.printPQ(pqForPrint);
        System.out.println(main.calcStats(pq, numCustomers));
    }
}
