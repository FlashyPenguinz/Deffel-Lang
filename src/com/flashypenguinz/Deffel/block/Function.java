package com.flashypenguinz.Deffel.block;

import com.flashypenguinz.Deffel.utils.InvalidCodeException;
import com.flashypenguinz.Deffel.utils.Parameter;
import com.flashypenguinz.Deffel.utils.Type;
import com.flashypenguinz.Deffel.utils.Value;
import com.flashypenguinz.Deffel.utils.Variable;

public class Function extends Block {

	private String name, type;
	private Parameter[] params;
	private boolean returns;
	private Value returnValue;
	
	public Function(Block superBlock, String name, String type, boolean returns, Parameter[] params) {
		super(superBlock); 
		this.name = name;
		this.type = type;
		this.returns = returns;
		this.params = params;
	}

	public void setReturnValue(Value returnValue) {
		this.returnValue = returnValue;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public Parameter[] getParameters() {
		return params;
	}
	
	@Override
	public void run() throws InvalidCodeException {
		invoke();
	}
	
	public Value invoke(Value... values) throws InvalidCodeException {
		// Invoke the method with the given values
		Type t = null;
		if(!type.isEmpty())
			t = Type.valueOf(type.toUpperCase());
		
		if(values.length != params.length) {
			throw new InvalidCodeException("Wrong number of values for parameters on method "+name+", got "+ values.length+", looking for "+params.length);
		}
		
		for (int i = 0; i < values.length && i < params.length; i++) {
			Parameter p = params[i];
			Value v = values[i];
			if(p.getType() != v.getType())
				throw new InvalidCodeException("Parameter "+ p.getName()+" should be type of " + p.getType() + ", got "+ v.getType());
			addVariable(new Variable(this, p.getType(), p.getName(), v.getValue()));
		}
		
		for(Block b: getSubBlocks()) {
			b.run();
			if(returnValue!=null) {
				break;
			}
		}
		if(returnValue == null && returns)
			throw new InvalidCodeException("Expected return value, got nothing");
		if(returns&&!returnValue.getType().equals(t))
			throw new InvalidCodeException("Got "+returnValue.getType().toString().toLowerCase()+" as return type, expected "+type);
		
		Value localReturnValue = returnValue;
		returnValue = null;
		return localReturnValue;
	}
	
}
