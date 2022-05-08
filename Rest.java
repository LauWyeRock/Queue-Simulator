package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;

class Rest extends Event {
    private final Server server;

    Rest(Customer customer, double eventTime, Server server) {
        super(customer, eventTime);
        this.server = server;
    }

    @Override
    Pair<Optional<Event>, Shop> execute(Shop shop) {
        Customer customer = super.getCustomer();
        Server server = shop.get(this.server.getID() - 1);

        return Pair.<Optional<Event>, Shop>of(
                Optional.empty(),
                shop.done(server, customer));
    }

    @Override
    public String toString() {
        return "resting";
    }
}
