import java.io.*;
import java.util.Scanner;

public class RedBlackTreeTest {

    /**
     * constructor with non-argument
     */
    public RedBlackTreeTest() {

    }

    /**
     * This is a function that used to read the file from specific routine
     * and then add each word int the file to generate the red black tree
     * @param location the location of the file
     * @return one forming red black tree
     * @throws IOException throw the exception in IO operation
     * @precondition the location should be valid
     */
    public static RedBlackTree fileReader(String location) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(location)));
        RedBlackTree RBTree = new RedBlackTree();
        String sentence;
        while((sentence = br.readLine())!= null){
            RBTree.insert(sentence);
        }
        br.close();
        return RBTree;
    }

    /**
     * display the initial RedBlackTree information
     * related its number of nodes, height and time complexity
     * @param RBTree RedBlackTree
     * @precondition RedBlackTree cannot be none
     */
    public static void initial(RedBlackTree RBTree){
        int nodes = RBTree.getSize();
        int height = RBTree.height();
        double time = 2*(Math.log(nodes+1)/Math.log(2));
        System.out.println("Red Black Tree is loaded with "+nodes+" words");
        System.out.println("Initial tree height is "+height);
        System.out.println("Never worse than 2 * Lg( n+1) = "+time);
    }

    /**
     * give the response for user's different command
     * @param RBTree RedBlackTree
     * @param sc the function of Scanner
     * @throws IOException throw the exception in IO operation
     * @precondition RedBlackTree cannot be none, sc should be initialized
     */
    public static void operation(RedBlackTree RBTree, Scanner sc) throws IOException {
        String menu = "Legal commands are: \n" +
                "d   display the entire word tree with a level order traversal.\n" +
                "s    print the words of the tree in sorted order (using an inorder traversal).\n" +
                "r    print the words of the tree in reverse sorted order (reverse inorder traversal). \n" +
                "c   <word> to spell check this word.\n" +
                "a   <word> add this word to tree.\n" +
                "f   <fileName> to check this text file for spelling errors.\n" +
                "i   display the diameter of the tree.\n" +
                "m  view this menu.\n" +
                "!   to quit.\n";
        System.out.println(menu);
        String str = sc.nextLine();
        String command;
        String content = "";
        if(str.contains(" ")){
            command = str.split(" ")[0].toLowerCase();
            content = str.split(" ")[1];
        }else{
            command = str;
        }

        while(true){
            switch (command){
                case ">d":
                    RBTree.levelOrderTraversal();
                    break;
                case ">s":
                    RBTree.inOrderTraversal();
                    break;
                case ">r":
                    RBTree.reverseOrderTraversal();
                    break;
                case ">c":
                    String result = RBTree.closeBy(content);
                    if(result.equals(content)){
                        System.out.println("Found "+content+" after "+RBTree.getRecentCompares()+" comparisons");
                        RBTree.setRecentCompares(1);
                    }else{
                        System.out.println(content+" Not in dictionary. Perhaps you mean");
                        System.out.println(result);
                        RBTree.setRecentCompares(1);
                    }
                    break;
                case ">a":
                    if(!RBTree.contains(content)){
                        RBTree.insert(content);
                        System.out.println(content+" was added to dictionary.");
                    }else{
                        System.out.println("The word ‘"+content+"’ is already in the dictionary.");
                    }
                    break;
                case ">f":
                    String filename = content;
                    BufferedReader br = new BufferedReader(new FileReader(new File("./"+filename)));
                    String sentences;
                    int error = 0;
                    while((sentences = br.readLine())!= null){
                        String[] words = sentences.split("[^a-zA-Z0-9']+");
                        for(int i=0;i<words.length;i++){
                            if(!RBTree.contains(words[i])){
                                if(!words[i].equals("")) {
                                    error++;
                                    System.out.println("'"+words[i]+"' was not found in dictionary.");
                                }
                            }
                        }
                    }
                    if(error == 0){
                        System.out.println("No spelling errors found.");
                    }
                    break;
                case ">i":
                    int initialLength = 2;
                    int diameter = RBTree.diameter()+initialLength;
                    System.out.println("The diameter of the tree is "+diameter);
                    break;
                case ">m":
                    System.out.println(menu);
                    break;
                case ">!":
                    System.out.println("Bye");
                    return;
                default:
                    System.out.println("The input is invalid");
            }
            str =sc.nextLine();
            if(str.contains(" ")){
                command = str.split(" ")[0];
                content = str.split(" ")[1];
            }else{
                command = str;
            }
        }

    }

    /**
     * the main function
     * user need to input the name of processing file
     * then follow the guide in screen to perform
     * @param args the default arguments
     */
    public static void main(String[] args){
        boolean status = false;
        while(status == false){
            System.out.println("Please input the name of the file including the file format:");
            Scanner sc = new Scanner(System.in);
            String name = sc.nextLine();
            String location = "./"+name;
            System.out.println("java RedBlackTreeSpellChecker "+name);
            System.out.println("Loading a tree of English words from "+name);
            RedBlackTree RBTree;
            try{
                RBTree = fileReader(location);
                initial(RBTree);
                operation(RBTree,sc);
                status = true;
            }catch(Exception e){
                System.out.println(e);
                System.out.println("Your input is invalid, please try again.");
            }
        }
    }
}
