package com.flashypenguinz.Deffel.block;

import com.flashypenguinz.Deffel.expressions.Expression;
import com.flashypenguinz.Deffel.utils.InvalidCodeException;

public class Say extends Block implements ReadOnlyBlock {

	private String expression;
	
	public Say(Block superBlock, String expression) {
		super(superBlock);
		this.expression = expression;
	}

	@Override
	public void run() throws InvalidCodeException {
		Expression e = Expression.parse(expression, this);
		System.out.println(e.evaluate().getValue().toString().trim());
	}

}
