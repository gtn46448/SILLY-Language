/**
 * Derived class that represents an assignment statement in the SILLY language.
 *   @author Dave Reed
 *   @version 2/3/19
 */
public class VarDeclaration extends Statement {
    private Token variable;
    private Assignment assign;

    /**
     * Reads in a assignment statement from the specified TokenStream
     *   @param input the stream to be read from
     */
    public VarDeclaration(TokenStream input) throws Exception {
        Token keyword = input.next();
        if (!keyword.toString().equals("var")) {
            throw new Exception("SYNTAX ERROR: Invalid variable declaration");
        }
        
        this.variable = input.lookAhead();
        this.assign = new Assignment(input);
    }
    
    /**
     * Executes the current assignment statement.
     */
    public void execute() throws Exception {
        Interpreter.MEMORY.declareVariable(this.variable);
        this.assign.execute();
    }
    
    /**
     * Converts the current assignment statement into a String.
     *   @return the String representation of this statement
     */
    public String toString() {
        return "var " + this.assign;
    }
}

