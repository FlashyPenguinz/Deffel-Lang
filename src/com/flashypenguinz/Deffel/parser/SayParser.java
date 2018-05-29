package com.flashypenguinz.Deffel.parser;

import com.flashypenguinz.Deffel.block.Block;
import com.flashypenguinz.Deffel.block.Say;
import com.flashypenguinz.Deffel.tokenizer.Regex;
import com.flashypenguinz.Deffel.tokenizer.Tokenizer;
import com.flashypenguinz.Deffel.utils.InvalidCodeException;

public class SayParser extends Parser<Say> {

	public SayParser() {
		super(Say.class);
	}

	@Override
	public boolean shouldParse(String line) {
		return line.matches("say "+Regex.EXPRESSION);
	}

	@Override
	public Say parse(Block superBlock, Tokenizer tokenizer) throws InvalidCodeException {
		tokenizer.nextToken(); // Skip the say token
		
		String expression = tokenizer.getLine();
			
		return new Say(superBlock, expression);
	}

	
	
}
