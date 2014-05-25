package com.github.jesty.camera.webservices;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.jesty.camera.entities.Categoria;
import com.github.jesty.camera.entities.Parlamentare;

@Controller
public class AutocompleteServices {
	
	@RequestMapping(value="/autocomplete/parlamentare", method = RequestMethod.GET)
	public @ResponseBody List<Parlamentare> listAllParlamentare(@RequestParam(required=false)String q){
		List<Parlamentare> result;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DiCosaParlano");
		EntityManager em = emf.createEntityManager();
	    result = em.createNativeQuery("SELECT p.* FROM PARLAMENTARE p", Parlamentare.class).getResultList();
	    return result;
	}
	
	
	@RequestMapping(value="/autocomplete/luogo", method = RequestMethod.GET)
	public @ResponseBody List<String> listAllLuoghi(@RequestParam(required=false)String q){

		List<String> result;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DiCosaParlano");
		EntityManager em = emf.createEntityManager();
		result = em.createNativeQuery("SELECT distinct COLLEGIO FROM PARLAMENTARE").getResultList();
		return result;
		
	}
	
	@RequestMapping(value="/autocomplete/categoria", method = RequestMethod.GET)
	public @ResponseBody List<Categoria> listAllCategoria(@RequestParam(required=false)String q){
		List<Categoria> result;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DiCosaParlano");
		EntityManager em = emf.createEntityManager();
		result = em.createNativeQuery("SELECT c.* FROM CATEGORIA c", Categoria.class).getResultList();
		return result;
	}

}
