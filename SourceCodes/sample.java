public static class Kruskal{
    long sum; int n;
    ArrayList<Edge> MST;
    PriorityQueue<Edge> edge;
    public Kruskal(int n){
        this.n = n; this.sum = 0;
        this.MST = new ArrayList<>();
        this.edge = new PriorityQueue<Edge>(Comparator.comparingLong(o -> o.cost)){};
    }
    public long kruskal(){
        UnionFind uf = new UnionFind(n);
        Edge e;
        while(!edge.isEmpty()){
            e = edge.poll();
            if(!uf.same(e.from, e.to)){
                uf.unite(e.from, e.to);
                sum += e.cost;
                MST.add(e);
            }
        }
        return sum;
    }
    public void addEdge(int from, int to, long cost){
        edge.add(new Edge(from, to, cost));
    }
    private class UnionFind {
        int par[]; int rank[]; int size[]; int count;
        public UnionFind(int n) {
            this.par = new int[n];
            this.rank = new int[n];
            this.size = new int[n];
            this.count = n;
            for(int i = 0; i < n; i++) {
                par[i] = i; rank[i] = 0; size[i] = 1;
            }
        }
        int find(int x) {
            if(par[x] == x) return x;
            else return par[x] = find(par[x]);
        }
        void unite(int x, int y) {
            x = find(x);
            y = find(y);
            if(x == y) return ;
            if(rank[x] < rank[y]) {
                par[x] = y;
                size[y] += size[x];
            }else {
                par[y] = x;
                size[x] += size[y];
                if(rank[x] == rank[y]) {
                    rank[x]++;
                }
            }
            count--;
        }
        boolean same(int x, int y) {
            return find(x) == find(y);
        }
    }
    private class Edge{
        int from, to; long cost;
        Edge(int from, int to, long cost){
            this.from = from;
            this.to = to;
            this.cost = cost;
        }
    }
}