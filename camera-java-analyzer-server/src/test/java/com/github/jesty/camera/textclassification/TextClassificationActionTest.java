package com.github.jesty.camera.textclassification;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.github.jesty.camera.ContextConstants;
import com.github.jesty.camera.entities.Categoria;
import com.github.jesty.camera.entities.Documento;

public class TextClassificationActionTest {

	@Test
	public void testVerifyCategoryNormalization() throws Exception {
		TextClassificationService service = new FakeTextClassificationService();
		Map<String, Object> context = new HashMap<String, Object>();
		Documento value = new Documento();
		value.setText("test");
		context.put(ContextConstants.OBJ, value);
		new TextClassificationAction(service, 60).doAction(context);
		List<Categoria> categories = value.getCategories();
		int size = categories.size();
		assertEquals(6, size);
		
	}
	
}
