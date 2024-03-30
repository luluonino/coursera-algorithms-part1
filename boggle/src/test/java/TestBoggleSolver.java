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
    public void testGetAllValidWords() {
        In in = new In(workDir + "/data/dictionary-yawl.txt");
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);

        BoggleBoard board = new BoggleBoard(workDir + "/data/board-points100.txt");

        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
