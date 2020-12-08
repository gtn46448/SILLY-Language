import java.util.ArrayList;

/**
 * Derived class that represents a repeat statement in the SILLY language.
 *   @author Dave Reed
 *   @author modified by Grant Nakagawa
 *   @version 2/3/19
 */
public class Repeat extends Statement {
    private Expression expr;
    private ArrayList<Statement> stmts;  
    
    /**
     * Reads in a repeat statement from the specified stream
     *   @param input the stream to be read from
     */
    public Repeat(TokenStream input) throws Exception {
        if (!input.next().toString().equals("repeat")) {
            throw new Exception("SYNTAX ERROR: Malformed repeat statement");
        }

        this.expr = new Expression(input);
        
        if (!input.next().toString().equals("{")) {
            throw new Exception("SYNTAX ERROR: Malformed repeat statement");
        }    
        
        this.stmts = new ArrayList<Statement>();
        while (!input.lookAhead().toString().equals("}")) {
            this.stmts.add(Statement.getStatement(input));
        }
        input.next();
    }

    /**
     * Executes the current repeat statement.
     */
    public void execute() throws Exception {
        DataValue repVal = this.expr.evaluate();
        if (repVal.getType() != Token.Type.INTEGER) {
            throw new Exception("RUNTIME ERROR: repeat expression must be a number");
        } 
        else {
            int numReps = ((Integer) repVal.getValue());
            for (int i = 0; i < numReps; i++) {
            	Interpreter.MEMORY.addMemStack();
                for (Statement stmt : this.stmts) {
                    stmt.execute();
                }
                Interpreter.MEMORY.removeMemStack();
            }
        }
    }

    /**
     * Converts the current repeat statement into a String.
     *   @return the String representation of this statement
     */
    public String toString() {
        String str = "repeat " + this.expr + " { \n";
        for (Statement stmt : this.stmts) {
            str += "    " + stmt + "\n";
        }
        return str + "}";
    }
}
