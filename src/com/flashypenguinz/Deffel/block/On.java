package com.flashypenguinz.Deffel.block;

import com.flashypenguinz.Deffel.utils.Indicator;
import com.flashypenguinz.Deffel.utils.InvalidCodeException;

public class On extends Block {

	private Indicator indicator;
	private boolean returns;
	
	public On(Indicator indicator) {
		super(null);
		this.indicator = indicator;
		returns = false;
	}

	public void setReturn(boolean returns) {
		this.returns = returns;
	}
	
	@Override
	public void run() throws InvalidCodeException {
		for(Block b: getSubBlocks()) {
			b.run();
			if(returns)
				break;
		}
	}
	
	public Indicator getIndicator() {
		return indicator;
	}

}
