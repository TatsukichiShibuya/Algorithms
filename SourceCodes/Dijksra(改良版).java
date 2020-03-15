public static class Dijkstra {
    int n; long[] dist;
    ArrayList<Pair>[] list;
    final long inf = Long.MAX_VALUE/10;
    public Dijkstra(int n) {
        this.n = n;
        this.dist = new long[n];
        list = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            list[i] = new ArrayList<>();
            dist[i] = inf;
        }
    }
    public void addEdge(int from, int to, long cost) {
        list[from].add(new Pair(to, cost));
    }
    public void dijkstra(int s) {
        PriorityQueue<Pair> Q = new PriorityQueue<>();
        Q.add(new Pair(s, 0));
        boolean[] used = new boolean[n];
        while (!Q.isEmpty()) {
            Pair p = Q.poll();
            if (used[p.to]) continue;
            used[p.to] = true;
            dist[p.to] = p.cost;
            for (Pair edge : list[p.to]) {
                Q.add(new Pair(edge.to, p.cost + edge.cost));
            }
        }
    }
    class Pair implements Comparable<Pair> {
        int to; long cost;
        Pair(int to, long cost) {
            this.to = to;
            this.cost = cost;
        }
        public int compareTo(Pair p) {
            return Long.compare(cost, p.cost);
        }
    }
}
