package pl.ryszardszwajlik.research.gcs.performance.stats;

public class Stat {

    private long sum = 0;
    private long counter = 0;

    public synchronized void add(long val) {
        sum += val;
        counter++;
    }

    public synchronized double avg() {
        return (double)sum / counter;
    }
}
