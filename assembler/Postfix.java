/**Postfix.java
 * Henry and Erik
 * 10/15/21
 * Transforms Infix to Postfix
 * */


import java.io.*; 


public class Postfix { 

    private Stack<String> postfix = new Stack<String>();
    
    public void convertFile(String fileName){
        String sp = " ";
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(fr);
            String line = null;
            line = reader.readLine();
            //Seperate Postfix Commands for each expression
            int numExp = 0;
            while (line != null) {
                String[] tokens = line.trim().split("\\s+");
                
                Stack<String> expStack = new Stack<String>();

                for(int i = 0; i < tokens.length; i++){
                    String token = tokens[i];
                    if(token.equals(";")){
                        numExp++;
                        System.out.println("Expression " + numExp + "\n" + expStack.peek() + " ;");
                        //prints expression to file
                        break;
                    }
                    if(token.equals(")")){
                        String right = expStack.pop().getData();
                        String oper = expStack.pop().getData();
                        String left = expStack.pop().getData();
                        //System.out.println(left + right + oper);
                        expStack.push(new Node<String>(left + sp + right + sp + oper + sp, null));
                        this.postfix.push(new Node<String>(left + sp + right + sp + oper + sp, null));
                    }
                    if(! token.equals("(") && ! token.equals(")")  ){
                        expStack.push(new Node<String>(token,null));
                    }

                }//line 22 for loop 
                line = reader.readLine();
            }
            reader.close();
        }
        catch(FileNotFoundException ex){
          System.out.println("not found");
        }
        catch(IOException ex){
            System.out.println("error reading file");
        }
    
      }

    public void writeFile(String fileName){
        try{
            FileWriter write = new FileWriter(fileName);
            Stack<String> writing = new Stack<String>();

            while(! this.postfix.isEmpty()){
                writing.push(this.postfix.pop());
            }
            while(! writing.isEmpty()){
            write.append(writing.pop().getData() + "\n");
            }
            write.close();

        }
        catch(IOException ex){
            System.out.println("Error creating File");
        }


    }

    public static void main(String[] args){ 
        for (String argument: args) {
            System.out.println(argument);
        }
        String fileName = "exp.txt";
        String writeName = "conv.txt"; 
        Postfix test = new Postfix();
        test.convertFile(fileName);
        test.writeFile(writeName);
 
    } 
}