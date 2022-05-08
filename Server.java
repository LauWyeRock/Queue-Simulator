package cs2030.simulator;

import java.util.Optional;
import java.util.function.Supplier;
import cs2030.util.ImList;

class Server {
    private static final int DEFAULT_MAXQ = 1;
    private static final double ZERO = 0.0;
    private final int id;
    private final double waitUntil;
    private final Optional<Customer> currCustomer;
    private final ImList<Customer> queue;
    private final int maxQ;
    private final Supplier<Double> restTime;
    private final boolean idle;

    Server(int id) {
        this(id, DEFAULT_MAXQ);
    }

    Server(int id, int maxQ) {
        this(id, ZERO, Optional.empty(), ImList.<Customer>of(), maxQ);
    }

    Server(int id, int maxQ, ImList<Customer> queue) {
        this(id, ZERO, Optional.empty(), queue, maxQ);
    }

    Server(int id, int maxQ, Supplier<Double> restTime) {
        this(id, ZERO, Optional.empty(), ImList.<Customer>of(), maxQ, restTime);
    }

    Server(int id, double waitUntil, Optional<Customer> currCustomer, ImList<Customer> queue,
            int maxQ) {
        this(id, waitUntil, currCustomer, queue, maxQ, () -> ZERO);
    }

    Server(int id, double waitUntil, Optional<Customer> currCustomer, ImList<Customer> queue,
            int maxQ, Supplier<Double> restTime) {
        this(id, waitUntil, currCustomer, queue, maxQ, restTime, true);
    }

    Server(int id, double waitUntil, Optional<Customer> currCustomer, ImList<Customer> queue,
            int maxQ, Supplier<Double> restTime, boolean idle) {
        this.id = id;
        this.waitUntil = waitUntil;
        this.currCustomer = currCustomer;
        this.queue = queue;
        this.maxQ = maxQ;
        this.restTime = restTime;
        this.idle = idle;
    }

    Server serve(Customer customer, double time) {
        return new Server(this.id,
                time,
                Optional.<Customer>of(customer),
                (this.queue.size() != 0) ? this.queue.remove(0).second() : this.queue,
                this.maxQ,
                this.restTime,
                false);
    }

    Server queue(Customer customer) {
        return new Server(this.id,
                this.waitUntil,
                this.currCustomer,
                this.queue.add(customer),
                this.maxQ,
                this.restTime,
                false);
    }

    Server done(Customer customer) {
        return new Server(this.id, 
                (hasQ()) ? this.waitUntil : ZERO,
                Optional.empty(),
                this.queue,
                this.maxQ,
                this.restTime,
                true);
    }

    Server maybeRest() {
        return new Server(this.id,
                this.waitUntil + this.restTime.get(),
                Optional.empty(),
                this.queue,
                this.maxQ,
                this.restTime,
                false);
    }

    int getID() {
        return this.id;
    }

    int getMaxQ() {
        return this.maxQ;
    }

    Optional<Customer> getCurrCustomer() {
        return this.currCustomer;
    }

    ImList<Customer> getQueue() {
        return this.queue;
    }

    Supplier<Double> getRestTime() {
        return this.restTime;
    }

    double getWaitUntil() {
        return this.waitUntil;
    }

    boolean isIdle() {
        return this.idle;
    }

    boolean canQ() {
        return this.queue.size() < this.maxQ;
    }

    boolean hasQ() {
        return this.queue.size() >= 1;
    }

    boolean isSelfCheck() {
        return false;
    }

    @Override
    public String toString() {
        return String.format("%d", id);
    }


}

