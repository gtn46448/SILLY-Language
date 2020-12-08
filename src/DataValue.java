/**
 * Interface that defines the data type operations for the SILLY language. 
 *   @author Dave Reed
 *   @version 2/3/19
 */
public interface DataValue extends Comparable<DataValue> {   
    public Object getValue();
    public Token.Type getType();
    public String toString();
}