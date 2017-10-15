// default package

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Instantiate an (n+2)*(n+2) WeightedQuickUnionUF to represent the percolation problem.
 * The top and bottom rows are open and connected as the two ends.
 */
public class Percolation {
  private int n;
  private int[][] status;
  private int numOpen;
  private WeightedQuickUnionUF uf; // 0-indexed union-find with size n+2

  public Percolation(int n) throws IllegalArgumentException {
    if (n <= 0) {
      throw new IllegalArgumentException();
    }

    this.n = n;
    this.status = new int[n+2][n+2];
    for (int i=0; i<n+2; i++) {
      this.status[0][i] = 1; // top row
      this.status[n+1][i] = 1; // bottom row
    }
    this.numOpen = 0;
    this.uf = new WeightedQuickUnionUF((n+2)*(n+2));
    for (int i=1; i< n+2; i++) {
      this.uf.union(0, i); // union first row 0 - n+1
      this.uf.union((n+2)*(n+1), (n+2)*(n+1)+i);
    }
  }

  private int ufIndex(int row, int col) {
    return row*(n+2)+col;
  }

  public void open(int row, int col) throws IllegalArgumentException { // row, col are 1-indexed
    if (row <1 || row > this.n || col < 1 || col > this.n) {
      throw new IllegalArgumentException();
    }
    this.status[row][col] = 1;
    this.numOpen += 1;
    if (isOpen(row-1, col)) {
      this.uf.union(ufIndex(row-1, col), ufIndex(row, col));
    }
    if (isOpen(row, col-1)) {
      this.uf.union(ufIndex(row, col-1), ufIndex(row, col));
    }
    if (isOpen(row+1, col)) {
      this.uf.union(ufIndex(row+1, col), ufIndex(row, col));
    }
    if (isOpen(row, col+1)) {
      this.uf.union(ufIndex(row, col+1), ufIndex(row, col));
    }
  }

  public boolean isOpen(int row, int col) {  // is site (row, col) open?
    return this.status[row][col] == 1;
  }

  public boolean isFull(int row, int col) { // is site (row, col) full?
    return this.status[row][col] == 0;
  }

  public int numberOfOpenSites() { // number of open sites
    return this.numOpen;
  }

  public boolean percolates() { // does the system percolate?
    return this.uf.connected(0, (n+2)*(n+2)-1);
  }
}
