package com.flashypenguinz.Deffel.expressions;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.regex.Matcher;

import com.flashypenguinz.Deffel.block.Block;
import com.flashypenguinz.Deffel.tokenizer.DeffelTokenizer;
import com.flashypenguinz.Deffel.tokenizer.Token;
import com.flashypenguinz.Deffel.tokenizer.TokenType;
import com.flashypenguinz.Deffel.utils.InvalidCodeException;
import com.flashypenguinz.Deffel.utils.Operator;
import com.flashypenguinz.Deffel.utils.Type;
import com.flashypenguinz.Deffel.utils.Value;
import com.flashypenguinz.Deffel.utils.Variable;

public class Expression {

	private Value value;

	public Expression(Value value) {
		this.value = value;
	}

	/** Parses a value to get a result (5+7) */
	public static Expression parse(String line, Block block)
			throws InvalidCodeException {
		try {
			String expressionLine = line;
			DeffelTokenizer tokenizer = new DeffelTokenizer(line);
			ArrayList<Token> tokens = new ArrayList<>();

			// Creates token list
			while (tokenizer.hasNextToken()) {
				tokens.add(tokenizer.nextToken());
			}
			
			Stack<Value> values = new Stack<Value>();
			Stack<Operator> operators = new Stack<Operator>();

			int skipTokens = 0;
			
			for (Token token : tokens) {
				if(skipTokens != 0) {
					skipTokens--;
					continue;
				}
				if (token.getType().equals(TokenType.NUMBER_LITERAL)) {
					Value value = new Value(Type.NUMBER, Double.valueOf(token
							.getToken()));
					if (operators.size() > 1) {
						Operator a = operators.pop();
						Operator b = operators.peek();
						operators.push(a);
						if (isNegative(a, b, value)) {
							operators.pop();
							value.setValue(-((double) value.getValue()));
						}
					}
					values.push(value);
				} else if (token.getType().equals(TokenType.STRING_LITERAL)) {
					values.push(new Value(Type.STRING, token.getToken().substring(1, token.getToken().length()-1)));
				} else if(token.getType().equals(TokenType.BOOLEAN_LITERAL)) {
					values.push(new Value(Type.BOOLEAN, Boolean.valueOf(token.getToken())));
				} else if (token.getType().equals(TokenType.TOKEN)) {
					Operator operator = Operator.getOperator(token.getToken());
					if (operator.equals(Operator.LEFTPARENTHESIS)) {
						operators.push(operator);
					} else if (operator.equals(Operator.RIGHTPARENTHESIS)) {
						while (operators.peek() != Operator.LEFTPARENTHESIS)
							values.push(operators.pop().evaulate(values.pop(),
									values.pop()));
						operators.pop();
					} else if (operator.equals(Operator.PLUS)
							|| operator.equals(Operator.MINUS)
							|| operator.equals(Operator.MULTIPLY)
							|| operator.equals(Operator.DIVIDE)) {
						while (!operators.isEmpty()
								&& operator.hasPrecedence(operators.peek()))
							values.push(operators.pop().evaulate(values.pop(),
									values.pop()));
						operators.push(operator);
					}
				} else if(token.getType().equals(TokenType.VARIABLE)){
					String variableToken = token.getToken();
					boolean local = variableToken.contains("_");
					if(!local) {
						variableToken = variableToken.substring(1, variableToken.length()-1);
						Variable var = com.flashypenguinz.Deffel.Runtime.getRuntime().getVariable(variableToken);
						if(var != null)
							values.push(var);
						else
							throw new InvalidCodeException(variableToken+" is not a valid global variable!");
					} else {
						variableToken = variableToken.substring(2, variableToken.length()-1);
						if(block.hasVariable(variableToken)) {
							Variable var = block.getVariable(variableToken);
							values.push(var);
						} else {
							throw new InvalidCodeException(variableToken+" is not a valid local variable!");
						}
					}
				} else {
					for(AddonExpression e: com.flashypenguinz.Deffel.Runtime.expressionParser.getExpressions()) {
						Matcher matcher = e.getRegex().matcher(expressionLine);
						if(matcher.find()) {
							String expression = matcher.group().trim();
							expressionLine = matcher.replaceFirst("");
							Value value = e.getValue(block, new DeffelTokenizer(expression));
							int size = 0;
							DeffelTokenizer t = new DeffelTokenizer(expression);
							while(t.hasNextToken()) {
								t.nextToken();
								size++;
							}
							skipTokens = size;
							values.push(value);
							break;
						}
					}
				}
			}
			while (!operators.isEmpty()) {
				values.push(operators.pop()
						.evaulate(values.pop(), values.pop()));
			}
			return new Expression(values.pop());
		} catch (EmptyStackException e) {
			throw new InvalidCodeException("The expression \""+line.trim()+"\" is Invalid");
		} catch (InvalidCodeException e) {
			throw new InvalidCodeException(e.getMessage());
		}
	}

	public static boolean isNegative(Operator a, Operator b, Value value) {
		if (a.equals(Operator.MINUS) && b.equals(Operator.LEFTPARENTHESIS))
			return true;
		return false;
	}

	public Value evaluate() {
		return value;
	}

}
