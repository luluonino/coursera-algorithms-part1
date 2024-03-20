import edu.princeton.cs.algs4.StdOut;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestBaseballElimination {

    private final Path currentRelativePath = Paths.get("");
    private final String workDir = currentRelativePath.toAbsolutePath().toString();

    @Test
    public void TestConstructor() {
        StdOut.println(workDir);
        BaseballElimination be = new BaseballElimination(workDir + "/data/teams5.txt");
    }
}
