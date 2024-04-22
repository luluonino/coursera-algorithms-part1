import java.util.HashMap;
import java.util.ArrayList;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {
    private final int numTeams; // number of teams in the division
    private final HashMap<String, Integer> indexMap; // map team name to index
    private final ArrayList<String> teams; // list of team names
    private final ArrayList<Integer> w; // list of wins
    private final ArrayList<Integer> l; // list of losses
    private final ArrayList<Integer> r; // list of remaining games
    private final HashMap<String, Integer> g; // games left between teams
    private final ArrayList<Boolean> isEliminated; // list of elimination status
    private final ArrayList<Bag<String>> certificate; // list of certificate of elimination

    /**
     * Constructor for BaseballElimination class.
     * It initializes the baseball division from given filename in format specified.
     * @param filename name of file containing baseball division
     */
    public BaseballElimination(String filename) {
        // create a baseball division from given filename in format specified
        this.indexMap = new HashMap<>();
        this.teams = new ArrayList<>();
        this.w = new ArrayList<>();
        this.l = new ArrayList<>();
        this.r = new ArrayList<>();
        this.g = new HashMap<>();
        this.isEliminated = new ArrayList<>();
        this.certificate = new ArrayList<>();

        In in = new In(filename);
        this.numTeams = Integer.parseInt(in.readLine());
        int index = 0;
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] fields = line.strip().split(" +");
            this.indexMap.put(fields[0], index);
            this.teams.add(fields[0]);
            this.w.add(Integer.parseInt(fields[1]));
            this.l.add(Integer.parseInt(fields[2]));
            this.r.add(Integer.parseInt(fields[3]));
            for (int i = 0; i < this.numTeams; i++) {
                this.g.put(
                    String.format("%d_%d", index, i),
                    Integer.parseInt(fields[i + 4])
                );
            }
            index++;
        }

        // one FlowNetwork for each team
        int s = this.numTeams * this.numTeams + this.numTeams;
        int t = s + 1;
        int numV = t + 1; // to simplify the indexing
        for (int i = 0; i < this.numTeams; i++) {
            this.isEliminated.add(false);
            this.certificate.add(new Bag<String>());

            // look at trivial elimination first
            for (int j = 0; j < this.numTeams; j++) {
                if (j == i) continue;
                if (w.get(i) + r.get(i) - w.get(j) < 0) {
                    this.isEliminated.set(i, true);
                    this.certificate.get(i).add(this.teams.get(j));
                    break;
                }
            }
            if (this.isEliminated.get(i)) continue;

            // non-trivial case
            FlowNetwork network = new FlowNetwork(numV);
            int totR = 0; // total games between other teams in the division
            // add edges for each of the other teams
            for (int j = 0; j < this.numTeams; j++) {
                if (j == i) continue;
                // edge team_j (j) to t (numT^2)
                network.addEdge(new FlowEdge(
                    j, t, Math.max(0, w.get(i) + r.get(i) - w.get(j))
                ));
                for (int k = j + 1; k < this.numTeams; k++) {
                    if (k == i) continue;
                    int vPair = k + (j + 1) * this.numTeams;
                    // edge from s (numT^2-1) to team pair (j, k) (k+j*numTeams)
                    int rJk = g.get(String.format("%d_%d", j, k));
                    totR += rJk;
                    network.addEdge(new FlowEdge(s, vPair, rJk));
                    // edge from team pair to each team
                    network.addEdge(
                        new FlowEdge(vPair, j, Double.POSITIVE_INFINITY)
                    );
                    network.addEdge(
                        new FlowEdge(vPair, k, Double.POSITIVE_INFINITY)
                    );
                }
            }
            FordFulkerson ff = new FordFulkerson(network, s, t);
            if (totR > ff.value()) {
                this.isEliminated.set(i, true);
            }
            for (int j = 0; j < this.numTeams; j++) {
                if (ff.inCut(j)) this.certificate.get(i).add(this.teams.get(j));
            }
        }
    }

    /**
     * Returns the number of teams in the division
     * @return the number of teams in the division
     */
    public int numberOfTeams() {
        return this.numTeams;
    }

    /**
     * Returns all teams in the division
     * @return An iterable of all teams in the division
     */
    public Iterable<String> teams() {
        return this.teams;
    }

    /**
     * Returns the number of wins for given team
     * @param team The name of the team
     * @return The number of wins for given team
     * @throws IllegalArgumentException if team is not in division
     */
    public int wins(String team) {
        if (!this.indexMap.containsKey(team))
            throw new IllegalArgumentException();
        return this.w.get(this.indexMap.get(team));
    }

    /**
     * Returns the number of losses for given team
     * @param team The name of the team
     * @return The number of losses for given team
     * @throws IllegalArgumentException if team is not in division
     */
    public int losses(String team) {
        if (!this.indexMap.containsKey(team))
            throw new IllegalArgumentException();
        return this.l.get(this.indexMap.get(team));
    }

    /**
     * Returns the number of remaining games for given team
     * @param team The name of the team
     * @return The number of remaining games for given team
     * @throws IllegalArgumentException if team is not in division
     */
    public int remaining(String team) {
        if (!this.indexMap.containsKey(team))
            throw new IllegalArgumentException();
        return this.r.get(this.indexMap.get(team));
    }

    /**
     * Returns the number of remaining games between two given teams
     * @param team1 The name of the first team
     * @param team2 The name of the second team
     * @return The number of remaining games between two given teams
     * @throws IllegalArgumentException if either team is not in division
     */
    public int against(String team1, String team2) {
        if (!this.indexMap.containsKey(team1)
            || !this.indexMap.containsKey(team2)
        ) throw new IllegalArgumentException();
        return this.g.get(String.format(
            "%d_%d", this.indexMap.get(team1), this.indexMap.get(team2)
        ));
    }

    /**
     * Check if the given team is eliminated
     * @param team The name of the team
     * @return True if team is eliminated, false otherwise
     * @throws IllegalArgumentException if team is not in division
     */
    public boolean isEliminated(String team) { // is given team eliminated?
        if (!this.indexMap.containsKey(team))
            throw new IllegalArgumentException();
        return this.isEliminated.get(this.indexMap.get(team)).booleanValue();
    }

    /**
     * Returns the subset R of teams that eliminates given team; null if not eliminated
     * @param team The name of the team
     * @return An iterable of teams that eliminates given team
     * @throws IllegalArgumentException if team is not in division
     */
    public Iterable<String> certificateOfElimination(String team) {
        // subset R of teams that eliminates given team; null if not eliminated
        if (!this.indexMap.containsKey(team))
            throw new IllegalArgumentException();
        if (this.isEliminated(team)) {
            return this.certificate.get(this.indexMap.get(team));
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
