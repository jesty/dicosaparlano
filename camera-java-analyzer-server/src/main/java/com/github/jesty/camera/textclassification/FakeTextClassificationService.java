package com.github.jesty.camera.textclassification;

import java.util.Map;

import com.github.jesty.camera.entities.Documento;

public class FakeTextClassificationService implements TextClassificationService {

	@Override
	public String execute(Map<String, Object> context, Documento currentDocumento) {
		return "{\"status\":{\"code\":\"0\",\"msg\":\"OK\",\"credits\":\"562\",\"remaining_credits\":\"497840\"},\"category_list\":[{\"code\":\"02002000\",\"label\":\"Giustizia, Criminalità - Giustizia\",\"abs_relevance\":\"0.18346418\",\"relevance\":\"100\"},{\"code\":\"14003000\",\"label\":\"Sociale - Demografia\",\"abs_relevance\":\"0.1717658\",\"relevance\":\"94\"},{\"code\":\"11024000\",\"label\":\"Politica - Politica (generico)\",\"abs_relevance\":\"0.15504713\",\"relevance\":\"85\"},{\"code\":\"11010000\",\"label\":\"Politica - Partiti, Movimenti\",\"abs_relevance\":\"0.13003518\",\"relevance\":\"71\"},{\"code\":\"11017000\",\"label\":\"Politica - Migrazioni\",\"abs_relevance\":\"0.12737636\",\"relevance\":\"69\"},{\"code\":\"13003000\",\"label\":\"Scienza, Tecnologia - Scienze umane\",\"abs_relevance\":\"0.1227199\",\"relevance\":\"67\"},{\"code\":\"14003003\",\"label\":\"Sociale - Demografia - Immigrazione clandestina\",\"abs_relevance\":\"0.118748285\",\"relevance\":\"65\"},{\"code\":\"02007000\",\"label\":\"Giustizia, Criminalità - Giustizia e diritti\",\"abs_relevance\":\"0.114026584\",\"relevance\":\"62\"},{\"code\":\"14003002\",\"label\":\"Sociale - Demografia - Immigrazione\",\"abs_relevance\":\"0.11206856\",\"relevance\":\"61\"},{\"code\":\"14006000\",\"label\":\"Sociale - Famiglia\",\"abs_relevance\":\"0.110211894\",\"relevance\":\"60\"},{\"code\":\"15000000\",\"label\":\"Sport\",\"abs_relevance\":\"0.102902025\",\"relevance\":\"56\"},{\"code\":\"11006000\",\"label\":\"Politica - Governo\",\"abs_relevance\":\"0.101442836\",\"relevance\":\"55\"}]}";
	}


}
