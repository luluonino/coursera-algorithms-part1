import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Topological;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordNet {
    private final HashMap<String, Set<Integer>> hashMap;
    private final List<String> synsets;
    private final SAP sap;
    private int size;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException();

        // save all words to hashMap
        this.hashMap = new HashMap<>();
        this.synsets = new ArrayList<>();
        In synsetsIn = new In(synsets);
        while (!synsetsIn.isEmpty()) {
            String line = synsetsIn.readLine();
            String[] fields = line.split(",");
            int id = Integer.parseInt(fields[0]);
            this.synsets.add(fields[1]);
            String[] words = fields[1].split(" ");
            for (String word: words) {
                if (!this.hashMap.containsKey(word)) {
                    this.hashMap.put(word, new HashSet<>(List.of(id)));
                } else this.hashMap.get(word).add(id);
            }
            this.size++;
        }
        synsetsIn.close();

        // create the digraph
        final Digraph digraph = new Digraph(this.size);
        In hypernymsIn = new In(hypernyms);
        while (!hypernymsIn.isEmpty()) {
            String line = hypernymsIn.readLine();
            String[] fields = line.split(",");
            int v = Integer.parseInt(fields[0]);
            for (int i = 1; i < fields.length; i++) {
                int w = Integer.parseInt(fields[i]);
                digraph.addEdge(v, w);
            }
        }
        hypernymsIn.close();

        Topological topological = new Topological(digraph);
        if (!topological.hasOrder()) throw new IllegalArgumentException();
        int numRoots = 0;
        for (int v = 0; v < digraph.V(); v++) {
            if (digraph.outdegree(v) == 0) numRoots++;
        }
        if (numRoots != 1) throw new IllegalArgumentException();

        this.sap = new SAP(digraph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return hashMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException();
        return hashMap.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException();
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();

        int dist = this.sap.length(
            this.hashMap.get(nounA),
            this.hashMap.get(nounB)
        );
        return dist;
    }

    // a synset (second field of synsets.txt) that is the common ancestor
    // of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException();
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();

        int ancestor = this.sap.ancestor(
            this.hashMap.get(nounA),
            this.hashMap.get(nounB)
        );
        if (ancestor != -1) return this.synsets.get(ancestor);
        else return null;
    }

    // do unit testing of this class
    // public static void main(String[] args) {
    // }
}
