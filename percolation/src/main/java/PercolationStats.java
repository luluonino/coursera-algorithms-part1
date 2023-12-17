import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private static final double CONFIDENCE_95 = 1.96;
  private final int n;
  private final int trials;
  private final double[] sample;
  private double mean;
  private double stddev;
  private double confidenceLo;
  private double confidenceHi;

  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException();
    }
    this.n = n;
    this.trials = trials;
    this.sample = new double[trials];
    this.doExperiment();
  }

  private void doExperiment() {
    for (int i = 0; i < this.trials; i++) {
      Percolation percolation = new Percolation(this.n);
      double threshold = 0.;
      while (true) {
        int row = StdRandom.uniformInt(this.n) + 1;
        int col = StdRandom.uniformInt(this.n) + 1;
        if (percolation.isOpen(row, col)) { // don't reopen open ones
          continue;
        }
        percolation.open(row, col);
        threshold += 1.;
        if (percolation.percolates()) {
          this.sample[i] = threshold / (this.n * this.n);
          break;
        }
      }
    }
    this.mean = StdStats.mean(this.sample);
    this.stddev = StdStats.stddev(this.sample);
    this.confidenceLo =
            this.mean - this.stddev * CONFIDENCE_95 / Math.sqrt(this.trials);
    this.confidenceHi =
            this.mean + this.stddev * CONFIDENCE_95 / Math.sqrt(this.trials);
  }

  public double mean() {
    return this.mean;
  }

  public double stddev() {
    return this.stddev;
  }

  public double confidenceLo() {
    return this.confidenceLo;
  }

  public double confidenceHi() {
    return this.confidenceHi;
  }

  public static void main(String[] args) {
    int n = 0;
    int trials = 0;
    if (args.length == 2) {
      n = Integer.parseInt(args[0]);
      trials = Integer.parseInt(args[1]);
    }

    PercolationStats percolationStats = new PercolationStats(n, trials);
    System.out.println(
            "mean                    = " + percolationStats.mean()
    );
    System.out.println(
            "stddev                  = " + percolationStats.stddev()
    );
    System.out.println("95% confidence interval = ["
      + percolationStats.confidenceLo() + ", "
      + percolationStats.confidenceHi() + "]");
  }

}
