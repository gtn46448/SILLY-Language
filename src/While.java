import java.util.ArrayList;

/**
 * Derived class that represents a while statement in the SILLY language.
 *   @author Grant Nakagawa
 *   @version 2/3/19
 */
public class While extends Statement {
	private Expression expr;
	private ArrayList<Statement> stmts;  

	/**
	 * Reads in a repeat statement from the specified stream
	 *   @param input the stream to be read from
	 */
	public While(TokenStream input) throws Exception {
		if (!input.next().toString().equals("while")) {
			throw new Exception("SYNTAX ERROR: Malformed while statement");
		}

		this.expr = new Expression(input);

		if (!input.next().toString().equals("{")) {
			throw new Exception("SYNTAX ERROR: Malformed while statement");
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
		DataValue trueVal = this.expr.evaluate();
		if (trueVal.getType() != Token.Type.BOOLEAN) {
			throw new Exception("RUNTIME ERROR: while expression must be a boolean");
		} 
		else if (((Boolean) trueVal.getValue())) {
			Interpreter.MEMORY.addMemStack();
			for (Statement stmt : this.stmts) {
				stmt.execute();
			}
			Interpreter.MEMORY.removeMemStack();
			this.execute();
		}
	}

	/**
	 * Converts the current repeat statement into a String.
	 *   @return the String representation of this statement
	 */
	public String toString() {
		String str = "while " + this.expr + " { \n";
		for (Statement stmt : this.stmts) {
			str += "    " + stmt + "\n";
		}
		return str + "}";
	}
}
