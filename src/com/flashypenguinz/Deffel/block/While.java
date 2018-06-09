package com.flashypenguinz.Deffel.block;

import com.flashypenguinz.Deffel.utils.Condition;
import com.flashypenguinz.Deffel.utils.InvalidCodeException;

public class While extends ConditionalBlock implements LoopBlock {
	
	private boolean breaks = false;
	private boolean continues = false;
	
	public While(Block superBlock, Condition[] conditions) {
		super(superBlock, conditions);
	}
	
	@Override
	public void run() throws InvalidCodeException {
		while(areConditionsTrue()&&!breaks) {
			for(Block subBlock: getSubBlocks()) {
				subBlock.run();
				if(continues||breaks) {
					continues = false;
					break;
				}
			}
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
