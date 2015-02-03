import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by bfelder on 2/1/15.
 */
public class PercolationStats {
    private Percolation perc;
    private double[] runResults;
    private int runCount;
    private int gridCount;
    private double _mean = -1;
    private double _stddev = -1;
    private double _confidenceAddend = -1;

    /**
     * perform T independent experiments on an N-by-N grid
     * @param N
     * @param T
     */
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException();
        gridCount = (int)Math.round(Math.pow(N, 2));
        runCount = T;
        runResults = new double[runCount];
        Random rand = new Random();
        // Run the simulation T times.
        for (int i = 0; i < runCount; i++) {
            perc = new Percolation(N);
            while (! perc.percolates()) {
                // Pick a random site.
                int randomRow = rand.nextInt(N) + 1;
                int randomColumn = rand.nextInt(N) + 1;
                // System.out.println("Random: " + randomRow + " " + randomColumn);
                // Only do this if the site is not already open.
                // Don't want to count re-opens.
                if (!perc.isOpen(randomRow, randomColumn)) {
                    perc.open(randomRow, randomColumn);
                    runResults[i]++;
                }
            }
            // Need to divide each runResult by gridCount,
            // to get the % of the grid that was filled to percolate.
            runResults[i] /= gridCount;
        }
    }

    /**
     * sample mean of percolation threshold
     * @return
     */
    public double mean() {
        if (_mean == -1)
            _mean = StdStats.mean(runResults);
        return _mean;
    }

    /**
     * sample standard deviation of percolation threshold
     * @return
     */
    public double stddev() {
        if (_stddev == -1)
            _stddev = StdStats.stddev(runResults);
        return _stddev;
    }

    protected double confidenceAddend() {
        if (_confidenceAddend == -1) {
            _confidenceAddend = (1.96 * stddev()) / (Math.sqrt(runCount));
        }
        return _confidenceAddend;
    }

    /**
     * low  endpoint of 95% confidence interval
     * @return
     */
    public double confidenceLo() {
        return mean() - confidenceAddend();
    }

    /**
     * high endpoint of 95% confidence interval
     * @return
     */
    public double confidenceHi() {
        return mean() + confidenceAddend();
    }

    public String toString() {
        DecimalFormat df = new DecimalFormat("#.#############");
        String toReturn =  "";
        toReturn +=    "mean                    = " + df.format(this.mean());
        toReturn +=  "\nstddev                  = " + df.format(this.stddev());
        toReturn +=  "\n95% confidence interval = " + df.format(this.confidenceLo()) +
                                               ", " + df.format(this.confidenceHi());
        toReturn += "\n";
        return toReturn;
    }

    /**
     * test client (described below)
     * @param args
     */
    public static void main(String[] args) {
        int gridWidth = Integer.parseInt(args[0]);
        int simCount = Integer.parseInt(args[1]);
        PercolationStats percStats = new PercolationStats(gridWidth, simCount);
        System.out.println(percStats.toString());
    }
}
