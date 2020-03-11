public class test {

    public static boolean subSetSum(int[] x,int n,int k){
        int i = n-1;
        while(i>=0){
            if(x[i]<=k) k = k - x[i];
            if(k == 0) return true;
            i = i-1;
        }
        return false;
    }
    public static void main(String[] args){
        int[] x = {2,3,4,5,8,9,16};
        int k = 41;
        System.out.println(subSetSum(x,x.length,k));
    }

}
