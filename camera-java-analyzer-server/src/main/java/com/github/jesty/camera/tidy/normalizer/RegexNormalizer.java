package com.github.jesty.camera.tidy.normalizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import com.github.jesty.camera.tidy.Normalizer;

public class RegexNormalizer implements Normalizer {
	
	private static final String BLANK_SPACE = " ";
	private String text;
	
	public Normalizer init(String text) {
		this.text = text;
		applyRegex(removeNewLines());
		applyRegex(normalizeSpaces());
		return this;
	}

	@Override
    public Normalizer init(InputStream inputStream) {
	    try {
			return init(inputStreamToText(inputStream));
		} catch (IOException e) {
			e.printStackTrace();
			return this;
		}
    }

	private String inputStreamToText(InputStream inputStream) throws IOException {
		if (inputStream != null) {
			Writer writer = new StringWriter();
			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} finally {
				inputStream.close();
			}
			return writer.toString();
		} else {        
			return "";
		}
	}

	@Override
    public Normalizer cleanScript() {
		applyRegex(removeByTagName("script"));
	    return this;
    }

	@Override
    public Normalizer cleanStyleTag() {
		applyRegex(removeByTagName("style"));
	    return this;
    }

	@Override
    public Normalizer cleanComments() {
		applyRegex(removeComments());
	    return this;
    }

	@Override
    public Normalizer cleanQuickListDiv() {
		applyRegex(removeDivByIdOrClassRegex("quicklist"));
	    return this;
    }

	@Override
    public Normalizer cleanFooterDiv() {
		applyRegex(removeDivByIdOrClassRegex("footer"));
	    return this;
    }

	@Override
    public Normalizer cleanHeaderDiv() {
		applyRegex(removeDivByIdOrClassRegex("header"));
	    return this;
    }

	@Override
    public Normalizer cleanMenuDiv() {
		applyRegex(removeDivByIdOrClassRegex("menu"));
	    return this;
    }
	
	@Override
    public Normalizer cleanHeadDiv() {
		applyRegex(removeDivByIdOrClassRegex("head"));
	    return this;
    }

	@Override
    public Normalizer stripTags() {
		applyRegex(removeTags());
	    return this;
    }
	
	@Override
    public Normalizer stripPuntaction() {
		applyRegex(removePuntaction());
	    return this;
    }

	@Override
    public Normalizer normalizeBlankSpaces() {
		applyRegex(normalizeSpaces());
	    return this;
    }

	@Override
    public String getResult() {
	    return this.text;
    }
	
	private String removeNewLines(){
		return "(\r\n|[\r\n])";
	}

	private String removeTags() {
	    return "(<([^>]+)>)";
    }

	private String normalizeSpaces() {
	    return "\\s+";
    }
	
	private String removeComments(){
		return "<!--(.|\\s)*?-->";
	}
	
	private String removePuntaction(){
		return "\\p{Punct}+";
	}

	private String removeByTagName(String tagName) {
	    return new StringBuilder("<").append(tagName).append("\\b[^>]*>(.*?)<\\/").append(tagName).append(">").toString();
    }
	
	private String removeDivByIdOrClassRegex(String classOrId){
		return removeTagByIdOrClassRegex("div", classOrId);
	}
	
	private String removeTagByIdOrClassRegex(String tagName, String classOrId){
		return new StringBuilder("<").append(tagName).append("[^>]*[class|id]=[\"|']").append(classOrId).append("[\"|'][^>]*>(.*?)</").append(tagName).append(">").toString();
	}
	
	private void applyRegex(String regex){
		this.text = text.replaceAll(regex, BLANK_SPACE);
	}


}
