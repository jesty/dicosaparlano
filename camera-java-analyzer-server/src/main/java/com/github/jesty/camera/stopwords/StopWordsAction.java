package com.github.jesty.camera.stopwords;

import java.util.List;
import java.util.Map;

import opennlp.tools.tokenize.SimpleTokenizer;

import com.github.jesty.camera.entities.Documento;
import com.github.jesty.camera.web.AbstractDocumentoAction;

public class StopWordsAction extends AbstractDocumentoAction {

	private StopWords stopWords = new StopWords();
	
	@Override
	public void doAction(Map<String, Object> context) throws Exception {
		Documento currentDocumento = getCurrentDocumento(context);
		SimpleTokenizer simpleTokenizer = SimpleTokenizer.INSTANCE;
		String[] tokenize = simpleTokenizer.tokenize(currentDocumento.getText());
		List<String> withoutStopWords = stopWords.analyze(tokenize);
		StringBuilder rebuildString = new StringBuilder();
		for (String string : withoutStopWords) {
			rebuildString.append(string).append(" ");
		}
		currentDocumento.setText(rebuildString.toString().trim());
	}
	
	@Override
	public void init() {
		stopWords.build();
	}


}
