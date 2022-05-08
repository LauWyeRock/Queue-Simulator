package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;

class Serve extends Event {
    private final Server server;

    Serve(Customer customer, double eventTime, Server server) {
        super(customer, eventTime);
        this.server = server;
    }

    @Override
    Pair<Optional<Event>, Shop> execute(Shop shop) {
        Customer customer = super.getCustomer();
        Server server = shop.get(this.server.getID() - 1);

        return Pair.<Optional<Event>, Shop>of(
                Optional.<Event>of(new Done(customer,
                        customer.getFinishTime(),
                        server)),
                shop.serve(server, customer, customer.getFinishTime()));
    }

    @Override
    public String toString() {
        return String.format("%s %s serves by %s", super.toString(),
                super.getCustomer().toString(), this.server.toString());
    }
}
