/**
 * Class that represents a Boolean value.
 *   @author Dave Reed
 *   @version 2/3/19
 */
public class BooleanValue implements DataValue {
    private boolean value;

    /**
     * Constructs a Boolean value.
     *   @param val the value being stored
     */
    public BooleanValue(boolean val) {
        this.value = val;
    }

    /**
     * Accesses the stored Boolean value.
     *   @return the Boolean value (as an Object)
     */
    public Object getValue() {
        return (Boolean) this.value;
    }

    /**
     * Identifies the actual type of the value.
     *   @return Token.Type.BOOLEAN
     */
    public Token.Type getType() {
        return Token.Type.BOOLEAN;
    }

    /**
     * Converts the Boolean value to a String.
     *   @return a String representation of the Boolean value
     */
    public String toString() {
        return "" + this.value;
    }

    /**
     * Comparison method for BooleanValues.
     *   @param other the value being compared with
     *   @return negative if <, 0 if ==, positive if >
     */
    public int compareTo(DataValue other) {
        return ((Boolean)this.getValue()).compareTo((Boolean)other.getValue());
    }
}
