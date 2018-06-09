package com.flashypenguinz.Deffel.parser;

import java.util.ArrayList;

import com.flashypenguinz.Deffel.block.Block;
import com.flashypenguinz.Deffel.block.For;
import com.flashypenguinz.Deffel.block.VariableAssignment;
import com.flashypenguinz.Deffel.tokenizer.DeffelTokenizer;
import com.flashypenguinz.Deffel.tokenizer.Regex;
import com.flashypenguinz.Deffel.tokenizer.Token;
import com.flashypenguinz.Deffel.tokenizer.Tokenizer;
import com.flashypenguinz.Deffel.utils.Comparison;
import com.flashypenguinz.Deffel.utils.Condition;
import com.flashypenguinz.Deffel.utils.InvalidCodeException;

public class ForParser extends Parser<For> {

	public ForParser() {
		super(For.class);
	}

	@Override
	public boolean shouldParse(String line) {
		return line.matches("for( )?\\(" + Regex.VARIABLE_ASSIGNMENT
				+ "\\;" + Regex.EXPRESSION + "( )?" + Regex.COMPARISON
				+ "( )?" + Regex.EXPRESSION + "\\;"+Regex.VARIABLE_ASSIGNMENT+"\\)");
	}

	@Override
	public For parse(Block superBlock, Tokenizer tokenizer) throws InvalidCodeException {
		tokenizer.nextToken().getToken(); // Skip the for token
		if (!tokenizer.nextToken().getToken().equals("(")) {
            throw new InvalidCodeException("If statement does not begin with opening parenthesis");
        }
		String[] section = tokenizer.getLine().split(";");
		
		section[0] = section[0].substring(1, section[0].length()).trim();
		VariableAssignment declaration = VariableAssignmentParser.parseVariableAssignment(superBlock, new DeffelTokenizer(section[0]));
		
		section[2] = section[2].substring(0, section[2].length()-1).trim();
		VariableAssignment incrementation = VariableAssignmentParser.parseVariableAssignment(superBlock, new DeffelTokenizer(section[2]));
		
		ArrayList<Condition> conditions = new ArrayList<>();

        ArrayList<Token> a = new ArrayList<>(), b = new ArrayList<>();
        Comparison comparison = null;
        boolean isA = true;
        DeffelTokenizer t = new DeffelTokenizer(section[1].trim()+";");
        while (t.hasNextToken()) {
            Token token = t.nextToken();
            if (token.getToken().equals(";")) {
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
        return new For(superBlock, declaration, conditions.toArray(new Condition[conditions.size()]), incrementation);
	}
}
