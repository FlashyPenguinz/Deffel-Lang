package com.flashypenguinz.Deffel.utils;

public enum Comparison {

	EQUAL("="), NOTEQUAL("!="), GREATERTHAN(">"), LESSTHAN("<"), GREATERTHANEQUALTO(">="), LESSTHANEQUALTO("<=");
	
	private String token;
	
	Comparison(String token) {
		this.token = token;
	}
	
	public static Comparison valueOfToken(String token) {
		for(Comparison c: Comparison.values()) {
			if(c.toString().equalsIgnoreCase(token)) {
				return c;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return token;
	}
	
	
}
