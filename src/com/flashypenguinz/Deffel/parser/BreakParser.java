package com.flashypenguinz.Deffel.parser;

import com.flashypenguinz.Deffel.block.Block;
import com.flashypenguinz.Deffel.block.Break;
import com.flashypenguinz.Deffel.tokenizer.Tokenizer;
import com.flashypenguinz.Deffel.utils.InvalidCodeException;

public class BreakParser extends Parser<Break>{

	public BreakParser() {
		super(Break.class);
	}

	@Override
	public boolean shouldParse(String line) {
		return line.matches("break");
	}

	@Override
	public Break parse(Block superBlock, Tokenizer tokenizer)
			throws InvalidCodeException {
		return new Break(superBlock);
	}

}
