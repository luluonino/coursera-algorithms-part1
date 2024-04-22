import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class SAP {
    private final Digraph digraph;

    private final HashMap<String, List<Integer>> cache;

    /**
     * Constructor
     * @param G the input digraph
     */
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

    /**
     * Length of the shortest ancestral path between two vertices
     * @param v the first vertex
     * @param w the second vertex
     * @return length of the shortest ancestral path
     */
    public int length(int v, int w) {
        return findSAP(v, w).get(1);
    }

    /**
     * Common ancestor of two vertices that participates in a shortest
     * @param v the first vertex
     * @param w the second vertex
     * @return common ancestor of two vertices, -1 if no such path
     */
    public int ancestor(int v, int w) {
        return findSAP(v, w).get(0);
    }

    /**
     * Length of the shortest ancestral path between two sets of vertices
     * @param v the first set of vertices
     * @param w the second set of vertices
     * @return length of the shortest ancestral path, -1 if no such path
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return findSAP(v, w).get(1);
    }

    /**
     * Common ancestor of two sets of vertices that participates in a shortest
     * @param v the first set of vertices
     * @param w the second set of vertices
     * @return common ancestor of two sets of vertices, -1 if no such path
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return findSAP(v, w).get(0);
    }

    /**
     * Find the shortest ancestral path between two vertices
     * @param v the first vertex
     * @param w the second vertex
     * @return a list of two elements, the first element is the common ancestor,
     * the second element is the length of the shortest ancestral path
     */
    private List<Integer> findSAP(int v, int w) {
        return findSAP(new HashSet<>(List.of(v)), new HashSet<>(List.of(w)));
    }

    /**
     * Find the shortest ancestral path between two iterables of vertices
     * @param v the first iterable of vertices
     * @param w the second iterable of vertices
     * @return a list of two elements, the first element is the common ancestor,
     * the second element is the length of the shortest ancestral path
     */
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

    /**
     * Find the shortest ancestral path between two sets of vertices
     * @param v the first set of vertices
     * @param w the second set of vertices
     * @return a list of two elements, the first element is the common ancestor,
     * the second element is the length of the shortest ancestral path
     */
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
