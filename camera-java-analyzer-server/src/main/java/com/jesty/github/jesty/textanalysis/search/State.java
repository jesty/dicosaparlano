package com.jesty.github.jesty.textanalysis.search;

import java.io.Serializable;

public class State<M extends Matchable> implements Serializable {

	private char nextChar;

	private State<M> left;

	private State<M> middle;

	private State<M> right;

	private State<M> failure;

	private M output;

	public State() {
		super();
	}

	public char getNextChar() {
		return nextChar;
	}
	
	public void setNextChar(char nextChar) {
		this.nextChar = nextChar;
	}

	public State<M> getLeft() {
		return left;
	}

	public void setLeft(State<M> left) {
		this.left = left;
	}

	public State<M> getMiddle() {
		return middle;
	}

	public void setMiddle(State<M> middle) {
		this.middle = middle;
	}

	public State<M> getRight() {
		return right;
	}

	public void setRight(State<M> right) {
		this.right = right;
	}

	public State<M> getFailure() {
		return failure;
	}

	public void setFailure(State<M> failure) {
		this.failure = failure;
	}

	public M getOutput() {
		return output;
	}

	public void setOutput(M output) {
		this.output = output;
	}
	
	

}
