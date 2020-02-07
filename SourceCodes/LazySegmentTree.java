package examples;

public class LazySegmentTree {//単位元，constractor内1か所，eval内3か所，update内2か所，query内1か所変更
	int n; long[][] dat; final long e = 0;//単位元
	public LazySegmentTree(long[] a) {
		n = 1; int l = a.length;
		while(n < a.length) n *= 2;
		dat = new long[2][2*n-1];
		for (int i = 0; i < n; i++) dat[0][i+n-1] = (i<l)?a[i]:e;
		for (int i = n-2; i >= 0; i--) dat[0][i] = dat[0][2*i+1]+dat[0][2*i+2];//変更
		for (int i = 0; i < 2*n-1; i++) dat[1][i] = e;
	}
	private void eval(int k, int l, int r) {
		if(dat[1][k] != 0) {
			dat[0][k] += dat[1][k];//変更
			if(r-l>1) {
				dat[1][2*k+1] += dat[1][k]/2;//変更
				dat[1][2*k+2] += dat[1][k]/2;//変更
			}
			dat[1][k] = e;
		}
	}
	private void update(int a, int b, long x, int k, int l, int r) {
		eval(k,l,r);
		if(r<=a||b<=l) return;
		if(a<=l&&r<=b) {
			dat[1][k] += x*(r-l);//変更
			eval(k,l,r);
		}else {
			update(a,b,x,2*k+1,l,(l+r)/2);
			update(a,b,x,2*k+2,(l+r)/2,r);
			dat[0][k] = dat[0][2*k+1]+dat[0][2*k+2];//変更
		}
	}
	public void update(int a, int b, long x) {//外部呼出用
		update(a,b,x,0,0,n);
		return;
	}
	private long query(int a, int b, int k, int l, int r) {
		if(r<=a||b<=l) return e;
		eval(k,l,r);
		if(a<=l&&r<=b) {
			return dat[0][k];
		}else {
			long vl = query(a,b,2*k+1,l,(l+r)/2);
			long vr = query(a,b,2*k+2,(l+r)/2,r);
			return vl+vr;//変更
		}
	}
	public long query(int a, int b) {//外部呼出用
		return query(a,b,0,0,n);
	}
	public long query() {//外部呼出用
		return query(0,n,0,0,n);
	}
}