package com.jesty.github.jesty.textanalysis.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class AhoCorasick<M extends Matchable> {

	/** Top of the trie. Added so that TST can represent empty string. */
	private State<M> superRoot;

	/** Root of TST. */
	private State<M> root;

	/** Characters for the top level transitions. Added to basic TST. */
	private Set<Character> topChars = new HashSet<Character>();
	
	private boolean prepared = false;

	/**
	 * Constructor.
	 */
	public AhoCorasick() {
		superRoot = new State<M>();
		superRoot.setFailure(superRoot);
	}

	//TODO Javadoc
	public AhoCorasick(M... terms) {
		this();
		for (M term : terms) {
			add(term);
		}
		
		prepare();
	}
	
	public AhoCorasick(List<M> terms) {
		this();
		for (M term : terms) {
			add(term);
		}
		
		prepare();
	}
			
	/**
	 * Initializes the fail transitions of all states. 
	 * The function described in articles by Dori & Landau for adding failure links.
	 * 
	 * To be performed after construction, and after search words have been added.
	 */
	public void prepare() {
		Queue<State<M>> q = new LinkedList<State<M>>();
		// Base case. Failure for depth 1 is the root node.
		for (char c : topChars) {
			State<M> x = goTo(superRoot, c);
			x.setFailure(superRoot);
			q.add(x);
		}
		// Traverse for nodes at depth greater than 1.
		while (!q.isEmpty()) {
			State<M> r = q.remove();
			traverse(r.getMiddle(), r, q);
		}
		
		this.prepared = true;
	}

	/**
	 * Search for patterns in input, depends on {@link #prepare()}.
	 * 
	 * This method is faster than {@link #search(CharSequence)}.
	 * 
	 * @param input
	 *            the text to be searched
	 */
	public void search(CharSequence input, MatchAction<M> ma) {
		if (!prepared) {
			//TODO throw an exception if not prepared
			prepare();
		}
		
		State<M> state = superRoot;
		int position = 0;

		while (position < input.length()) {
			char c = input.charAt(position);
			position++;

			// feed c into automaton, go to the next state
			while (goTo(state, c) == null) {
				state = state.getFailure();
			}
			state = goTo(state, c);
			output(state, position, ma);
		}
	}
	
	/**
	 * Search for patterns in input and return them in a {@link List}, depends
	 * on {@link #prepare()}. This method is slower than
	 * {@link #search(CharSequence, MatchAction))}.
	 * 
	 * @param input
	 *            The text to search in.
	 *            
	 * @return The list of the matched patterns.
	 */
	public List<MatchResult<M>> search(CharSequence input) {
		final List<MatchResult<M>> results = new ArrayList<MatchResult<M>>();
		
		search(input, new MatchAction<M>() {			
			@Override
			public void action(final int position, final M matchable) {
				results.add(new MatchResult<M>() {					
					@Override
					public M value() {
						return matchable;
					}
					
					@Override
					public int start() {
						return position - matchable.pattern().length();
					}
					
					@Override
					public int end() {
						return position;
					}
				});				
			}
		});
		
		return results;
	}
	

	/**
	 * Method described in Dori & Landau for adding failure links.
	 * 
	 * @param p
	 *            current state in traversal
	 * @param r
	 *            the parent of p
	 * @param q
	 *            a container to store fail states
	 */
	private void traverse(State<M> p, State<M> r, Queue<State<M>> q) {
		if (p == null) {
			return;
		}
		State<M> s = p;
		char a = s.getNextChar();
		State<M> state = r.getFailure();

		while (goTo(state, a) == null) {
			state = state.getFailure();
		}

		State<M> failstate = goTo(state, a);
		s.setFailure(failstate);
		q.add(s);
		traverse(s.getLeft(), r, q);
		traverse(s.getRight(), r, q);
	}

	/**
	 * The transition function described in articles by Aho & Corasick and Dori
	 * & Landau. Modified to use the callback <code>ma</code> (Match action).
	 * 
	 * @param x
	 *            the current node, corresponds to a statte in the automaton
	 * @param a
	 *            the next input
	 * @return destination node
	 */
	private State<M> goTo(State<M> x, char a) {
		if (x == null) {
			return null;
		}
		if (x == superRoot) {
			if (topChars.contains(a)) {
				return get(root, String.valueOf(a), 0);
			} else {
				return superRoot;
			}
		}
		return get(x.getMiddle(), String.valueOf(a), 0);
	}

	/**
	 * The output function described in articles by Aho & Corasick and Dori &
	 * Landau. Modified to use the callback <code>ma</code> (Match action).
	 * 
	 * @param state
	 *            the state in the automaton
	 * @param position
	 *            the position in the input
	 */
	private void output(State<M> state, int position, MatchAction<M> ma) {
		while (true) {
			if (state == superRoot) {
				break;
			}
			if (state.getOutput() != null) {
				ma.action(position, state.getOutput());
			}
			state = state.getFailure();
		}
	}

	/**************************************************************
	 * Is string key in the symbol table?
	 **************************************************************/

	/**
	 * Access TST to find if value exists for given key.
	 * 
	 * @param key
	 *            string to search for
	 * @return true if key exists
	 */
	private boolean contains(String key) {
		State<M> x = get(root, key, 0);
		if (x == null) {
			return false;
		}
				
		return x.getOutput() != null;
	}

	/**
	 * Access TST to find value for given key.
	 * 
	 * @param x
	 *            the Node in the TST
	 * @param key
	 *            string to search for
	 * @param d
	 *            index into key
	 * @return subtrie corresponding to given key
	 */
	private State<M> get(State<M> x, String key, int d) {
		if (x == null) {
			return null;
		}
		char c = key.charAt(d);
		if (c < x.getNextChar()) {
			return get(x.getLeft(), key, d);
		} else if (c > x.getNextChar()) {
			return get(x.getRight(), key, d);
		} else if (d < key.length() - 1) {
			return get(x.getMiddle(), key, d + 1);
		} else {
			return x;
		}
	}

	/**************************************************************
	 * Insert string s into the symbol table.
	 * 
	 * @param element
	 *            element to insert. The {@link Matchable#pattern()} value will be used for the match.
	 **************************************************************/

	public void add(M element) {		
		if (element == null) {
			throw new IllegalArgumentException("Null element not allowed.");
		}
		
		String pattern = element.pattern();
		if (pattern == null || pattern.isEmpty()) {
			throw new IllegalArgumentException("Empty or null pattern() not allowed.");
		}
				
		topChars.add(pattern.charAt(0));
		if (!contains(pattern)) {
			root = add(root, element, 0);
		}
		
		this.prepared = false;
	}

	/**
	 * Add String to TST.
	 * 
	 * @param x
	 *            the Node in the TST
	 * @param matchable
	 *            element to add
	 * @param d
	 *            index into s
	 * @return updated Node
	 */
	private State<M> add(State<M> x, M matchable, int d) {
		char c = matchable.pattern().charAt(d);
		if (x == null) {
			x = new State<M>();
			x.setNextChar(c);
		}
		if (c < x.getNextChar()) {
			x.setLeft(add(x.getLeft(), matchable, d));
		} else if (c > x.getNextChar()) {
			x.setRight(add(x.getRight(), matchable, d));
		} else if (d < matchable.pattern().length() - 1) {
			x.setMiddle(add(x.getMiddle(), matchable, d + 1));
		} else {
			x.setOutput(matchable);
		}
		return x;
	}

	/**
	 * Callback interface for exact set matching.
	 */
	public interface MatchAction<T> {

		/**
		 * Method to pass message that a match was made.
		 * 
		 * @param position
		 *            where the match was made.
		 * @param pattern
		 *            the element that was matched.
		 */
		void action(int position, T found);

	}
}
