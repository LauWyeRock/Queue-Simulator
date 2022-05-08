package cs2030.simulator;

import java.util.List;
import java.util.Optional;
import cs2030.util.ImList;

class Shop {
    private final ImList<Server> servers;

    Shop(List<Server> servers) {
        this.servers = ImList.<Server>of(servers);
    }

    Shop(ImList<Server> servers) {
        this.servers = servers;
    }

    Server get(int index) {
        return this.servers.get(index);
    }

    Shop queue(Server server, Customer customer) {
        Server tempServer = server.queue(customer);
        return new Shop(this.servers.set(server.getID() - 1, tempServer));
    }

    Shop done(Server server, Customer customer) {
        Server tempServer = server.done(customer);
        return new Shop(this.servers.set(server.getID() - 1, tempServer));
    }

    Optional<Server> getIdleServer() {
        ImList<Server> temp = this.servers.filter(x -> x.isIdle());
        return (temp.isEmpty()) ? Optional.<Server>empty() : Optional.<Server>of(temp.get(0));
    }

    Optional<Server> getQueueableServer() {
        ImList<Server> temp = this.servers.filter(x -> x.canQ());
        return (temp.isEmpty()) ? Optional.<Server>empty() : Optional.<Server>of(temp.get(0));
    }

    Shop maybeRest(Server server) {
        Server tempServer = server;
        return new Shop(this.servers.set(server.getID() - 1, tempServer));
    }

    Shop serve(Server server, Customer customer, double time) {
        Server tempServer = server.serve(customer, time);
        return new Shop(this.servers.set(server.getID() - 1, tempServer));
    }

    @Override
    public String toString() {
        return servers.toString();
    }
}
