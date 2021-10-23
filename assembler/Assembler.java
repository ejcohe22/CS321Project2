/** Assembler.java
 * Henry and Erik
 * 10/15/21
 * Transforms Postfix to Assembly
 * */


import java.io.*; 

// Creating class that will convert an infix expression to a assembly expression
public class Assembler { 

    private int register = 1;

    // Field holds a stack with assembly expressions
    private Stack<String> assembler = new Stack<String>();
    
    // Method converts Postfix expressions within a given file
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
            System.out.println("Assembley:");
            while (line != null) {
                // Trimming any leading spaces and splitting the line by spaces
                String[] tokens = line.trim().split("\\s+");
                // Creating a stack to hold our expressions
                Stack<String> expStack = new Stack<String>();
                System.out.println("expression " + numExp + ": " );
                // Iterating through each token
                for(int i = 0; i < tokens.length; i++){
                    // Getting i-th token
                    String token = tokens[i];

                    // If the current token is ;, we have reached the end of our expression
                    // We add one to the number of the expression, print the final expression
                    // Push the final expression to the global assembler stack
                    // and break out of the for loop to continue on to the next line
                    if(token.equals(";")){
                        // Print the expression to command line
                        // Add one to the expression count
                        numExp++;
                        assembler.push(new Node<String>("\n\n",null));
                        break;
                    }

                    // If token is an operator...
                    if(token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")){

                        // Pop the right operand
                        String right = expStack.pop().getData();
                        // Pop the left operand
                        String left = expStack.pop().getData();

                        // LD Left
                        System.out.println("   LD    " + left );
                        assembler.push(new Node<String>(("   LD    " + left), null));

                        // OPD Right
                        switch (token) {
                            case "+":  
                                System.out.println("   AD    " + right );
                                assembler.push(new Node<String>(("   AD    " + right),null));
                                break;
                            case "-":  
                                System.out.println("   SB    " + right );
                                assembler.push(new Node<String>(("   SB    " + right),null));
                                break;
                            case "*":  
                                System.out.println("   ML    " + right );
                                assembler.push(new Node<String>(("   ML    " + right),null));
                                break;
                            case "/":  
                                System.out.println("   DV    " + right );
                                assembler.push(new Node<String>(("   DV    " + right),null));
                                break;
                            default:
                                System.out.println("OH NO!!!!!!!");
                        }
                        // Store as Tempn
                        System.out.println("   ST    TMP" + register);
                        assembler.push(new Node<String>(("   ST    TMP" + register),null));

                        // Push a new node containing the expression register location
                        expStack.push(new Node<String>(("TMP" + register), null));
                        register++;


                    // If the token is not an operator, push the token to the stack
                    }else{
                        expStack.push(new Node<String>(token,null));
                    }

                }
                
                // Read a new line which may contain an expression
                line = reader.readLine();
                register = 1;
                
            }

            // Close the buffer reader
            reader.close();
        }
        catch(FileNotFoundException ex){
          System.out.println("Assembler.convertFile():: unable to open file " + fileName);
        }
        catch(IOException ex){
            System.out.println("Assembler.convertFile():: error reading file " + fileName);
        }
    
      }
    
    // Method writes a file containing all the converted assembly expressions
    public void writeFile(String fileName){

        try{
            
            // Creating file writer object
            FileWriter write = new FileWriter(fileName);
            // Creating a stack which will contain all the expressions in their original order
            Stack<String> writing = new Stack<String>();

            // Moving the assembly expressions from the assembly field
            // to the writing stack. This will return the expressions to their 
            // Original order
            while(! this.assembler.isEmpty()){
                writing.push(this.assembler.pop());
            }

            // Going through the writing stack, popping them
            // and then appending that to the file we are writing
            while(! writing.isEmpty()){
            write.append(writing.pop().getData() + "\n");
            }

            // Close the file once we are done writing assembly expressions to it
            write.close();
            
            // Letting user know that the file has finished writing
            System.out.println("Finished writing file " + fileName);

        }
        catch(IOException ex){
            System.out.println("Assembler.writeFile():: Cannot write to file " + fileName);
        }


    }

    public static void main(String[] args){ 

        // Creating new Postfix object
        Postfix Postfix = new Postfix();
        
        // Creating new assembler object
        Assembler expression = new Assembler();

        // Default operation, input file is exp.txt
        // Output file is conv.txt
        if (args.length == 0){

            // Reading exp.txt file
            Postfix.convertFile("exp.txt");
            // Writing assembler expressions from exp.txt to conv.txt
            expression.writeFile("conv.txt");

            // Printing use statement
            System.out.println("usage: Assembler input [output]");

        }
        
        // Case in which we are given an input file name but not an output file name
        else if (args.length == 1){
            
            // Getting the input file name
            String inputName = args[0];

            // Reading the input file
            expression.convertFile(inputName);

            // Creating output file name
            String outputFile = inputName.replace(".txt", "") + "_assembly.txt";

            expression.writeFile(outputFile);

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
            System.out.println("usage: Assembler input [output] ");
        }

 
    } 
}