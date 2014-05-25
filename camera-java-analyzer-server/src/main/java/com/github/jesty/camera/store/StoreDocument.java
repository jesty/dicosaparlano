package com.github.jesty.camera.store;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.github.jesty.camera.entities.Documento;
import com.github.jesty.camera.web.AbstractDocumentoAction;

public class StoreDocument extends AbstractDocumentoAction {

	@Override
	public void doAction(Map<String, Object> context) {

		Documento currentDocumento = getCurrentDocumento(context);
		remoteStore2(currentDocumento);
		//		CameraPersist.persistDocumento(getCurrentDocumento(context));

	}


	

	private void remoteStore2(Documento documento) {
		try {
			String json = getJson(documento);

			String url = getUrl(documento);


			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost(url);

			StringEntity input = new StringEntity(json, "UTF-8");
			input.setContentType("application/json");
			postRequest.setEntity(input);

			HttpResponse response = httpClient.execute(postRequest);
			
			StatusLine statusLine = response.getStatusLine();


		} catch(Exception e){
			//scrivi eccezione, ma non bloccca il flusso
			e.printStackTrace();
		}
	}



	private String getJson(Documento documento) throws IOException,
	JsonGenerationException, JsonMappingException {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(new Documento[]{documento});
		return json;
	}


	private String getUrl(Documento documento) {
		if(documento.getUri().contains("camera.it")){
			return "http://camera-fej.rhcloud.com/acts/cimport.json";
		} else {
			return "http://camera-fej.rhcloud.com/documents/cimport.json";
		}
	}

}
