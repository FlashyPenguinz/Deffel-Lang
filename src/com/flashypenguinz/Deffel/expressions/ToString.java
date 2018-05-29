package com.flashypenguinz.Deffel.expressions;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

import com.flashypenguinz.Deffel.block.Block;
import com.flashypenguinz.Deffel.tokenizer.Regex;
import com.flashypenguinz.Deffel.tokenizer.Tokenizer;
import com.flashypenguinz.Deffel.utils.InvalidCodeException;
import com.flashypenguinz.Deffel.utils.Type;
import com.flashypenguinz.Deffel.utils.Value;

public class ToString extends AddonExpression {

	@Override
	public Pattern getRegex() {
		return Pattern.compile("convert "+Regex.EXPRESSION+" to string");
	}

	@Override
	public Value getValue(Block superBlock, Tokenizer tokenizer) throws InvalidCodeException {
		tokenizer.nextToken(); // Skip convert token
		String expression = tokenizer.getLine().substring(0, tokenizer.getLine().length()-10);
		Value value = Expression.parse(expression, superBlock).evaluate();
		if(value.getType().equals(Type.NUMBER)) {
			if (((double) value.getValue() == Math.floor((double) value.getValue())) && !Double.isInfinite((double) value.getValue())) {
				DecimalFormat format = new DecimalFormat();
				format.setDecimalSeparatorAlwaysShown(false);
				String number = format.format((double) value.getValue());
				Value v = new Value(Type.STRING, number);
				return v;
			}
		}
		return new Value(Type.STRING, value.getValue());
	}

}
