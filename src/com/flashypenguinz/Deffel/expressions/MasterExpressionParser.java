package com.flashypenguinz.Deffel.expressions;

public class MasterExpressionParser {

	private AddonExpression[] expressions;
	
	public MasterExpressionParser() {
		expressions = new AddonExpression[] {
			new Input(),
			new ToString()
		};
	}
	
	public AddonExpression[] getExpressions() {
		return expressions;
	}
	
}
