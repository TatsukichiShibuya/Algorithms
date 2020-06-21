import java.io.*;
import java.util.*;

public class Main {
    static void solve(InputReader in, PrintWriter out){

        WaveletMatrix wm = new WaveletMatrix(new int[]{1,4,3,2,5,7,8,6,9});
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            sb.append(wm.access(i)).append(' ');
        }
        out.println(sb.toString());
    }
    public static class WaveletMatrix{
        // int配列の高速処理を行う
        // n = 配列長、m = データ一つの大きさ(intなら32)
        // 元のデータより10倍くらい大きくなる
        byte[][] index;  // 索引(byteにするかは悩み中)
        CumulativeSum[] CSum;
        int[] border;  // border[i] = k ならindexのi番目をソートしたものはk以降が1(それ以前が0)
        public WaveletMatrix(int[] t){  // intの列tを扱う(長さは10^6までを想定)

            //索引の作成
            int[] tmp = t.clone();
            index = new byte[32][t.length];  // longなら64とか、適切な値に
            border = new int[32];
            ArrayDeque<Integer> deq0, deq1;
            for (int i = 1; i <= 32; i++) {
                deq0 = new ArrayDeque<>();
                deq1 = new ArrayDeque<>();
                for (int j = 0; j < t.length; j++) {
                    if(((tmp[j]>>(32-i))&1)==1){  // 上からi番目のbitが立っているか
                        deq1.addFirst(tmp[j]);
                        index[i-1][j] = 1;
                    }else{
                        deq0.addFirst(tmp[j]);
                    }
                }
                int c = 0;
                while(!deq0.isEmpty()) tmp[c++] = deq0.pollLast();
                border[i-1] = c;
                while(!deq1.isEmpty()) tmp[c++] = deq1.pollLast();
            }

            //累積和に変換
            CSum = new CumulativeSum[32];
            for (int i = 0; i < 32; i++) {
                CSum[i] = new CumulativeSum(index[i]);
            }
        }
        int access(int k){  // t[k] O(m)
            int res = 0;
            int c = 1<<(32-1);
            int now = k;
            for (int i = 0; i < 32; i++) {
                if(CSum[i].access(now)==1){
                    res |= c;
                    now = border[i]+CSum[i].rank(now);
                }else{
                    now = now-CSum[i].rank(now);
                }
                if(i==0) c = 1 << 30;  // 正負フラグの処理(もっと賢い方法がありそう)
                else c >>= 1;
            }
            return res;
        }
        int rank(int c, int l, int r){ return rank(c, r)-rank(c, l);}  // 外部呼び出し用
        int rank(int c, int k){  // t[0, k)でのcの出現回数(k>0) O(m)
            //作成中
            return 0;
        }
        int select(int c, int k){  // t中のk番目のcの位置(k>0)
            //作成中
            return 0;
        }
        int quantile(int l, int r, int k){  // t[l, r)でのk番目に小さい値
            //作成中
            return 0;
        }
        int top(int l, int r, int k){  // t[l, r)での出現頻度の多い数字を頻度と合わせてk個
            //作成中
            return 0;
        }
        int sum(int l, int r){  // t[l, r)の合計
            //作成中
            return 0;
        }
        int intersect(int l1, int r1, int l2, int r2){  //t[l1, r1)とt[l2, r2)に共通して出現する値とその頻度
            //作成中
            return 0;
        }

    }

    public static class CumulativeSum{  // 長さ10^6までのbit列を想定
        int[] sum;
        CumulativeSum(byte[] bit){  // bitはbit列(byteにするかは悩み中)
            sum = new int[bit.length];
            sum[0] = bit[0];
            for (int i = 1; i < bit.length; i++) {
                sum[i] = sum[i-1]+bit[i];
            }
        }
        int access(int k){
            return (k==0)?sum[0]:sum[k]-sum[k-1];
        }
        int rank(int a, int b){  // [a,b)に含まれる1の数(0 <= a < b)
            return rank(b)-rank(a);
        }
        int rank(int k){  // [0,k)に含まれる1の数
            return (k==0)?0:sum[k-1];
        }
        int select(int k){  // k番目の1の位置(k>0)
            int l = -1, r = sum.length;
            while(r-l>1){
                int mid = (l+r)/2;
                if(sum[mid]==k) r = mid;
                else if(sum[mid]<k) l = mid;
                else if(sum[mid]>k) r = mid;
            }
            return r;  // r=nで存在せず
        }
    }
    public static class SuccinctDictionaries{
        //  完備辞書は作成中、累積和で代用
    }

    public static void main(String[] args) throws Exception{
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        solve(in, out);
        out.close();
    }
    //競プロ用の高速入力、上とは関係ない
    public static class InputReader{
        private BufferedReader br;
        private StringTokenizer st;
        public InputReader(InputStream is){
            br = new BufferedReader(new InputStreamReader(is));
            st = null;
        }
        public String ns(){
            if(st == null || !st.hasMoreTokens()){
                try{
                    st = new StringTokenizer(br.readLine());
                }catch (Exception e){
                    throw new RuntimeException(e);
                }
            }
            return st.nextToken();
        }
        public long nl(){
            return Long.parseLong(ns());
        }
        public int ni(){
            return Integer.parseInt(ns());
        }
        public Double nd(){
            return Double.parseDouble(ns());
        }
        public int[] ni(int n){
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = ni();
            }
            return a;
        }
        public long[] nl(int n){
            long[] a = new long[n];
            for (int i = 0; i < n; i++) {
                a[i] = nl();
            }
            return a;
        }
        public double[] nd(int n){
            double[] a = new double[n];
            for (int i = 0; i < n; i++) {
                a[i] = nd();
            }
            return a;
        }
    }
}