import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private static final double BORDER_ENERGY = 1000.;
    private int width;
    private int height;
    private int[][] rgb;
    private double[][] energy;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException();

        this.width = picture.width();
        this.height = picture.height();

        this.rgb = new int[this.width][this.height];
        this.energy = new double[this.width][this.height];

        // get the colors
        for (int col = 0; col < this.width; col++) {
            for (int row = 0; row < this.height; row++) {
                int rgb = picture.getRGB(col, row);
                this.rgb[col][row] = rgb;
            }
        }

        // compute the energy map
        this.computeEnergy();
    }

    // current picture
    public Picture picture() {
        Picture picture = new Picture(this.width, this.height);
        for (int col = 0; col < this.width; col++) {
            for (int row = 0; row < this.height; row++) {
                int argb = (255 << 24) | this.rgb[col][row];
                picture.setRGB(col, row, argb);
            }
        }
        return picture;
    }

    // width of current picture
    public int width() {
        return this.width;
    }

    // height of current picture
    public int height() {
        return this.height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (!validateIndex(x, y))
            throw new IllegalArgumentException("index out of range");
        return this.energy[x][y];
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int[] seam = new int[this.width];
        for (int k = 0; k < seam.length; k++) {
            seam[k] = 0;
        }
        // all pixels on border
        if (this.height <= 2 || this.width <= 2) {
            return seam;
        }

        // initialize path
        final double[][] distTo = new double[this.width][this.height];
        final int[][] pixelTo = new int[this.width][this.height];
        for (int col = 0; col < this.width; col++) {
            for (int row = 0; row < this.height; row++) {
                if (col == 0) distTo[col][row] = this.energy[col][row];
                else distTo[col][row] = Double.POSITIVE_INFINITY;
                pixelTo[col][row] = -1;
            }
        }
        // only look at pixels not on the boundary
        for (int col = 1; col < this.width - 1; col++) {
            for (int row = 1; row < this.height - 1; row++) {
                double pixEnergy = this.energy[col][row];
                // from col-1, row-1
                double dist0 = distTo[col - 1][row - 1] + pixEnergy;
                if (dist0 < distTo[col][row]) {
                    distTo[col][row] = dist0;
                    pixelTo[col][row] = row - 1;
                }
                // from col-1, row
                double dist1 = distTo[col - 1][row] + pixEnergy;
                if (dist1 < distTo[col][row]) {
                    distTo[col][row] = dist1;
                    pixelTo[col][row] = row;
                }
                // from col-1, row+1
                double dist2 = distTo[col - 1][row + 1] + pixEnergy;
                if (dist2 < distTo[col][row]) {
                    distTo[col][row] = dist2;
                    pixelTo[col][row] = row + 1;
                }
            }
        }

        // find the shortest path
        double distSmallest = Double.POSITIVE_INFINITY;
        int indexSmallest = -1;
        for (int row = 1; row < this.height - 1; row++) {
            if (distTo[this.width - 2][row] < distSmallest) {
                distSmallest = distTo[this.width - 2][row];
                indexSmallest = row;
            }
        }

        // assemble the result
        seam[this.width - 1] = indexSmallest;
        seam[this.width - 2] = indexSmallest;
        for (int col = this.width - 2; col > 0; col--) {
            seam[col - 1] = pixelTo[col][seam[col]];
        }

        return seam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] seam = new int[this.height];
        for (int k = 0; k < seam.length; k++) {
            seam[k] = 0;
        }

        // all pixels on border
        if (this.height <= 2 || this.width <= 2) {
            return seam;
        }

        // initialize path
        final double[][] distTo = new double[this.width][this.height];
        final int[][] pixelTo = new int[this.width][this.height];
        for (int col = 0; col < this.width; col++) {
            for (int row = 0; row < this.height; row++) {
                if (row == 0) distTo[col][row] = this.energy[col][row];
                else distTo[col][row] = Double.POSITIVE_INFINITY;
                pixelTo[col][row] = -1;
            }
        }
        // only look at pixels not on the boundary
        for (int row = 1; row < this.height - 1; row++) {
            for (int col = 1; col < this.width - 1; col++) {
                double pixEnergy = this.energy[col][row];
                // from col-1, row-1
                double dist0 = distTo[col - 1][row - 1] + pixEnergy;
                if (dist0 < distTo[col][row]) {
                    distTo[col][row] = dist0;
                    pixelTo[col][row] = col - 1;
                }
                // from col, row-1
                double dist1 = distTo[col][row - 1] + pixEnergy;
                if (dist1 < distTo[col][row]) {
                    distTo[col][row] = dist1;
                    pixelTo[col][row] = col;
                }
                // from col+1, row-1
                double dist2 = distTo[col + 1][row - 1] + pixEnergy;
                if (dist2 < distTo[col][row]) {
                    distTo[col][row] = dist2;
                    pixelTo[col][row] = col + 1;
                }
            }
        }

        // find the shortest path
        double distSmallest = Double.POSITIVE_INFINITY;
        int indexSmallest = -1;
        for (int col = 1; col < this.width - 1; col++) {
            if (distTo[col][this.height - 2] < distSmallest) {
                distSmallest = distTo[col][this.height - 2];
                indexSmallest = col;
            }
        }

        // assemble the result
        seam[this.height - 1] = indexSmallest;
        seam[this.height - 2] = indexSmallest;
        for (int row = this.height - 2; row > 0; row--) {
            seam[row - 1] = pixelTo[seam[row]][row];
        }

        return seam;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException();
        if (!validateSeam(seam, false))
            throw new IllegalArgumentException("Seam invalid");
        if (this.height <= 1)
            throw new IllegalArgumentException("Picture height too small");

        // Update color arrays
        this.updateColorArrays(seam, false);
        this.computeEnergy();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException();
        if (!validateSeam(seam, true))
            throw new IllegalArgumentException("Seam invalid");
        if (this.width <= 1)
            throw new IllegalArgumentException("Picture width too small");

        // Update color arrays
        this.updateColorArrays(seam, true);
        this.computeEnergy();
    }

    private boolean isBorder(final int i, final int j) {
        if (!validateIndex(i, j))
            throw new IllegalArgumentException("Index out of range");
        return i == 0 || j == 0 || i == this.width - 1 || j == this.height - 1;
    }

    private boolean validateIndex(final int i, final int j) {
        return !(i < 0 || j < 0 || i >= this.width || j >= this.height);
    }

    private boolean validateIndex(final int i, boolean isColIndex) {
        return !(i < 0 || i >= (isColIndex ? this.width : this.height));
    }

    private boolean validateSeam(int[] seam, boolean isVertical) {
        int expectedLength = isVertical ? this.height : this.width;
        if (seam.length != expectedLength) return false;
        if (!validateIndex(seam[0], isVertical)) return false;
        for (int i = 1; i < expectedLength; i++) {
            if (!validateIndex(seam[i], isVertical)) return false;
            if (Math.abs(seam[i - 1] - seam[i]) > 1) return false;
        }
        return true;
    }

    private void updateColorArrays(int[] seam, boolean isVertical) {
        if (isVertical) {
            for (int row = 0; row < this.height; row++) {
                for (int col = seam[row]; col < this.width - 1; col++) {
                    this.rgb[col][row] = this.rgb[col + 1][row];
                }
            }
            this.width--;
        } else {
            for (int col = 0; col < this.width; col++) {
                System.arraycopy(
                        this.rgb[col], seam[col] + 1,
                        this.rgb[col], seam[col],
                        this.height - seam[col] - 1
                );
            }
            this.height--;
        }
    }

    private void computeEnergy() {
        int[][] r = new int[this.width][this.height];
        int[][] g = new int[this.width][this.height];
        int[][] b = new int[this.width][this.height];
        for (int col = 0; col < this.width; col++) {
            for (int row = 0; row < this.height; row++) {
                int rgb = this.rgb[col][row];
                r[col][row] = (rgb & 0xff0000) >> 16;
                g[col][row] = (rgb & 0xff00) >> 8;
                b[col][row] = rgb & 0xff;
            }
        }
        for (int col = 0; col < this.width; col++) {
            for (int row = 0; row < this.height; row++) {
                if (this.isBorder(col, row)) {
                    this.energy[col][row] = BORDER_ENERGY;
                } else {
                    this.energy[col][row] =
                        Math.pow(r[col - 1][row] - r[col + 1][row], 2)
                      + Math.pow(r[col][row - 1] - r[col][row + 1], 2)
                      + Math.pow(g[col - 1][row] - g[col + 1][row], 2)
                      + Math.pow(g[col][row - 1] - g[col][row + 1], 2)
                      + Math.pow(b[col - 1][row] - b[col + 1][row], 2)
                      + Math.pow(b[col][row - 1] - b[col][row + 1], 2);
                    this.energy[col][row] = Math.sqrt(this.energy[col][row]);
                }
            }
        }
    }
}
