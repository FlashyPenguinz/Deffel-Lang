package com.flashypenguinz.Deffel.parser;

import com.flashypenguinz.Deffel.block.Block;
import com.flashypenguinz.Deffel.tokenizer.Tokenizer;
import com.flashypenguinz.Deffel.utils.InvalidCodeException;

public abstract class Parser<T extends Block> {

	private Class<T> type;
	
	public Parser(Class<T> type) {
		this.type = type;
	}
	
	/**
	 * Takes a line and checks to see if its for this parser
	 */
	public abstract boolean shouldParse(String line);
	
	public Class<T> getType() {
		return type;
	}
	
	/**
	 * Take the superBlock and tokenizer for this line and returns the block for this line
	 */
	public abstract T parse(Block superBlock, Tokenizer tokenizer) throws InvalidCodeException ;
	
}
