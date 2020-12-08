/**
 * Class that represents a String value (which is stored in the heap).
 *   @author Dave Reed
 *   @version 2/3/19
 */
public class StringValue implements DataValue {
    private String value;

    /**
     * Constructs a String value.
     *   @param str the String being stored
     */
    public StringValue(String str) {
        this.value = str;
    }

    /**
     * Accesses the stored String value.
     *   @return the String value (as an Object)
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * Identifies the actual type of the value.
     *   @return Token.Type.STRING
     */
    public Token.Type getType() {
        return Token.Type.STRING;
    }

    /**
     * Converts the String value to a String.
     *   @return the stored String value
     */
    public String toString() {
        return this.value;
    }

    /**
     * Comparison method for StringValues.
     *   @param other the value being compared with
     *   @return negative if <, 0 if ==, positive if >
     */
    public int compareTo(DataValue other) {
        return this.toString().compareTo(other.toString());
    }
}
