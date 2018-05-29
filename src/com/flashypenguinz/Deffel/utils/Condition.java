package com.flashypenguinz.Deffel.utils;

import com.flashypenguinz.Deffel.block.Block;
import com.flashypenguinz.Deffel.expressions.Expression;

public class Condition {

	private Block superBlock;
	private String a, b;
	private Comparison comparison;
	
	public Condition(Block superBlock, String a, String b, Comparison comparison) {
        this.superBlock = superBlock;
		this.a = a;
        this.b = b;
        this.comparison = comparison;
    }
	
	public boolean isConditionTrue() throws InvalidCodeException {
		Value a = Expression.parse(this.a, superBlock).evaluate(), b = Expression.parse(this.b, superBlock).evaluate();

        boolean success;
        
        if(comparison == Comparison.EQUAL) {
        	success = a.getValue().equals(b.getValue());
        } else if(comparison == Comparison.NOTEQUAL) {
        	success = !a.getValue().equals(b.getValue());
        } else {
        	if (!a.getType().equals(Type.NUMBER)) {
                throw new IllegalArgumentException("Attempted to use " + comparison + " with non-number first value.");
            }

            if (!b.getType().equals(Type.NUMBER)) {
                throw new IllegalArgumentException("Attempted to use " + comparison + " with non-number second value.");
            }
            
            double aInt = (double) a.getValue(), bInt = (double) b.getValue();
            
            if (comparison == Comparison.GREATERTHAN) {
                success = aInt > bInt;
            } else if (comparison == Comparison.LESSTHAN) {
                success = aInt < bInt;
            } else if (comparison == Comparison.GREATERTHANEQUALTO) {
                success = aInt >= bInt;
            } else if (comparison == Comparison.LESSTHANEQUALTO) {
                success = aInt <= bInt;
            } else {
                throw new IllegalArgumentException(comparison + " is not supported");
            }
        }
        return success;
	}
	
}
