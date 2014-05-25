package com.github.jesty.camera.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import com.github.jesty.camera.ContextConstants;
import com.github.jesty.camera.entities.Documento;
import com.github.jesty.camera.tidy.Content;
import com.github.jesty.camera.tidy.normalizer.RegexNormalizer;

public class WebAnalyzerAction extends AbstractDocumentoAction {

	@Override
	public void doAction(Map<String, Object> context) throws ClientProtocolException, IOException {
		HttpClient client = HttpClientBuilder.create().build();
		String url = (String) context.get(ContextConstants.URL);
		HttpGet get = new HttpGet(url);
		HttpResponse result = client.execute(get);
		InputStream response = result.getEntity().getContent();
		Content content = new Content(response);
		content.normalize(new RegexNormalizer());
		//result.hasRedirect() || result.isInError());
		Documento currentDocumento = getCurrentDocumento(context);
		currentDocumento.setText(content.getText());
	}

}
