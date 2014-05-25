package com.jesty.github.jesty.textanalysis.search;

import java.io.Serializable;

public interface Matchable extends Serializable {
	/**
	 * Returns a pattern that represents the object and will be used for the
	 * string matching.
	 * 
	 * @return The string representation to be used for the matching.
	 */
	public String pattern();
	
}
