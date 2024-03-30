import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TST;
import edu.princeton.cs.algs4.TrieSET;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BoggleSolver {
    // private final TrieSET dictionary;
    private final TST<Boolean> dictionary;
//    private final TrieSET dictionary;

    /**
     * Initializes the data structure using the given array of strings as the dictionary.
     * (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
     * @param dictionary array of words
     */
    public BoggleSolver(String[] dictionary) {
//        this.dictionary = new TrieSET();
        this.dictionary = new TST<>();
        Arrays.stream(dictionary).forEach(word -> {this.dictionary.put(word, Boolean.TRUE);});
//        Arrays.stream(dictionary).forEach(this.dictionary::add);
        StdOut.println(String.format("Total number of words: %d", this.dictionary.size()));
    }

    /**
     * Returns the set of all valid words in the given Boggle board, as an Iterable.
     * @param board the input Boggle board configuration
     * @return all valid words
     */
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        HashSet<String> results = new HashSet<>();
        for (int row = 0; row < board.rows(); row++) {
            for (int col = 0; col < board.cols(); col++) {
                boolean[][] marked = new boolean[board.rows()][board.cols()];
                visit(row, col, marked, "", results, board);
            }
        }
        return results;
    }

    private void visit (
        int row,
        int col,
        boolean[][] marked,
        String prefix,
        Set<String> results,
        BoggleBoard board
    ) {
        char c = board.getLetter(row, col);
        String newPrefix = prefix + c;
        if (newPrefix.length() >= 3) {
            if (this.dictionary.contains(newPrefix)) results.add(newPrefix);
        }
        boolean noMatch = true;
        for (String ignored : this.dictionary.keysWithPrefix(newPrefix)) {
            noMatch = false;
            break;
        }
        if (noMatch) return;
        marked[row][col] = Boolean.TRUE;

        int nRows = board.rows();
        int nCols = board.cols();
        // Visit neighbouring dices
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (row + i < 0 || row + i > nRows - 1 || col + j < 0 || col + j > nCols - 1) continue;
                if (i == 0 && j == 0) continue;
                if (marked[row + i][col + j]) continue;
                visit(row + i, col + j, marked, newPrefix, results, board);
                marked[row + i][col + j] = Boolean.FALSE; // reset on our way back
            }
        }
    }

    /**
     * Returns the score of the given word if it is in the dictionary, zero otherwise.
     * (You can assume the word contains only the uppercase letters A through Z.)
     * @param word the word to be scored
     * @return Boggle score
     */
    public int scoreOf(String word) {
        // word length	  	points
        // 3–4	        	1
        // 5	        	2
        // 6	        	3
        // 7	        	5
        // 8+	        	11

//        if (word == null) throw new IllegalArgumentException("null word");
        if (word == null) return 0;
        if (!this.dictionary.contains(word)) return 0;
        if (word.length() <= 2) return 0;
        switch (word.length()) {
            case 3:
            case 4:
                return 1;
            case 5: return 2;
            case 6: return 3;
            case 7: return 5;
            default: return 11;
        }
    }
}
