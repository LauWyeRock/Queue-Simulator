package cs2030.simulator;

import java.util.List;
import java.util.Optional;
import cs2030.util.PQ;
import cs2030.util.ImList;
import cs2030.util.Pair;

public class Simulate4 {
    private final PQ<Event> arrivals;
    private final Shop shop;

    public Simulate4(int numServer, List<Double> arrivalTimes) {
        ImList<Server> tempServe = ImList.<Server>of();
        for (int i = 1; i <= numServer; i++) {
            tempServe = tempServe.add(new Server(i));
        }

        this.shop = new Shop(tempServe);

        PQ<Event> temp = new PQ<Event>(new EventComparator());
        int i = 1;
        for (double arrivalTime : arrivalTimes) {
            temp = temp.add(new Arrive(new Customer(i, arrivalTime), arrivalTime));
            i++;
        }
        this.arrivals = temp;
    }

    public String run() {
        String results = "";
        PQ<Event> running = this.arrivals;
        Shop tempShop = this.shop;
        Statistic statistics = new Statistic(); 

        while (!running.isEmpty()) {
            Event currentEvent = running.poll().first();
            String event = currentEvent.toString();
            Pair<Optional<Event>, Shop> currentPair = currentEvent.execute(tempShop);

            results = (event.contains("Decide") || event.contains("resting")) ? results : 
                results + event + "\n";
            statistics = (event.contains("done")) ? statistics.addServed() : 
                (event.contains("leaves")) ? statistics.addLeft() : statistics;

            if (event.contains("waits")) {
                statistics = statistics.addWait(currentPair.first().orElseThrow().getEventTime() -
                        currentEvent.getEventTime());
            } 
            running = running.poll().second();

            if (!currentPair.first().equals(Optional.empty())) {
                running = running.add(currentPair.first().orElseThrow());
            }

            tempShop = currentPair.second();
        }
        return results + statistics;
    }

    @Override
    public String toString() {
        return String.format("Queue: %s; Shop: %s", arrivals.toString(), shop.toString());
    }
}
