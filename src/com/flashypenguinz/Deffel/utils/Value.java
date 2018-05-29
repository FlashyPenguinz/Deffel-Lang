package com.flashypenguinz.Deffel.utils;

/**
 * Represents a value
 */
public class Value {

	private Type type;
	private Object value;
	
	public Value(Object value) {
		new Value(null, value);
	}
	
	public Value(Type type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	public Type getType() {
		return type;
	}
	
	public Object getValue() {
		if (type == null || value == null) {
            return value;
        }

        if (type==Type.NUMBER) {
        	return Double.valueOf(value.toString());
        } else if (type == Type.STRING) {
            return value;
        } else {
            return value;
        }
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
}
