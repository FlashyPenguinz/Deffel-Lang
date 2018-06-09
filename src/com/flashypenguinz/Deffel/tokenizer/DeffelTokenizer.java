package com.flashypenguinz.Deffel.tokenizer;

import java.util.regex.Pattern;

public class DeffelTokenizer extends Tokenizer {

	public DeffelTokenizer(String str) {
		super(str);

		registerTokenData(new TokenData(Pattern.compile("^("+Regex.COMPARISON+")"), TokenType.TOKEN));
		registerTokenData(new TokenData(Pattern.compile("^(\\#)"), TokenType.COMMENT));
		registerTokenData(new TokenData(Pattern.compile("^("+Regex.OPERATOR+")"), TokenType.TOKEN));
        registerTokenData(new TokenData(Pattern.compile("^(" + Regex.NUMBER_LITERAL + ")"), TokenType.NUMBER_LITERAL));
        
        for (String t : new String[] { "=", "\\(", "\\)", "\\.", "\\,", "\\:", "\\;"})
            registerTokenData(new TokenData(Pattern.compile("^(" + t + ")"), TokenType.TOKEN));
        
        registerTokenData(new TokenData(Pattern.compile("^("+Regex.BOOLEAN_LITERAL+")"), TokenType.BOOLEAN_LITERAL));
        registerTokenData(new TokenData(Pattern.compile("^(" + Regex.IDENTIFIER + ")"), TokenType.IDENTIFIER));
        registerTokenData(new TokenData(Pattern.compile("^(" + Regex.VARIABLE + ")"), TokenType.VARIABLE));
        registerTokenData(new TokenData(Pattern.compile("^(//.*)"), TokenType.EMPTY));
        registerTokenData(new TokenData(Pattern.compile("^(" + Regex.STRING_LITERAL + ")"), TokenType.STRING_LITERAL));
	}

}
