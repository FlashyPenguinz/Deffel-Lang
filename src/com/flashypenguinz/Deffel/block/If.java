package com.flashypenguinz.Deffel.block;

import java.util.ArrayList;

import com.flashypenguinz.Deffel.utils.Condition;
import com.flashypenguinz.Deffel.utils.InvalidCodeException;

public class If extends ConditionalBlock {

	private ArrayList<ElseIf> elseIfs;
	private Else elze;
	
	public If(Block superBlock, Condition[] conditions) {
		super(superBlock, conditions);

		elseIfs = new ArrayList<>();
	}

	public ElseIf addElseIf(ElseIf elseIf) {
        elseIfs.add(elseIf);
        return elseIf;
    }

    public void setElse(Else elze) {
        this.elze = elze;
    }
	
	@Override
	public void run() throws InvalidCodeException {
		if(areConditionsTrue()) {
			for(Block subBlock: getSubBlocks()) {
				subBlock.run();
			}
		} else {
			for (ElseIf elseIf : elseIfs) {
                if (elseIf.areConditionsTrue()) {
                    for (Block subBlock : elseIf.getSubBlocks()) {
                        subBlock.run();
                    }
                    return;
                }
            }
			if (elze != null) {
                for (Block subBlock : elze.getSubBlocks()) {
                    subBlock.run();
                }
            }
		}
	}

}
