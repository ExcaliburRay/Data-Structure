public class Node {
    int data;
    Node next;

    public Node(int v){
        data = v;
    }

    public static void main(String args[]){
        int n = 5;
        Node t = new Node(1);
        Node x = t;
        for(int i=2;i<=n;i++){
            x.next = new Node(i);
            x = x.next;
        }
        x.next = t;
        x = t.next;
        while(x != t){
            System.out.println(x.data);
            x = x.next;
        }
    }
}
