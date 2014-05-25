package com.github.jesty.camera.textclassification;

import java.util.Map;

import com.github.jesty.camera.entities.Documento;

public interface TextClassificationService {

	String execute(Map<String, Object> context, Documento currentDocumento);

}
