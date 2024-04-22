/**
 *  26-way trieSet modified from edu.princeton.cs.algs4.TrieSET
 */
public class TrieSET26 {
    private static final int R = 26; // 26-way trie
    private static final int OFFSET = 'A'; // offset for ASCII characters

    private Node root;      // root of trie
    private int n;          // number of keys in trie

    // R-way trie node
    public static class Node {
        private Node[] next = new Node[R];
        private String word = "";
        private boolean isString;
        private boolean isVisited = false;

        public Node get(char c) {
            return next[c - OFFSET];
        }

        public boolean isString() {
            return isString;
        }

        public String getWord() {
            return word;
        }

        public void setVisited(boolean value) {
            isVisited = value;
        }

        public boolean isVisited() {
            return isVisited;
        }
    }

    /**
     * Initializes an empty set of strings.
     */
    public TrieSET26() {
    }

    /**
     * Returns the root of the trie.
     * @return the root of the trie
     */
    public Node getRoot() {
        return root;
    }

    /**
     * Does the set contain the given key?
     *
     * @param key the key
     * @return {@code true} if the set contains {@code key} and
     * {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(String key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        Node x = get(root, key, 0);
        if (x == null) return false;
        return x.isString;
    }

    /**
     * Returns the Node of the string in the set that is the longest prefix of {@code key},
     * @param x the node to start with
     * @param key the key
     * @param d the depth
     * @return the last Node of the string in the set that is the longest prefix of {@code key}
     */
    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.next[c - OFFSET], key, d + 1);
    }

    /**
     * Adds the key to the set if it is not already present.
     * @param key the key to add
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void add(String key) {
        if (key == null) throw new IllegalArgumentException("argument to add() is null");
        root = add(root, key, 0);
    }

    /**
     * Add the key to the subtrie rooted at x.
     * @param x the node to start with
     * @param key the key
     * @param d the depth
     * @return the node after adding the key
     */
    private Node add(Node x, String key, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            if (!x.isString) n++;
            x.isString = true;
            x.word = key;
        } else {
            char c = key.charAt(d);
            x.next[c - OFFSET] = add(x.next[c - OFFSET], key, d + 1);
        }
        return x;
    }
}