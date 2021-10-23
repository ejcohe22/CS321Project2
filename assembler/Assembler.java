/** Assembler.java
 * Henry and Erik
 * 10/15/21
 * Transforms Postfix to Assembly
 * */


import java.io.*; 

// Creating class that will convert an infix expression to a assembly expression
public class Assembler { 

    // Creating field for temporary storage spots
    private int tempVars;
    // Creating an empty stack that will hold our final expressions
    private Stack<String> finalExpressions;

    // These fields hold string formats for the assembly language
    // load format
    private String load = "LD %s\n";
    // Store format
    private String store = "ST %s\n";
    // Add format
    private String add = "AD %s\n";
    // Subtract format
    private String subtract = "SB %s\n";
    // Multiply format
    private String multiply = "ML %s\n";
    // Divide format
    private String divide = "DV %s\n";
    // Temporary variable format
    private String temporary = "TMP%s\n";

    // This field holds our assembly string
    private String assembly = "";

    // Default constructor method
    public Assembler(){
        
        // Sets tempVars to 1
        this.tempVars = 1;
        // Initializes the finalExpressions stack
        this.finalExpressions = new Stack<String>();
        
    }

    // Method Takes in two operands and an operator, then converts them to 
    // an expression in assembly
    public String evaluate(String left, String operator, String right){

        // Second step is always in the switch case
        String stepTwo = "";

        // First step is always to load the left operand
        String stepOne = String.format(this.load,left);

        // Using switch statement to look through the possible operators
        switch(operator){

            // Addition case
            case "+":
                // Step two, add the right operand
                stepTwo = String.format(this.add,right);
                break;
            
            // Subtraction case
            case "-":
                // Step two, subtract the right operand
                stepTwo = String.format(this.subtract,right);
                break;
            
            // Multiplication case
            case "*":
                // Step two, multiply the right operand
                stepTwo = String.format(this.multiply,right);
                break;
            
            // Division case
            case "/":
                // Step two, divide the right operand
                stepTwo = String.format(this.divide,right); 
                break;          
        }

        // Last step is to always load to a temporary variable
        // Making a temporary variable string 
        String temporaryVariable = String.format(this.temporary,this.tempVars);
        // Adding one to the tempVars field
        this.tempVars += 1;
        // Step three, store the result of left + right to TMP1
        String stepThree = String.format(this.store,temporaryVariable);

        // Add all steps together
        this.assembly += stepOne + stepTwo + stepThree;

        return temporaryVariable;
    }

    // Method reads our postfix file and converts it to assembly
    // All the assembly expressions are stored in a global field
    public void convertFile(String fileName){

        try {
            
            // Creating filereader object
            FileReader reader = new FileReader(fileName);
            // Creating buffered reader object
            BufferedReader buffer = new BufferedReader(reader);
            // Reading the first line in the text file
            String line = buffer.readLine();

            // Initializing counter for the number of expressions (For debugging)
            int numExp = 0;

            // Iterating through each expression in the file
            while (line != null){

                // Getting a list of all the tokens within the expression
                // First trim any extra leading space, then separate by spaces
                String[] tokens = line.trim().split("\\s+");
                //Creating Stack to hold temporary expressions
                Stack<String> expStack = new Stack<String>();

                // Iterating through each token
                for (int i = 0; i < tokens.length; i++) {
                    
                    // Getting ith token
                    String token = tokens[i];

                    if (token.equals(";")){


                        // Print Assembly code to command line
                        System.out.println("Expression " + numExp + " converted to:");
                        System.out.println(this.assembly);
                        // Push expression to the global stack
                        this.finalExpressions.push(new Node<String>(assembly, null));
                        // Add one to expression counter
                        numExp++;
                        // Reset global tempVars counter
                        this.tempVars = 1;
                        // Reset assembly string
                        this.assembly = "";
                        break;
                    }

                    // If the current token is an operator
                    if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")){

                        // Right operand
                        String right = expStack.pop().getData();
                        // Left operand
                        String left = expStack.pop().getData();
                        // Push the result of evaluate to the stack
                        expStack.push(new Node<String>(this.evaluate(left, token, right), null));
                    }

                    // If the token is not an operator
                    else if (!token.equals("+") && !token.equals("-") && !token.equals("*") && !token.equals("/")){

                        // Push the token to the stack
                        expStack.push(new Node<String>(token,null));
                    }

                }

                // Read a new line which may contain an expression
                line = buffer.readLine();
            }

            // Closing the buffer object
            buffer.close();
        } 
        catch (FileNotFoundException ex) {
            System.out.println("Assembler.convertFile():: unable to open file " + fileName);
        }
        catch (IOException ex){
            System.out.println("Assembler.convertFile():: error reading file " + fileName);
        }
    }


    public static void main(String[] args) {
        
        // Creating test object
        Assembler test = new Assembler();

        test.convertFile("exp_postfix.txt");
    }
}