/**
 * Henry and Erik
 * 10/13/2021
 * Java class representing a stack
 */



public class Stack<T>{
    
    private Node<T> head = null;

    public Stack(){
        this.head = null;
    }

    public Stack(Node<T> newHead){
        this.head = newHead;
    }

    public void push(Node<T> node){
        node.setnext(this.head);
        this.head = node;
    }

    public Node<T> pop(){

        if (this.head != null){
            Node<T> top = this.head;
            this.head = top.getNext();
        return top;
        }
        else{
            return null;
        }
    }

    public T peek(){
        return this.head.getData();
    }

    public boolean isEmpty(){
        boolean output = true;

        if (this.head == null){
            output = true;
        }
        else{
            output = false;
        }

        return output;
    }
}