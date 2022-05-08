package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;

class Wait extends Event {
    private final Server server;

    Wait(Customer customer, double eventTime, Server server) {
        super(customer, eventTime);
        this.server = server;
    }

    @Override
    Pair<Optional<Event>, Shop> execute(Shop shop) {
        Customer customer = super.getCustomer();
        Server server = shop.get(this.server.getID() - 1);

        if (server.hasQ() || !server.isIdle()) {
            return Pair.<Optional<Event>, Shop>of(
                    Optional.<Event>of(new Decide(customer,
                            server.getWaitUntil(),
                            server)),
                    shop.queue(server, customer));
        } else {
            return Pair.<Optional<Event>, Shop>of(
                    Optional.<Event>of(new Serve(customer.wait(() -> server.getWaitUntil()),
                            server.getWaitUntil(),
                            server)),
                    shop.queue(server, customer));
        }
    }

    @Override
    public String toString() {
        return String.format("%s %s waits at %s", super.toString(),
                super.getCustomer().toString(), server.toString());
    }
}
