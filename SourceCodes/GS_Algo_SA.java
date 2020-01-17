import java.util.ArrayDeque;
import java.util.Scanner;

public class GS_Algo_SA {

	public static void main(String[] args) {

		GS g = new GS(new Scanner(System.in));
		g.run();
		g.print_pair();
	}

	public static class GS {

		private final int n;
		private Rank[] m_rank_que;
		private final int[][] f_rank;
		private int[] m;
		private int[] f;
		private boolean flag;

		GS(Scanner sc){
			this.n = Integer.parseInt(sc.nextLine());
			this.m_rank_que = new Rank[n];
			this.f_rank = new int[n][n];
			this.m = new int[n];
			this.f = new int[n];
			this.flag = true;
			for (int i = 0; i < n; i++) {
				m[i] = -1;
				m_rank_que[i] = new Rank(sc.nextLine());
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
						while(true){
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
		}
		public void print_pair() {
			for (int i = 0; i < n; i++) {
				System.out.printf("(%d, %d) ",i+1 ,m[i]+1);
				if(i != 0 && i%5 == 0) System.out.println();
			}
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
