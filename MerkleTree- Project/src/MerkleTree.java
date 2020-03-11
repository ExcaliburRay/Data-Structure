import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class MerkleTree {

    /**
     * Read the file from the local address and add the text into SinglyLinkedList by line
     * if the quantity of nodes is not even, copy the last node and add it to the end of the list
     * @param fileName the name of the file
     * @return the text information stored in the SinglyLinkedList
     * @throws IOException
     * @precondition the file should under the project and stay in the same routine with src
     * @postcondtion read till the end of the file
     */
    public static SinglyLinkedList fileReader(String fileName) throws IOException {
        SinglyLinkedList text = new SinglyLinkedList();
        BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
        String line;
        String temp = "";
        while((line = br.readLine())!= null){
            text.addAtEndNode(line);
            temp =line;
        }
        if(text.countNodes()%2!=0){
            text.addAtEndNode(temp);
        }
        text.reset();
        return text;
    }

    /**
     * the hashing function which transfer the original text into hash information
     * @param text the name of the file
     * @return the hashing value of the original string
     * @throws NoSuchAlgorithmException
     */
    public static String hashing(String str) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(str.getBytes(StandardCharsets.UTF_8));
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <= 31; i++) {
            byte b = hash[i];
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    /**
     * get the root node of the MerkleTree
     * use the recursion function to check the quantity of nodes whether even
     * if it is not even, copy the last node and add it to the end of the list
     * treat two nodes as a pair, get their hashing value one by one to get the upper layer
     * when the quantity of upper layer become one, out of recursion
     * @param layer the left node in the bottom layer in hashing value
     * @return the hashing value of the root node
     * @throws NoSuchAlgorithmException
     */
    public static String getRootNode(SinglyLinkedList layer) throws NoSuchAlgorithmException {
        layer.reset();
        SinglyLinkedList internalLayer = new SinglyLinkedList();
        int numOfNodes = layer.countNodes();
        String parentHashValue = "";
        for(int i=0;i<numOfNodes/2;i++){
            String left = (String)layer.next();
            String right = (String)layer.next();
            String parent = left+right;
            parentHashValue = hashing(parent);
            internalLayer.addAtEndNode(parentHashValue);
        }
        if(internalLayer.countNodes() == 1){
            internalLayer.reset();
            String root = (String)internalLayer.next();
            return root;
        }
        if(internalLayer.countNodes()%2!=0){
            internalLayer.addAtEndNode(parentHashValue);
        }
        return getRootNode(internalLayer);
    }

    /**
     * hashing the value of bottom layer
     * use the hashing function to get the hashing value of the original text character by character
     * @param list the text information in the file reader
     * @return the hashing value of this file in SinglyLinkedList type
     * @throws NoSuchAlgorithmException
     */
    public static SinglyLinkedList constructLayerOne(SinglyLinkedList list) throws NoSuchAlgorithmException {
        list.reset();
        String line;
        SinglyLinkedList listInLayerOne = new SinglyLinkedList();
        while(list.hasNext()){
            line = (String)list.next();
            String hashcode = hashing(line);
            listInLayerOne.addAtEndNode(hashcode);
        }
        listInLayerOne.reset();
        return listInLayerOne;
    }

    /**
     * main function
     * after the execution, the Merkle Root of CrimeLatLonXY.csv is matched
     * A5A74A770E0C3922362202DAD62A97655F8652064CCCBE7D3EA2B588C7E07B58
     * running in a loop until user input the correct file address
     * @param args default declaration
     * @precondition user input should only be the name of the file
     * DO NOT INCLUDE THE ROUTINE
     * THE FILES SHOULD BE IN THE SAME POSITION OF SRC
     * EG INPUT: CrimeLatLonXY.csv
     */
    public static void main(String[] args) {
        //String[] loc = {"./CrimeLatLonXY.csv","./CrimeLatLonXY1990_Size2.csv","./CrimeLatLonXY1990_Size3.csv","./smallFile.txt"};
        boolean status = false;
        while(status == false){
            System.out.println("Please input the name of the file including the file format:");
            Scanner sc = new Scanner(System.in);
            String location = "./"+sc.nextLine();
            SinglyLinkedList list;
            //SinglyLinkedList initialList = new SinglyLinkedList();
            SinglyLinkedList layerOne;
            try{
                list = fileReader(location);
                layerOne = constructLayerOne(list);
                String rootNode = getRootNode(layerOne);
                System.out.print("The Merkle root node of this file is: "+rootNode);
                status = true;
            }catch(Exception e){
                System.out.println(e);
                System.out.println("Your input is invalid, please try again.");
            }
        }

    }
}
