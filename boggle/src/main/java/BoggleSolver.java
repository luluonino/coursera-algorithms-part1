import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.Bag;

public class BoggleSolver {
    private final TrieSET26 dictionary;
    private int rows;
    private int cols;
    private Bag<String> results;
    private ArrayList<TrieSET26.Node> visitedNodes;
    private TrieSET26.Node root;
    private char[] letters = new char[16];
    private boolean[] marked = new boolean[16];
    private Dice[] dices;
    private Dice[] dices4x4 = constructDices(4, 4);

    private class Dice {
        private Bag<Integer> adj;

        public Dice() {
        }

        public void setAdj(Bag<Integer> adj) {
            this.adj = adj;
        }
    }

    /**
     * Initializes the data structure using the given array of strings as the
     * dictionary. (You can assume each word in the dictionary contains only
     * the uppercase letters A through Z.)
     * @param dictionary array of words in the dictionary
     */
    public BoggleSolver(final String[] dictionary) {
        this.dictionary = new TrieSET26();
        Arrays.stream(dictionary).forEach(word -> {
            if (word.length() >= 3) this.dictionary.add(word);
        });
        this.root = this.dictionary.getRoot();
    }

    /**
     * Returns the set of all valid words in the given Boggle board, as an
     * Iterable.
     * @param board the input Boggle board configuration
     * @return all valid words
     */
    public Iterable<String> getAllValidWords(final BoggleBoard board) {
        this.rows = board.rows();
        this.cols = board.cols();

        if (this.rows == 4 && this.cols == 4) this.dices = this.dices4x4;
        else this.dices = constructDices(this.rows, this.cols);
        if (this.rows * this.cols > 16) {
            this.marked = new boolean[this.rows * this.cols];
            this.letters = new char[this.rows * this.cols];
        }
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                this.letters[row * this.cols + col]
                    = board.getLetter(row, col);
            }
        }
        this.results = new Bag<>();
        this.visitedNodes = new ArrayList<>();
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                this.visit(this.root, row * this.cols + col, 0);
            }
        }
        for (TrieSET26.Node node: this.visitedNodes) {
            node.setVisited(false);
        }
        return this.results;
    }

    /**
     * Visit (row, col) and its neighbours iteratively.
     * @param node node in dictionary to start with for current letter
     * @param index flattened 1-d index of dice
     * @param d length of current prefix
     */
    private void visit(
        TrieSET26.Node node,
        final int index,
        final int d
    ) {
        if (node == null) return;
        char c = this.letters[index];
        TrieSET26.Node nextNode = node.get(c);
        if (nextNode == null) return;
        if (c == 'Q') nextNode = nextNode.get('U');
        if (nextNode == null) return;

        int increment = c == 'Q' ? 2 : 1;
        if (d + increment >= 3 && nextNode.isString() && !nextNode.isVisited()) {
            this.results.add(nextNode.getWord());
            nextNode.setVisited(true);
            this.visitedNodes.add(nextNode);
        }

        marked[index] = true;

        for (int neighbour: this.dices[index].adj) {
            if (marked[neighbour]) continue;
            this.visit(
                    nextNode,
                    neighbour,
                    d + increment
            );
        }

        marked[index] = false;
    }

    /**
     * Returns the score of the given word if it is in the dictionary, zero
     * otherwise. (You can assume the word contains only the uppercase letters
     * A through Z.)
     * @param word the word to be scored
     * @return Boggle score
     */
    public int scoreOf(final String word) {
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

    private Dice[] constructDices(int rows, int cols) {
        Dice[] dices = new Dice[rows * cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Bag<Integer> adj = new Bag<>();
                if (row != 0) adj.add((row - 1) * cols + col);
                if (row != rows - 1) adj.add((row + 1) * cols + col);
                if (col != 0) adj.add(row * cols + col - 1);
                if (col != cols - 1) adj.add(row * cols + col + 1);
                if (row != 0 && col != 0) adj.add((row - 1) * cols + col - 1);
                if (row != 0 && col != cols - 1) adj.add((row - 1) * cols + col + 1);
                if (row != rows - 1 && col != 0) adj.add((row + 1) * cols + col - 1);
                if (row != rows - 1 && col != cols - 1) adj.add((row + 1) * cols + col + 1);
                dices[row * cols + col] = new Dice();
                dices[row * cols + col].setAdj(adj);
            }
        }
        return dices;
    }
}
