import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestSeamCarver {
    private final Path currentRelativePath = Paths.get("");
    private final String workDir = currentRelativePath.toAbsolutePath().toString();
    private static final boolean HORIZONTAL = true;
    private static final boolean VERTICAL = false;

    @Test
    public void testFindSeam() {
        StdOut.println(workDir + "/data/10x10.png");
        Picture picture = new Picture(workDir + "/data/1x8.png");
        SeamCarver carver = new SeamCarver(picture);
        int[] seam = carver.findVerticalSeam();
        printSeam(carver, seam, VERTICAL);
        seam = carver.findHorizontalSeam();
        printSeam(carver, seam, HORIZONTAL);
    }

    @Test
    public void testRemoveSeam() {
        Picture inputImg = new Picture(workDir + "/data/HJocean.png");
        int removeColumns = 200;
        int removeRows = 100;

        StdOut.printf("image is %d columns by %d rows\n", inputImg.width(), inputImg.height());
        SeamCarver sc = new SeamCarver(inputImg);
        sc.picture().save("input.png");

        Stopwatch sw = new Stopwatch();

        for (int i = 0; i < removeRows; i++) {
            int[] horizontalSeam = sc.findHorizontalSeam();
            sc.removeHorizontalSeam(horizontalSeam);
        }

        for (int i = 0; i < removeColumns; i++) {
            int[] verticalSeam = sc.findVerticalSeam();
            sc.removeVerticalSeam(verticalSeam);
        }
        Picture outputImg = sc.picture();
        outputImg.save("carved.png");

        StdOut.printf("new image size is %d columns by %d rows\n", sc.width(), sc.height());

        StdOut.println("Resizing time: " + sw.elapsedTime() + " seconds.");
        inputImg.show();
        outputImg.show();
    }

    private static void printSeam(SeamCarver carver, int[] seam, boolean direction) {
        double totalSeamEnergy = 0.0;

        for (int row = 0; row < carver.height(); row++) {
            for (int col = 0; col < carver.width(); col++) {
                double energy = carver.energy(col, row);
                String marker = " ";
                if ((direction == HORIZONTAL && row == seam[col]) ||
                        (direction == VERTICAL   && col == seam[row])) {
                    marker = "*";
                    totalSeamEnergy += energy;
                }
                StdOut.printf("%7.2f%s ", energy, marker);
            }
            StdOut.println();
        }
        // StdOut.println();
        StdOut.printf("Total energy = %f\n", totalSeamEnergy);
        StdOut.println();
        StdOut.println();
    }
}
