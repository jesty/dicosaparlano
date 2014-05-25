package com.github.jesty.camera.pipes;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.github.jesty.camera.CreateDocumentoAction;
import com.github.jesty.camera.facets.GeneratesFacets;
import com.github.jesty.camera.politiciansnameclassification.NameClassificationAction;
import com.github.jesty.camera.stopwords.StopWordsAction;
import com.github.jesty.camera.store.StoreDocument;
import com.github.jesty.camera.textclassification.TextClassificationAction;
import com.github.jesty.camera.textclassification.TextalyticsTextClassficiationService;
import com.github.jesty.camera.web.LinkAnalyzerAction;
import com.github.jesty.camera.web.WebAnalyzerAction;

@Component
public class WebArticlePipeReal extends AbstractPipe {
	
	@Override
	@PostConstruct
	public void init() {
		this.add(
				new CreateDocumentoAction(),
				new LinkAnalyzerAction(),
				new WebAnalyzerAction(),
				new NameClassificationAction(),
				new StopWordsAction(),
				new TextClassificationAction(new TextalyticsTextClassficiationService(), 60),
				new StoreDocument(),
				new GeneratesFacets());
		super.init();
	}

}
