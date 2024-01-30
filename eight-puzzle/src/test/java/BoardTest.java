import static org.junit.jupiter.api.Assertions.*;

import edu.princeton.cs.algs4.StdOut;
import org.junit.jupiter.api.Test;

public class BoardTest {

    private static final int[][] goalTiles = new int[][] {
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 0}
    };
    private static final Board testBoard0 = new Board(goalTiles);

    private static final int[][] testTiles1 = new int[][] {
            {8, 3, 5},
            {2, 7, 1},
            {0, 4, 6}
    };
    private static final Board testBoard1 = new Board(testTiles1);

    private static final int[][] testTiles2 = new int[][] {
            {8, 3, 5},
            {2, 0, 1},
            {7, 4, 6}
    };
    private static final Board testBoard2 = new Board(testTiles2);

    private static final int[][] testTiles4by4 = new int[][] {
            {0, 3, 5, 9},
            {12, 7, 10, 1},
            {8, 4, 15, 2},
            {6, 11, 13, 14}
    };
    private static final Board testBoard4by4 = new Board(testTiles4by4);


    @Test
    public void testToString() {
        System.out.print(testBoard1);
        System.out.print(testBoard4by4);
    }

    @Test
    public void testHamming() {
        assertEquals(0, testBoard0.hamming());
        assertEquals(8, testBoard1.hamming());
        assertEquals(15, testBoard4by4.hamming());
    }

    @Test
    public void testMahattan() {
        assertEquals(0, testBoard0.manhattan());
        assertEquals(16, testBoard1.manhattan());
    }

    @Test
    public void testIsGoal() {
        assertTrue(testBoard0.isGoal());
        assertFalse(testBoard1.isGoal());
        assertFalse(testBoard4by4.isGoal());
    }

    @Test
    public void testEquals() {
        assertFalse(testBoard0.equals(null));
        assertTrue(testBoard0.equals(new Board(goalTiles)));
        assertFalse(testBoard0.equals(new Board(testTiles1)));
        assertFalse(testBoard1.equals(new Board(goalTiles)));
        assertFalse(testBoard4by4.equals(new Board(goalTiles)));
        assertTrue(testBoard4by4.equals(new Board(testTiles4by4)));
        assertFalse(testBoard0.equals("Not a board"));
    }

    @Test
    public void testNeighbors() {
        for (Board neighbor: testBoard0.neighbors()) {
            StdOut.print(neighbor);
        }
        for (Board neighbor: testBoard1.neighbors()) {
            StdOut.print(neighbor);
        }
        for (Board neighbor: testBoard2.neighbors()) {
            StdOut.print(neighbor);
        }
        for (Board neighbor: testBoard4by4.neighbors()) {
            StdOut.print(neighbor);
        }
    }

    @Test
    public void testTwin() {
        StdOut.print(testBoard0.twin());
        assertEquals(testBoard0.twin(), testBoard0.twin());
        StdOut.print(testBoard4by4.twin());
    }
}
