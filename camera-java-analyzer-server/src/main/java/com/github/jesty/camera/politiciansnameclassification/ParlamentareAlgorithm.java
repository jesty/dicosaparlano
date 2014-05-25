package com.github.jesty.camera.politiciansnameclassification;

import java.io.Serializable;

import com.jesty.github.jesty.textanalysis.search.Matchable;


public class ParlamentareAlgorithm implements Serializable, Matchable {
	
    private static final long serialVersionUID = -1582299211310459373L;
    
	private Integer id;
	private String pattern;
	private String url;

	private String surname;

	private String name;
	
	public ParlamentareAlgorithm() { }
		
	public ParlamentareAlgorithm(Integer id, String pattern, String name, String surname, String url) {
	    this.id = id;
	    this.pattern = pattern;
		this.name = name;
		this.surname = surname;
	    this.url = url;
    }

	
	public void setUri(String wikipedia) {
	    this.url = wikipedia;
    }
	
	public String getUri() {
		return url;
    }

	public Integer getId() {
	    return id;
    }
	
	public void setId(Integer id) {
	    this.id = id;
    }
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getUrl() {
		return url;
	}
	

	@Override
	public String toString() {
		return this.pattern;
	}

	@Override
    public String pattern() {
		return this.pattern;
    }
	
	public String getHtmlName(){
		int lastSlash = url.lastIndexOf('/');
		if(lastSlash > 0) {
			return url.substring(lastSlash);
		} else {
			return null;
		}
	}
	
	@Override
	public int hashCode() {
	    return this.url.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
	    return obj instanceof ParlamentareAlgorithm ? getUri().equals(((ParlamentareAlgorithm)obj).getUri()) : false;
	}
	
}
