package cs2030.simulator;

import java.util.Optional;
import java.util.function.Supplier;
import cs2030.util.ImList;
import cs2030.util.Pair;

class SelfCheck extends Server {
    private final ImList<Pair<Pair<Double, Boolean>, Optional<Customer>>> waitCustomers;

    SelfCheck(int id, int maxQ, int numSelfCheck) {
        super(id, maxQ);

        ImList<Pair<Pair<Double, Boolean>, Optional<Customer>>> temp =
            ImList.<Pair<Pair<Double, Boolean>, Optional<Customer>>>of();

        for (int i = 1; i <= numSelfCheck; i++) {
            temp = temp.add(Pair.<Pair<Double, Boolean>,
                    Optional<Customer>>of(Pair.<Double, Boolean>of(0.0, true), Optional.empty()));
        }

        this.waitCustomers = temp;
    }

    SelfCheck(int id, int maxQ, ImList<Pair<Pair<Double, Boolean>,
            Optional<Customer>>> waitCustomers, ImList<Customer> queue) {
        super(id, maxQ, queue);
        this.waitCustomers = waitCustomers;
    }

    SelfCheck(Server server, ImList<Pair<Pair<Double, Boolean>,
            Optional<Customer>>> waitCustomers) {
        super(server.getID(), server.getWaitUntil(), server.getCurrCustomer(),
                server.getQueue(), server.getMaxQ(), server.getRestTime(), server.isIdle());
        this.waitCustomers = waitCustomers;
    }

    double getWaitUntil() {
        int i = 0;
        double minWaitTime = this.waitCustomers.get(0).first().first();
        for (; i < this.waitCustomers.size(); i++) {
            if (this.waitCustomers.get(i).first().first() < minWaitTime) {
                minWaitTime = this.waitCustomers.get(i).first().first();
            }
        }

        return minWaitTime;
    }

    SelfCheck queue(Customer customer) {
        return new SelfCheck(super.queue(customer), this.waitCustomers);
    }

    SelfCheck done(Customer customer) {
        int i = 0;

        while (i < this.waitCustomers.size()) {
            if (this.waitCustomers.get(i).second().equals(Optional.<Customer>of(customer))) {
                break;
            }
            i++;
        }

        ImList<Pair<Pair<Double, Boolean>, Optional<Customer>>> temp =
            waitCustomers.set(i, Pair.<Pair<Double, Boolean>,
                    Optional<Customer>>of(Pair.<Double, Boolean>of(waitCustomers
                            .get(i)
                            .first().first(), true), Optional.empty()));

        return new SelfCheck(super.getID(), super.getMaxQ(), temp, super.getQueue());
    }

    SelfCheck maybeRest() {
        return this;
    }

    SelfCheck serve(Customer customer, double time) {
        int i = 0;

        while (i < this.waitCustomers.size()) {
            if (this.waitCustomers.get(i).first().second() == true) {
                break;
            }
            i++;
        }

        ImList<Pair<Pair<Double, Boolean>, Optional<Customer>>> temp =
            waitCustomers.set(i, Pair.<Pair<Double, Boolean>,
                    Optional<Customer>>of(Pair.<Double, Boolean>of(time, false),
                        Optional.<Customer>of(customer)));

        return new SelfCheck(super.getID(), super.getMaxQ(), temp,
                (super.getQueue().size() != 0) ? super.getQueue().remove(0).second() :
                super.getQueue());
    }

    boolean isIdle() {
        ImList<Pair<Pair<Double, Boolean>, Optional<Customer>>> temp =
            this.waitCustomers.filter(x -> x.first().second() == true);

        return (temp.isEmpty()) ? false : true;
    } 

    boolean isSelfCheck() {
        return true;
    }

    @Override
    public String toString() {
        int i = 0;
        while (i < this.waitCustomers.size()) {
            if (this.waitCustomers.get(i).second().equals(Optional.empty())) {
                break;
            }
            i++;
        }
        int count = (i == this.waitCustomers.size()) ? 0 : i;
        return "self-check " + (super.getID() + count);
    }
} 
