package cs2030.util; 

import java.util.PriorityQueue;
import java.util.Comparator;

public class PQ<T> {
    private static final int initialCap = 1; 
    private final PriorityQueue<T> pq;

    public PQ(Comparator<? super T> cmp) {
        this.pq = new PriorityQueue<T>(initialCap, cmp);
    }

    public PQ(PriorityQueue<T> newpq) {
        this.pq = newpq; 
    }

    public PQ(PQ<T> newpq) {
        this.pq = newpq.getInnerPQ();
    } 

    public PriorityQueue<T> getInnerPQ() {
        return this.pq;
    } 

    public PQ<T> add(T elem) {
        PriorityQueue<T> newpq = new PriorityQueue<T>(pq);
        newpq.add(elem);
        return new PQ<T>(newpq); 
    }

    public boolean isEmpty() {
        return pq.size() == 0;
    }

    public Pair<T, PQ<T>> poll() {
        PQ<T> newpq = new PQ<T>(new PriorityQueue<T>(pq));
        T elem = newpq.pq.poll(); 
        return Pair.<T, PQ<T>>of(elem, newpq);
    }     

    @Override
    public String toString() {
        return pq.toString();
    }
}
