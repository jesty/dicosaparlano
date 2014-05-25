package com.jesty.github.jesty.textanalysis.search;

public interface MatchResult<T> {
	public int start();

	public int end();

	public T value();

}
