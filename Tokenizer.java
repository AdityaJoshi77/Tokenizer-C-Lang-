import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

enum TokenType {
    KEYWORD, IDENTIFIER, NUMBER, OPERATOR, SEPARATOR, STRING_LITERAL, COMMENT, UNKNOWN
}


public class Tokenizer 
{
    // REGULAR EXPRESSIONS for Patterns for token types
    /* description :
     * These regular expressions describe the patterns for keywords, identifiers, numbers, operators, separators, string literals, and comments.
     */
    private static final String KEYWORDS = "\\b(auto|break|case|char|const|continue|default|do|double|else|enum|extern|"
    + "float|for|goto|if|int|long|return|short|signed|sizeof|static|struct|switch|"
    + "typedef|union|unsigned|void|volatile|while)\\b";
    private static final String IDENTIFIER = "\\b[a-zA-Z_]\\w*\\b";
    private static final String NUMBER = "\\b\\d+(\\.\\d+)?\\b";
    private static final String OPERATOR = "[+\\-*/=<>!&|]+";
    private static final String SEPARATOR = "[(){};,]";
    private static final String STRING_LITERAL = "\"[^\"]*\"";
    private static final String COMMENT = "//.*|/\\*.*?\\*/";


    // METHOD : tokenize()
    /* description : 
     * Input : the tokenize() method takes a String code as input, which contains entire C code snippet entered by the user.
     
     * Output : It returns a List<Token> which contains all the identified tokens.
     */
    public List<Token> tokenize(String code) {
        List<Token> tokens = new ArrayList<>();
        
        // tokenPatterns:
        /* description:
         * We combine all individual token type patterns into a single pattern tokenPatterns using String.join("|", ...).
         */
        String tokenPatterns = String.join("|", KEYWORDS, IDENTIFIER, NUMBER, OPERATOR, SEPARATOR, STRING_LITERAL, COMMENT);

        // pattern:
        /* dscription:
         * The Pattern.compile(tokenPatterns) line compiles this combined pattern, enabling us to search the code for matches across all token types.
         */
        Pattern pattern = Pattern.compile(tokenPatterns);
        
        // matcher : 
        /* description:
         * initializes a Matcher object that will scan the input code for patterns matching any of the defined token types.
         */
        Matcher matcher = pattern.matcher(code);

        while (matcher.find()) {
            // while(matcher.find())
            /* description :
             * This loop iterates through the code, finding each occurrence of a pattern that matches a token type.
             */
        
            String value = matcher.group();
            // String value = matcher.group()
            /* description:
             *  For each match, this line retrieves the exact string (token value) that matched the pattern.
             * In other words, the matcher.group() first extracted a token, and thereafter checks its type in the following if-else ladder.
             */

            /* Checking the token type 
             * For each matched value, the code checks which type it belongs to by running value.matches(...) for each token type.
            
             * For instance, if the value is "int", it matches the KEYWORDS pattern.

            *If it’s an identifier like main, it matches the IDENTIFIER pattern.
             */ 
            
            /* Creating and Adding Tokens : 
                
            For each match, the code creates a new Token object with the identified TokenType and value and adds it to the tokens list.

            Example for int main():
            "int" matches KEYWORDS and is added as Token{type=KEYWORD, value="int"}.

            "main" matches IDENTIFIER and is added as Token{type=IDENTIFIER, value="main"}.

            "(" and ")" match SEPARATOR and are added as separators.
             */
             
            if (value.matches(KEYWORDS)) {
                tokens.add(new Token(TokenType.KEYWORD, value));
            } else if (value.matches(IDENTIFIER)) {
                tokens.add(new Token(TokenType.IDENTIFIER, value));
            } else if (value.matches(NUMBER)) {
                tokens.add(new Token(TokenType.NUMBER, value));
            } else if (value.matches(OPERATOR)) {
                tokens.add(new Token(TokenType.OPERATOR, value));
            } else if (value.matches(SEPARATOR)) {
                tokens.add(new Token(TokenType.SEPARATOR, value));
            } else if (value.matches(STRING_LITERAL)) {
                tokens.add(new Token(TokenType.STRING_LITERAL, value));
            } else if (value.matches(COMMENT)) {
                tokens.add(new Token(TokenType.COMMENT, value));
            } else {
                tokens.add(new Token(TokenType.UNKNOWN, value));
            }
        }
        
        return tokens;
        /* Returning the Tokens : 
        After the loop completes, the tokens list contains all identified tokens, categorized by type.

        The tokenize method then returns this list.
         */
    }

    // DRIVER CODE : 
    public static void main(String[] args) {
        Tokenizer tokenizer = new Tokenizer();
        Scanner scanner = new Scanner(System.in);
        StringBuilder code = new StringBuilder();

        System.out.println("Enter C code \n(type 'END' on a new line to finish):");

        while (true) {
            String line = scanner.nextLine();
            if (line.equals("END")) {
                break;
            }
            code.append(line).append("\n");
        }

        List<Token> tokens = tokenizer.tokenize(code.toString());

        for (Token token : tokens) {
            System.out.println(token);
        }

        scanner.close();
    }
}
