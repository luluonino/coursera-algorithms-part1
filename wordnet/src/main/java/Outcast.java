import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(final WordNet wordnet) {
        this.wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(final String[] nouns) {
        String outcast = null;
        int largestDist = -1;
        for (String nounA: nouns) {
            int dist = 0;
            for (String nounB: nouns) {
                dist += this.wordNet.distance(nounA, nounB);
            }
            if (dist > largestDist) {
                largestDist = dist;
                outcast = nounA;
            }
        }
        return outcast;
    }

    public static void main(final String[] args) { // see test client below
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}