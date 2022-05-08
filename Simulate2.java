package cs2030.simulator;

import java.util.List;
import cs2030.util.PQ;
import cs2030.util.ImList;

public class Simulate2 {
    private final PQ<Event> events;
    private final Shop shop;

    public Simulate2(int numServer, List<Double> arrivalTimes) {
        ImList<Server> tempServe = ImList.<Server>of();
        for (int i = 1; i <= numServer; i++) {
            tempServe = tempServe.add(new Server(i));
        }

        this.shop = new Shop(tempServe);

        PQ<Event> temp = new PQ<Event>(new EventComparator());
        int i = 1;
        for (double time : arrivalTimes) {
            temp = temp.add(new EventStub(new Customer(i, time), time));
            i++;
        }
        this.events = temp;
    }

    public String run() {
        String results = "";
        PQ<Event> running = this.events;
        while (!running.isEmpty()) {
            results += running.poll().first().toString() + "\n"; 
            running = running.poll().second();
        }

        return results + "-- End of Simulation --";
    }

    @Override
    public String toString() {
        return String.format("Queue: %s; Shop: %s", events.toString(), shop.toString());
    }
}
