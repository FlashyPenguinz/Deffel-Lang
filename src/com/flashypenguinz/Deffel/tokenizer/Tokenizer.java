package com.flashypenguinz.Deffel.tokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import com.flashypenguinz.Deffel.utils.InvalidCodeException;

public class Tokenizer {

	protected ArrayList<TokenData> tokenDatas;
	
	protected String str;
	
	protected Token lastToken;
	protected boolean pushBack;
	
	public Tokenizer(String str) {
		this.tokenDatas = new ArrayList<TokenData>();
		this.str = str;
	}
	
	public Token nextToken() throws InvalidCodeException {
		str = str.trim();
		if(pushBack) {
			pushBack = false;
			return lastToken;
		}
		
		if(str.isEmpty()) {
			return (lastToken = new Token("", TokenType.EMPTY));
		}
		
		for(TokenData data: tokenDatas) {
			Matcher matcher = data.getPattern().matcher(str);
			if(matcher.find()) {
				String token = matcher.group().trim();
				str = matcher.replaceFirst("");
				return (lastToken = new Token(token, data.getType()));
			}
		}
		throw new InvalidCodeException("Could not parse: "+str);
	}
	
	public void registerTokenData(TokenData data) {
		tokenDatas.add(data);
	}
	
	public String getLine() {
		return str;
	}
	
	public boolean hasNextToken() {
		return !str.isEmpty();
	}
	
	public void pushBack() {
		if(lastToken!=null) {
			this.pushBack = true;
		}
	}
	
	public static String constructLine(List<Token> tokens) {
		String line = "";
		for(Token token: tokens) {
			line+=token.getToken()+" ";
		}
		return line;
	}
	
}
