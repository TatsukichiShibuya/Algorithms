import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class GS_Algo_SA {

	public static void main(String[] args) {

		GS g = new GS(new Scanner(System.in));
		g.run();
		g.SA(1, 2);
	}


	public static class GS {

		private final int n;
		private Rank[] m_rank_que;
		private final int[][] m_rank;
		private final int[][] f_rank;
		private int[] m;
		private int[] f;
		private boolean flag;
		private double err;

		GS(Scanner sc){
			this.n = Integer.parseInt(sc.nextLine());
			this.m_rank_que = new Rank[n];
			this.m_rank = new int[n][n];
			this.f_rank = new int[n][n];
			this.m = new int[n];
			this.f = new int[n];
			this.flag = true;
			this.err = 0;
			for (int i = 0; i < n; i++) {
				m[i] = -1;
				String line = sc.nextLine();
				m_rank_que[i] = new Rank(line);
				m_rank[i] = str_to_int(line);
			}

			for (int i = 0; i < n; i++) {
				f[i] = -1;
				for (int j = 0; j < n; j++)
					f_rank[i][sc.nextInt()-1] = j;
			}
		}

		public void run() {
			for (int i = 0; i < n; i++) {//高々n回で決まる
				flag = true;
				for (int j = 0; j < n; j++) {//男のj番目を考える
					if(m[j] == -1) {
						flag = false;
						while(true) {
							int best = m_rank_que[j].pollLast();
							if(f[best] == -1) {
								f[best] = j;
								m[j] = best;
								break;
							}else if(f_rank[best][j] < f_rank[best][f[best]]) {
								m[f[best]] = -1;
								f[best] = j;
								m[j] = best;
								break;
							}
						}

					}
				}
				if(flag) break;
			}
			err = error(m, f);
			print_pair(m);
			System.out.println(err);
		}

		public void SA(int seed, int num) {//焼きなましseedはランダムのシード，numは組み替える辺の数(未実装)
			Random rand = new Random(seed);
			double T = 100;
			ArrayList<Integer> p = new ArrayList<Integer>();
			for (int i = 0; i < n; i++) p.add(i);
			boolean flag2 = false;
			double best_err = err;
			int[] best_m = m;
			int[] best_f = f;
			int count = 0;

			while(T > 1) {
				Collections.shuffle(p);
				int w1 = p.get(0); int w2 = p.get(1);
				int h1 = f[p.get(0)]; int h2 = f[p.get(1)];
				flag2 = true;
				for (int i = 0; i < n; i++) {
					boolean f1 = m_rank[h1][w2]>m_rank[h1][i]&&f_rank[i][f[i]]>f_rank[i][h1];
					boolean f2 = m_rank[h2][w1]>m_rank[h2][i]&&f_rank[i][f[i]]>f_rank[i][h2];
					boolean f3 = f_rank[w1][h2]>f_rank[w1][i]&&m_rank[i][m[i]]>m_rank[i][w1];
					boolean f4 = f_rank[w2][h1]>f_rank[w2][i]&&m_rank[i][m[i]]>m_rank[i][w2];
					if(f1||f2||f3||f4) {//不安定
						flag2 = false;
						break;
					}
				}
				if(flag2) {
					double before = f_rank[w1][h1]+f_rank[w2][h2]+m_rank[h1][w1]+m_rank[h2][w2];
					double after = f_rank[w1][h2]+f_rank[w2][h1]+m_rank[h1][w2]+m_rank[h2][w1];
					double bias = (after-before)*2/n+1;
					if(after < before || T/bias>rand.nextInt(100)) {//入れ替える
						f[w1] = h2;
						f[w2] = h1;
						m[h1] = w2;
						m[h2] = w1;
						T -= 1;
						System.out.printf("%f -> %f\n", err, err+after-before);
						err += after-before;
						count++;
						if(err < best_err) {
							best_err = err;
							best_m = m;
							best_f = f;
						}
					}else {
						System.out.println("don't change(line117)");
					}
				}else{
					System.out.println("don't change(line120)");
					T = T*0.99;				}
			}
			print_pair(best_m);
			System.out.println(best_err);
			System.out.printf("%d回更新", count);
		}

		public double get_error() {
			return err;
		}

		public int error(int[] a, int[] b) {//順位の和として損失を定義
			int e = 0;
			for (int i = 0; i < n; i++) {
				e += m_rank[i][a[i]];
				e += f_rank[i][b[i]];
			}
			return e;
		}

		public void print_pair(int[] a) {
			for (int i = 0; i < n; i++) {
				System.out.printf("(%d, %d) ",i+1 ,a[i]+1);
				if(i != 0 && i%5 == 0) System.out.println();
			}
		}

		public int[] str_to_int(String s) {
			String[] S = s.split(" ");
			int[] res = new int[n];
			for (int i = 0; i <S.length ; i++) {
				res[i] = Integer.parseInt(S[i])-1;
			}
			return res;
		}
	}


	public static class Rank{

		ArrayDeque<Integer> rank;
		Rank(String S){
			this.rank = new ArrayDeque<Integer>();
			String[] s = S.split(" ");
			for (int i = 0; i < s.length; i++) {
				this.rank.addFirst(Integer.parseInt(s[i])-1);
			}
		}

		public Integer pollLast() {
			return rank.pollLast();
		}
	}
}
