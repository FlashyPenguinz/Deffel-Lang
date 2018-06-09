package com.flashypenguinz.Deffel.block;

import com.flashypenguinz.Deffel.utils.InvalidCodeException;

public class Break extends Block implements ReadOnlyBlock {

	public Break(Block superBlock) {
		super(superBlock);
	}

	@Override
	public void run() throws InvalidCodeException {
		Block currentBlock = getSuperBlock();
		while(currentBlock!=null) {
			if(currentBlock instanceof LoopBlock) {
				((LoopBlock) currentBlock).setBreaks(true);
				return;
			}
			currentBlock = currentBlock.getSuperBlock();
		}
		throw new InvalidCodeException("No loop to break out of!");
	}

}
