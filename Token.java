public class Token {
    private TokenType type;
    private String faceValue;

    public Token(TokenType type, String faceValue) {
        this.type = type;
        this.faceValue = faceValue;
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return faceValue;
    }

    @Override
    public String toString() {
        return "Token {" + "type = " + type + ", value = '" + faceValue + '\'' + '}' + "\n";
    }
}
