package com.flashypenguinz.Deffel.utils;

public enum Operator {

	PLUS("+"), MINUS("-"), MULTIPLY("*"), DIVIDE("/"), LEFTPARENTHESIS("("), RIGHTPARENTHESIS(
			")");

	String token;

	Operator(String token) {
		this.token = token;
	}

	public static Operator getOperator(String token) {
		for (Operator o : Operator.values()) {
			if (o.toString().equals(token)) {
				return o;
			}
		}
		return null;
	}

	public Value evaulate(Value a, Value b) {
		if (!((a.getType().equals(Type.NUMBER) || a.getType().equals(
				Type.STRING)) && (b.getType().equals(Type.NUMBER) || b
				.getType().equals(Type.STRING))))
			if (!(a.getType().equals(Type.NUMBER) || a.getType().equals(
					Type.STRING)))
				throw new IllegalArgumentException(a.getType().toString()
						+ " is not a valid type for this operator!");
			else
				throw new IllegalArgumentException(b.getType().toString()
						+ " is not a valid type for this operator!");
		if (this.equals(Operator.PLUS)) {
			if (a.getType().equals(Type.STRING)
					|| b.getType().equals(Type.STRING)) {
				if (a.getType() == Type.NUMBER) {
					return new Value(Type.STRING, b.getValue().toString()
							+ (double) a.getValue());
				} else if (b.getType() == Type.NUMBER) {
					return new Value(Type.STRING, (double) b.getValue()
							+ a.getValue().toString());
				} else {
					return new Value(Type.STRING, b.getValue().toString()
							+ a.getValue().toString());
				}
			} else {
				if (a.getType().equals(Type.NUMBER)
						|| b.getType().equals(Type.NUMBER)) {
					return new Value(Type.NUMBER, (double) a.getValue()
							+ (double) b.getValue());
				}
			}
		} else if (this.equals(Operator.MINUS)) {
			if (a.getType().equals(Type.NUMBER)
					|| b.getType().equals(Type.NUMBER)) {
				return new Value(Type.NUMBER, (double) b.getValue()
						- (double) a.getValue());
			}
		} else if (this.equals(Operator.MULTIPLY)) {
			if (a.getType().equals(Type.NUMBER)
					|| b.getType().equals(Type.NUMBER)) {
				return new Value(Type.NUMBER, (double) a.getValue()
						* (double) b.getValue());
			}
		} else if (this.equals(Operator.DIVIDE)) {
			if (a.getType().equals(Type.NUMBER)
					|| b.getType().equals(Type.NUMBER)) {
				return new Value(Type.NUMBER, (double) b.getValue()
						/ (double) a.getValue());
			}
		}
		return new Value(Type.NOTHING);
	}

	public boolean hasPrecedence(Operator operator) {
		if (operator.equals(Operator.LEFTPARENTHESIS)
				|| operator.equals(Operator.RIGHTPARENTHESIS))
			return false;
		if ((this.equals(Operator.MULTIPLY) || this.equals(Operator.DIVIDE))
				&& (operator.equals(Operator.PLUS) || operator
						.equals(Operator.MINUS)))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return token;
	}

}
