import java.util.Arrays;

public class DynamicStack {

    private Object[] stack;
    private int size;
    private int top;

    /**
     * constructor
     * initial size the stack is 6
     * the initial top index is -1
     */
    public DynamicStack() {
        size = 6;
        stack = new Object[size];
        top = -1;
    }

    /**
     * pop one element out of the DynamicStack
     * @runtime The runtime of this method is Θ（1) whatever best and worst case
     * @return the pop element
     */
    public Object pop(){
        Object obj;
        if(!isEmpty()){
            obj = stack[top];
            top--;
        }else{
            obj = "empty stack";
        }
        return obj;
    }

    /**
     * push one element into the DynamicStack
     * @runtime The runtime of this method is Θ（1) whatever best and worst case
     * @param obj the element need to push
     */
    public void push(Object obj){
        if(isFull()){
            size = size*2;
            Object[] newStack = new Object[size];
            System.arraycopy(stack,0,newStack,0,stack.length);
            stack = newStack;
        }
        top++;
        stack[top] = obj;
    }

    /**
     * distinguish the size of stack if empty or not
     * @return
     */
    public boolean isEmpty(){
        return top==-1;
    }

    /**
     * distinguish the size of stack if full or not
     * @return full is true
     */
    public boolean isFull(){
        if(top == size-1) return true;
        else return false;
    }

    /**
     * print the stack
     * @return the stack
     */
    public String toString(){
        int length = top+1;
        Object[] currentStack = new Object[length];
        System.arraycopy(stack,0,currentStack,0,length);
        return Arrays.toString(currentStack);
    }

    /**
     * main function
     * @runtime The runtime of this method is Θ（n)
     * @param args default argument
     */
    public static void main(String[] args){
        DynamicStack stack = new DynamicStack();
        for(int i=0;i<1000;i++){
            stack.push(i);
        }
        for(int i=0;i<1000;i++){
            System.out.println(stack.pop());
        }
    }
}
