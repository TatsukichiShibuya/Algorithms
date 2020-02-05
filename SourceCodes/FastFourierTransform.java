package examples;

public class FastFourierTransform {

	public Complex[] dft(Complex[] f, int n, int inv) {
		if(n == 1) return f;
		Complex[] f0 = new Complex[n/2], f1 = new Complex[n/2];
		for (int i = 0; i < n/2; i++) {
			f0[i] = new Complex(f[2*i]);
			f1[i] = new Complex(f[2*i+1]);
		}
		f0 = dft(f0, n/2, inv);
		f1 = dft(f1, n/2, inv);
		Complex zeta = new Complex(Math.cos(inv*2*Math.PI/n), Math.sin(inv*2*Math.PI/n));
		Complex pow_zeta = new Complex(1, 0);
		for (int i = 0; i < n; i++) {
			f[i] = f0[i%(n/2)].add(pow_zeta.mul(f1[i%(n/2)]));
			pow_zeta = pow_zeta.mul(zeta);
		}
		return f;
	}
	public long[] multiply(long[] F, long[] G) {
		int size = 1;
		while(F.length+G.length>size) size *= 2;
		Complex[] f = new Complex[size], g = new Complex[size];
		for (int i = 0; i < size; i++) f[i] = new Complex((i<F.length)?F[i]:0, 0);
		for (int i = 0; i < size; i++) g[i] = new Complex((i<G.length)?G[i]:0, 0);
		Complex[] f_inv = dft(f, size, 1), g_inv = dft(g, size, 1);
		Complex[] h_inv = new Complex[size];
		for (int i = 0; i < size; i++) h_inv[i] = new Complex(f_inv[i].mul(g_inv[i]));
		Complex[] h = dft(h_inv, size, -1);
		long[] H = new long[size];
		for (int i = 0; i < size; i++) H[i] = (Math.round(h[i].re()))/size;
		return H;
	}
	private class Complex {
		private final double re;
		private final double im;
		public Complex(double re, double im) {
			this.re = re;
			this.im = im;
		}
		public Complex(Complex c) {
			this.re = c.re();
			this.im = c.im();
		}
		public double re() {
			return re;
		}
		public double im() {
			return im;
		}
		public Complex add(Complex c) {
			return new Complex(re + c.re, im + c.im());
		}
		public Complex mul(Complex c) {
			return new Complex(re*c.re - im*c.im, re*c.im + im*c.re);
		}
	}
}