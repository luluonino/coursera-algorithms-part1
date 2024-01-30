import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Board {
    private final int[][] tiles;
    private final int n;
    private int hamming = -1;
    private int manhattan = -1;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(final int[][] tiles) {
        this.n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(tiles[i], 0, this.tiles[i], 0, n);
        }
    }

    // string representation of this board
    public String toString() {
        int largest = this.n * this.n - 1;
        int lengthN2 = String.valueOf(largest).length();
        int lengthN = String.valueOf(this.n).length();
        StringBuilder builder = new StringBuilder();
        builder.append(this.n);
        for (int i = 0; i < n; i++) {
            builder.append("\n");
            builder.append(" ".repeat(lengthN));
            for (int j = 0; j < n; j++) {
                builder.append(
                    String.format("%-" + (lengthN2 + 2) + "d", this.tiles[i][j])
                );
            }
        }
        builder.append("\n");
        return builder.toString();
    }

    // board dimension n
    public int dimension() {
        return this.n;
    }

    // number of tiles out of place
    public int hamming() {
        if (this.hamming != -1) return this.hamming;
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != 0
                    && n * i + j + 1 != this.tiles[i][j])
                    count++;
            }
        }
        this.hamming = count;
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (this.manhattan != -1) return this.manhattan;
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] == 0) continue;
                int goalCol = (this.tiles[i][j] - 1) % n;
                int goalRow = (this.tiles[i][j] - 1) / n;
                count += Math.abs(goalRow - i) + Math.abs(goalCol - j);
            }
        }
        this.manhattan = count;
        return count;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.manhattan() == 0;
    }

    // does this board equal y?
    @Override
    public boolean equals(final Object y) {
        if (y == null) return false;
        Board that;
        try {
            that = (Board) y;
        } catch (ClassCastException e) {
            return false;
        }
        return Arrays.deepEquals(this.tiles, that.tiles);
    }

//    @Override
//    public int hashCode() {
//        return this.toString().hashCode();
//    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<>();
        int rowBlank = 0;
        int colBlank = 0;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.tiles[i][j] == 0) {
                    rowBlank = i;
                    colBlank = j;
                    break;
                }
            }
        }
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 && j != 0) continue;
                if (i == 0 && j == 0) continue;
                int iNeighbor = rowBlank + i;
                int jNeighbor = colBlank + j;
                if (iNeighbor == -1
                    || iNeighbor == this.n
                    || jNeighbor == -1
                    || jNeighbor == this.n
                ) continue;
                int[][] newTiles = new int[this.n][this.n];
                for (int k = 0; k < this.n; k++) {
                    System.arraycopy(this.tiles[k], 0, newTiles[k], 0, this.n);
                }
                newTiles[rowBlank][colBlank] = newTiles[iNeighbor][jNeighbor];
                newTiles[iNeighbor][jNeighbor] = 0;
                neighbors.add(new Board(newTiles));
            }
        }
        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] newTiles = new int[this.n][this.n];
        for (int i = 0; i < this.n; i++) {
            System.arraycopy(this.tiles[i], 0, newTiles[i], 0, this.n);
        }
        int row1 = 0;
        int col1 = 0;
        int row2 = 0;
        int col2 = 1;

        // always swap [0,0], [0, 1], or [1, 0], [1, 1]
        if (newTiles[row1][col1] == 0 || newTiles[row2][col2] == 0) {
            row1 = 1;
            row2 = 1;
        }

        int temp = newTiles[row1][col1];
        newTiles[row1][col1] = newTiles[row2][col2];
        newTiles[row2][col2] = temp;
        return new Board(newTiles);
    }
}
