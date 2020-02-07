package examples;

import java.util.function.BiFunction;

public static class LazySegmentTree {//単位元，写像，eval内一か所，(update内一か所)書き換え
	int n; long[] dat; long[] lazy; final long e = 1;//単位元
	final BiFunction<Long, Long, Long> fanc = (l1,l2) -> l1*l2; // 使う写像
	public LazySegmentTree(long[] a) {
		n = 1; int l = a.length;
		while(n < a.length) n *= 2;
		dat = new long[2*n-1];
		lazy = new long[2*n-1];
		for (int i = 0; i < n; i++) dat[i+n-1] = (i<l)?a[i]:e;
		for (int i = n-2; i >= 0; i--) dat[i] = fanc.apply(dat[2*i+1],dat[2*i+2]);
		for (int i = 0; i < 2*n-1; i++) lazy[i] = e;
	}
	private void eval(int k, int l, int r) {
		if(lazy[k] != 0) {
			dat[k] = fanc.apply(dat[k], lazy[k]);
			if(r-l>1) {
				//書き換え
				long sqrt = (long)Math.round(Math.sqrt(lazy[k]));
				lazy[2*k+1] = fanc.apply(lazy[2*k+1], sqrt);
				lazy[2*k+2] = fanc.apply(lazy[2*k+2], sqrt);
				//書き換え
			}
			lazy[k] = e;
		}
	}
	private void update(int a, int b, long x, int k, int l, int r) {
		eval(k,l,r);
		if(r<=1||b<=l) return;
		if(a<=l&&r<=b) {
			lazy[k] = fanc_x(x,r-l);//効率悪いので基本的には都度書き換え
			eval(k,l,r);
		}else {
			update(a,b,x,2*k+1,l,(l+r)/2);
			update(a,b,x,2*k+2,(l+r)/2,r);
			dat[k] = fanc.apply(dat[2*k+1],dat[2*k+2]);
		}
	}
	public void update(int a, int b, long x) {//外部呼出用
		update(a,b,x,0,0,n);
		return;
	}
	private long query(int a, int b, int k, int l, int r) {
		if(r<=a||b<=l) return 1;
		eval(k,l,r);
		if(a<=l&&r<=b) {
			return dat[k];
		}else {
			long vl = query(a,b,2*k+1,l,(l+r)/2);
			long vr = query(a,b,2*k+2,(l+r)/2,r);
			return fanc.apply(vl, vr);
		}
	}
	public long query(int a, int b) {//外部呼出用
		return query(a,b,0,0,n);
	}
	public long query() {//外部呼出用
		return query(0,n,0,0,n);
	}
	private long fanc_x(long x, int c) {//c回fancを実行
		long res = e;
		for (int i = 0; i < c; i++) {
			res = fanc.apply(res, x);
		}
		return res;
	}
}
