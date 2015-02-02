import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by bfelder on 2/1/15.
 */
public class PercolationStats {
    private Percolation perc;
    private int[] runResults;
    private int runCount;
    private int gridCount;
    private int runTotal;
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
        runTotal = 0;
        runResults = new int[runCount];
        Random rand = new Random();
        // Run the simulation T times.
        for (int i = 0; i < runCount; i++) {
            perc = new Percolation(N);
            while (! perc.percolates()) {
                // Pick a random site.
                int randomRow = rand.nextInt(N) + 1;
                int randomColumn = rand.nextInt(N) + 1;
                System.out.println("Random: " + randomRow + " " + randomColumn);
                // Only do this if the site is not already open.
                // Don't want to count re-opens.
                if (!perc.isOpen(randomRow, randomColumn)) {
                    perc.open(randomRow, randomColumn);
                    runResults[i]++;
                    runTotal++;
                }
            }
        }

    }

    /**
     * sample mean of percolation threshold
     * @return
     */
    public double mean() {
        // What was the average number of opens needed to percolate?
        double toReturn = runTotal / runCount;
        // What was the percentage of the number of squares to the total
        // squares in the grid?
        toReturn /= gridCount;

        return toReturn;
    }

    /**
     * sample standard deviation of percolation threshold
     * @return
     */
    public double stddev() {
        return 1;
    }

    /**
     * low  endpoint of 95% confidence interval
     * @return
     */
    public double confidenceLo() {
        return 1;
    }

    /**
     * high endpoint of 95% confidence interval
     * @return
     */
    public double confidenceHi() {
        return 1;
    }

    public String toString() {
        DecimalFormat df = new DecimalFormat("#.########");
        String toReturn =  "mean = " + df.format(this.mean());
        toReturn += "\nrunCount = " + this.runCount;
        toReturn += "\ngridCount = " + this.gridCount;
        toReturn += "\nrunTotal = " + this.runTotal;
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
