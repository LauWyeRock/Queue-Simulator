package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;

class Arrive extends Event {
    
    Arrive(Customer customer, double eventTime) {
        super(customer, eventTime);
    }

    @Override
    Pair<Optional<Event>, Shop> execute(Shop shop) {
        Customer customer = super.getCustomer();

        if (shop.getIdleServer().map(x -> x.isIdle()).orElse(false)) {
            Server server = shop.getIdleServer().orElseThrow();

            return Pair.<Optional<Event>, Shop>of(
                    Optional.<Event>of(new Serve(customer, super.getEventTime(), server)),
                    shop);
        } else if (shop.getQueueableServer().map(x -> x.canQ()).orElse(false)) {
            Server server = shop.getQueueableServer().orElseThrow();

            return Pair.<Optional<Event>, Shop>of(
                    Optional.<Event>of(new Wait(customer, super.getEventTime(), server)),
                    shop);
        } else {
            return Pair.<Optional<Event>, Shop>of(
                    Optional.<Event>of(new Leave(customer, super.getEventTime())),
                    shop);
        } 
    }

    @Override
    public String toString() {
        return String.format("%s %s arrives", super.toString(), super.getCustomer().toString());
    }
} 
