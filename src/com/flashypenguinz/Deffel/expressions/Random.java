package com.flashypenguinz.Deffel.expressions;

import java.util.regex.Pattern;

import com.flashypenguinz.Deffel.block.Block;
import com.flashypenguinz.Deffel.tokenizer.Regex;
import com.flashypenguinz.Deffel.tokenizer.Tokenizer;
import com.flashypenguinz.Deffel.utils.InvalidCodeException;
import com.flashypenguinz.Deffel.utils.Type;
import com.flashypenguinz.Deffel.utils.Value;

public class Random extends AddonExpression {
	
	@Override
	public Pattern getRegex() {
		return Pattern.compile("pick random between "+Regex.EXPRESSION+" and "+Regex.EXPRESSION);
	}

	@Override
	public Value getValue(Block superBlock, Tokenizer tokenizer) throws InvalidCodeException {
		tokenizer.nextToken(); // Skip pick token
		tokenizer.nextToken(); // Skip random token
		tokenizer.nextToken(); // Skip between token
		String[] expressions = tokenizer.getLine().split("and");
		Value first = Expression.parse(expressions[0].trim(), superBlock).evaluate();
		Value second = Expression.parse(expressions[1].trim(), superBlock).evaluate();
		if(first.getType()!=Type.NUMBER||second.getType()!=Type.NUMBER)
			throw new InvalidCodeException("Random expression expected numbers!");
		
		return new Value(Type.NUMBER, (Math.random()*(((double)second.getValue()-(double)first.getValue())+1))+(double)first.getValue());
	}

}
