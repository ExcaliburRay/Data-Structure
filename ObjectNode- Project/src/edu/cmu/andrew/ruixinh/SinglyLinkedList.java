package edu.cmu.andrew.ruixinh;

public class SinglyLinkedList {
    /**
     *  head represents the first node of the list
     *  tail represents the last node of the list
     *  countNodes represents the quantity of nodes in the list
     *  iterator represents the Iterator move the position of node
     */
    private ObjectNode head;
    private ObjectNode tail;
    private int countNodes;
    private ObjectNode iterator;

    /**
     * constructor with no arguments
     */
    public SinglyLinkedList() {
        head = null;
        tail = null;
        countNodes = 0;
        iterator = null;
    }

    /**
     * add the ObjectNode to the end of the list
     * @param c one objectNode that need to add
     * @precondition attribute should not be none type
     * @postcondition one Node will be generated after the tail and this node will not be none
     */
    public void addAtEndNode(Object c) {
        if (tail != null) {
            tail.addNodeAfter(c);
            tail = tail.getLink();
        } else {
            head = new ObjectNode(c, null);
            tail = head;
        }

    }

    /**
     * add the ObjectNode before the head of the list
     * @param c one objectNode that need to add
     * @precondition attribute should not be none type
     * @postcondition one Node will be generated before the head and this node will not be none
     */
    public void addAtFrontNode(Object c) {
        if (head == null) {
            head = new ObjectNode(c, null);
            tail = head;
        } else {
            head = new ObjectNode(c, head);
        }
    }

    /**
     * get the quantity of the nodes in the list
     * @return the nodes quantity
     * @precondition this list should contain at least one node and cannot be empty
     * @postcondition the length of this list will be generated and the type is int which larger than 0
     */
    public int countNodes() {
        countNodes = ObjectNode.listLength(head);
        return countNodes;
    }

    /**
     * get the last node in the list
     * @return the last node
     * @precondition this list should contain at least one node and cannot be empty
     * @postcondition obtain the information in the tail
     */
    public Object getLast() {
        return tail.getData();
    }

    /**
     * get the node in one specific position
     * @param i the index of the node in need
     * @return the ObjectNode in specific position
     * @precondition this list should contain at least one node and cannot be empty, the i should in the range of the list
     */
    public Object getObjectAt(int i) {
        Object obj = ObjectNode.listPosition(head, i).getData();
        return obj;
    }

    /**
     * the iterator method to check whether this list has next node
     * @return true or false
     * @precondition this list contains nodes
     */
    public boolean hasNext() {
        return iterator != null;
    }

    /**
     * get the next node in the list
     * @return the objectNode in Object type
     * @precondition this list contains nodes
     */
    public Object next() {
        Object obj = iterator.getData();
        iterator = iterator.getLink();
        return obj;
    }

    /**
     * treat the pos of iterator come to the head of the list
     * @precondition this list contains nodes
     */
    public void reset() {
        iterator = head;
    }

    /**
     * rewrite the toString method to display the value of the ObjectNode
     * @return the value of ObjectNode or empty String
     */
    public String toString() {
        if (head != null) {
            return head.toString();
        } else {
            return "empty list";
        }
    }

    /**
     * main function to execute the program
     * @param args default declaration
     */
    public static void main(String[] args) {
        // put the data into an array
        char[] data = new char[26];
        byte init = 'A';
        for (int i = 0; i < data.length; i++) {
            data[i] = (char) (init + i);
        }

        // add node to two SinglyLinkedList
        SinglyLinkedList listOne = new SinglyLinkedList();
        SinglyLinkedList listTwo = new SinglyLinkedList();
        for (int i = 0; i < data.length; i++) {
            listOne.addAtFrontNode(data[i]);
        }

        for (int i = 0; i < data.length; i++) {
            listTwo.addAtEndNode(data[i]);
        }

        // display the required information
        listOne.reset();
        listTwo.reset();
        System.out.println("The last node in this list is " + listOne.getLast());
        System.out.println("The total nodes in this list is " + listOne.countNodes());
        System.out.println("Node in the position 4 of this list is " + listOne.getObjectAt(4));

        System.out.println("The result of LinkedList taken by adding node at the tail of the list:");
        while (listOne.hasNext()) {
            System.out.print(listOne.next());
        }

        System.out.println();

        System.out.println("The result of LinkedList taken by adding node at the head of the list:");
        while (listTwo.hasNext()) {
            System.out.print(listTwo.next());
        }
    }
}
