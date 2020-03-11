import java.math.BigInteger;
import java.sql.SQLOutput;

public class RedBlackTree {

    public RedBlackNode nil;
    public RedBlackNode root;
    public static final int RED = 1;
    public static final int BLACK = 0;
    private int recentCompares;

    /**
     * Reb Black Trees
     *  A red-black tree is a binary search tree with an extra bit of storage per node.
     *  The extra bit represents the color of the node. It's either red or black.
     *  Each node contains the fields: color, key, left, right, and p. Any nil
     *  pointers are regarded as pointers to external nodes (leaves) and key bearing
     *  nodes are considered as internal nodes of the tree.
     *
     *  Red-black tree properties:
     *  1. Every node is either red or black.
     *  2. The root is black.
     *  3. Every leaf (nil) is black.
     *  4. If a node is red then both of its children are black.
     *  5. For each node, all paths from the node to descendant leaves contain the
     *     same number of black nodes.
     *
     *  From these properties, it can be shown (by an iduction proof) that
     *  the tree has a height no more than 2 * Lg(n + 1).
     *
     *  In the implementation of the tree, we use a single node to represent all
     *  of the external nulls. Its color will always be black. The parent pointer (p)
     *  in the root will point to this node and so will all the internal nodes
     *  that would normally contain a left or right value of null. In other words,
     *  instead of containing a null pointer as a left child or a null pointer as a
     *  right child, these internal nodes will point to the one node that represents
     *  the external nulls.
     *
     *  This constructor creates an empty RedBlackTree.
     *  It creates a RedBlackNode that represents null.
     *  It sets the internal variable tree to point to this
     *  node. This node that an empty tree points to will be
     *  used as a sentinel node. That is, all pointers in the
     *  tree that would normally contain the value null, will instead
     *  point to this sentinel node.
     */
    public RedBlackTree() {
        nil = new RedBlackNode(BLACK,"-1",null,null,null,null);
        root = new RedBlackNode(BLACK,"-1",null,nil,nil,nil);
        recentCompares = 1;
    }

    /**
     * obtain the number of values inserted into the tree.
     * @return number of values inserted into the tree.
     * @runtime The runtime of this method is Θ（n)
     * @precondition
     */
    public int getSize(){
        Queue queue = new Queue();
        RedBlackNode node = root;
        queue.enQueue(node);
        int num = 0;
        while(!queue.isEmpty()){
            node = (RedBlackNode) queue.deQueue();
            num++;
            if(node.getLc()!=nil) queue.enQueue(node.getLc());
            if(node.getRc()!=nil) queue.enQueue(node.getRc());
        }
        return num;
    }

    /**
     * The no argument inOrderTraversal() method calls the
     * recursive inOrderTraversal(RedBlackNode) - passing the root.
     */
    public void inOrderTraversal() {
        inOrderTraversal(root);
    }

    /**
     * Perform an inorder traversal of the tree.
     * The inOrderTraversal(RedBlackNode) method is recursive and displays the content of the tree.
     * It makes calls on System.out.println(). This method would normally be private.
     * @param t the root of the tree on the first call.
     * @runtime The runtime of this method is Θ（n)
     */
    public void inOrderTraversal(RedBlackNode t){
        if(t.getLc()!=nil){
            inOrderTraversal(t.getLc());
        }
        System.out.println(t);
        if(t.getRc()!=nil){
            inOrderTraversal(t.getRc());
        }
    }

    /**
     * Perform a reverseOrder traversal of the tree.
     * The reverseOrderTraversal(RedBlackNode) method is recursive
     * and displays the content of the tree in reverse order.
     * This method would normally be private.
     * @param t the root of the tree on the first call.
     * @runtime The runtime of this method is Θ（n)
     */
    public void reverseOrderTraversal(RedBlackNode t){
        Queue queue = new Queue();
        DynamicStack stack = new DynamicStack();
        RedBlackNode node = t;
        queue.enQueue(node);
        while(!queue.isEmpty()){
            node = (RedBlackNode) queue.deQueue();
            stack.push(node);
            if(node.getRc()!=nil) queue.enQueue(node.getRc());
            if(node.getLc()!=nil) queue.enQueue(node.getLc());
        }

        while(!stack.isEmpty()){
            System.out.println(stack.pop());
        }
    }

    /**
     * The no argument reverseOrderTraversal() method calls
     * the recursive reverseOrderTraversal(RedBlackNode) - passing the root.
     */
    public void reverseOrderTraversal(){
        reverseOrderTraversal(root);
    }

