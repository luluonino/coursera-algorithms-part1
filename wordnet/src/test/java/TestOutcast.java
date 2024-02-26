import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestOutcast {
    private final Path currentRelativePath = Paths.get("");
    private final String workDir = currentRelativePath.toAbsolutePath().toString();

    @Test
    public void testOutcast() {
        WordNet wordNet = new WordNet(
                workDir + "/data/synsets.txt",
                workDir + "/data/hypernyms.txt"
        );

        Outcast outcast = new Outcast(wordNet);

        assertEquals(
            "table",
            outcast.outcast(
                new String[] {"horse", "zebra", "cat", "bear", "table"}
            )
        );
        assertEquals(
            "bed",
            outcast.outcast(
                new String[] {"water", "soda", "bed", "orange_juice", "milk", "apple_juice", "tea", "coffee"}
            )
        );
    }
}
