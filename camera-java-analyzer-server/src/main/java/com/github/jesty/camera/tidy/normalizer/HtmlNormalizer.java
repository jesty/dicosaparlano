package com.github.jesty.camera.tidy.normalizer;

import java.io.IOException;
import java.io.InputStream;

import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.github.jesty.camera.tidy.Normalizer;

public class HtmlNormalizer implements Normalizer {
	
	private static final String BLANK_SPACE = " ";
	private String text;
	private Document document;

	@Override
    public Normalizer init(InputStream inputStream) {
		DOMParser parser = new DOMParser();
		try {
			InputSource inputSource = new InputSource(inputStream);
			parser.parse(inputSource);
			document = parser.getDocument();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
    }

	@Override
    public Normalizer cleanScript() {
		removeByTagName("script");
	    return this;
    }

	@Override
    public Normalizer cleanStyleTag() {
		removeByTagName("style");
	    return this;
    }

	@Override
    public Normalizer cleanComments() {
		applyRegex(removeComments());
	    return this;
    }

	@Override
    public Normalizer cleanQuickListDiv() {
		removeDivByIdOrClassRegex("quicklist");
	    return this;
    }

	@Override
    public Normalizer cleanFooterDiv() {
		removeDivByIdOrClassRegex("footer");
	    return this;
    }

	@Override
    public Normalizer cleanHeaderDiv() {
		removeDivByIdOrClassRegex("header");
	    return this;
    }

	@Override
    public Normalizer cleanMenuDiv() {
		removeDivByIdOrClassRegex("menu");
	    return this;
    }
	
	@Override
    public Normalizer cleanHeadDiv() {
		removeDivByIdOrClassRegex("head");
	    return this;
    }

	@Override
    public Normalizer stripPuntaction() {
		applyRegex(removePuntaction());
	    return this;
    }
	
	@Override
    public Normalizer stripTags() {
		applyRegex(removeTags());
	    return this;
    }

	@Override
    public Normalizer normalizeBlankSpaces() {
		applyRegex(normalizeSpaces());
	    return this;
    }

	@Override
    public String getResult() {
		applyRegex(removeNewLines());
		applyRegex(normalizeSpaces());
		return getCurrentText();
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
	
	private String removePuntaction(){
		return "\\p{Punct}+";
	}
	
	private String removeComments(){
		return "<!--(.|\\s)*?-->";
	}

	private void removeByTagName(String tagName) {
		NodeList elements = document.getElementsByTagName(tagName);
		removeAll(elements);
    }

	private void removeDivByIdOrClassRegex(String classOrId){
		removeTagByIdOrClassRegex("div", classOrId);
	}
	
	private void removeTagByIdOrClassRegex(String tagName, String classOrId){
		NodeList elements = document.getElementsByTagName(tagName);
		for (int i = 0; i < elements.getLength(); i++) {
	        Node element = elements.item(i);
	        NamedNodeMap attributes = element.getAttributes();
			if(attributes != null && (getAttribute(attributes, "id").equals(classOrId) || getAttribute(attributes, "class").equals(classOrId))){
				element.getParentNode().removeChild(element);
			}
        }
	}

	private String getAttribute(NamedNodeMap attributes, String name) {
	    Node namedItem = attributes.getNamedItem(name);
		return namedItem != null ? namedItem.getNodeValue() : "";
    }
	
	private void applyRegex(String regex){
	     this.text = getCurrentText().replaceAll(regex, BLANK_SPACE);
	}

	private String getCurrentText() {
	    return (text == null ? document.getElementsByTagName("html").item(0).getTextContent() : text);
    }
	
	private void removeAll(NodeList elements) {
	    for (int i = 0; i < elements.getLength(); i++) {
	        Node element = elements.item(i);
	        element.setTextContent("");
        }
    }

}
