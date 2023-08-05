/* *****************************************************************************
 *  Name:              Shakar Ram.M
 *  Last modified:     18/07/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int t;
    private double[] p;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("N and trials must be greater than 0");
        t = trials;
        p = new double[t];
        for (int i = 0; i < t; i++) {
            Percolation obj = new Percolation(n);

            while (!obj.percolates()) {

                int randomRow = StdRandom.uniform(1, n + 1);
                int randomCol = StdRandom.uniform(1, n + 1);
                obj.open(randomRow, randomCol);

            }


            p[i] = (double) obj.numberOfOpenSites() / (n * n);


        }


    }

    public double mean() {

        return StdStats.mean(p);
    }

    public double stddev() {
        return StdStats.stddev(p);

    }

    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(t));
    }

    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(t));
    }

    public static void main(String[] args) {
        int gridSize = 5;
        int trialNo = 5;
        if (args.length >= 2) {
            gridSize = Integer.parseInt(args[0]);
            trialNo = Integer.parseInt(args[1]);
        }

        PercolationStats stat = new PercolationStats(gridSize, trialNo);
        System.out.println("mean                             =" + stat.mean());
        System.out.println("stddev                           =" + stat.stddev());
        System.out.println(
                "95% confidence interval          =" + "[" + stat.confidenceLo() + ", " + stat
                        .confidenceHi() + "]");

    }
}
