package com.flashypenguinz.Deffel.parser;

import java.util.ArrayList;
import java.util.HashMap;

import com.flashypenguinz.Deffel.block.Block;
import com.flashypenguinz.Deffel.block.Else;
import com.flashypenguinz.Deffel.block.ElseIf;
import com.flashypenguinz.Deffel.block.If;
import com.flashypenguinz.Deffel.tokenizer.Regex;
import com.flashypenguinz.Deffel.tokenizer.Token;
import com.flashypenguinz.Deffel.tokenizer.Tokenizer;
import com.flashypenguinz.Deffel.utils.Comparison;
import com.flashypenguinz.Deffel.utils.Condition;
import com.flashypenguinz.Deffel.utils.InvalidCodeException;

public class IfParser extends Parser<Block>{

	private HashMap<Block, If> lastIf = new HashMap<Block, If>();;
	
	public IfParser() {
		super(Block.class);
	}

	@Override
	public boolean shouldParse(String line) {
		return line.equals("else") || line.matches("(if|elseif)( )?\\((" + Regex.EXPRESSION + "( )?" + Regex.COMPARISON + "( )?" + Regex.EXPRESSION + ")*\\)");
	}

	@Override
	public Block parse(Block superBlock, Tokenizer tokenizer) throws InvalidCodeException {
		String type = tokenizer.nextToken().getToken(); // Get the type (if, elseif, else)
		if (type.equals("elseif") && lastIf.get(superBlock) == null) {
            throw new InvalidCodeException("Attempted to write elseif statement without an if statement");
        }
		if (type.equals("else")) {
            if (lastIf.get(superBlock) == null) {
                throw new InvalidCodeException("Attempted to write else statement without if statement");
            } else {
                Else elze = new Else(superBlock);
                lastIf.get(superBlock).setElse(elze);
                lastIf.put(superBlock, null);
                return elze;
            }
        }
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
        if (type.equals("if")) {
        	lastIf.put(superBlock, new If(superBlock, conditions.toArray(new Condition[conditions.size()])));
        	return lastIf.get(superBlock);
        } else {
            return lastIf.get(superBlock).addElseIf(new ElseIf(superBlock, conditions.toArray(new Condition[conditions.size()])));
        }
	}
}
