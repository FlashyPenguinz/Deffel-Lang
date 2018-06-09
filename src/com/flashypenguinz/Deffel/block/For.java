package com.flashypenguinz.Deffel.block;

import com.flashypenguinz.Deffel.utils.Condition;
import com.flashypenguinz.Deffel.utils.InvalidCodeException;

public class For extends ConditionalBlock implements LoopBlock {
	
	private boolean breaks = false;
	private boolean continues = false;
	
	private VariableAssignment declaration;
	private VariableAssignment incrementation;
	
	public For(Block superBlock, VariableAssignment declaration, Condition[] conditions, VariableAssignment incrementation) {
		super(superBlock, conditions);
		this.declaration = declaration;
		this.declaration.setSuperBlock(this);
		this.incrementation = incrementation;
		this.incrementation.setSuperBlock(this);
	}
	
	@Override
	public void run() throws InvalidCodeException {
		declaration.run();
		while(areConditionsTrue()&&!breaks) {
			for(Block subBlock: getSubBlocks()) {
				subBlock.run();
				if(continues||breaks) {
					continues = false;
					break;
				}
			}
			incrementation.run();
		}
	}
	
	@Override
	public void setBreaks(boolean breaks) {
		this.breaks = breaks;
	}

	@Override
	public void setContinues(boolean continues) {
		this.continues = continues;
	}

}
