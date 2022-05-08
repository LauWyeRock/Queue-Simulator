package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;

class Done extends Event {
    private final Server server;

    Done(Customer customer, double eventTime, Server server) {
        super(customer, eventTime);
        this.server = server;
    }

    @Override
    Pair<Optional<Event>, Shop> execute(Shop shop) {
        Customer customer = super.getCustomer();
        Server server = shop.get(this.server.getID() - 1).maybeRest();

        return Pair.<Optional<Event>, Shop>of(
                Optional.<Event>of(new Rest(customer,
                        server.getWaitUntil(), server)),
                shop.maybeRest(server));
    }

    @Override
    public String toString() {
        return String.format("%s %s done serving by %s", super.toString(),
                super.getCustomer().toString(), this.server.toString());
    }
}
