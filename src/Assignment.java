/**
 * Derived class that represents an assignment statement in the SILLY language.
 *   @author Dave Reed
 *   @version 2/3/19
 */
public class Assignment extends Statement {
    private Token vbl;
    private Expression expr;
    
    /**
     * Reads in a assignment statement from the specified TokenStream
     *   @param input the stream to be read from
     */
    public Assignment(TokenStream input) throws Exception {
        this.vbl = input.next();
        Token op = input.next();
        if (this.vbl.getType() != Token.Type.IDENTIFIER || !op.toString().equals("=")) {
	        throw new Exception("SYNTAX ERROR: Malformed assignment statement");
        }        
        this.expr = new Expression(input);
    }
    
    /**
     * Executes the current assignment statement.
     */
    public void execute() throws Exception {
        Interpreter.MEMORY.storeVariable(this.vbl, this.expr.evaluate());
    }
    
    /**
     * Converts the current assignment statement into a String.
     *   @return the String representation of this statement
     */
    public String toString() {
        return this.vbl + " = " + this.expr;
    }
}
