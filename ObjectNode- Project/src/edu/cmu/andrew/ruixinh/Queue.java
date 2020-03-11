package edu.cmu.andrew.ruixinh;

public class Queue {
    Node f,r;
    public Queue(){
        f = null;
        r = null;
    }

    public boolean emptyQueue(){
        return f == null;
    }

    public void addAtRear(int x){
        Node t = new Node(x);
        if(f == null){
            f = r = t;
        }else{
            r.next = t;
            r = r.next;
        }
    }

    public int removeFromFront(){
        int t = f.data;
        f = f.next;
        if(f == null){
            r = null;
        }
        return t;
    }

    public static void main(String[] args){
        Queue q = new Queue();
        q.addAtRear(1);
        q.addAtRear(2);
        q.addAtRear(3);
        q.addAtRear(4);
        while(!q.emptyQueue()){
            System.out.println(q.removeFromFront());
        }
    }
}
