package cs2030.simulator;

import java.util.function.Supplier;
import cs2030.util.Lazy;

class Customer {
    private static final double DEFAULT_SERVICE_TIME = 1.0;
    private final int id;
    private final Supplier<Double> arrivalTime;
    private final Lazy<Double> serviceTime;

    Customer(int id, double arrivalTime) {
        this.id = id;
        this.arrivalTime = () -> arrivalTime;
        this.serviceTime = Lazy.<Double>of(() -> DEFAULT_SERVICE_TIME);
    }

    Customer(int id, double arrivalTime, Supplier<Double> serviceTime) {
        this.id = id;
        this.arrivalTime = () -> arrivalTime;
        this.serviceTime = Lazy.<Double>of(serviceTime);
    }

    Customer(int id, double arrivalTime, Lazy<Double> serviceTime) {
        this.id = id;
        this.arrivalTime = () -> arrivalTime;
        this.serviceTime = serviceTime;
    }

    Customer(int id, Supplier<Double> arrivalTime, Lazy<Double> serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    Customer wait(Supplier<Double> waitUntil) {
        return new Customer(this.id, waitUntil, this.serviceTime);
    }

    int getID() {
        return id;
    }

    double getFinishTime() {
        return this.arrivalTime.get() + this.serviceTime.get();
    }

    @Override
    public String toString() {
        return String.format("%d", id);
    }
} 
