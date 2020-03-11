public class RedBlackNode {

    public static final int RED = 1;
    public static final int BLACK = 0;
    private int color;
    private String data;
    private RedBlackNode p;
    private RedBlackNode lc;
    private RedBlackNode rc;

    /**
     * Construct a RedBlackNode with data, color, parent pointer, left child pointer and right child pointer.
     * @param data the values of this node
     * @param color the color of this node, red or black
     * @param p the node of its parent
     * @param lc the node of its left child
     * @param rc the node of its right child
     */
    public RedBlackNode(String data, int color, RedBlackNode p, RedBlackNode lc, RedBlackNode rc) {
        this.data = data;
        this.color = color;
        this.p = p;
        this.lc = lc;
        this.rc = rc;
    }

    /**
     * The getColor() method returns RED or BLACK.
     * @return the color value (RED or BLACK)
     */
    public int getColor() {
        return color;
    }

    /**
     * The setColor() method sets the color of the RedBlackNode.
     * @param color is either RED or BLACK
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * The getData() method returns the data in the node.
     * @return the data value in the node
     */
    public String getData() {
        return data;
    }

    /**
     * The setData() method sets the data or key of the RedBlackNode.
     * @param data is an int holding a node's data value
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * The getP() method returns the parent of the RedBlackNode.
     * @return the parent field
     */
    public RedBlackNode getP() {
        return p;
    }

    /**
     * The setP() method sets the parent of the RedBlackNode.
     * @param p establishes a parent pointer for this node
     */
    public void setP(RedBlackNode p) {
        this.p = p;
    }

    /**
     * The setLc() method sets the left child of the RedBlackNode.
     * @return establishes a left child for this node
     */
    public RedBlackNode getLc() {
        return lc;
    }

    /**
     * The setLc() method sets the left child of the RedBlackNode.
     * @param lc establishes a left child for this node
     */
    public void setLc(RedBlackNode lc) {
        this.lc = lc;
    }

    /**
     * The getRc() method returns the right child of the RedBlackNode.
     * @return the right child field
     */
    public RedBlackNode getRc() {
        return rc;
    }

    /**
     * The setRc() method sets the right child of the RedBlackNode.
     * @param rc establishes a right child for this node.
     */
    public void setRc(RedBlackNode rc) {
        this.rc = rc;
    }

    /**
     * The toString() methods returns a string representation of the RedBlackNode.
     * @Overrides: toString in class java.lang.Object
     * @return the string representation of a RedBlackNode
     */
    public String toString(){
        RedBlackNode node = this;
        String nodeExpression = "data = "+node.getData()+": Color = "+node.getColor()+
                    ": Parent = "+node.getP().getData()+": LC = "+node.getLc().getData()+": RC = "+node.getRc().getData();
        return nodeExpression;
    }
}
