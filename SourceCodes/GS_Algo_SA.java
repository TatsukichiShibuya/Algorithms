import java.util.ArrayDeque;
import java.util.Scanner;

public class GS_Algo_SA {

	public static void main(String[] args) {

		GS g = new GS(new Scanner(System.in));
		g.run();
		g.print_pair();
		System.out.println(g.get_error());
	}

	public static class GS {

		private final int n;
		private Rank[] m_rank_que;
		private final int[][] m_rank;
		private final int[][] f_rank;
		private int[] m;
		private int[] f;
		private boolean flag;
		private int err;

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
						int vest = m_rank_que[j].pollLast();
						if(f[vest] == -1) {
							f[vest] = j;
							m[j] = vest;
						}else if(f_rank[vest][j] < f_rank[vest][f[vest]]) {
							m[f[vest]] = -1;
							f[vest] = j;
							m[j] = vest;
						}
					}
				}
				if(flag) break;
			}
			err = error(m, f);
		}
		public int get_error() {
			return err;
		}
		public int error(int[] a, int[] b) {
			int e = 0;
			for (int i = 0; i < n; i++) {
				e += m_rank[i][m[i]];
				e += f_rank[i][f[i]];
			}
			return e;
		}
		public void print_pair() {
			for (int i = 0; i < n; i++) {
				System.out.printf("(%d, %d) ",i+1 ,m[i]+1);
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
