package com.flashypenguinz.Deffel.expressions;

import java.util.regex.Pattern;

import com.flashypenguinz.Deffel.block.Block;
import com.flashypenguinz.Deffel.tokenizer.Tokenizer;
import com.flashypenguinz.Deffel.utils.InvalidCodeException;
import com.flashypenguinz.Deffel.utils.Value;

public abstract class AddonExpression {

	public abstract Pattern getRegex();
	
	public abstract Value getValue(Block superBlock, Tokenizer tokenizer) throws InvalidCodeException;
	
}
