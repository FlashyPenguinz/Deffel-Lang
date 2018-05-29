package com.flashypenguinz.Deffel.parser;

import com.flashypenguinz.Deffel.block.Block;
import com.flashypenguinz.Deffel.block.On;
import com.flashypenguinz.Deffel.tokenizer.Regex;
import com.flashypenguinz.Deffel.tokenizer.Tokenizer;
import com.flashypenguinz.Deffel.utils.Indicator;
import com.flashypenguinz.Deffel.utils.InvalidCodeException;

public class OnParser extends Parser<On> {

	public OnParser() {
		super(On.class);
	}

	@Override
	public boolean shouldParse(String line) {
		return line.matches("on "+Regex.IDENTIFIER+":");
	}

	@Override
	public On parse(Block superBlock, Tokenizer tokenizer) throws InvalidCodeException {
		tokenizer.nextToken(); // Skip the on token
		
		String indicatorToken = tokenizer.nextToken().getToken();
		Indicator indicator = null;
		try {
			indicator = Indicator.valueOf(indicatorToken.toUpperCase());
		} catch(Exception e) {
			throw new InvalidCodeException(indicatorToken +" is not a valid indicator");
		}
			
		return new On(indicator);
	}

	
	
}
