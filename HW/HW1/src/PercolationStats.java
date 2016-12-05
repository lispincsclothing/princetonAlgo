import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by student on 11/6/16.
 */
public class PercolationStats {
    private double results[];

    public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
    {
        assertPositive(n);
        assertPositive(trials);
        results = new double[trials];
        runExperiments(n, trials);
    }

    private void assertPositive(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Number has to be larger than 1");
        }
    }

    private void runExperiments(int size, int numExperiments) {
        for (int i = 0; i < numExperiments; i++) {
            results[i] = runExperiment(size);
        }
    }

    private double runExperiment(int size){
        Percolation p = new Percolation(size);
        int openSpaces = 0;
        do {
            int row = random(size);
            int column = random(size);
            if (!p.isOpen(row, column)){
                p.open(row, column);
                openSpaces++;
            }
        } while (!p.percolates());
        return (double) openSpaces / ((double) size * size);
    }

    private int random(int size){
        //Q: Why size + 1
        return StdRandom.uniform(1, size+1);
    }

    public double mean()                          // sample mean of percolation threshold
    {
        return StdStats.mean(results);
    }

    public double stddev()                        // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(results);
    }

    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        return mean() - confidence();
    }

    private double confidence(){
        // TODO: 12/4/16 Why 1.96
        return(1.96 * stddev() / Math.sqrt(results.length));
    }
    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        return mean() + confidence();
    }

    public static void main(String[] args)    // test client (described below)
    {
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean                     =" + stats.mean());
        System.out.println("stddev                   =" + stats.stddev());
        System.out.println("95% confidence interval  =" + stats.confidenceLo() + ", " + stats.confidenceHi());
    }
}
