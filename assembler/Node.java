/**Node.java
 * Henry and Erik
 * 10/13/21
 * Class represents a Generic Node
 * */

public class node{
    private <T> data;
    private Node<T> next;

    public Node(T data, Node<T> next) {
        this.data = data;
        this.next = next;
    }

    public void setData(<T> data){
        this.data = data;
    }

    public void setnext(Node<T> node){
        this.next = node;
    }

    public <T> getData(){
        return this.data;
    }

    public Node<T> getNext(){
        return this.next;
    }
}
