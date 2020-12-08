import java.util.ArrayList;

/**
 * Derived class that represents a while statement in the SILLY language.
 * 
 * @author Grant Nakagawa
 * @version 2/3/19
 */
public class If extends Statement {
	private Expression expr;
	private ArrayList<Statement> ifStmts;
	private ArrayList<Statement> elStmts;

	/**
	 * Reads in a repeat statement from the specified stream
	 * 
	 * @param input
	 *            the stream to be read from
	 */
	public If(TokenStream input) throws Exception {
		if (!input.next().toString().equals("if")) {
			throw new Exception("SYNTAX ERROR: Malformed if statement");
		}

		this.expr = new Expression(input);

		if (!input.next().toString().equals("{")) {
			throw new Exception("SYNTAX ERROR: Malformed if statement");
		}

		this.ifStmts = new ArrayList<Statement>();
		while (!(input.lookAhead().toString().equals("}else{") || input
				.lookAhead().toString().equals("}"))) {
			this.ifStmts.add(Statement.getStatement(input));
		}
		if (input.next().toString().equals("}else{")) {
			this.elStmts = new ArrayList<Statement>();
			while (!input.lookAhead().toString().equals("}")) {
				this.elStmts.add(Statement.getStatement(input));
			}
			input.next();
		}
	}

	/**
	 * Executes the current repeat statement.
	 */
	public void execute() throws Exception {
		DataValue trueVal = this.expr.evaluate();
		if (trueVal.getType() != Token.Type.BOOLEAN) {
			throw new Exception(
					"RUNTIME ERROR: if expression must be a boolean");
		} else {
			Interpreter.MEMORY.addMemStack();
			if (((Boolean) trueVal.getValue())) {
				for (Statement stmt : this.ifStmts) {
					stmt.execute();
				}
			} else {
				for (Statement stmt : this.elStmts) {
					stmt.execute();
				}
			}
			Interpreter.MEMORY.removeMemStack();
		}
	}

	/**
	 * Converts the current repeat statement into a String.
	 * 
	 * @return the String representation of this statement
	 */
	public String toString() {
		String str = "if " + this.expr + " { \n";
		for (Statement stmt : this.ifStmts) {
			str += "    " + stmt + "\n";
		}
		if (this.elStmts != null) {
			str += "}else{ \n";
			for (Statement stmt : this.elStmts) {
				str += "    " + stmt + "\n";
			}
		}
		return str + "}";
	}
}