    /**
     * The insert() method places a data item into the tree.
     * @Pre-condition memory is available for insertion
     * @param value is an integer to be inserted
     * @runtime The runtime of this method is Θ（lg(n))
     */
    public void insert(String id, BigInteger value){

        RedBlackNode parent = nil;
        RedBlackNode child = root;

        while(child!=nil){
            parent = child;
            if(id.compareTo(child.getID())<0){
                child = child.getLc();
            }else{
                child = child.getRc();
            }
        }

        if(parent.getID() == nil.getID()){
            root = new RedBlackNode(BLACK,id,value,nil,nil,nil);
        }else{
            if(id.compareTo(parent.getID())<0){
                RedBlackNode left = new RedBlackNode(RED,id,value,parent,nil,nil);
                parent.setLc(left);
                RBInsertFixup(parent.getLc());
            }else if(id.compareTo(parent.getID())>0){
                RedBlackNode right = new RedBlackNode(RED,id,value,parent,nil,nil);
                parent.setRc(right);
                RBInsertFixup(parent.getRc());
            }else{
                parent.setData(id,value);
            }
        }

    }

    /**
     * leftRotate() performs a single left rotation. This would normally be a private method.
     * @param x the RedBlackNode
     * @precondition the right child of x is not nil
     * @precondition the parent of root is nil
     */
    public void leftRotate(RedBlackNode x){
        RedBlackNode y = x.getRc();
        x.setRc(y.getLc());
        y.getLc().setP(x);
        y.setP(x.getP());

        if(x.getP() == nil){
            root = y;
        }else{
            if(x == x.getP().getLc()){
                x.getP().setLc(y);
            }else{
                x.getP().setRc(y);
            }
        }
        y.setLc(x);
        x.setP(y);
    }

    /**
     * rightRotate() performs a single right rotation This would normally be a private method.
     * @param x the RedBlackNode
     * @precondition the right child of x is not nil
     * @precondition the parent of root is nil
     */
    public void rightRotate(RedBlackNode x){
        // y now points to node to left of x
        RedBlackNode y = x.getLc();
        // y's right subtree becomes x's left subtree
        x.setLc(y.getRc());
        // right subtree of y gets a new parent
        y.getRc().setP(x);
        // y's parent is now x's parent
        y.setP(x.getP());

        // if x is at root then y becomes new root
        if(x.getP() == nil){
            root = y;
        }else{
            // if x is a left child then adjust x's parent's left child or...
            if(x == x.getP().getLc()){
                x.getP().setLc(y);
            }else{
                // adjust x's parent's right child
                x.getP().setRc(y);
            }
        }
        y.setRc(x);
        x.setP(y);

    }

    /**
     * Fixing up the tree so that Red Black Properties are preserved. This would normally be a private method.
     *   Here, I will outline two pseudocode descriptions.
     *   The first will be for understanding and the second
     *   will be closer to an implemenatation.
     *
     *  Fixing up the tree so that Red Black Properties
     *  are preserved.
     *   Tracing-level Pseudo-code for RB-Insert-fixup
     *   When writing code, it's probably better to work from the
     *   more low-level pseudo-code below.
     * @param z the RedBlackNode
     */
    public void RBInsertFixup(RedBlackNode z){
        while(z.getP().getColor() == RED){
            if(z.getP() == z.getP().getP().getLc()){
                RedBlackNode y = z.getP().getP().getRc();
                if(y.getColor() == RED){
                    z.getP().setColor(BLACK);
                    y.setColor(BLACK);
                    z.getP().getP().setColor(RED);
                    z = z.getP().getP();
                }else{
                    if(z == z.getP().getRc()){
                        z = z.getP();
                        leftRotate(z);
                    }
                    z.getP().setColor(BLACK);
                    z.getP().getP().setColor(RED);
                    rightRotate(z.getP().getP());
                }
            }else{
                RedBlackNode parent = z.getP().getP().getLc();
                if(parent.getColor()==RED){
                    z.getP().setColor(BLACK);
                    parent.setColor(BLACK);
                    z.getP().getP().setColor(RED);
                    z = z.getP().getP();
                }else{
                    if(z == z.getP().getLc()){
                        z = z.getP();
                        rightRotate(z);
                    }
                    z.getP().setColor(BLACK);
                    z.getP().getP().setColor(RED);
                    leftRotate(z.getP().getP());
                }
            }
        }
        root.setColor(BLACK);
    }


    /**
     * The boolean contains() returns true if the String v is in the RedBlackTree and false otherwise.
     * It counts each comparison it makes in the variable recentCompares.
     * @param v the value to search for
     * @return true if v is in the tree, false otherwise;
     * @runtime The runtime of this method is Θ（lg(n))
     */
    public boolean contains(String id){
        RedBlackNode node = root;
        while(node!=nil){
            if(node.getID().compareTo(id)<0){
                node = node.getRc();
            }else if(node.getID().compareTo(id)>0){
                node = node.getLc();
            }else{
                return true;
            }
        }
        return false;
    }

