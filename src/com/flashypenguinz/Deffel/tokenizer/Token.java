package com.flashypenguinz.Deffel.tokenizer;

public class Token {

	private String token;
	private TokenType type;
	
	public Token(String token, TokenType type) {
		this.token = token;
		this.type = type;
	}
	
	public Token(TokenType type) {
		this.type = type;
	}
	
	public String getToken() {
		return token;
	}
	
	public TokenType getType() {
		return type;
	}
	
}
