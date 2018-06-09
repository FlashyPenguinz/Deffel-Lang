package com.flashypenguinz.Deffel.expressions;

import java.util.regex.Pattern;

import com.flashypenguinz.Deffel.Runtime;
import com.flashypenguinz.Deffel.block.Block;
import com.flashypenguinz.Deffel.block.Function;
import com.flashypenguinz.Deffel.parser.CallFunctionParser;
import com.flashypenguinz.Deffel.tokenizer.Regex;
import com.flashypenguinz.Deffel.tokenizer.Tokenizer;
import com.flashypenguinz.Deffel.utils.InvalidCodeException;
import com.flashypenguinz.Deffel.utils.Value;

public class CallFunction extends AddonExpression {

	@Override
	public Pattern getRegex() {
		return Pattern.compile(Regex.FUNCTION_CALL);
	}

	@Override
	public Value getValue(Block superBlock, Tokenizer tokenizer)
			throws InvalidCodeException {
		com.flashypenguinz.Deffel.block.CallFunction callFunction = CallFunctionParser.parseFunction(tokenizer, superBlock);
		Function function = Runtime.getRuntime().getFunction(callFunction.getName());
		if(function == null)
			throw new InvalidCodeException(callFunction.getName()+" is not a valid function name!");
		Value value = function.invoke(callFunction.getValues().toArray(new Value[callFunction.getValues().size()]));
		return value;
	}

}
