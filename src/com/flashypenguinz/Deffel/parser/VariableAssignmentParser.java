package com.flashypenguinz.Deffel.parser;

import com.flashypenguinz.Deffel.block.Block;
import com.flashypenguinz.Deffel.block.VariableAssignment;
import com.flashypenguinz.Deffel.tokenizer.Regex;
import com.flashypenguinz.Deffel.tokenizer.Tokenizer;
import com.flashypenguinz.Deffel.utils.InvalidCodeException;

public class VariableAssignmentParser extends Parser<VariableAssignment> {

	public VariableAssignmentParser() {
		super(VariableAssignment.class);
	}

	@Override
	public boolean shouldParse(String line) {
		return line.matches("set "+Regex.VARIABLE+" (to|\\=) "+Regex.EXPRESSION);
	}

	@Override
	public VariableAssignment parse(Block superBlock, Tokenizer tokenizer) throws InvalidCodeException {
		tokenizer.nextToken(); // Skip the set token
		
		String variableToken = tokenizer.nextToken().getToken();
		boolean global = variableToken.contains("_");
		variableToken = variableToken.substring(2, variableToken.length()-1);
		
		tokenizer.nextToken(); // Skip the to or = token
		
		String expression = tokenizer.getLine();
		
		return new VariableAssignment(superBlock, variableToken, expression, global);
		
	}

	
	
}
