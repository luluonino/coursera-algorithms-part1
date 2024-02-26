import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class SAP {
    private final Digraph digraph;

    private final HashMap<String, List<Integer>> cache;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(final Digraph G) {
        if (G == null) throw new IllegalArgumentException("input is null");
        this.digraph = new Digraph(G.V()); // deep copy
        for (int v = 0; v < G.V(); v++) {
            for (int w: G.adj(v)) {
                this.digraph.addEdge(v, w);
            }
        }
        this.cache = new HashMap<>();
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return findSAP(v, w).get(1);
    }

    // a common ancestor of v and w that participates in a shortest
    // ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return findSAP(v, w).get(0);
    }

    // length of shortest ancestral path between any vertex in v and
    // any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return findSAP(v, w).get(1);
    }

    // a common ancestor that participates in shortest ancestral path;
    // -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return findSAP(v, w).get(0);
    }

    // do unit testing of this class
    // public static void main(String[] args) {
    // }

    private List<Integer> findSAP(int v, int w) {
        return findSAP(new HashSet<>(List.of(v)), new HashSet<>(List.of(w)));
    }

    private List<Integer> findSAP(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException("input is null");
        if (!v.iterator().hasNext() || !w.iterator().hasNext())
            return List.of(-1, -1);

        HashSet<Integer> hashSetV = new HashSet<>();
        HashSet<Integer> hashSetW = new HashSet<>();
        v.forEach(hashSetV::add);
        w.forEach(hashSetW::add);
        return findSAP(hashSetV, hashSetW);
    }

    private List<Integer> findSAP(HashSet<Integer> v, HashSet<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException("input is null");
        if (v.isEmpty() || w.isEmpty())
            throw new IllegalArgumentException("input should not be empty");

        HashSet<String> keySet = new HashSet<>();
        keySet.add(v.toString());
        keySet.add(w.toString());
        String key = keySet.toString();
        if (this.cache.containsKey(key)) {
            return this.cache.get(key);
        }

        BreadthFirstDirectedPaths dfsV =
            new BreadthFirstDirectedPaths(this.digraph, v);
        BreadthFirstDirectedPaths dfsW =
            new BreadthFirstDirectedPaths(this.digraph, w);
        int shortestLen = this.digraph.V();
        int ancestor = -1;
        boolean hasAncestor = false;
        for (int i = 0; i < this.digraph.V(); i++) {
            if (dfsV.hasPathTo(i) && dfsW.hasPathTo(i)) {
                hasAncestor = true;
                int dist = dfsV.distTo(i) + dfsW.distTo(i);
                if (dist < shortestLen) {
                    shortestLen = dist;
                    ancestor = i;
                    if (shortestLen == 0) break;
                }
            }
        }

        if (hasAncestor) {
            this.cache.put(key, Arrays.asList(ancestor, shortestLen));
            return List.of(ancestor, shortestLen);
        } else return List.of(-1, -1);
    }
}
