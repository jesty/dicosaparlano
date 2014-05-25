package com.jesty.github.jesty.textanalysis.search;

import java.util.ArrayList;
import java.util.List;

public class AhoCorasickString extends AhoCorasick<MatchableString> {
	/**
	 * Constructor.
	 */
	public AhoCorasickString() {
		super();
	}

	public AhoCorasickString(String... terms) {
		this();
		for (String term : terms) {
			add(new MatchableString(term));
		}

		prepare();
	}

	public AhoCorasickString(List<String> terms) {
		this();
		for (String term : terms) {
			add(new MatchableString(term));
		}

		prepare();
	}

	/**
	 * Use instead {@link #searchStrings(CharSequence, MatchAction)}
	 */
	@Override
	@Deprecated
	public void search(CharSequence input, MatchAction<MatchableString> ma) {
		super.search(input, ma);
	}

	public void searchStrings(final CharSequence input, final MatchAction<String> ma) {
		super.search(input, new MatchAction<MatchableString>() {
			@Override
			public void action(int position, MatchableString found) {
				ma.action(position, found.pattern());
			}
		});
	}

	/**
	 * Use instead {@link #searchStrings(CharSequence)}
	 */
	@Override
	@Deprecated
	public List<MatchResult<MatchableString>> search(CharSequence input) {
		return super.search(input);
	}

	public List<MatchResult<String>> searchStrings(CharSequence input) {
		final List<MatchResult<String>> results = new ArrayList<MatchResult<String>>();

		searchStrings(input, new MatchAction<String>() {
			@Override
			public void action(final int position, final String found) {
				results.add(new MatchResult<String>() {
					@Override
					public String value() {
						return found;
					}

					@Override
					public int start() {
						return position - found.length();
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
}
