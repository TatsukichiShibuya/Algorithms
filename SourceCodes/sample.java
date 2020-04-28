public static class TopologicalSort{
    Edge[] e; int n; boolean[] use;
    ArrayList<Integer> sortedList;
    public TopologicalSort(int n){
        this.n = n;
        e = new Edge[n];
        for (int i = 0; i < n; i++) e[i] = new Edge();
    }
    public void addEdge(int from, int to){ e[from].add(to); }
    public ArrayList<Integer> topologicalSort(){
        sortedList = new ArrayList<>();
        use = new boolean[n];
        for (int i = 0; i < n; i++) {
            if(!use[i]){
                dfs(i);
                use[i] = true;
            }
        }
        Collections.reverse(sortedList);
        return sortedList;
    }
    private void dfs(int to){
        for(Integer x: e[to]){
            if(!use[x]){
                dfs(x);
                use[x] = true;
            }
        }
        sortedList.add(to);
    }
    private class Edge extends ArrayList<Integer>{}
}