    /**
     * number of comparisons made in last call on the contains method.
     * @return the number of recent compares
     */
    public int getRecentCompares(){
        return recentCompares;
    }

    /**
     * modify the value of recent compares
     * @param value the value of modifying
     */
    public void setRecentCompares(int value){
        recentCompares = value;
    }

    /**
     * The method closeBy(v) returns a value close to v in the tree. If v is found in the tree it returns v.
     * @param value the value to search close by for.
     * @runtime The runtime of this method is Θ（lg(n))
     */
    public BigInteger closeBy(String id){
        RedBlackNode node = root;
        RedBlackNode closeNode = nil;
        while(node != nil){
            if(node.getID().compareTo(id)>0){
                closeNode = node;
                node = node.getLc();
            }else if(node.getID().compareTo(id)<0){
                closeNode = node;
                node = node.getRc();
            }else{
                closeNode = node;
                node = nil;
            }
            setRecentCompares(getRecentCompares()+1);
        }
        return closeNode.getValue();
    }

    /**
     * This a recursive routine that is used to compute the height of the red black tree.
     * It is called by the height() method. The height() method passes the root of the tree to this method.
     * This method would normally be private.
     * @param t a pointer to a node in the tree.
     * @return the height of node t
     * @runtime The runtime of this method is Θ（lg(n))
     */
    private int height(RedBlackNode t){
        int i = -1 ,j = -1;
        if(t.getLc() != nil){
            i = height(t.getLc());
        }
        if(t.getRc()!=nil){
            j = height(t.getRc());
        }
        if(i<j){
            return j+1;
        }else{
            return i+1;
        }
    }

    /**
     * This method calls the recursive method height.
     * @return the height of the red black tree.
     */
    public int height(){
        return height(root);
    }

    /**
     * This method calls the recursive method diameter.
     * @return the diameter of the red black tree.
     */
    public int diameter(){
        return diameter(root);
    }

    /**
     * This a recursive routine that is used to compute the diameter of the red black tree.
     * It is called by the diameter() method. The diameter() method passes the root of the tree to this method.
     * This method would normally be private.
     * @param t a pointer to a node in the tree.
     * @return the diameter of node t
     * @runtime The runtime of this method is Θ（lg(n))
     */
    //formula: Diameter = Max ( leftHeight + rightHeight + 1, Max (leftDiameter, rightDiameter) )
    private int diameter(RedBlackNode t){
        int leftHeight = 0;
        int leftDiameter = 0;
        int rightHeight = 0;
        int rightDiameter = 0;
        if(t.getLc()!=nil){
            leftHeight = height(t.getLc());
            leftDiameter = diameter(t.getLc());
        }
        if(t.getLc()!=nil){
            rightHeight = height(t.getRc());
            rightDiameter = diameter(t.getRc());
        }
        return Math.max(leftHeight+rightHeight+1,Math.max(leftDiameter,rightDiameter));
    }

    /**
     * This method displays the RedBlackTree in level order.
     * It first displays the root. Then it displays all children of the root.
     * Then it displays all nodes on level 3 and so on. It is not recursive. It uses a queue.
     * @runtime The runtime of this method is Θ（n)
     */
    public void levelOrderTraversal(){
        Queue queue = new Queue();
        RedBlackNode node = root;
        queue.enQueue(node);
        while(!queue.isEmpty()){
            node = (RedBlackNode) queue.deQueue();
            System.out.println(node);
            if(node.getLc()!=nil) queue.enQueue(node.getLc());
            if(node.getRc()!=nil) queue.enQueue(node.getRc());
        }

    }

    /**
     * search the info in red black tree
     * @param rbt the red black tree
     * @param id the reference id need to search
     */
    public static void search(RedBlackTree rbt,String id){
        if(rbt.contains(id)){
            System.out.println("The result of search is "+rbt.closeBy(id));
        }else{
            System.out.println("This Red Black Tree do not contains the specific element");
        }
    }

    /**
     * Test the RedBlack tree.
     * @param args the default argument
     */
    public static void main(String[] args) {

        RedBlackTree rbt = new RedBlackTree();

        for(int i= 1; i <= 50; i++){
            BigInteger value = BigInteger.valueOf(i);
            rbt.insert("val"+value,value);
        }

        //distinguish whether val3 in the tree
        search(rbt,"val3");

        //rbt.inOrderTraversal();
        //rbt.reverseOrderTraversal();

    }
}
