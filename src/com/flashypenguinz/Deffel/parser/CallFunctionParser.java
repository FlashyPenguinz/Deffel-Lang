package com.flashypenguinz.Deffel.parser;

import java.util.ArrayList;
import java.util.Arrays;

import com.flashypenguinz.Deffel.block.Block;
import com.flashypenguinz.Deffel.block.CallFunction;
import com.flashypenguinz.Deffel.tokenizer.Regex;
import com.flashypenguinz.Deffel.tokenizer.Token;
import com.flashypenguinz.Deffel.tokenizer.Tokenizer;
import com.flashypenguinz.Deffel.utils.InvalidCodeException;

public class CallFunctionParser extends Parser<CallFunction> {

	public CallFunctionParser() {
		super(CallFunction.class);
	}

	@Override
	public boolean shouldParse(String line) {
		return line.matches(Regex.FUNCTION_CALL);
	}

	@Override
	public CallFunction parse(Block superBlock, Tokenizer tokenizer) throws InvalidCodeException {
		return parseFunction(tokenizer, superBlock);
	}
	
	public static CallFunction parseFunction(Tokenizer tokenizer, Block block) throws InvalidCodeException {
		tokenizer.nextToken(); // Skip call token
		tokenizer.nextToken(); // Skip function token
		Token name = tokenizer.nextToken();
		tokenizer.nextToken(); // Skip the ( token
		
		String[] array = tokenizer.getLine().split(",");
		array[array.length-1] = array[array.length-1].substring(0, array[array.length-1].length()-1);
		if(array[0].isEmpty())
			array = new String[0];
		
		return new CallFunction(block, name, new ArrayList<String>(Arrays.asList(array)));
	}

}
