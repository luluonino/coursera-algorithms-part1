import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solver {

    private final Board initial;
    private final Board twin;
    private List<Board> solution = null;
    private int moves = 0;
    private boolean isSolvable = false;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(final Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        this.initial = initial;
        this.twin = initial.twin();

        MinPQ<SearchNode> queue = new MinPQ<>();
        MinPQ<SearchNode> queueTwin = new MinPQ<>();

        queue.insert(new SearchNode(this.initial, 0, null));
        queueTwin.insert(new SearchNode(this.twin, 0, null));
        while (true) {
            // original initial
            SearchNode node = queue.delMin();
            if (node.getBoard().isGoal()) {
                this.isSolvable = true;
                this.moves = node.getPreviousMoves();

                Board[] solutionBoards = new Board[this.moves + 1];
                int index = this.moves;
                while (node != null) {
                    solutionBoards[index--] = node.getBoard();
                    node = node.getPreviousNode();
                }
                this.solution = new ArrayList<>(Arrays.asList(solutionBoards));
                break;
            }
            for (Board neighbor: node.getBoard().neighbors()) {
                if (node.getPreviousNode() == null
                    || !neighbor.equals(node.getPreviousNode().getBoard())
                ) {
                    queue.insert(new SearchNode(
                        neighbor, node.getPreviousMoves() + 1, node
                    ));
                }
            }

            // twin
            SearchNode nodeTwin = queueTwin.delMin();
            if (nodeTwin.getBoard().isGoal()) {
                this.isSolvable = false;
                this.solution = null;
                this.moves = -1;
                break;
            }
            for (Board neighbor: nodeTwin.getBoard().neighbors()) {
                if (node.getPreviousNode() == null
                    || !neighbor.equals(nodeTwin.getPreviousNode().getBoard())
                ) {
                    queueTwin.insert(new SearchNode(
                        neighbor, nodeTwin.getPreviousMoves() + 1, nodeTwin
                    ));
                }
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return this.isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return this.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return this.solution;
    }

    private class SearchNode implements Comparable<SearchNode> {
        private Board board = null;
        private SearchNode previousNode = null;
        private int previousMoves = 0;
        private int priority = 0;

        SearchNode(
            final Board board,
            final int previousMoves,
            final SearchNode searchNode
        ) {
            this.board = board;
            this.previousMoves = previousMoves;
            this.previousNode = searchNode;
            this.priority = board.manhattan() + previousMoves;
        }

        public Board getBoard() {
            return this.board;
        }

        public SearchNode getPreviousNode() {
            return this.previousNode;
        }

        public int getPreviousMoves() {
            return this.previousMoves;
        }

        @Override
        public int compareTo(final SearchNode that) {
            return this.priority - that.priority;
        }
    }
}
