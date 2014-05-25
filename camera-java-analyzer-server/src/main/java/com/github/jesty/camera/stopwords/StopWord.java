package com.github.jesty.camera.stopwords;


public class StopWord extends Word {
	
	public StopWord(String word) {
		super(word);
	}

	private String type;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "StopWord [type=" + type + ", word=" + getWord() + ", original="+getOriginal() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		StopWord other = (StopWord) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	
	@Override
	public Word clone() {
		//TODO: rivedere
		StopWord word = new StopWord("");
		word.setOriginal(this.getOriginal());
		word.setWord(this.getWord());
		word.setType(this.type);
		return word;
	}
	
	
}
