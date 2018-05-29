package com.flashypenguinz.Deffel.block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.flashypenguinz.Deffel.utils.InvalidCodeException;
import com.flashypenguinz.Deffel.utils.Variable;

/**
 * Represents a block of code
 */
public abstract class Block {

	private Block superBlock;
	private ArrayList<Block> subBlocks;
	private ArrayList<Variable> variables;
	
	public Block(Block superBlock) {
		this.superBlock = superBlock;
		this.subBlocks = new ArrayList<>();
		this.variables = new ArrayList<>();
	}
	
	public void varpurge() {
		this.variables.clear();
		for(Block subBlock: subBlocks) {
			subBlock.varpurge();
		}
	}
	
	public void setSuperBlock(Block superBlock) {
		this.superBlock = superBlock;
	}
	
	public Block getSuperBlock() {
		return superBlock;
	}
	
	public List<Variable> getVariables() {
		return variables;
	}
	
	public Block[] getBlockTree() {
		ArrayList<Block> blocks = new ArrayList<Block>();
		
		Block block = this;
		
		while(block!=null) {
			blocks.add(block);
			block = block.getSuperBlock();
		}
		Collections.reverse(blocks);
		return blocks.toArray(new Block[blocks.size()]);
	}
	
	public Block[] getSubBlocks() {
		return subBlocks.toArray(new Block[subBlocks.size()]);
	}
	
	public void addBlock(Block block) {
		subBlocks.add(block);
	}
	
	public Variable getVariable(String name) {
		Variable v = null;
        for(Block b: getBlockTree()) {
        	if(!b.equals(this)) {
        		if(b.hasVariable(name)) {
        			v = b.getVariable(name);
        		}
        	}
        }
        if(v!=null) {
        	return v;
        } else {
        	for(Variable var: variables) {
        		if(var.getName().equals(name)) {
        			return var;
        		}
        	}
        }
        return null;
    }
	
	public void addVariable(Variable var) {
		variables.add(var);
	}
	
	public boolean hasVariable(String name) {
        return getVariable(name)!=null;
    }
	
	public void removeVariable(Variable variable) {
        Optional<Block> superBlock = Arrays.stream(getBlockTree(), 0, getBlockTree().length - 1)
                .filter(b -> b.hasVariable(variable.getName()))
                .findFirst();

        if (superBlock.isPresent()) {
            superBlock.ifPresent(block -> block.variables.remove(variable));
        }  else {
            if (hasVariable(variable.getName())) {
                variables.remove(variable);
            } else {
                throw new IllegalStateException("Variable " + variable.getName() + " cannot be removed because it is not defined in this scope.");
            }
        }
    }
	
	public abstract void run() throws InvalidCodeException;
	
}
