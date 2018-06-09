package com.flashypenguinz.Deffel.block;

import com.flashypenguinz.Deffel.expressions.Expression;
import com.flashypenguinz.Deffel.utils.InvalidCodeException;
import com.flashypenguinz.Deffel.utils.Type;
import com.flashypenguinz.Deffel.utils.Value;

public class Return extends Block implements ReadOnlyBlock {

	private String value;
	
	public Return(Block superBlock, String value) {
		super(superBlock);
		this.value = value;
	}

	@Override
	public void run() throws InvalidCodeException {
		if(getBlockTree()[0] instanceof Function) {
			if(!value.isEmpty())
				((Function) getBlockTree()[0]).setReturnValue(Expression.parse(value, getSuperBlock()).evaluate());
			else
				((Function) getBlockTree()[0]).setReturnValue(new Value(Type.NOTHING, value));
		} else if(getBlockTree()[0] instanceof On) {
			((On) getBlockTree()[0]).setReturn(true);
		}
	}

}
