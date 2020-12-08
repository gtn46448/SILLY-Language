/**
 * Derived class that represents an output statement in the SILLY language.
 *   @author Dave Reed
 *   @version 2/3/19
 */
public class Import extends Statement {
    private Token fileName;
    
    /**
     * Reads in an import statement from the specified TokenStream.
     *   @param input the stream to be read from
     */
    public Import(TokenStream input) throws Exception {
        if (!input.next().toString().equals("import") || 
                input.lookAhead().getType() != Token.Type.STRING) {
	    throw new Exception("SYNTAX ERROR: Malformed import statement");
	}        
	this.fileName = input.next();
    }
    
    /**
     * Executes the current import statement.
     */
    public void execute() throws Exception {
        try {
            String file = this.fileName.toString();
            TokenStream infile = new TokenStream(file.substring(1, file.length()-1));
            while (infile.hasNext()) {
                Statement stmt = Statement.getStatement(infile);
                stmt.execute();
            }
        }
        catch (java.io.FileNotFoundException e) {
            System.out.println("File not found: " + this.fileName);
        }
    }

    /**
     * Converts the current import statement into a String.
     *   @return the String representation of this statement
     */
    public String toString() {
        return " import " + this.fileName;
    }
}
