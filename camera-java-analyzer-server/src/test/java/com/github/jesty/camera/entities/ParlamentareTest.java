package com.github.jesty.camera.entities;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.github.jesty.camera.entities.Categoria;
import com.github.jesty.camera.entities.Parlamentare;
import com.github.jesty.camera.webservices.AutocompleteServices;
import com.github.jesty.persistenceservices.CameraPersist;

public class ParlamentareTest {
	
	@Test
	public void testRead() throws Exception {
		Parlamentare parlamentare = CameraPersist.readParlamentare("http://dati.camera.it/ocd/persona.rdf/p306523");
		assertNotNull(parlamentare.getCategories());
		assertTrue(parlamentare.getCategories().size() > 0);
	}
	
	@Test
	public void testAll() throws Exception {
		List<String> luogo = new LinkedList<String>();
		List<String> categories = new LinkedList<String>();
		categories.add("Sociale - Demografia");
		List<Parlamentare> search = CameraPersist.search(categories, luogo);
		assertNotNull(search);
		assertTrue(search.size() > 0);
	}
	
	
	@Test
	public void testSaveNoCategorie() throws Exception {
		Parlamentare x = new Parlamentare();
		x.setNome("Davide");
		x.setCognome("Cerbo");
		x.setCollegio("Salerno");
		x.setLuogo("Salerno");
		x.setId("aasss");
		CameraPersist.persistParlamentare(x);
	}
	
	@Test
	public void testSave() throws Exception {
		Parlamentare x = new Parlamentare();
		x.setNome("Davide");
		x.setCognome("Cerbo");
		x.setCollegio("Salerno");
		x.setLuogo("Salerno");
		x.setId("aasss");
		List<Categoria> categories = new LinkedList<Categoria>();
		categories.add(new Categoria(1, "Test 1", null));
		categories.add(new Categoria(2, "Test 2", null));
		categories.add(new Categoria(3, "Test 3", null));
		x.setCategories(categories);
		CameraPersist.persistParlamentare(x);
	}
	

}
