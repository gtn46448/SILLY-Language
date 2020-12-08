# SILLY-Language
School project written in Java creating a interpretter for a simple procedural programming language named SILLY. Also provided is Test Code text file demonstrating various capabilies of the language that can be imported and run.

Grammar Rules
```
<statement>  --> <vardec> | <assignment> | <output> | <repeat> | <import> | <while> | <if> 

<vardec>     --> 'var' <assignment>

<assignment> --> <identifier> '=' <expression>

<output>     --> 'output' <expression> |
                   'output' '{' { <expression> } '}'

<repeat>     --> 'repeat' <expression> '{' { <statement> } '}'

<import>     --> 'import' <string>

<while>      --> 'while' <expression> '{' { <statement> } '}'  

<if>         --> 'if' <expression> '{' { <statement> } [ '}else{' { <statement> } ] '}' 

<expression> --> <identifier> | <integer> | <string> | <boolean> | 
                   '(' <expression> ')' |
                   '(' <unary_op> <expression> ')' | 
                   '(' <expression> <binary_op> <expression> ')' |

<identifier> --> <letter> { ( <letter> | <digit> ) }
<integer>     --> [ - ] <digit> { <digit> } 
<string>     --> '"' { <char> } '"'
<boolean>    --> 'true' | 'false'

<letter>     --> 'a' | 'b' | 'c' | ... | 'z' | 'A' | 'B' | 'C' | ... | 'Z'
<digit>      --> '0' | '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9' 
<char>       --> any non-whitespace character other than '"'

<unary_op>   --> 'not' | 'len'
<binary_op>  --> '+' | '-' | '*' | '/' | '%' | '==' | '!=' |
                 '>' | '>=' | '<' | '<=' | 'and' | 'or' | 'index'
```
