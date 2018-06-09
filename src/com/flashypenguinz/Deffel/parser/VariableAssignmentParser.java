package com.flashypenguinz.Deffel.parser;

import com.flashypenguinz.Deffel.block.Block;
import com.flashypenguinz.Deffel.block.VariableAssignment;
import com.flashypenguinz.Deffel.tokenizer.Regex;
import com.flashypenguinz.Deffel.tokenizer.Token;
import com.flashypenguinz.Deffel.tokenizer.Tokenizer;
import com.flashypenguinz.Deffel.utils.InvalidCodeException;

public class VariableAssignmentParser extends Parser<VariableAssignment> {

	public VariableAssignmentParser() {
		super(VariableAssignment.class);
	}

	@Override
	public boolean shouldParse(String line) {
		return line.matches(Regex.VARIABLE_ASSIGNMENT);
	}

	@Override
	public VariableAssignment parse(Block superBlock, Tokenizer tokenizer) throws InvalidCodeException {
		return parseVariableAssignment(superBlock, tokenizer);
	}

	public static VariableAssignment parseVariableAssignment(Block superBlock, Tokenizer tokenizer) throws InvalidCodeException {
		tokenizer.nextToken(); // Skip the set token
		
		String variableToken = tokenizer.nextToken().getToken();
		boolean local = variableToken.contains("_");
		if(!local)
			variableToken = variableToken.substring(1, variableToken.length()-1);
		else
			variableToken = variableToken.substring(2, variableToken.length()-1);
		
		Token firstExpression = tokenizer.nextToken(); 
		Token secondExpression = tokenizer.nextToken(); 
		
		String expression = "";
		if(secondExpression.getToken().matches("(to|\\=|\\+|\\-)")) {
			if(secondExpression.getToken().equals("to"))
				expression = tokenizer.getLine();
			else {
				if(secondExpression.getToken().equals("=")) {
					if(firstExpression.getToken().equals("+")) {
						if(local)
							expression = "{_"+variableToken+"} + ("+tokenizer.getLine()+")";
						else
							expression = "{"+variableToken+"} + ("+tokenizer.getLine()+")";
					} else {
						if(local)
							expression = "{_"+variableToken+"} - ("+tokenizer.getLine()+")";
						else
							expression = "{"+variableToken+"} - ("+tokenizer.getLine()+")";
					}
				} else if(secondExpression.getToken().equals("+")) {
					if(firstExpression.getToken().equals("+")) {
						if(local)
							expression = "{_"+variableToken+"} + 1";
						else
							expression = "{"+variableToken+"} + 1";
					} else {
						throw new InvalidCodeException("Cannot set variable to +-!");
					}
				} else if(secondExpression.getToken().equals("-")) {
					if(firstExpression.getToken().equals("-")) {
						if(local)
							expression = "{_"+variableToken+"} - 1";
						else
							expression = "{"+variableToken+"} - 1";
					} else {
						throw new InvalidCodeException("Cannot set variable to -+!");
					}
				}
			}
		} else {
			expression = secondExpression.getToken()+tokenizer.getLine();
		}
		
		return new VariableAssignment(superBlock, variableToken, expression, !local);
	}
	 
}
