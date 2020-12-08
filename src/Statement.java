/**
 * Abstract class for representing a statement in the SILLY language.
 *   @author Dave Reed
 *   @version 2/3/19
 */
public abstract class Statement {
    public abstract void execute() throws Exception;
    public abstract String toString();
    
    /**
     * Static method that reads in an arbitrary Statement.
     *   @param input the TokenStream from which the program is read
     *   @return the next Statement in the program
     */
    public static Statement getStatement(TokenStream input) throws Exception {
        Token first = input.lookAhead(); 

        if (first.toString().equals("output")) {
            return new Output(input);
        }
        else if (first.toString().equals("import")) {
            return new Import(input);
        }  
        else if (first.toString().equals("repeat")) {
            return new Repeat(input);
        } 
        else if (first.toString().equals("while")) {
        	return new While(input);
        }
        else if (first.toString().equals("if")) {
        	return new If(input);
        }
        else if (first.toString().equals("var")) {
            return new VarDeclaration(input);
        } 
        else if (first.getType() == Token.Type.IDENTIFIER) {
            return new Assignment(input);
        }
        else {
            throw new Exception("SYNTAX ERROR: Unknown statement type (" + first + ")");
        }
    }
}
