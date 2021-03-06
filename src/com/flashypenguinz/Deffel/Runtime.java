package com.flashypenguinz.Deffel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.flashypenguinz.Deffel.block.Block;
import com.flashypenguinz.Deffel.block.Endable;
import com.flashypenguinz.Deffel.block.Function;
import com.flashypenguinz.Deffel.block.On;
import com.flashypenguinz.Deffel.block.ReadOnlyBlock;
import com.flashypenguinz.Deffel.expressions.MasterExpressionParser;
import com.flashypenguinz.Deffel.parser.MasterParser;
import com.flashypenguinz.Deffel.parser.Parser;
import com.flashypenguinz.Deffel.tokenizer.DeffelTokenizer;
import com.flashypenguinz.Deffel.utils.Indicator;
import com.flashypenguinz.Deffel.utils.InvalidCodeException;
import com.flashypenguinz.Deffel.utils.Variable;

public class Runtime {
	
	public static MasterParser parser = new MasterParser();
	public static MasterExpressionParser expressionParser = new MasterExpressionParser();
	
	private static Runtime runtime;
	
	private ArrayList<On> indicators;
	private ArrayList<Function> functions;
	private ArrayList<Variable> globalVariables;
	
	public Runtime() {
		runtime = this;
		try {
			this.indicators = new ArrayList<On>();
			this.functions = new ArrayList<Function>();
			this.globalVariables = new ArrayList<Variable>();
			
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader("examples/current.deffel"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			Block block = null;
			
			String line = "";
			while((line=reader.readLine())!=null) {
				line = line.trim();
				DeffelTokenizer firstTokenizer = new DeffelTokenizer(line);
				String firstToken = firstTokenizer.nextToken().getToken();
				if(line.isEmpty()) continue;
				if (firstToken == null) {
	                continue;
	            }
				if (firstToken.equals("#")) {
	                continue;
	            }
				if (firstToken.equals("end")||firstToken.equals("else")||firstToken.equals("elseif")) {
	                if (block == null) {
	                	reader.close();
	                    throw new InvalidCodeException("Attempted to end non-existent block");
	                }
	                if (!(block instanceof Endable)) {
	                	reader.close();
						throw new InvalidCodeException("Attempted to end non-endable block");
	                }
	                block = block.getSuperBlock();
	                if(firstToken.equals("end"))
	                	continue;
	            }
				DeffelTokenizer tokenizer = new DeffelTokenizer(line);
				Parser<?> matchedParser = parser.match(line);
				if(matchedParser == null) {
					reader.close();
					throw new InvalidCodeException("Invalid line: "+line);
				}
				boolean makeNewBlock = true;
				Block newBlock = matchedParser.parse(block, tokenizer);
				if(newBlock instanceof On) {
					indicators.add((On) newBlock);
				} else if(newBlock instanceof Function) {
					functions.add((Function) newBlock);
				} else {
					if((newBlock instanceof ReadOnlyBlock)) {
						makeNewBlock = false;
					}
					if(block != null)
						block.addBlock(newBlock);
				}
				if(makeNewBlock) 
					block = newBlock;
			}
			
			for (On indicator: indicators) {
				if(indicator.getIndicator().equals(Indicator.START))
					indicator.run();
			}
			
		} catch (InvalidCodeException e) {
			System.err.println(e.getMessage());
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Function getFunction(String name) {
		for(Function function: functions) {
			if(function.getName().equals(name)) {
				return function;
			}
		}
		return null;
	}
	
	public boolean hasFunction(String name) {
		return getFunction(name) != null;
	}
	
	public Variable getVariable(String name) {
		for(Variable var: globalVariables) {
			if(var.getName().equals(name))
				return var;
		}
		return null;
	}
	
	public boolean hasVariable(String name) {
		if(getVariable(name) == null)
			return false;
		return true;
	}
	
	public void addVariable(Variable var) {
		globalVariables.add(var);
	}
	
	public void removeVariable(Variable var) {
		globalVariables.remove(var);
	}
	
	public static Runtime getRuntime() {
		return runtime;
	}
	
	public static void main(String[] args) {
		new Runtime();
	}
	
}
