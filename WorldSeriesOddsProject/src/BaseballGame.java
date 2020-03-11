public class BaseballGame {

    /**
     *  i_WinNeeds represents the winning time needs for user i
     *  j_WinNeeds represents the winning time needs for user j
     */
    private int i_WinNeeds;
    private int j_WinNeeds;

    /**
     * get the value of winning time needs for user i
     * @return winning time needs for user i
     */
    public int getI_WinNeeds() {
        return i_WinNeeds;
    }

    /**
     * set the value of winning time needs for user i
     * @param i_WinNeeds winning time needs
     */
    public void setI_WinNeeds(int i_WinNeeds) {
        this.i_WinNeeds = i_WinNeeds;
    }

    /**
     * get the value of winning time needs for user j
     * @return winning time needs for user j
     */
    public int getJ_WinNeeds() {
        return j_WinNeeds;
    }

    /**
     * set the value of winning time needs for user i
     * @param j_WinNeeds winning time needs
     */
    public void setJ_WinNeeds(int j_WinNeeds) {
        this.j_WinNeeds = j_WinNeeds;
    }

    /**
     * constructor with no arguments
     */
    public BaseballGame(){
    }

    /**
     * recursion method to get the winning probability of user i
     * @param i winning time needs for user i
     * @param j winning time needs for user j
     * @return the winning probability
     * @runtime The runtime of this method is Θ（2^n)
     * @precondition i,j larger or equal to 0
     */
    public double recursion(int i, int j) {

        if(i==0 && j>0){
            return 1;
        }else if(i>0 && j==0){
            return 0;
        }else{
            return (recursion(i-1,j)+recursion(i,j-1))/2;
        }
    }

    /**
     * dp method to get the winning probability of user i
     * @param i winning time needs for user i
     * @param j winning time needs for user j
     * @return the winning probability
     * @runtime The runtime of this method is Θ（n^2)
     * @precondition i,j larger or equal to 0
     */
    public double dp(int i,int j){
        double[][] resultSet = new double[i+1][j+1];
        for(int a=0;a<i+1;a++){
            resultSet[a][0] = 0;
        }
        for(int b=0;b<j+1;b++){
            resultSet[0][b] = 1;
        }
        for(int a=1;a<i+1;a++){
            for(int b=1;b<j+1;b++){
                resultSet[a][b] = (resultSet[a-1][b]+resultSet[a][b-1])/2;
            }
        }
        return resultSet[i][j];

    }

    /**
     * main function would run the recursion and dp method simultaneously
     * user need to edit the value of i and j to test different results
     * @param args default arguments
     */
    public static void main(String[] args){
        BaseballGame game = new BaseballGame();
        //edit the value i and j at here
        game.setI_WinNeeds(7);
        game.setJ_WinNeeds(6);
        Timer rec_timer = new Timer();
        rec_timer.start();
        double rec_result = game.recursion(game.getI_WinNeeds(),game.getJ_WinNeeds());
        rec_timer.stop();
        System.out.println("the running time at recursion mode is "+rec_timer);
        System.out.println("the winning probability for player i is: "+rec_result);
        Timer dp_timer = new Timer();
        dp_timer.start();
        double dp_result = game.dp(game.getI_WinNeeds(),game.getJ_WinNeeds());
        dp_timer.stop();
        System.out.println("the winning probability for player i is: "+dp_result);
        System.out.println("the running time at dp mode is "+dp_timer);

    }
}
