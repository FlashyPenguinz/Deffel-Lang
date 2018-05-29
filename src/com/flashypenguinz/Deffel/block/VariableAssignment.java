package com.flashypenguinz.Deffel.block;

import com.flashypenguinz.Deffel.expressions.Expression;
import com.flashypenguinz.Deffel.utils.InvalidCodeException;
import com.flashypenguinz.Deffel.utils.Type;
import com.flashypenguinz.Deffel.utils.Value;
import com.flashypenguinz.Deffel.utils.Variable;

public class VariableAssignment extends Block implements ReadOnlyBlock {

	private String name;
	private String value;
	
	private boolean global;
	
	public VariableAssignment(Block superBlock, String name, String value, boolean global) {
		super(superBlock);
		this.name = name;
		this.value = value;
		this.global = global;
	}

	@Override
	public void run() throws InvalidCodeException {
		Expression e = Expression.parse(value, this);
		
		Value value = e.evaluate();
		Type type = value.getType();
		
		if(global) {
			if(!com.flashypenguinz.Deffel.Runtime.getRuntime().hasVariable(name)) {
				com.flashypenguinz.Deffel.Runtime.getRuntime().addVariable(new Variable(null, type, name, value.getValue()));
			} else {
				Variable var = com.flashypenguinz.Deffel.Runtime.getRuntime().getVariable(name);
				com.flashypenguinz.Deffel.Runtime.getRuntime().removeVariable(var);
				com.flashypenguinz.Deffel.Runtime.getRuntime().addVariable(new Variable(null, type, name, value.getValue()));
			}
		} else {
			if(!getSuperBlock().hasVariable(name)) {
				getSuperBlock().addVariable(new Variable(getSuperBlock(), type, name, value.getValue()));
			} else {
				Variable var = getSuperBlock().getVariable(name);
				Block varBlock = var.getBlock();
				removeVariable(var);
				varBlock.addVariable(new Variable(varBlock, type, name, value.getValue()));
			}
		}
	}

	public String getName() {
		return name;
	}
	
}
