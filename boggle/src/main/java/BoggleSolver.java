import edu.princeton.cs.algs4.TST;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BoggleSolver {
    private final TST<Boolean> dictionary;

    /**
     * Initializes the data structure using the given array of strings as the
     * dictionary. (You can assume each word in the dictionary contains only
     * the uppercase letters A through Z.)
     * @param dictionary array of words in the dictionary
     */
    public BoggleSolver(final String[] dictionary) {
        this.dictionary = new TST<>();
        Arrays.stream(dictionary).forEach(
            word -> {
                this.dictionary.put(word, true);
            }
        );
    }

    /**
     * Returns the set of all valid words in the given Boggle board, as an
     * Iterable.
     * @param board the input Boggle board configuration
     * @return all valid words
     */
    public Iterable<String> getAllValidWords(final BoggleBoard board) {
        HashSet<String> results = new HashSet<>();
        for (int row = 0; row < board.rows(); row++) {
            for (int col = 0; col < board.cols(); col++) {
                boolean[][] marked = new boolean[board.rows()][board.cols()];
                this.visit(row, col, marked, "", results, board);
            }
        }
        return results;
    }

    /**
     * Visit (row, col) and its neighbours iteratively.
     * @param row index of current row
     * @param col index of current col
     * @param marked 2d array to mark visited dices
     * @param prefix letters from dices alreay visited
     * @param results already found valid words
     * @param board the Boggle board
     */
    private void visit(
        final int row,
        final int col,
        final boolean[][] marked,
        final String prefix,
        final Set<String> results,
        final BoggleBoard board
    ) {
        char c = board.getLetter(row, col);
        String newPrefix = prefix + (c == 'Q' ? "QU" : c); // Qu case
        if (newPrefix.length() >= 3) { // no need to look at it when len < 3
            if (this.dictionary.contains(newPrefix)) results.add(newPrefix);
        }
        // no need to look at neighbours if no more match with current prefix
        boolean noMatch = true;
        for (String ignored : this.dictionary.keysWithPrefix(newPrefix)) {
            noMatch = false;
            break;
        }
        if (noMatch) return;

        marked[row][col] = true;

        int nRows = board.rows();
        int nCols = board.cols();
        // Visit neighbouring dices
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (row + i < 0
                    || row + i > nRows - 1
                    || col + j < 0
                    || col + j > nCols - 1
                ) continue; // invalid index
                if (i == 0 && j == 0) continue; // current dice
                if (marked[row + i][col + j]) continue; // already visited
                this.visit(row + i, col + j, marked, newPrefix, results, board);
                // reset on our way back
                marked[row + i][col + j] = false;
            }
        }
    }

    /**
     * Returns the score of the given word if it is in the dictionary, zero
     * otherwise. (You can assume the word contains only the uppercase letters
     * A through Z.)
     * @param word the word to be scored
     * @return Boggle score
     */
    public int scoreOf(String word) {
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
