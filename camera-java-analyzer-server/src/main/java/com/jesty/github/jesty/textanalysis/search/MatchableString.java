package com.jesty.github.jesty.textanalysis.search;

public class MatchableString implements Matchable {
	private String value;

	public MatchableString(String value) {
		this.value = value;
	}

	@Override
	public String pattern() {
		return value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
