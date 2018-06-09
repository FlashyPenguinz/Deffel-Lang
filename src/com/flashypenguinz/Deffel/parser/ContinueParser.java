package com.flashypenguinz.Deffel.parser;

import com.flashypenguinz.Deffel.block.Block;
import com.flashypenguinz.Deffel.block.Continue;
import com.flashypenguinz.Deffel.tokenizer.Tokenizer;
import com.flashypenguinz.Deffel.utils.InvalidCodeException;

public class ContinueParser extends Parser<Continue>{

	public ContinueParser() {
		super(Continue.class);
	}

	@Override
	public boolean shouldParse(String line) {
		return line.matches("continue");
	}

	@Override
	public Continue parse(Block superBlock, Tokenizer tokenizer)
			throws InvalidCodeException {
		return new Continue(superBlock);
	}

}
