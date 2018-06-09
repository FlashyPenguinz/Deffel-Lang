package com.flashypenguinz.Deffel.utils;

import java.util.regex.Pattern;

public enum Comparison {

	EQUAL("(=|(is)?( )?equal(s)?( )?(to)?|is equal to)"), NOTEQUAL(
			"(!=|(does)?( )?not equal(s)?( )?(to)?|doesn(')?t equal(s)?( )?(to)?)"), GREATERTHAN(
			"(>|(is)?( )?greater( )?(than)?)"), LESSTHAN(
			"(<|(is)?( )?less( )?(than)?)");

	private String pattern;

	Comparison(String pattern) {
		this.pattern = pattern;
	}

	public static Comparison valueOfToken(String token) {
		for(Comparison c: values()) {
			if(Pattern.matches("^("+c.getPattern()+")", token)) {
				return c;
			}
		}
		return null;
	}
	
	public String getPattern() {
		return pattern;
	}

}
