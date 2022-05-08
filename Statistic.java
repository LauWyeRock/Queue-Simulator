package cs2030.simulator;

class Statistic {
    private final double waitTime;
    private final int numServed;
    private final int numLeft;

    Statistic() {
        this.waitTime = 0;
        this.numServed = 0;
        this.numLeft = 0;
    }

    Statistic(double waitTime, int numServed, int numLeft) {
        this.waitTime = waitTime;
        this.numServed = numServed;
        this.numLeft = numLeft;
    }

    public Statistic addServed() {
        return new Statistic(this.waitTime, this.numServed + 1, this.numLeft);
    }

    public Statistic addLeft() {
        return new Statistic(this.waitTime, this.numServed, this.numLeft + 1);
    }

    public Statistic addWait(double wait) {
        return new Statistic(this.waitTime + wait, this.numServed, this.numLeft);
    }

    @Override
    public String toString() {
        return String.format("[%.3f %d %d]", this.waitTime / this.numServed, 
                this.numServed, this.numLeft);
    }
}
