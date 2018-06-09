package com.flashypenguinz.Deffel.parser;

import java.util.ArrayList;

import com.flashypenguinz.Deffel.block.Block;
import com.flashypenguinz.Deffel.block.Function;
import com.flashypenguinz.Deffel.tokenizer.Regex;
import com.flashypenguinz.Deffel.tokenizer.Token;
import com.flashypenguinz.Deffel.tokenizer.TokenType;
import com.flashypenguinz.Deffel.tokenizer.Tokenizer;
import com.flashypenguinz.Deffel.utils.InvalidCodeException;
import com.flashypenguinz.Deffel.utils.Parameter;
import com.flashypenguinz.Deffel.utils.Type;

public class FunctionParser extends Parser<Function> {

	public FunctionParser() {
		super(Function.class);
	}

	@Override
	public boolean shouldParse(String line) {
		return line.matches("on function " + Regex.IDENTIFIER + "( )?" + "\\(("
				+ Regex.IDENTIFIER + " " + Regex.VARIABLE + "(\\,)?( )?)*\\)"
				+ "(( )?return(s)? " + Regex.IDENTIFIER + ")?(:)?");
	}

	@Override
	public Function parse(Block superBlock, Tokenizer tokenizer) throws InvalidCodeException {
		tokenizer.nextToken(); // Skip the on
		tokenizer.nextToken(); // Skip the function
		String name = tokenizer.nextToken().getToken();
		tokenizer.nextToken(); // Skip the ( token

		Token first = tokenizer.nextToken();

		ArrayList<Parameter> params = new ArrayList<>();

		if (!first.getToken().equals(")")) {
			String[] paramData = new String[] { first.getToken(), null }; // 0 - type, 1 - value

			while (tokenizer.hasNextToken()) {
				Token token = tokenizer.nextToken();
				if (token.getToken().equals(")"))
					break;
				if(token.getToken().equals(","))
					continue;
				if (paramData[0] == null) {
					paramData[0] = token.getToken();
				} else {
					if(token.getToken().matches(Regex.VARIABLE)){
						if(token.getToken().contains("_")) {
							paramData[1] = token.getToken().substring(2, token.getToken().length()-1);
							params.add(new Parameter(Type.valueOf(paramData[0].toUpperCase()),
									paramData[1]));
							paramData = new String[2];
						} else {
							throw new InvalidCodeException("Function parameter variables must be local! "
									+ token.getToken().substring(1, token.getToken().length()-1)+" in function "+name+" was defined global!");
						}
					} else {
						throw new InvalidCodeException("Function parameter doesn't have a defined variable!");
					}
				}
			}
		}
		String returnType = "";
		boolean returns = false;
		if(!tokenizer.nextToken().getType().equals(TokenType.TOKEN)) {
			returnType = tokenizer.nextToken().getToken();
			returns = true;
		}
		return new Function(null, name, returnType, returns, 
				params.toArray(new Parameter[params.size()]));
	}
}
