import java.math.BigInteger;
import java.util.Scanner;

public class ReversePolishNotation {

    /**
     * this method would reads a line from the user,
     * evaluates the expression and displays the result.
     * It continues to do this until the user hits
     * the return key with no input or it encounters an error.
     * @param rbt Red Black Tree
     * @return the display information
     * @throws Exception contains different kinds of runtime exception
     */
    public static String display(RedBlackTree rbt) throws Exception {
        Scanner sc = new Scanner(System.in);
        String line[] = sc.nextLine().split(" ");
        String display = "";
        String id;
        BigInteger a;
        BigInteger b;
        int unaryTime = 0;
        BigInteger temp = BigInteger.valueOf(Integer.MIN_VALUE);

        //We will use the standard binary operators (+, -, *, / , %, =) with their usual meanings.
        // The assignment operator “=” requires that the left hand side of the expression be a variable.
        // We will also use a unary minus. The unary minus will be represented with the tilde character “~”.
        // Finally, we will use a ternary operation - powerMod.
        // powerMod computes x to the y modulo z.
        // It will be represented by the circumflex character “^”.
        if(line.length==1){
            if(rbt.contains(line[0])){
                display = String.valueOf(rbt.closeBy(line[0]));
            }else if(!line[0].equals("<return>")){
                display = "Exception";
                throw new IllegalArgumentException(display);
            }
        }
        for(int i=0;i<line.length;i++){
            switch (line[i]){
                case "=":
                    id = line[0];
                    if(isNumber(id)){
                        throw new Exception(id+" not an lvalue");
                    }
                    if(display.equals("")){
                        display = line[i-1];
                    }
                    BigInteger value = new BigInteger(display);
                    rbt.insert(id,value);
                    display = String.valueOf(rbt.closeBy(id));
                    break;
                case "+":
                    try{
                        a = new BigInteger(line[i-2]);
                    }catch (Exception e){
                        if(rbt.contains(line[i-2])){
                            a = rbt.closeBy(line[i-2]);
                        }else{
                            if(display.equals("")){
                                throw new Exception("no variable "+line[i-1]);
                            }
                            a = new BigInteger(display);
                        }
                    }
                    try{
                        b = new BigInteger(line[i-1]);
                    }catch (Exception e){
                        if(rbt.contains(line[i-1])){
                            b = rbt.closeBy(line[i-1]);
                        }else{
                            if(display.equals("")){
                                throw new Exception("no variable "+line[i-1]);
                            }
                            b = new BigInteger(display);
                        }
                    }
                    display = String.valueOf(a.add(b));
                    break;
                case "-":
                    try{
                        a = new BigInteger(line[i-2]);
                    }catch (Exception e){
                        if(rbt.contains(line[i-2])){
                            a = rbt.closeBy(line[i-2]);
                        }else{
                            if(display.equals("")){
                                throw new Exception("no variable "+line[i-1]);
                            }
                            a = new BigInteger(display);
                        }
                    }
                    try{
                        b = new BigInteger(line[i-1]);
                    }catch (Exception e){
                        if(rbt.contains(line[i-1])){
                            b = rbt.closeBy(line[i-1]);
                        }else{
                            if(display.equals("")){
                                throw new Exception("no variable "+line[i-1]);
                            }
                            b = new BigInteger(display);
                        }
                    }
                    display = String.valueOf(a.subtract(b));
                    break;
                case "*":
                    try{
                        a = new BigInteger(line[i-2]);
                    }catch (Exception e){
                        if(rbt.contains(line[i-2])){
                            a = rbt.closeBy(line[i-2]);
                        }else{
                            if(display.equals("")){
                                throw new Exception("no variable "+line[i-1]);
                            }
                            a = new BigInteger(display);
                        }
                    }
                    try{
                        b = new BigInteger(line[i-1]);
                    }catch (Exception e){
                        if(rbt.contains(line[i-1])){
                            b = rbt.closeBy(line[i-1]);
                        }else{
                            if(display.equals("")){
                                throw new Exception("no variable "+line[i-1]);
                            }
                            b = new BigInteger(display);
                        }
                    }
                    display = String.valueOf(a.multiply(b));
                    break;
                case "/":
                    try{
                        a = new BigInteger(line[i-2]);
                    }catch (Exception e){
                        if(rbt.contains(line[i-2])){
                            a = rbt.closeBy(line[i-2]);
                        }else{
                            if(display.equals("")){
                                throw new Exception("no variable "+line[i-1]);
                            }
                            a = new BigInteger(display);
                        }
                    }
                    try{
                        b = new BigInteger(line[i-1]);
                    }catch (Exception e){
                        if(rbt.contains(line[i-1])){
                            b = rbt.closeBy(line[i-1]);
                        }else{
                            if(display.equals("")){
                                throw new Exception("no variable "+line[i-1]);
                            }
                            b = new BigInteger(display);
                        }
                    }
                    display = String.valueOf(a.divide(b));
                    break;
                case "%":
                    try{
                        a = new BigInteger(line[i-2]);
                    }catch (Exception e){
                        if(rbt.contains(line[i-2])){
                            a = rbt.closeBy(line[i-2]);
                        }else{
                            if(display.equals("")){
                                throw new Exception("no variable "+line[i-1]);
                            }
                            a = new BigInteger(display);
                        }
                    }
                    try{
                        b = new BigInteger(line[i-1]);
                    }catch (Exception e){
                        if(rbt.contains(line[i-1])){
                            b = rbt.closeBy(line[i-1]);
                        }else{
                            if(display.equals("")){
                                throw new Exception("no variable "+line[i-1]);
                            }
                            b = new BigInteger(display);
                        }
                    }
                    display = String.valueOf(a.divideAndRemainder(b)[1]);
                    break;
                case "^":
                    BigInteger base;
                    try{
                        base = new BigInteger(line[i-3]);
                    }catch (ArrayIndexOutOfBoundsException aie){
                        throw new Exception("stack underflow exception");
                    } catch (NumberFormatException nfe){
                        if(rbt.contains(line[i-3])){
                            base = rbt.closeBy(line[i-3]);
                        }else{
                            base = temp;
                        }
                    }
                    BigInteger exp = new BigInteger(line[i-2]);
                    BigInteger mod = new BigInteger(line[i-1]);
                    display = String.valueOf(base.modPow(exp,mod));
                    break;
                case "~":
                    a = new BigInteger("0");
                    if(unaryTime == 0){
                        try{
                            b = new BigInteger(line[i-1]);
                        }catch (Exception e){
                            b = rbt.closeBy(display);
                        }
                        temp = a.subtract(b);
                        unaryTime ++;
                    }else{
                        temp = a.subtract(temp);
                    }
                    display = String.valueOf(temp);
                    break;
                case "<return>":
                    display = "terminating";
                    break;
            }
        }
        return display;
    }

    /**
     * judge one string can transfer to BigInteger or not
     * @param str normal string
     * @return true means can be cast to BigInteger
     */
    public static boolean isNumber(String str){
        try{
            BigInteger num = new BigInteger(str);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     * main function
     * @param args default argument
     * @throws Exception runtime exception
     */
    public static void main(String[] args) throws Exception {
        RedBlackTree rbt = new RedBlackTree();
        System.out.println("java ReversePolishNotation");
        while (true){
            String output = display(rbt);
            System.out.println(output);
            if(output.equals("terminating")|| output.equals("Exception"))
                break;
        }
    }
}
