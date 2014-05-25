package com.github.jesty.camera.textclassification;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.github.jesty.camera.ContextConstants;
import com.github.jesty.camera.entities.Documento;

public class TextalyticsTextClassficiationService implements TextClassificationService {
	
	

	@Override
	public String execute(Map<String, Object> context, Documento currentDocumento) {
		HttpClient client = HttpClientBuilder.create().build();
	    HttpPost post = new HttpPost("https://textalytics.com/core/class-1.1");
	    try {
	      List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	      nameValuePairs.add(new BasicNameValuePair("key", "API_KEY"));
	      nameValuePairs.add(new BasicNameValuePair("of", "json"));
	      nameValuePairs.add(new BasicNameValuePair("model", "IPTC_it"));
	      nameValuePairs.add(new BasicNameValuePair(ContextConstants.TXT, currentDocumento.getText()));
	      
	      post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	 
	      HttpResponse response = client.execute(post);
	      StringBuilder sb = new StringBuilder();
	      BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	      String line = "";
	      while ((line = rd.readLine()) != null) {
	        sb.append(line);
	      }
	      
	      return sb.toString();

	    } catch (IOException e) {
	      throw new RuntimeException(e);
	    }
	}

}
