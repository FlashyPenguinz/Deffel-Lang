package com.flashypenguinz.Deffel.parser;


public class MasterParser {

	private Parser<?>[] parsers;
	
	public MasterParser() {
		parsers = new Parser<?>[] {
				new OnParser(),
				new SayParser(),
				new VariableAssignmentParser(),
				new IfParser(),
				new FunctionParser(),
				new CallFunctionParser(),
				new ReturnParser(),
				new WhileParser(),
				new ForParser(),
				new BreakParser()};
	}
	
	public Parser<?> match(String line) {
		for(Parser<?> parser: parsers)
			if(parser.shouldParse(line))
				return parser;
		return null;
	}
	
}
