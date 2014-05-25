package com.github.jesty.camera.entities;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.github.jesty.camera.entities.Categoria;
import com.github.jesty.camera.entities.Documento;
import com.github.jesty.camera.entities.Parlamentare;
import com.github.jesty.persistenceservices.CameraPersist;

public class DocumentoTest {
	
	@Test
	public void testDocumento() throws Exception {
		Documento documento = new Documento();
		documento.setUri("http:/sdadsasd2213212223");
		List<Categoria> categories = new LinkedList<Categoria>();
		categories.add(new Categoria(1, "Test 1", null));
		categories.add(new Categoria(2, "Test 5sds5", null));
		categories.add(new Categoria(3, "Test 36", null));
		categories.add(new Categoria(3, "Test 4", null));
		categories.add(new Categoria(3, "Test 5A", null));
		documento.setCategories(categories);
		List<Parlamentare> parlamentari = new LinkedList<Parlamentare>();
		Parlamentare paralamentare1 = new Parlamentare();
		paralamentare1.setId("aasss");
		paralamentare1.setCategories(categories);
		parlamentari.add(paralamentare1);
		documento.setParlamentari(parlamentari);
		CameraPersist.persistDocumento(documento);
	}

}
