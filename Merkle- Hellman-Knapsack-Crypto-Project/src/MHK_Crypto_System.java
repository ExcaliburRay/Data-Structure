import java.math.BigInteger;
import java.util.*;

public class MHK_Crypto_System {

    /**
     * q represents one random number which higher than the sum of super increasing sequence
     * r represents one number which is coprime to q
     */
    private BigInteger q;
    private BigInteger r;

    /**
     * obtain the value of q
     * @return the value of q
     */
    public BigInteger getQ() {
        return q;
    }

    /**
     * obtain the value of r
     * @return the value of r
     */

    public BigInteger getR() {
        return r;
    }

    /**
     * modify the value of q
     * @param q one random number which higher than the sum of super increasing sequence
     */
    public void setQ(BigInteger q) {
        this.q = q;
    }

    /**
     * modify the value of r
     * @param r one number which is coprime to q
     */
    public void setR(BigInteger r) {
        this.r = r;
    }

    /**
     * transfer the String information into Binary at eight digits
     * @param s the user input information
     * @return transfer the user input String into byte in String format
     */
    public String convertCharByte(String s) {
        char[] c = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < c.length; i++) {
            String str = Integer.toBinaryString(c[i]);
            while (str.length() < 8) {
                str = "0" + str;
            }
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * calculate the CoPrime number
     * @param q one random number which higher than the sum of super increasing sequence
     * @return the coPrime number of q
     */
    public BigInteger generateCoPrime(BigInteger q) {
        BigInteger coPrime;
        while (true) {
            Random rd = new Random();
            coPrime = new BigInteger(String.valueOf(rd.nextInt(q.intValue())));
            BigInteger val = coPrime.gcd(q);
            if (val.intValue() == 1) {
                break;
            }
        }
        return coPrime;
    }

    /**
     * we already know the super increasing sentence of the private key
     * we use the following function, through generate random number to get the value of q and r
     * @param w the super increasing sequence
     */
    public void generatePrivateKey(SinglyLinkedList w) {
        // the init of super increasing sequence of integers
        w.reset();
        BigInteger sum = new BigInteger("0");
        while (w.hasNext()) {
            BigInteger bi = new BigInteger(String.valueOf(w.next()));
            sum = sum.add(bi);
        }
        Random rd = new Random();
        setQ(sum.add(new BigInteger(String.valueOf(rd.nextInt(1000)))));
        setR(generateCoPrime(getQ()));
    }

    /**
     * we already know the super increasing sentence of the private key
     * we use the function (private key * r) mod q to get the specific public key
     * @param w the super increasing sentence
     * @return the public key which stored in SinglyLinkedList
     */
    public SinglyLinkedList generatePublicKey(SinglyLinkedList w) {
        w.reset();
        SinglyLinkedList b = new SinglyLinkedList();
        while (w.hasNext()) {
            BigInteger val = new BigInteger(String.valueOf(w.next()));
            BigInteger pVal = val.multiply(getR()).mod(getQ());
            b.addAtEndNode(pVal.intValue());
        }
        b.reset();
        return b;
    }

    /**
     * we use the user input in binary type and the public key the encryption the information
     * the method is using the public key multiply the binary info the get the encryption value
     * store the encryption value into one SinglyLinkedList
     * @param str the user input in binary type
     * @param b public key
     * @return encryption value in SinglyLinkedList type
     */
    public SinglyLinkedList encryption(String str, SinglyLinkedList b) {
        b.reset();
        SinglyLinkedList beta = new SinglyLinkedList();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < str.length() - 7; ) {
            list.add(str.substring(i, i + 8));
            i += 8;
        }
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String pk = it.next();
            String[] info = pk.split("");
            int val = 0;
            int count = 0;
            while (b.hasNext()) {
                val += Integer.valueOf(info[count]) * (int) b.next();
                count++;
            }
            b.reset();
            beta.addAtEndNode(val);
        }
        return beta;
    }

    /**
     * this method is aim to decrypt the encryption value
     * At first, we calculate the modular inverse of r to q
     * Then we find the largest value in super increasing sequence
     * due to its characteristic, we reverse the super increasingList, the first node will be the largest
     * we treat the remaining value larger than super increasingList as 1, other as 0.
     * we transfer the binary value we get into original text
     * @param beta the encryption value in SinglyLinkedList type
     * @param w the super increasing sequence
     * @return the original text
     */
    public String decryption(SinglyLinkedList beta, SinglyLinkedList w) {
        //get the modular inverse of r mod q
        beta.reset();
        w.reset();
        BigInteger modInverse = getR().modInverse(getQ());
        //the list of Decryption
        SinglyLinkedList value = new SinglyLinkedList();
        //the list after the processing of decryption
        SinglyLinkedList binaryValue = new SinglyLinkedList();
        while (beta.hasNext()) {
            BigInteger v = BigInteger.valueOf((int) beta.next()).multiply(modInverse).mod(getQ());
            value.addAtEndNode(v);
        }
        value.reset();


        //reverse the SinglyLinkedList w
        SinglyLinkedList reverseW = new SinglyLinkedList();
        while (w.hasNext()) {
            reverseW.addAtFrontNode(w.next());
        }
        reverseW.reset();

        //calculate the value of decryption
        while (value.hasNext()) {
            BigInteger bigCurrent = (BigInteger) value.next();
            int current = bigCurrent.intValue();
            StringBuilder sb = new StringBuilder();
            while (reverseW.hasNext()) {
                int point = (int) reverseW.next();
                if (current >= point) {
                    sb.append(1);
                    current = current - point;
                } else {
                    sb.append(0);
                }
            }
            reverseW.reset();
            String actualValue = sb.reverse().toString();
            binaryValue.addAtEndNode(actualValue);
        }
        binaryValue.reset();

        //convert byte into string
        StringBuilder finalResult = new StringBuilder();
        while (binaryValue.hasNext()) {
            String str = binaryValue.next().toString();
            int num = Integer.parseInt(str, 2);
            String character = String.valueOf((char)num);
            finalResult.append(character);
        }

        return finalResult.toString();
    }

    /**
     * the main function to encryption and decryption
     * @param args default declaration
     */
    public static void main(String[] args) {
        MHK_Crypto_System pass = new MHK_Crypto_System();
        //user interaction to input information
        System.out.println("Enter a string and I will encrypt it as single large integer.");
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        while (str.length() > 80) {
            System.out.println("the string entered can not exceed 80 characters. please try again");
            str = sc.nextLine();
        }
        //convert string into byte
        System.out.println("Clear text:");
        System.out.println(str);
        System.out.println("Number of clear text bytes = " + str.length());
        String result = pass.convertCharByte(str);
        //add nodes into SinglyLinkedList and generate private key
        int[] spIncreasingSeq = {2, 7, 11, 21, 42, 89, 180, 354};
        SinglyLinkedList w = new SinglyLinkedList();
        for (int i = 0; i < spIncreasingSeq.length; i++) {
            w.addAtEndNode(spIncreasingSeq[i]);
        }
        w.reset();
        pass.generatePrivateKey(w);
        //generate public key
        SinglyLinkedList b = pass.generatePublicKey(w);
        //Encrypt the information
        System.out.print(str);
        System.out.println(" is encrypted as");
        SinglyLinkedList beta = pass.encryption(result, b);
        beta.reset();
        while (beta.hasNext()) {
            System.out.print(beta.next());
        }
        //Decrypt the information
        String finalResult = pass.decryption(beta, w);
        System.out.println();
        System.out.println("Result of decryption: " + finalResult);
    }
}
