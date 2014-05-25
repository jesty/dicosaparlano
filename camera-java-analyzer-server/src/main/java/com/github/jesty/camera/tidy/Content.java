package com.github.jesty.camera.tidy;

import java.io.InputStream;


public class Content {

	private InputStream inputStream;
	private String text;

	public Content(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void normalize(Normalizer normalizer) {
		this.text = normalizer.init(inputStream)
			.cleanScript()
			.cleanStyleTag()
			.cleanQuickListDiv()
			.cleanFooterDiv()
			.cleanHeadDiv()
			.cleanMenuDiv()
			.stripTags()
			.stripPuntaction()
			.cleanComments()
			.normalizeBlankSpaces()
			.getResult();
	}

	public String getText() {
		return text;
	}

}
