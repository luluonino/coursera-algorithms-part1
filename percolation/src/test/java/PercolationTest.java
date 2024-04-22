import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PercolationTest {
    private Percolation percolation;
    private int SIZE = 100;

    public PercolationTest() {
        this.percolation = new Percolation(SIZE);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, percolation.numberOfOpenSites());
        assertEquals(false, percolation.percolates());
        assertEquals(false, percolation.percolates());
    }

    @Test
    public void testOpen() {
        this.percolation.open(1, 1);
        assertEquals(true, percolation.isOpen(1, 1));
        assertEquals(false, percolation.isOpen(1, 2));
    }

    @Test
    public void testPercolates() {
        for (int i = 1; i <= SIZE; i++) {
            this.percolation.open(i, 1);
        }
        assertEquals(true, this.percolation.percolates());
    }
}
