package examples;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

class Dijkstra {

	Node[] list;
	int[] dist;
	final int inf = 1000000000;

	public Dijkstra(int n) {
		list = new Node[n];
		dist = new int[n];
		for (int i = 0; i < list.length; i++) {
			list[i] = new Node();
			dist[i] = inf;
		}
	}

	public void dijkstra(int s) {
		dist[s] = 0;
		PriorityQueue<Pair> que = new PriorityQueue<Pair>(
			new Comparator<Pair>(){
				public int compare(Pair a, Pair b) {
					return b.getDist()-a.getDist();
				}
			}
		);//(頂点番号，距離)のArrayListを距離が小さい順に保存
		Pair p = new Pair(s, dist[s]);
		que.add(p);
		while(!que.isEmpty()) {
			p = que.poll();
			int v = p.getVer();
			if(dist[v] < p.getDist()) continue;
			for (int i = 0; i < list[v].size(); i++) {
				Edge e = list[v].get(i);
				if(dist[e.to] > dist[v] + e.cost) {
					dist[e.to] = dist[v] + e.cost;
				que.add(new Pair(e.to, dist[e.to]));
				}
			}
		}
	}

	public void addNode(int from, int to, int cost) {
		Edge e = new Edge(to, cost);
		Edge e_reverse = new Edge(from, cost);
		list[from].addEdge(e);
		list[to].addEdge(e_reverse);//一方向なら削除
	}

	public void delete() {
		//todo
	}

	class Node{
		ArrayList<Edge> edges;
		public Node() {
			edges = new ArrayList<Edge>();
		}
		public void addEdge(Edge e) {
			edges.add(e);
		}
		public int size() {
			return edges.size();
		}
		public Edge get(int i) {
			return edges.get(i);
		}
	}

	class Edge {
		int to;
		int cost;
		public Edge(int to, int cost) {
			this.to = to;
			this.cost = cost;
		}
	}

	class Pair{
		ArrayList<Integer> pair;
		public Pair(int from, int distance) {
			pair = new ArrayList<Integer>();
			pair.add(from);
			pair.add(distance);
		}
		public int getVer() {//頂点を返す
			return pair.get(0);
		}
		public int getDist() {//距離を返す
			return pair.get(1);
		}
	}
}