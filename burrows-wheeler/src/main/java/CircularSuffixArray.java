import java.util.ArrayList;
import java.util.Collections;

import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {
    private final String originalString;
    private final int[] indices;

    /**
     * circular suffix array of s
     * @param s Original string
     */
    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException("input is null");
        this.originalString = s;
        this.indices = new int[s.length()];

        for (int i = 0; i < s.length(); i++) {
            this.indices[i] = i;
        }

        threeWayRadixQuickSort(this.indices, 0, s.length() - 1, 0);
    }

    /**
     * Returns the dth character in the ith suffix
     * @param i index of suffix (not sorted)
     * @param d index of character in suffix
     * @return character
     */
    private int charAt(int i, int d) {
        return this.originalString.charAt((i + d) % this.originalString.length());
    }

    /**
     * 3-way string radix quicksort a[lo..hi] starting at dth character
     * @param a array of indices of suffixes (not sorted)
     * @param lo low index
     * @param hi high index
     * @param d index of character in suffix
     */
    private void threeWayRadixQuickSort(int[] a, int lo, int hi, int d) {
        if (hi <= lo) return; // base case
        if (d >= this.originalString.length()) return; // base case (string exhausted)
        int lt = lo, gt = hi; // pointers of less than and greater than
        int v = charAt(a[lo], d); // pivot
        int i = lo + 1; // pointer of equal to
        while (i <= gt) { // loop invariant: a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi]
            int t = charAt(a[i], d);
            if (t < v) swap(a, lt++, i++);
            else if (t > v) swap(a, i, gt--);
            else i++;
        }
        threeWayRadixQuickSort(a, lo, lt - 1, d); // sort less than
        if (v >= 0) threeWayRadixQuickSort(a, lt, gt, d + 1); // sort equal to
        threeWayRadixQuickSort(a, gt + 1, hi, d); // sort greater than
    }

    /**
     * Swap two elements in an int array
     * @param a array
     * @param i index of first element
     * @param j index of second element
     */
    private void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    /**
     * length of original string
     * @return length
     */
    public int length() {
        return this.originalString.length();
    }

    /**
     * returns index of ith sorted suffix
     * @param i index of sorted suffix
     * @return index of original suffix
     */
    public int index(int i) {
        if (i < 0 || i >= this.originalString.length())
            throw new IllegalArgumentException("input out of range");
        return this.indices[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray csa = new CircularSuffixArray("abracadabra!");
        StdOut.println(csa.originalString);
        for (int i = 0; i < csa.length(); i++) {
            StdOut.println(csa.index(i));
        }
        csa = new CircularSuffixArray("aaaaaaaaaaaaa");
        StdOut.println(csa.originalString);
        for (int i = 0; i < csa.length(); i++) {
            StdOut.println(csa.index(i));
        }
    }
}
