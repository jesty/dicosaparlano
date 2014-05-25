package com.github.jesty.camera;

import java.util.LinkedList;
import java.util.Map;

import com.github.jesty.camera.entities.Categoria;
import com.github.jesty.camera.entities.Documento;
import com.github.jesty.camera.entities.Parlamentare;

public class CreateDocumentoAction implements Action {

	@Override
	public void doAction(Map<String, Object> context) throws Exception {
		Documento value = new Documento();
		value.setUri((String) context.get(ContextConstants.URL));
		value.setCategories(new LinkedList<Categoria>());
		value.setParlamentari(new LinkedList<Parlamentare>());
		context.put("obj", value);
	}

	@Override
	public void init() {
		
	}

}
