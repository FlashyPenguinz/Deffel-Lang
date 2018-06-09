package com.flashypenguinz.Deffel.parser;

import java.util.ArrayList;

import com.flashypenguinz.Deffel.block.Block;
import com.flashypenguinz.Deffel.block.While;
import com.flashypenguinz.Deffel.tokenizer.Regex;
import com.flashypenguinz.Deffel.tokenizer.Token;
import com.flashypenguinz.Deffel.tokenizer.Tokenizer;
import com.flashypenguinz.Deffel.utils.Comparison;
import com.flashypenguinz.Deffel.utils.Condition;
import com.flashypenguinz.Deffel.utils.InvalidCodeException;

public class WhileParser extends Parser<While>{

	public WhileParser() {
		super(While.class);
	}

	@Override
	public boolean shouldParse(String line) {
		return line.matches("while( )?\\((" + Regex.EXPRESSION + "( )?" + Regex.COMPARISON + "( )?" + Regex.EXPRESSION + ")*\\)");
	}

	@Override
	public While parse(Block superBlock, Tokenizer tokenizer) throws InvalidCodeException {
		tokenizer.nextToken().getToken(); // Skip the while token
		if (!tokenizer.nextToken().getToken().equals("(")) {
            throw new InvalidCodeException("If statement does not begin with opening parenthesis");
        }
		ArrayList<Condition> conditions = new ArrayList<>();

        ArrayList<Token> a = new ArrayList<>(), b = new ArrayList<>();
        Comparison comparison = null;
        boolean isA = true;
        while (tokenizer.hasNextToken()) {
            Token token = tokenizer.nextToken();
            if (token.getToken().equals(")")) {
            	conditions.add(new Condition(superBlock, Tokenizer.constructLine(a), Tokenizer.constructLine(b), comparison));
                a.clear();
                b.clear();
                comparison = null;
                isA = true;
                break;
            }
            if (Comparison.valueOfToken(token.getToken()) == null) {
                if (isA) {
                    a.add(token);
                } else {
                    b.add(token);
                }
            } else {
                comparison = Comparison.valueOfToken(token.getToken());
                isA = false;
            }
        }
        return new While(superBlock, conditions.toArray(new Condition[conditions.size()]));
	}
}
