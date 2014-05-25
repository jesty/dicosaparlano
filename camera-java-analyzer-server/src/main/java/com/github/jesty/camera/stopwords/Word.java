package com.github.jesty.camera.stopwords;

public class Word {
	
	private String word;
	private String original;
	private final String object = "Word";
	
	
	
	public Word(String word){
		this(word, word);
	}
	
	public Word(String word, String original){
		this.word = word;
		this.original = original;
//		this.object = "Word";
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	
	public void setOriginal(String original) {
		this.original = original;
	}
	
	public String getWord(){
		return word;
	}
	
	public String getOriginal(){
		return original;
	}
	
	public String getObject() {
		return object;
	}

	@Override
	public String toString() {
		return "Word [word=" + word + ", original=" + original + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}

	@Override
	public Word clone() {
		//TODO: rivedere, usare final e togliere set
		Word word = new Word("");
		word.setOriginal(this.original);
		word.setWord(this.word);
		return word;
	}

}
