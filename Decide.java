package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;

class Decide extends Event {
    private final Server server;

    Decide(Customer customer, double eventTime, Server server) {
        super(customer, eventTime);
        this.server = server;
    }

    @Override
    Pair<Optional<Event>, Shop> execute(Shop shop) {
        Customer customer = super.getCustomer();
        Server server = shop.get(this.server.getID() - 1);

        if (server.isIdle()) {
            return Pair.<Optional<Event>, Shop>of(
                    Optional.<Event>of(new Serve(customer.wait(() -> server.getWaitUntil()),
                            server.getWaitUntil(),
                            server)),
                    shop);
        } else {
            return Pair.<Optional<Event>, Shop>of(
                    Optional.<Event>of(new Decide(customer.wait(() -> server.getWaitUntil()),
                            server.getWaitUntil(),
                            server)),
                    shop);
        }
    }

    @Override
    public String toString() {
        return "Decide";
    }
}
