/**
 * Derived class that represents an expression in the SILLY language.
 * 
 * @author Dave Reed
 * @version 2/3/19
 */
public class Expression {
	private Token tok;
	private Expression expr1;
	private Token op;
	private Expression expr2;

	/**
	 * Creates an expression from the specified TokenStream.
	 * 
	 * @param input
	 *            the TokenStream from which the program is read
	 */
	public Expression(TokenStream input) throws Exception {
		if (input.lookAhead().toString().equals("(")) {
			input.next();
			if (input.lookAhead().getType() == Token.Type.UNARY_OP) {
				this.op = input.next();
				this.expr2 = new Expression(input);
			} else {
				this.expr1 = new Expression(input);
				if (input.lookAhead().getType() == Token.Type.BINARY_OP) {
					this.op = input.next();
					this.expr2 = new Expression(input);
				}
			}
			if (!input.next().toString().equals(")")) {
				throw new Exception("SYNTAX ERROR: Malformed expression");
			}
		} else {
			this.tok = input.next();
			if (this.tok.getType() != Token.Type.INTEGER
					&& this.tok.getType() != Token.Type.STRING
					&& this.tok.getType() != Token.Type.BOOLEAN
					&& this.tok.getType() != Token.Type.IDENTIFIER) {
				throw new Exception("SYNTAX ERROR: Malformed expression");
			}
		}
	}

	/**
	 * Evaluates the current expression.
	 * 
	 * @return the value represented by the expression
	 */
	public DataValue evaluate() throws Exception {
		if (this.tok != null) {
			if (this.tok.getType() == Token.Type.IDENTIFIER) {
				DataValue value = Interpreter.MEMORY.lookupVariable(this.tok);
				return value;
			} else if (this.tok.getType() == Token.Type.INTEGER) {
				return new IntegerValue(Integer.parseInt(this.tok.toString()));
			} else if (this.tok.getType() == Token.Type.STRING) {
				return new StringValue(this.tok.toString());
			} else if (this.tok.getType() == Token.Type.BOOLEAN) {
				return new BooleanValue(Boolean.valueOf(this.tok.toString()));
			}
		} else if (this.op == null) {
			return this.expr1.evaluate();
		} else if (this.op.getType() == Token.Type.UNARY_OP) {
			DataValue evalValue = this.expr2.evaluate();
			if (evalValue.getType() == Token.Type.STRING) {
				if (this.op.toString().equals("len")) {
					return new IntegerValue(evalValue.toString().length() - 2);
				}
			}
			if (evalValue.getType() == Token.Type.BOOLEAN) {
				if (this.op.toString().equals("not")) {
					if (evalValue.toString().equals("true")) {
						return new BooleanValue(false);
					} else if (evalValue.toString().equals("false")) {
						return new BooleanValue(true);
					}
				}
			}
		} else if (this.op.getType() == Token.Type.BINARY_OP) {
			DataValue lhsValue = this.expr1.evaluate();
			DataValue rhsValue = this.expr2.evaluate();

			if (lhsValue.getType() == rhsValue.getType()) {
				if (this.op.toString().equals("==")) {
					return new BooleanValue(lhsValue.compareTo(rhsValue) == 0);
				} else if (this.op.toString().equals("!=")) {
					return new BooleanValue(lhsValue.compareTo(rhsValue) != 0);
				} else if (this.op.toString().equals(">")) {
					return new BooleanValue(lhsValue.compareTo(rhsValue) > 0);
				} else if (this.op.toString().equals(">=")) {
					return new BooleanValue(lhsValue.compareTo(rhsValue) >= 0);
				} else if (this.op.toString().equals("<")) {
					return new BooleanValue(lhsValue.compareTo(rhsValue) < 0);
				} else if (this.op.toString().equals("<=")) {
					return new BooleanValue(lhsValue.compareTo(rhsValue) <= 0);
				} else if (lhsValue.getType() == Token.Type.STRING) {
					if (this.op.toString().equals("+")) {
						String str1 = lhsValue.toString();
						String str2 = rhsValue.toString();
						return new StringValue(str1.substring(0,
								str1.length() - 1) + str2.substring(1));
					}
				} else if (lhsValue.getType() == Token.Type.INTEGER) {
					int num1 = ((Integer) (lhsValue.getValue()));
					int num2 = ((Integer) (rhsValue.getValue()));

					if (this.op.toString().equals("+")) {
						return new IntegerValue(num1 + num2);
					} else if (this.op.toString().equals("-")) {
						return new IntegerValue(num1 - num2);
					} else if (this.op.toString().equals("*")) {
						return new IntegerValue(num1 * num2);
					} else if (this.op.toString().equals("/")) {
						return new IntegerValue(num1 / num2);
					} else if (this.op.toString().equals("%")) {
						return new IntegerValue(num1 % num2);
					}
				}
				else if (lhsValue.getType() == Token.Type.BOOLEAN) {
					boolean bool1 = ((Boolean) (lhsValue.getValue()));
					boolean bool2 = ((Boolean) (rhsValue.getValue()));
					if (this.op.toString().equals("and")) {
						return new BooleanValue(bool1 && bool2);
					} else if (this.op.toString().equals("or")) {
						return new BooleanValue(bool1 || bool2);
					}
				}
			} else if (lhsValue.getType() == Token.Type.STRING
					&& rhsValue.getType() == Token.Type.INTEGER) {
				if (this.op.toString().equals("index")) {
					String str1 = lhsValue.toString();
					str1 = str1.substring(1, str1.length() - 1);
					int index = (Integer) (rhsValue.getValue());
					return new StringValue("\""
							+ str1.substring(index, index + 1) + "\"");
				}
			}
		}
		throw new Exception("RUNTIME ERROR: illegal operand(s) for " + this.op);
	}

	/**
	 * Converts the current expression into a String.
	 * 
	 * @return the String representation of this expression
	 */
	public String toString() {
		if (this.tok != null) {
			return this.tok.toString();
		} else if (this.op == null) {
			return "( " + this.expr1 + " )";
		} else if (this.op.getType() == Token.Type.UNARY_OP) {
			return "( " + this.op + " " + this.expr2 + " )";
		} else {
			return "( " + this.expr1 + " " + this.op + " " + this.expr2 + " )";
		}
	}
}
