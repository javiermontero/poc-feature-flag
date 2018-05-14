package com.pocft.domain;

public enum Feature {
	
	ONE("one"),TWO("two"),THREE("three");
	
	private final String fWord;
	
	private Feature(String word) {
		this.fWord =word;
	}

	public String getfWord() {
		return fWord;
	}

}
