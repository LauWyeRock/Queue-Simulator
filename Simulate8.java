package cs2030.simulator;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import cs2030.util.PQ;
import cs2030.util.ImList;
import cs2030.util.Pair;

public class Simulate8 {
    private final PQ<Event> arrivals;
    private final Shop shop;

    public Simulate8(int numServer, int numSelfCheck, List<Pair<Double,
            Supplier<Double>>> inputTimes, int maxQ, Supplier<Double> restTimes) {
        ImList<Server> tempServe = ImList.<Server>of();
        int i = 1;
        for (; i <= numServer; i++) {
            tempServe = tempServe.add(new Server(i, maxQ, restTimes));
        }
        tempServe = (numSelfCheck == 0) ? tempServe :
            tempServe.add(new SelfCheck(i, maxQ, numSelfCheck));

        this.shop = new Shop(tempServe);

        PQ<Event> temp = new PQ<Event>(new EventComparator());
        int j = 1;
        for (Pair<Double, Supplier<Double>> time : inputTimes) {
            temp = temp.add(new Arrive(new Customer(j, time.first(), time.second()),
                    time.first()));
            j++;
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

            if (event.contains("Decide") || event.contains("waits")) {
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
