package com.flashypenguinz.Deffel.tokenizer;

public class Regex {

	public static final String IDENTIFIER = "[a-zA-Z]([a-zA-Z0-9]*)?";
    public static final String STRING_LITERAL = "(\")[^\"]*(\")";
    public static final String NUMBER_LITERAL = "(-)?[0-9]+([0-9.]{2,})?";
    public static final String BOOLEAN_LITERAL = "(true|false)";
    public static final String OPERATOR = "(\\+|\\-|\\*|\\/)";
    public static final String VARIABLE = "\\{(\\_)?"+IDENTIFIER+"\\}";
    public static final String VARIABLE_OR_LITERAL = "(" + VARIABLE + "|" + STRING_LITERAL + "|" + NUMBER_LITERAL + "|"+BOOLEAN_LITERAL+")";
    public static final String EXPRESSION = "(\")?(.)+(\")?";
    public static final String COMPARISON = "((=|(is)?( )?equal(s)?( )?(to)?|is equal to)|"
    										+ "(!=|(does)?( )?not equal(s)?( )?(to)?|doesn(')?t equal(s)?( )?(to)?)|"
    										+ "(>|(is)?( )?greater( )?(than)?)|"
    										+ "(<|(is)?( )?less( )?(than)?))";
    public static final String FUNCTION_CALL = "call function "+Regex.IDENTIFIER+"( )?\\(("+Regex.EXPRESSION+"( )?(,)?)*\\)";
    public static final String VARIABLE_ASSIGNMENT = "set "+Regex.VARIABLE+"( )?(\\+|\\-| )?(to|\\=|\\+|\\-)( )?("+Regex.EXPRESSION+")?";
    
}
