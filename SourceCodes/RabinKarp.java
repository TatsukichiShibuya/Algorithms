public static class RabinKarp{
    private char[] S, T;
    private int n, m; // n==s.len, m=t.len
    private long rad1, rad2; // 基数
    ArrayList<Integer> list;

    public RabinKarp(String s, String t, long rad1, long rad2){
        his.S = s.toCharArray(); this.T = t.toCharArray();
        this.n = s.length(); this.m = t.length();
        this.rad1 = rad1; this.rad2 = rad2;
    }
    public RabinKarp(String s, String t){ // 基数default
        this(s,t,1391121,231111);
    }
    public void rabinkarp(){
        list = new ArrayList<>();

        long[] h1 = new long[n + 1], h2 = new long[n + 1]; // 末尾に0を加えておく // hashの累積(右から)
        long r1 = 1, r2 = 1;
        for (int i = n-1; i >= 0; i--) { // hashの累積
            h1[i] = (h1[i+1]+S[i]*r1)%mod;
            h2[i] = (h2[i+1]+S[i]*r2)%mod;
            r1 *= rad1; r1 %= mod;
            r2 *= rad2; r2 %= mod;
        }

        long hashT1 = 0, hashT2 = 0; // Tをrad1, rad2でhash化したもの
        r1 = 1; r2 = 1;
        for (int i = m-1; i >= 0; i--) { // 検索する部分列のhash化
            hashT1 += (T[i]*r1)%mod; hashT1 %= mod;
            hashT2 += (T[i]*r2)%mod; hashT2 %= mod;
            r1 *= rad1; r1 %= mod;
            r2 *= rad2; r2 %= mod;
        }

        boolean flag1 = false, flag2 = false;
        for (int i = 0; i + m <= n; i++) { // S[i,i+m-1]==T を判定
            r1 = pow(pow(rad1, n-m-i),mod-2)*(h1[i]- h1[i+m])%mod; if(r1<0) r1 += mod;
            r2 = pow(pow(rad2, n-m-i),mod-2)*(h2[i]- h2[i+m])%mod; if(r2<0) r2 += mod;
            flag1 = r1 == hashT1; // rad1を基数にした時、hashが一致するか
            flag2 = r2 == hashT2; // rad2を基数にした時、hashが一致するか
            if(flag1&&flag2) list.add(i);
        }
    }
    private long pow(long x, long n){
        long res = 1;
        while(n > 0){
            if ((n & 1) == 1) {
                res *= x;
                res %= mod;
            }
            x *= x;
            x %= mod;
            n >>= 1;
        }
        return res;
    }
}
