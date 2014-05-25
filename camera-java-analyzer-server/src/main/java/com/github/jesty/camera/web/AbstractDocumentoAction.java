package com.github.jesty.camera.web;

import java.util.Map;

import com.github.jesty.camera.Action;
import com.github.jesty.camera.ContextConstants;
import com.github.jesty.camera.entities.Documento;

public abstract class AbstractDocumentoAction implements Action {

	@Override
	public void init() {
		//do nothing
	}
	
	public Documento getCurrentDocumento(Map<String, Object> context){
		return ((Documento)context.get(ContextConstants.OBJ));
	}
	
}
