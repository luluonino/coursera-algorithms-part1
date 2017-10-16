import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Instantiate an (n+2)*(n+2) WeightedQuickUnionUF to represent the percolation problem.
 * The top and bottom rows are open and connected as the two ends.
 */
public class Percolation {
  private final int n;
  private final boolean[][] status;
  private int numOpen;
  private final WeightedQuickUnionUF uf; // 0-indexed union-find with size n+2
  private final WeightedQuickUnionUF ufFullness;

  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException();
    }

    this.n = n;
    this.status = new boolean[n+2][n+2];
    for (int i = 0; i < n+2; i++) {
      this.status[0][i] = true; // top row
      this.status[n+1][i] = true; // bottom row
    }
    this.numOpen = 0;
    this.uf = new WeightedQuickUnionUF((n+2)*(n+2));
    this.ufFullness = new WeightedQuickUnionUF((n+2)*(n+2));
    for (int i = 1; i < n+2; i++) {
      this.uf.union(ufIndex(0, 0), ufIndex(0, i));
      this.uf.union(ufIndex(n+1, 0), ufIndex(n+1, i));
      this.ufFullness.union(ufIndex(0, 0), ufIndex(0, i));
    }
  }

  private int ufIndex(int row, int col) {
    return row*(n+2)+col;
  }

  public void open(int row, int col) { // row, col are 1-indexed
    if (row < 1 || row > this.n || col < 1 || col > this.n) {
      throw new IllegalArgumentException();
    }

    if (this.isOpen(row, col)) {
      return;
    }

    this.status[row][col] = true;
    this.numOpen += 1;

    if (row == 1) {
      this.uf.union(ufIndex(0, col), ufIndex(row, col));
      this.ufFullness.union(ufIndex(0, col), ufIndex(row, col));
    }
    else if (isOpen(row-1, col)) {
      this.uf.union(ufIndex(row-1, col), ufIndex(row, col));
      this.ufFullness.union(ufIndex(row-1, col), ufIndex(row, col));
    }

    if (row == n) {
      this.uf.union(ufIndex(n+1, col), ufIndex(row, col));
    }
    else if (isOpen(row+1, col)) {
      this.uf.union(ufIndex(row+1, col), ufIndex(row, col));
      this.ufFullness.union(ufIndex(row+1, col), ufIndex(row, col));
    }

    if (col > 1 && isOpen(row, col-1)) {
      this.uf.union(ufIndex(row, col-1), ufIndex(row, col));
      this.ufFullness.union(ufIndex(row, col-1), ufIndex(row, col));
    }
    if (col < n && isOpen(row, col+1)) {
      this.uf.union(ufIndex(row, col+1), ufIndex(row, col));
      this.ufFullness.union(ufIndex(row, col+1), ufIndex(row, col));
    }
  }

  public boolean isOpen(int row, int col) {  // is site (row, col) open?
    if (row < 1 || row > this.n || col < 1 || col > this.n) {
      throw new IllegalArgumentException();
    }
    return this.status[row][col];
  }

  public boolean isFull(int row, int col) { // is site (row, col) full?
    if (row < 1 || row > this.n || col < 1 || col > this.n) {
      throw new IllegalArgumentException();
    }
    return this.ufFullness.connected(ufIndex(0, 0), ufIndex(row, col));
  }

  public int numberOfOpenSites() { // number of open sites
    return this.numOpen;
  }

  public boolean percolates() { // does the system percolate?
    return this.uf.connected(0, (n+2)*(n+2)-1);
  }
}
