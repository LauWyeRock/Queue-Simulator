package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;

abstract class Event {
    private final Customer customer;
    private final double eventTime;

    Event(Customer customer, double eventTime) {
        this.customer = customer;
        this.eventTime = eventTime;
    }

    Customer getCustomer() {
        return this.customer;
    }

    double getEventTime() {
        return this.eventTime;
    }

    abstract Pair<Optional<Event>, Shop> execute(Shop shop);

    @Override
    public String toString() {
        return String.format("%.3f", this.eventTime);
    }
}
