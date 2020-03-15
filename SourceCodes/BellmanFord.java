public static class BellmanFord{
    private ArrayList<Edge> edge;
    long[] dist; int v, e;/*v:頂点数,e:辺数*/
    boolean directed;
    public BellmanFord(int v, int e, boolean directed){
        this.directed = directed;
        this.v = v;
        this.e = this.directed?2*e:e;
        edge = new ArrayList<>(this.e);
        dist = new long[v];
        Arrays.fill(dist,INF);
    }
    public boolean bellmanford(int s){//if a negative cycle exists, return true
        dist[s] = 0;
        boolean update;
        for (int j = 0; j < v; j++) {
            update = false;
            for (int i = 0; i < edge.size(); i++) {
                Edge e = edge.get(i);
                if(dist[e.from]!=INF && dist[e.to]>dist[e.from]+e.cost){
                    dist[e.to] = dist[e.from]+e.cost;
                    update = true;
                    if(j == v-1){
                        return true;
                    }
                }
            }
            if(!update) break;
        }
        return false;
    }
    public void addEdge(int from, int to, long cost){
        Edge e = new Edge(from, to, cost);
        edge.add(e);
        if(!this.directed){
            Edge e_rev = new Edge(to, from, cost);
            edge.add(e_rev);
        }
    }
    class Edge{
        int from, to; long cost;
        Edge(int from, int to, long cost){
            this.from = from;
            this.to = to;
            this.cost = cost;
        }
    }
}
