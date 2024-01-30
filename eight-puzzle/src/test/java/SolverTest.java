import static org.junit.jupiter.api.Assertions.*;

import edu.princeton.cs.algs4.StdOut;
import org.junit.jupiter.api.Test;


public class SolverTest {

    private static final int[][] goalTiles = new int[][] {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
    };
    private static final Board testBoard0 = new Board(goalTiles);

    private static final int[][] testTiles1 = new int[][] {
            {1, 0, 3},
            {4, 2, 5},
            {7, 8, 6}
    };
    private static final Board testBoard1 = new Board(testTiles1);

    private static final int[][] testTiles2 = new int[][] {
            {1, 2, 3},
            {4, 5, 6},
            {8, 7, 0}
    };
    private static final Board testBoard2 = new Board(testTiles2);

    @Test
    public void testSolver1() {
        Solver solver = new Solver(testBoard0);
        assertEquals(0, solver.moves());
        for(Board board: solver.solution()) {
            StdOut.print(board);
        }
    }

    @Test
    public void testSolver2() {
        Solver solver = new Solver(testBoard1);
        assertEquals(3, solver.moves());
        for(Board board: solver.solution()) {
            StdOut.print(board);
        }
    }

    @Test
    public void testIsSolvable() {
        Solver solver = new Solver(testBoard2);
        assertFalse(solver.isSolvable());
        assertEquals(-1, solver.moves());
        assertNull(solver.solution());
    }
}
