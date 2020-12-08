/**
 * Main method for the SILLY Interpreter. 
 *   @author Dave Reed 
 *   @version 2/3/19
 */
public class Interpreter {
    public static MemorySpace MEMORY = new MemorySpace();
    
    /** 
     * Main method for starting the SILLY interpreter.
     *   Note: currently runs in an infinite loop.
     */
    public static void main(String[] args) throws Exception {        
        TokenStream input = new TokenStream();
       
        while (true) {
            System.out.print(">>> ");
            Statement stmt = Statement.getStatement(input);
            //System.out.println(stmt);
            stmt.execute();
        } 

    }
}
