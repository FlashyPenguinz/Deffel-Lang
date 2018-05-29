package com.flashypenguinz.Deffel.block;

import com.flashypenguinz.Deffel.utils.Indicator;
import com.flashypenguinz.Deffel.utils.InvalidCodeException;

public class On extends Block {

	private Indicator indicator;
	
	public On(Indicator indicator) {
		super(null);
		this.indicator = indicator;
	}

	@Override
	public void run() throws InvalidCodeException {
		for(Block b: getSubBlocks()) {
			b.run();
		}
	}
	
	public Indicator getIndicator() {
		return indicator;
	}

}
