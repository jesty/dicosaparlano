package com.github.jesty.camera.tidy;

import java.io.InputStream;


public interface Normalizer {
	
	Normalizer init(InputStream inputStream);
	
	Normalizer cleanScript();
	
	Normalizer cleanStyleTag();
	
	Normalizer cleanComments();
	
	Normalizer cleanQuickListDiv();
	
	Normalizer cleanFooterDiv();
	
	Normalizer cleanHeaderDiv();
	
	Normalizer cleanHeadDiv();
	
	Normalizer stripTags();
	
	Normalizer normalizeBlankSpaces();
	
	Normalizer cleanMenuDiv();

	String getResult();

	Normalizer stripPuntaction();

}
