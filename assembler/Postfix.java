/**Postfix.java
 * Henry and Erik
 * 10/15/21
 * Transforms Infix to Postfix
 * */


import java.io.*;

// Creating class that will convert an infix expression to a postfix expression
public class Postfix { 

    // Field holds a stack with postfix expressions
    private Stack<String> postfix;
    // Field holds a stack with the infix expressions
    private Stack<String> infix;

    // Default constructor
    public Postfix(){

        // Initializing postfix stack
        this.postfix = new Stack<String>();
        // Initialzing infix stack
        this.infix = new Stack<String>();

    }

    // Getter method for the postfix stack
    public Stack<String> getPostfix() { return this.postfix; }

    // Getter method for the infix stack
    public Stack<String> getInfix() { return this.infix; }
    
    // Method converts all infix expressions within a given file
    public void convertFile(String fileName){

        // Space string for neater format
        String sp = " ";
        try {

            // Creating file reader object
            FileReader fr = new FileReader(fileName);
            // Creating buffer reader object
            BufferedReader reader = new BufferedReader(fr);
            // reading the first line in the text file
            String line = reader.readLine();

            // Seperate Postfix Commands for each expression
            int numExp = 0;
            while (line != null) {

                // Pushing the current line to the infix stack
                this.infix.push(new Node<String>(line, null));

                // Trimming any leading spaces and splitting the line by spaces
                String[] tokens = line.trim().split("\\s+");
                // Creating a stack to hold our expressions
                Stack<String> expStack = new Stack<String>();

                // Iterating through each token
                for(int i = 0; i < tokens.length; i++){
                    // Getting i-th token
                    String token = tokens[i];

                    // If the current token is ;, we have reached the end of our expression
                    // We add one to the number of the expression, print the final expression
                    // Push the final expression to the global postfix stack
                    // and break out of the for loop to continue on to the next line
                    if(token.equals(";")){
                        
                        // Get the final expression from expStack 
                        String expression = expStack.pop().getData();
                        // Reformating to remove extra spaces and add a semicolon at the end
                        expression = expression.replace("  ", " ") + ";";

                        // Print the expression to command line
                        System.out.println(numExp + ": " + expression);
                        // Push the expression to the global postfix stack
                        this.postfix.push(new Node<String>(expression, null));

                        // Add one to the expression count
                        numExp++;

                        break;
                    }

                    // If we hit a closing parenthesis...
                    if(token.equals(")")){

                        // Pop the right operand
                        String right = expStack.pop().getData();
                        // Pop the operation
                        String oper = expStack.pop().getData();
                        // Pop the left operand
                        String left = expStack.pop().getData();

                        // Push a new node containing the string constructed as left right operation
                        // to the expression stack
                        expStack.push(new Node<String>(left + sp + right + sp + oper + sp, null));
        
                    }
                    // If the token is a character or an operator, push the token to the stack
                    if(! token.equals("(") && ! token.equals(")")  ){
                        expStack.push(new Node<String>(token,null));
                    }

                }
                
                // Read a new line which may contain an expression
                line = reader.readLine();
            }

            // Close the buffer reader
            reader.close();
        }
        catch(FileNotFoundException ex){
          System.out.println("Postfix.convertFile():: unable to open file " + fileName);
        }
        catch(IOException ex){
            System.out.println("Postfix.convertFile():: error reading file " + fileName);
        }
    
      }
    
    // Method writes a file containing all the converted postfix expressions
    public void writeFile(String fileName){

        try{
            
            // Creating file writer object
            FileWriter write = new FileWriter(fileName);
            // Creating a stack which will contain all the expressions in their original order
            Stack<String> writing = new Stack<String>();

            // Moving the postfix expressions from the postfix field
            // to the writing stack. This will return the expressions to their 
            // Original order
            while(! this.postfix.isEmpty()){
                writing.push(this.postfix.pop());
            }
            

            // Going through the writing stack, popping them
            // and then appending that to the file we are writing
            while(! writing.isEmpty()){
                
                // Pop the writing stack
                Node<String> currentNode = writing.pop();
                // Get the line we are going to write
                String newLine = currentNode.getData();
                // Push the node back to the global stack
                this.postfix.push(currentNode);
                // Write new line
                write.append(newLine + "\n");
            }

            // Close the file once we are done writing postfix expressions to it
            write.close();
            
            // Letting user know that the file has finished writing
            System.out.println("Finished writing file " + fileName);

        }
        catch(IOException ex){
            System.out.println("Postfix.writeFile():: Cannot write to file " + fileName);
        }


    }

    public static void main(String[] args){ 
        
        // Creating new Postfix object
        Postfix expression = new Postfix();

        // Default operation, input file is exp.txt
        // Output file is conv.txt
        if (args.length == 0){

            // Reading exp.txt file
            expression.convertFile("exp.txt");
            // Writing postfix expressions from exp.txt to conv.txt
            expression.writeFile("conv.txt");

            // Printing use statement
            System.out.println("usage: Postfix input [output]");

        }
        
        // Case in which we are given an input file name but not an output file name
        else if (args.length == 1){
            
            // Getting the input file name
            String inputName = args[0];

            // Reading the input file
            expression.convertFile(inputName);
            
            // Creating output file name
            String outputFile = inputName.replace(".txt", "") + "_postfix.txt";

            // Writing the postfix expressions to the output file of name inputName_postfix.txt
            expression.writeFile(outputFile);
            
            // Rewritting usage statement
            System.out.println("usage: Postfix input [output]");

        }

        // Case in which we are given both the input and output file names
        else if (args.length == 2){

            // Getting the input file
            String inputName = args[0];
            // Getting the output file
            String outputName = args[1];

            // Reading the input file
            expression.convertFile(inputName);
            // Writing to the output file
            expression.writeFile(outputName);

        }

        // If the incorrect number of arguments were passed through print an error message
        // And print the usage statement
        else{
            System.out.println("Error: Too many arguments were passed!");
            System.out.println("usage: Postfix input [output]");
        }

 
    } 
}