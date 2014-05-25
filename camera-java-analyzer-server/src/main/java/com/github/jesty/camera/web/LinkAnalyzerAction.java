package com.github.jesty.camera.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.github.jesty.camera.entities.Documento;


@Component
public class LinkAnalyzerAction extends AbstractDocumentoAction {

	@Override
	public void doAction(Map<String, Object> context) throws Exception {
		Documento currentDocumento = getCurrentDocumento(context);
		String urlStr = currentDocumento.getUri();
		URL url = new URL(urlStr);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				url.openStream()));
		String line;
		StringBuilder sb = new StringBuilder("");
		while ((line = reader.readLine()) != null) {
			sb.append(line);
			if(line.contains("</head>")) { break; }
		}
		String content = sb.toString().replaceAll("\\s+", " ");
		currentDocumento.setTitle(extractTitle(content));
		currentDocumento.setDescription(extractDescription(content));
		currentDocumento.setImage(extractImage(content));
		reader.close();
	}

	private String extractTitle(String content) {
		String first = readMetaValue("og:title", content);
		String second = first.isEmpty() ? readMetaValue("title", content) : first;
		return second.isEmpty() ? extractTagTitle(content) : second;
	}
	
	private String extractTagTitle(String content){
		Pattern p = Pattern.compile("<title>(.*?)</title>");
	    Matcher m = p.matcher(content);
	    if(m.find()) {
	    	return m.group(1);
	    }
	    return "";
	}

	private String extractImage(String content) {
		String first = readMetaProperty("og:image", content);
		return first.isEmpty() ? readMetaValue("og:image", content) : first;
	}

	private String extractDescription(String content) {
		String first = readMetaValue("description", content);
		return first.isEmpty() ? readMetaValue("og:description", content) : first;
	}
	
	private String readMetaValue(String metaName, String content) {
		Pattern exp = Pattern.compile("<meta name=\"" + metaName + "\" content=\"([^\"]*)\"");
		Matcher matcher = exp.matcher(content);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return "";
	}
	
	private String readMetaProperty(String metaName, String content) {
		Pattern exp = Pattern.compile("<meta property=\"" + metaName + "\" content=\"([^\"]*)\"");
		Matcher matcher = exp.matcher(content);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return "";
	}
	
}
