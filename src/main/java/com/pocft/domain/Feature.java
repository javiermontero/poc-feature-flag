package com.pocft.domain;

public enum Feature {
	
	ONE("one"),TWO("two"),THREE("three");
	
	private final String value;
	
	Feature(String v) {
		this.value = v;
	}

	public String getValue() {
		return value;
	}

}
