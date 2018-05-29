package com.flashypenguinz.Deffel.tokenizer;

public enum TokenType {

	/** Nothing */
	EMPTY,
	
	/** A Comment */
	COMMENT,
	
	/** A token, for example, () - , */
	TOKEN,
	
	/** First character is a letter, anything after are letter or number */
	IDENTIFIER,
	
	/** A Variable */
	VARIABLE,
	
	/** Any whole number */
	NUMBER_LITERAL,

	/** Anything enclosed in double quotes: "Hello" "Welcome" */
	STRING_LITERAL,
	
}
