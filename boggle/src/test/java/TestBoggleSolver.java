import static org.junit.jupiter.api.Assertions.*;

import edu.princeton.cs.algs4.StdOut;
import org.junit.jupiter.api.Test;
import edu.princeton.cs.algs4.In;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestBoggleSolver {
    private final Path currentRelativePath = Paths.get("");
    private final String workDir = currentRelativePath.toAbsolutePath().toString();

    @Test
    public void testConstructor() {
        In in = new In(workDir + "/data/dictionary-algs4.txt");
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
    }

    @Test
    public void testScoreOf() {
        In in = new In(workDir + "/data/dictionary-algs4.txt");
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        assertEquals(0, solver.scoreOf(null));
        assertEquals(0, solver.scoreOf(""));
        assertEquals(0, solver.scoreOf("NOT_IN_DICTIONARY"));
        assertEquals(0, solver.scoreOf("NO"));
        assertEquals(1, solver.scoreOf("ADD"));
        assertEquals(1, solver.scoreOf("ABLE"));
        assertEquals(2, solver.scoreOf("YIELD"));
        assertEquals(3, solver.scoreOf("WEIGHT"));
        assertEquals(5, solver.scoreOf("VOLCANO"));
        assertEquals(11, solver.scoreOf("TRIANGLE"));
        assertEquals(11, solver.scoreOf("IMPOSSIBLE"));
    }

    @Test
    public void testGetAllValidWords1() {
        In in = new In(workDir + "/data/dictionary-algs4.txt");
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);

        BoggleBoard board = new BoggleBoard(workDir + "/data/board-q.txt");

        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }

    @Test
    public void testGetAllValidWords2() {
        In in = new In(workDir + "/data/dictionary-yawl.txt");
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);

        int[] expectedScores = {
                0, 1000, 100, 1111, 1250, 13464, 1500, 1, 2000, 200,
                26539, 2, 300, 3, 400, 4410, 4527, 4540, 4, 500, 5, 750, 777
        };

        long startTime = System.nanoTime();

        for (int expected: expectedScores) {
            BoggleBoard board = new BoggleBoard(workDir + String.format("/data/board-points%d.txt", expected));

            int score = 0;
            for (String word : solver.getAllValidWords(board)) {
                score += solver.scoreOf(word);
            }
            assertEquals(expected, score);
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
        StdOut.println(duration);
    }
}
