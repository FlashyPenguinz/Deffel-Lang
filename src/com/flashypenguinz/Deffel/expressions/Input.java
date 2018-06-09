package com.flashypenguinz.Deffel.expressions;

import java.util.Scanner;
import java.util.regex.Pattern;

import com.flashypenguinz.Deffel.block.Block;
import com.flashypenguinz.Deffel.tokenizer.Tokenizer;
import com.flashypenguinz.Deffel.utils.InvalidCodeException;
import com.flashypenguinz.Deffel.utils.Type;
import com.flashypenguinz.Deffel.utils.Value;

public class Input extends AddonExpression {

	private static Scanner scanner = new Scanner(System.in);
	
	@Override
	public Pattern getRegex() {
		return Pattern.compile("get( )+input");
	}

	@Override
	public Value getValue(Block superBlock, Tokenizer tokenizer) throws InvalidCodeException{
		String value = scanner.nextLine();
		double d = 0;
		try {  
		     d = Double.parseDouble(value);  
		} catch(NumberFormatException e) {  
			return new Value(Type.STRING, value);
		}  
		return new Value(Type.NUMBER, d);
	}

}
