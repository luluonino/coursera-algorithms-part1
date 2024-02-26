import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class TestSAP {
    private final Path currentRelativePath = Paths.get("");
    private final String workDir = currentRelativePath.toAbsolutePath().toString();

    @Test
    public void testLength1() {
        In in = new In(workDir + "/data/digraph1.txt");
        Digraph digraph = new Digraph(in);

        SAP sap = new SAP(digraph);
        assertEquals(-1, sap.length(0, 6));
        assertEquals(-1, sap.length(11, 6));
        assertEquals(2, sap.length(1, 2));
        assertEquals(2, sap.length(3, 5));
        assertEquals(3, sap.length(3, 2));
        assertEquals(5, sap.length(8, 12));
        assertEquals(5, sap.length(8, 12));

        assertEquals(-1, sap.length(List.of(0), List.of(6)));
        assertEquals(2, sap.length(List.of(1), List.of(2)));
        assertEquals(3, sap.length(List.of(3, 4), List.of(2)));
        assertEquals(3, sap.length(List.of(9, 11), List.of(4)));
        assertEquals(3, sap.length(List.of(11, 9), List.of(4)));
        assertEquals(0, sap.length(List.of(11, 9), List.of(4, 9)));
    }

    @Test
    public void testAncestor1() {
        In in = new In(workDir + "/data/digraph1.txt");
        Digraph digraph = new Digraph(in);

        SAP sap = new SAP(digraph);
        assertEquals(-1, sap.ancestor(0, 6));
        assertEquals(-1, sap.ancestor(11, 6));
        assertEquals(0, sap.ancestor(1, 2));
        assertEquals(1, sap.ancestor(3, 5));
        assertEquals(0, sap.ancestor(3, 2));
        assertEquals(1, sap.ancestor(8, 12));

        assertEquals(-1, sap.ancestor(List.of(0), List.of(6)));
        assertEquals(0, sap.ancestor(List.of(1), List.of(2)));
        assertEquals(0, sap.ancestor(List.of(3, 4), List.of(2)));
        assertEquals(1, sap.ancestor(List.of(9, 11), List.of(4)));
        assertEquals(9, sap.ancestor(List.of(9, 11), List.of(4, 9)));
    }
}
