package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;

class EventStub extends Event {

    EventStub(Customer customer, double eventTime) {
        super(customer, eventTime);
    }

    @Override
    Pair<Optional<Event>, Shop> execute(Shop shop) {
        return Pair.<Optional<Event>, Shop>of(Optional.empty(), shop);
    }
}
