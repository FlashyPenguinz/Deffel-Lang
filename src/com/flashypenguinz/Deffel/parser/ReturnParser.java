package com.flashypenguinz.Deffel.parser;

import com.flashypenguinz.Deffel.block.Block;
import com.flashypenguinz.Deffel.block.Return;
import com.flashypenguinz.Deffel.tokenizer.Regex;
import com.flashypenguinz.Deffel.tokenizer.Tokenizer;
import com.flashypenguinz.Deffel.utils.InvalidCodeException;

public class ReturnParser extends Parser<Return> {

	public ReturnParser() {
		super(Return.class);
	}

	@Override
	public boolean shouldParse(String line) {
		return line.matches("return( "+Regex.EXPRESSION+")?");
	}

	@Override
	public Return parse(Block superBlock, Tokenizer tokenizer) throws InvalidCodeException {
		tokenizer.nextToken(); // Skip the return token
		return new Return(superBlock, tokenizer.getLine());
	}

}
