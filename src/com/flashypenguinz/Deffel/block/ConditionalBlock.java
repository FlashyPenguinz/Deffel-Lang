package com.flashypenguinz.Deffel.block;

import com.flashypenguinz.Deffel.utils.Condition;
import com.flashypenguinz.Deffel.utils.InvalidCodeException;

public abstract class ConditionalBlock extends Block implements Endable {

	private Condition[] conditions;

    public ConditionalBlock(Block superBlock, Condition... conditions) {
        super(superBlock);

        this.conditions = conditions;
    }

    public boolean areConditionsTrue() throws InvalidCodeException {
        for (Condition condition : conditions) {
            if (!condition.isConditionTrue()) {
                return false;
            }
        }

        return true;
    }
	
}
