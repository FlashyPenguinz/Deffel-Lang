package com.flashypenguinz.Deffel.block;

import java.util.ArrayList;
import java.util.List;

import com.flashypenguinz.Deffel.expressions.Expression;
import com.flashypenguinz.Deffel.tokenizer.Token;
import com.flashypenguinz.Deffel.utils.InvalidCodeException;
import com.flashypenguinz.Deffel.utils.Value;
import com.flashypenguinz.Deffel.Runtime;

public class CallFunction extends Block implements ReadOnlyBlock {

	private Token name;
	private List<String> expressions;
	
	public CallFunction(Block superBlock, Token name, ArrayList<String> expressions) {
		super(superBlock);
		this.name = name;
		this.expressions = expressions;
	}
	
	public String getName() {
		return name.getToken();
	}
	
	public List<Value> getValues() throws InvalidCodeException {
		List<Value> values = new ArrayList<>();
		for(String expression: this.expressions) {
			values.add(Expression.parse(expression, getSuperBlock()).evaluate());
		}
		return values;
	}

	@Override
	public void run() throws InvalidCodeException {
		if(!Runtime.getRuntime().hasFunction(name.getToken()))
			throw new InvalidCodeException(name+" is not a defined function!");
		Function function = Runtime.getRuntime().getFunction(name.getToken());
		List<Value> values = getValues();
		function.invoke(values.toArray(new Value[values.size()]));
	}

}
