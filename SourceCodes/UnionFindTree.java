public class UnionFindTree {

	int par[]; int rank[]; int size[]; int count;

	public UnionFindTree(int n) {
		this.par = new int[n];
		this.rank = new int[n];
		this.size = new int[n];
		this.count = n;
		for(int i = 0; i < n; i++) {
			par[i] = i; rank[i] = 0; size[i] = 1;
		}
	}

	int find(int x) {//xの属する集合の根を探す
		if(par[x] == x) return x;
		else return par[x] = find(par[x]);
	}

	void unite(int x, int y) {//x,yの集合を合併
		x = find(x);
		y = find(y);
		if(x == y) return ;
		if(rank[x] < rank[y]) {
			par[x] = y;
			size[y] += size[x];
		}else {
			par[y] = x;
			size[x] = size[y];
			if(rank[x] == rank[y]) {
				rank[x]++;
			}
		}
		count--;
	}

	boolean same(int x, int y) {//x,yが同じ集合に属するか
		return find(x) == find(y);
	}

	int amount(int x) {//xの属する集合の要素数
		return size[find(x)];
	}

	int count() {//集合の数
		return count;
	}
}
