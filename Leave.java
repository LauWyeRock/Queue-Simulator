package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;

class Leave extends Event {
    
    Leave(Customer customer, double eventTime) {
        super(customer, eventTime);
    }

    @Override
    Pair<Optional<Event>, Shop> execute(Shop shop) {
        return Pair.<Optional<Event>, Shop>of(Optional.empty(), shop);
    }

    @Override
    public String toString() {
        return String.format("%s %s leaves", super.toString(), super.getCustomer().toString());
    }
}
