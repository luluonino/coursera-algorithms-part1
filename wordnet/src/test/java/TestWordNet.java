import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestWordNet {
    private final Path currentRelativePath = Paths.get("");
    private final String workDir = currentRelativePath.toAbsolutePath().toString();

    @Test
    public void testConstructor() {
        assertThrows(
                IllegalArgumentException.class, () -> {
                    WordNet wordNet3 = new WordNet(
                            workDir + "/data/synsets3.txt",
                            workDir + "/data/hypernyms3InvalidCycle.txt"
                    );
                }
        );

        assertThrows(
                IllegalArgumentException.class, () -> {
                    WordNet wordNet3 = new WordNet(
                            workDir + "/data/synsets3.txt",
                            workDir + "/data/hypernyms3InvalidTwoRoots.txt"
                    );
                }
        );

        assertThrows(
                IllegalArgumentException.class, () -> {
                    WordNet wordNet6 = new WordNet(
                            workDir + "/data/synsets6.txt",
                            workDir + "/data/hypernyms6InvalidCycle+Path.txt"
                    );
                }
        );

        assertThrows(
                IllegalArgumentException.class, () -> {
                    WordNet wordNet6 = new WordNet(
                            workDir + "/data/synsets6.txt",
                            workDir + "/data/hypernyms6InvalidTwoRoots.txt"
                    );
                }
        );

        WordNet wordNet1000 = new WordNet(
                workDir + "/data/synsets1000-subgraph.txt",
                workDir + "/data/hypernyms1000-subgraph.txt"
        );
        assertTrue(wordNet1000.isNoun("acyl"));
        assertFalse(wordNet1000.isNoun("there is no way this is in the list"));

        WordNet wordNet = new WordNet(
                workDir + "/data/synsets.txt",
                workDir + "/data/hypernyms.txt"
        );
    }

    @Test
    public void testIsNoun() {
        WordNet wordNet1000 = new WordNet(
                workDir + "/data/synsets1000-subgraph.txt",
                workDir + "/data/hypernyms1000-subgraph.txt"
        );

        assertTrue(wordNet1000.isNoun("tendon_of_Achilles"));
        assertTrue(wordNet1000.isNoun("vinyl"));
        assertFalse(wordNet1000.isNoun("tendon of Achilles"));
        assertFalse(wordNet1000.isNoun("there is no way this is in the list"));
    }

    @Test
    public void testDist() {
        WordNet wordNet15 = new WordNet(
                workDir + "/data/synsets15.txt",
                workDir + "/data/hypernyms15Tree.txt"
        );

        assertEquals(2, wordNet15.distance("b", "c"));
        assertEquals(3, wordNet15.distance("c", "e"));
        assertEquals(4, wordNet15.distance("m", "o"));
    }

    @Test
    public void testSap() {
        WordNet wordNet15 = new WordNet(
                workDir + "/data/synsets15.txt",
                workDir + "/data/hypernyms15Tree.txt"
        );

        assertEquals("a", wordNet15.sap("b", "c"));
        assertEquals("a", wordNet15.sap("c", "e"));
        assertEquals("k", wordNet15.sap("m", "o"));
    }
}
