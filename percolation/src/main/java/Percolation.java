import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Instantiate an (n+2)*(n+2) WeightedQuickUnionUF to represent the percolation
 * problem. The top and bottom rows are open and connected as the two ends.
 */
public class Percolation {
    private final int n;
    private final boolean[][] status;
    private int numOpen;
    private final WeightedQuickUnionUF uf; // 0-indexed union-find with size n+2
    private final WeightedQuickUnionUF ufFullness;
    // fullness concerns only the top row

    /**
     * Create n-by-n grid, with all sites blocked.
     * @param n the size of the grid
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.n = n;
        this.status = new boolean[n + 2][n + 2];
        for (int i = 0; i < n + 2; i++) {
            this.status[0][i] = true; // top row
            this.status[n + 1][i] = true; // bottom row
        }
        this.numOpen = 0;
        this.uf = new WeightedQuickUnionUF((n + 2) * (n + 2));
        this.ufFullness = new WeightedQuickUnionUF((n + 2) * (n + 2));
        for (int i = 1; i < n + 2; i++) {
            this.uf.union(ufIndex(0, 0), ufIndex(0, i));
            this.uf.union(ufIndex(n + 1, 0), ufIndex(n + 1, i));
            this.ufFullness.union(ufIndex(0, 0), ufIndex(0, i));
        }
    }

    /**
     * Convert 1-indexed row and col to 0-indexed union-find index.
     * @param row 1-indexed row
     * @param col 1-indexed col
     * @return 0-indexed union-find index
     */
    private int ufIndex(int row, int col) {
        return row * (this.n + 2) + col;
    }

    /**
     * Open site (row, col) if it is not open already.
     * @param row 1-indexed row
     * @param col 1-indexed col
     */
    public void open(int row, int col) {
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
        } else if (isOpen(row - 1, col)) {
            this.uf.union(ufIndex(row - 1, col), ufIndex(row, col));
            this.ufFullness.union(ufIndex(row - 1, col), ufIndex(row, col));
        }

        if (row == n) {
            this.uf.union(ufIndex(n + 1, col), ufIndex(row, col));
        } else if (isOpen(row + 1, col)) {
            this.uf.union(ufIndex(row + 1, col), ufIndex(row, col));
            this.ufFullness.union(ufIndex(row + 1, col), ufIndex(row, col));
        }

        if (col > 1 && isOpen(row, col - 1)) {
            this.uf.union(ufIndex(row, col - 1), ufIndex(row, col));
            this.ufFullness.union(ufIndex(row, col - 1), ufIndex(row, col));
        }
        if (col < n && isOpen(row, col + 1)) {
            this.uf.union(ufIndex(row, col + 1), ufIndex(row, col));
            this.ufFullness.union(ufIndex(row, col + 1), ufIndex(row, col));
        }
    }

    /**
     * Check if site (row, col) is open.
     * @param row 1-indexed row
     * @param col 1-indexed col
     * @return true if site is open, false otherwise
     */
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > this.n || col < 1 || col > this.n) {
            throw new IllegalArgumentException();
        }
        return this.status[row][col];
    }

    /**
     * Check if site (row, col) is full, i.e. connected to row 1,
     * or row 0 since row 0 is all connected
     * @param row 1-indexed row
     * @param col 1-indexed col
     * @return true if site is full, false otherwise
     */
    public boolean isFull(int row, int col) {
        if (row < 1 || row > this.n || col < 1 || col > this.n) {
            throw new IllegalArgumentException();
        }
        return this.ufFullness.find(ufIndex(0, 0)) == this.ufFullness.find(ufIndex(row, col));
    }

    /**
     * Number of open sites.
     * @return number of open sites
     */
    public int numberOfOpenSites() { // number of open sites
        return this.numOpen;
    }

    /**
     * Check if the system percolates.
     * @return true if the system percolates, false otherwise
     */
    public boolean percolates() { // does the system percolate?
        return this.uf.find(0) == this.uf.find((n + 2) * (n + 2) - 1);
    }
}
