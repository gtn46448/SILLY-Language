import java.util.HashMap;
import java.util.Map;

/**
 * Class that defines the memory space for the SILLY interpreter. 
 *   @author Dave Reed
 *   @author modified by Grant Nakagawa 
 *   @version 2/3/19
 */
public class MemorySpace {
	private Map<Integer, Map<Token, DataValue>> stackStack;

	/**
	 * Constructs an empty memory space.
	 */
	public MemorySpace() {
		this.stackStack = new HashMap<Integer, Map<Token, DataValue>>();
		stackStack.put(0, new HashMap<Token, DataValue>());
	}

	/**
	 * Adds another level to the memory space.
	 */
	public void addMemStack() {
		stackStack.put(stackStack.size(), new HashMap<Token, DataValue>());
	}

	/**
	 * Removes top level of the memory space. 
	 * No safeguards, so if implemented wrong this can
	 * remove all instances of memory space, which is probably bad.
	 */
	public void removeMemStack() {
		stackStack.remove(stackStack.size() - 1);
	}

	/**
	 * Declares a variable (and stores null) in the stack segment.
	 * @param token the variable name
	 */
	public void declareVariable(Token token) throws Exception {
		if (!this.stackStack.get(this.stackStack.size() - 1).containsKey(token)) {
			this.stackStack.get(this.stackStack.size() - 1).put(token, null);
		}
		else {
			throw new Exception("RUNTIME ERROR: variable " + token + " already declared");
		}
	}

	/**
	 * Stores a variable/value in the stack segment.
	 *   @param token the variable name
	 *   @param val the value to be stored under that name
	 */
	public void storeVariable(Token token, DataValue val) throws Exception {
		for(int i = this.stackStack.size() - 1; i >= 0; i--) {
			if (this.stackStack.get(i).containsKey(token)) {
				this.stackStack.get(i).put(token,  val);
				return;
			}

		}
		throw new Exception("RUNTIME ERROR: variable " + token + " not declared");
	}

	/**
	 * Retrieves the value for a variable (from the stack segment).
	 *   @param token the variable name
	 *   @return the value stored under that name
	 */
	public DataValue lookupVariable(Token token) throws Exception {
		for(int i = this.stackStack.size() - 1; i >= 0; i--) {
			if (this.stackStack.get(i).containsKey(token)) {
				return this.stackStack.get(i).get(token);
			}
		}
		throw new Exception("RUNTIME ERROR: variable " + token + " not assigned");
	}
}
