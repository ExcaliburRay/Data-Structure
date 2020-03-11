import java.util.Arrays;

/**
 * The Queue is a first in first out data structure.
 * This Queue holds Java Object references.
 * It grows dynamically as long as memory is available.
 * Note: Many of these Javadoc comments describe implementation details.
 * For non-classroom use, these implementation details would not be exposed.
 */
public class Queue {


    private Object[] queue;
    private int size;
    private int front;
    private int rear;

    /**
     * Create an empty queue that lives in a small array.
     */
    public Queue(){
        this.size = 5;
        this.queue = new Object[size];
        this.front = 0;
        this.rear = 0;
    }

    /**
     * Object method removes and returns reference in front of queue.
     * @precondition queue not empty
     * @return object in front of queue.
     * @runtime The runtime of this method is Θ（1)
     */
    public Object deQueue(){
        Object obj = null;
        if(!isEmpty()){
            obj = queue[front];
            front++;
        }
        return obj;
    }

    /**
     * Add an object reference to the rear of the queue.
     * @precondition Memory is available for doubling queue capacity when full.
     * @postcondition queue now contains x in the rear.
     * @param x is an object to be added to the rear of the queue.
     * @runtime The runtime of this method is Θ（1)
     */
    public void enQueue(Object x){
        if(isFull()){
            size = size*2;
            Object[] newArray = new Object[size];
            System.arraycopy(queue,0,newArray,0,queue.length);
            queue = newArray;
        }
        queue[rear] = x;
        rear++;
    }

    /**
     * Boolean method returns true on empty queue, false otherwise
     * @return Returns true if queue is empty.
     */
    public boolean isEmpty(){
        return front==rear;
    }

    /**
     * Boolean method returns true if queue is currently at capacity, false otherwise.
     * If full returns true and additional enqueue calls are made,
     * the queue will expand in size
     * @return Returns true if queue is at current capacity.
     */
    public boolean isFull(){

        if(rear == size) return true;
        else return false;
    }

    /**
     * Method getFront returns the front of the queue without removing it.
     * @precondition queue not empty
     * @return the queue front without removal.
     * @runtime The runtime of this method is Θ（1)
     */
    public Object getFront(){
        Object frontNode = queue[front];
        return frontNode;
    }

    /**
     * The toString method returns a String representation of the current queue contents.
     * @overrides toString in class java.lang.Object
     * @return a string representation of the queue.
     * It shows the front of the queue first.
     * It then shows the second and third and so on.
     */
    public String toString(){
        int length = rear-front;
        Object[] currentQueue = new Object[length];
        System.arraycopy(queue,front,currentQueue,0,length);
        return Arrays.toString(currentQueue);
    }

    /**
     * main is for testing the queue routines.
     * @param args Command line parameters are not used.
     */
    public static void main(String[] args){
        Queue q = new Queue();
        q.enQueue("A");
        q.enQueue("B");
        q.enQueue("C");
        q.deQueue();
        System.out.println(q);
    }
}

