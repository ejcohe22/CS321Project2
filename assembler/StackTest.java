/**
 * Henry and Erik
 * 10/13/2021
 * Unit Testing of our generic Stack
 */

public class StackTest{
    public static void main(String[] args) {
        //test constructors
        Stack<Integer> a = new Stack<Integer>();
        Stack<Integer> b = new Stack<Integer>(new Node<Integer>(2, null));
        
        //test peek
        System.out.println(a.peek() + " return null?");
        System.out.println(b.peek() + " return 2?");

        //test isEmpty
        System.out.println(a.isEmpty() + " return true?");

        
        //test pop
        Node<Integer> bNode = b.pop();
        System.out.println(b.peek() + " return null?");

        //test push
        a.push(bNode);
        System.out.println(a.peek() + " return 2?");

    }
}
