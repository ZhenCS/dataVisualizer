package algorithms;

import java.util.concurrent.atomic.AtomicBoolean;

public class RandomCluster extends Cluster {

    private DataSet dataset;

    private int maxIterations;
    private int updateInterval;

    // currently, this value does not change after instantiation
    private AtomicBoolean tocontinue;

    public RandomCluster(DataSet dataset,
                            int maxIterations,
                            int updateInterval,
                            boolean toContinue) {
        this.dataset = dataset;
        this.maxIterations = maxIterations;
        this.updateInterval = updateInterval;
        tocontinue = new AtomicBoolean(toContinue);
    }

    @Override
    public int getMaxIterations() {
        return maxIterations;
    }

    @Override
    public int getUpdateInterval() {
        return updateInterval;
    }

    @Override
    public boolean tocontinue() {
        return tocontinue.get();
    }

    @Override
    public void setMaxIterations(int iterations) {
        maxIterations = iterations;
    }

    @Override
    public void setUpdateInterval(int interval) {
        updateInterval = interval;
    }

    @Override
    public void setToContinue(boolean toContinue) {
        tocontinue = new AtomicBoolean(toContinue);
    }

    @Override
    public void run() {

    }
}
