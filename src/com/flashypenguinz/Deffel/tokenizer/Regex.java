package com.flashypenguinz.Deffel.tokenizer;

public class Regex {

	public static final String IDENTIFIER = "[a-zA-Z]([a-zA-Z0-9]*)?";
    public static final String STRING_LITERAL = "(\")[^\"]*(\")";
    public static final String NUMBER_LITERAL = "(-)?[0-9]+([0-9.]{2,})?";
    public static final String OPERATOR = "(\\+|\\-|\\*|\\/)";
    public static final String VARIABLE = "\\{(\\_)?"+IDENTIFIER+"\\}";
    public static final String VARIABLE_OR_LITERAL = "(" + VARIABLE + "|" + STRING_LITERAL + "|" + NUMBER_LITERAL + ")";
    public static final String EXPRESSION = "(\")?(.)+(\")?";
    
}
