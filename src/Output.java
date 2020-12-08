import java.util.ArrayList;

/**
 * Derived class that represents an output statement in the SILLY language.
 *   @author Dave Reed
 *   @author modified by Grant Nakagawa
 *   @version 2/3/19
 */
public class Output extends Statement {
	private ArrayList<Expression> exprs;
	private String bracketTrack;

	/**
	 * Reads in an output statement from the specified TokenStream.
	 *   @param input the stream to be read from
	 */
	public Output(TokenStream input) throws Exception {
		if (!input.next().toString().equals("output")) {
			throw new Exception("SYNTAX ERROR: Malformed output statement");
		}

		this.exprs = new ArrayList<Expression>();
		if (input.lookAhead().toString().equals("{")) {
			bracketTrack = input.next().toString();
			while (!input.lookAhead().toString().equals("}")) {
				this.exprs.add(new Expression(input));
			}
			input.next();
		} else {
			this.exprs.add(new Expression(input));
		}
	}

	/**
	 * Executes the current output statement.
	 */
	public void execute() throws Exception {
		for (Expression expr : this.exprs) {
			DataValue result = expr.evaluate();
			if (result.getType() == Token.Type.STRING) {
				String str = (String)(result.getValue());
				System.out.println(str.substring(1, str.length()-1) + " ");
			}
			else {
				System.out.println(result.getValue());
			}
		}
	}

	/**
	 * Converts the current output statement into a String.
	 *   @return the String representation of this statement
	 */
	public String toString() {
		if(this.bracketTrack != null) {
			String str = "output { \n";
			for (Expression expr : this.exprs) {
				str += "    " + expr + "\n";
			}
			return str + "}";
		}
		return "output " + this.exprs.get(0);
	}
}
