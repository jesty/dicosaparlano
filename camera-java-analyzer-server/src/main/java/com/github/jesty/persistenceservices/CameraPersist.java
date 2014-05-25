package com.github.jesty.persistenceservices;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.github.jesty.camera.entities.Categoria;
import com.github.jesty.camera.entities.Documento;
import com.github.jesty.camera.entities.Parlamentare;

public class CameraPersist {

	public static Documento readDocumento(String url){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DiCosaParlano");
		EntityManager em = emf.createEntityManager();
		Query q = em.createNativeQuery("select * from DOCUMENTO where doc_uri = '" + url + "'", Documento.class);
		List resultList = q.getResultList();
		return resultList != null && !resultList.isEmpty() ? (Documento)resultList.get(0) : null;
	}
	
	public static void addCategoriesToParlamentare (List<Categoria> categorie, String uri) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DiCosaParlano");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();


		Parlamentare p = em.find(Parlamentare.class, uri);
		if (p != null){
			List<Categoria> existentCategories = p.getCategories();
			
			List<Categoria> copy = new LinkedList<Categoria>(existentCategories);
			categorie.removeAll(copy);
			existentCategories.addAll(categorie);
			
			p.setCategories(existentCategories);
			
			em.merge(p);
		}

		em.getTransaction().commit();

		em.close();

	}

	public static void persistParlamentare (Parlamentare x) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DiCosaParlano");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();


		Parlamentare p = em.find(Parlamentare.class,x.getId());
		if (p == null){

			em.persist(x);

		}else {
			if(x.getCategories() != null && !x.getCategories().isEmpty()){
				p.setCategories(x.getCategories());
				em.merge(p);
			}
		}

		em.getTransaction().commit();

		em.close();

	}
	
	public static void persistCategory(Categoria categoria){

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DiCosaParlano");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		
		Categoria find = em.find(Categoria.class, categoria.getCategoria());
		if(find == null){
			em.persist(categoria);
		} else {
			em.merge(categoria);
		}
		
		em.getTransaction().commit();
	}


	public static void persistDocumento(Documento documentoNuovo){

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DiCosaParlano");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		
		List<Categoria> categories = documentoNuovo.getCategories();
		for (Categoria categoria : categories) {
			persistCategory(categoria);
		}

		Query q = em.createNativeQuery("select * from DOCUMENTO where doc_uri = '" + documentoNuovo.getUri() + "'", Documento.class);
		List resultList = q.getResultList();
		Documento documentoRecuperato = resultList != null && !resultList.isEmpty() ? (Documento)resultList.get(0) : null;

		if (documentoRecuperato == null){

			em.persist(documentoNuovo);

		}else {
			/*documentoNuovo.setId(documentoRecuperato.getId());
			em.merge(documentoNuovo);*/

		}
		
		em.getTransaction().commit();

		em.close();

		List<Parlamentare> parlamentari = documentoNuovo.getParlamentari();
		if(parlamentari != null){
			for (Parlamentare parlamentare : parlamentari) {
				addCategoriesToParlamentare(documentoNuovo.getCategories(), parlamentare.getId());
			}
		}


	}
	
	public static List<Parlamentare> search(List<String> categories, List<String> luogo){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DiCosaParlano");
		EntityManager em = emf.createEntityManager();
		List<Parlamentare> parlamentares = new LinkedList<Parlamentare>();
		String catString = buildIn(categories);
		String luogoString = buildIn(luogo);;
		String categoriaIN = " categoria in (" + catString + ")";
		String collegioIN = " collegio in (" + luogoString + ")";
		String sql = "select p.* from PARLAMENTARE p JOIN DOCUMENTO_PARLAMENTARE doc_par on (p.`ID` = doc_par.`parlamentari_ID`) JOIN DOCUMENTO doc on (doc.`ID` = doc_par.`Documento_ID`) JOIN DOCUMENTO_CATEGORIA doc_cat on (doc.id = doc_cat.`Documento_ID`) JOIN CATEGORIA cat ON (doc_cat.`categories_categoria`= cat.categoria) ";
		
		if (!catString.isEmpty()) {
			sql += " where " + categoriaIN;
		}
		
		if (!luogoString.isEmpty() && !catString.isEmpty()) {
			sql += " and " + collegioIN;
		}
		
		if (!luogoString.isEmpty() && catString.isEmpty()) {
			sql += " where " + collegioIN;
		}
		
		
		Query createNativeQuery = em.createNativeQuery(sql, Parlamentare.class);
		parlamentares = createNativeQuery.getResultList();
		for (Parlamentare parlamentare : parlamentares) {
			assingCat(parlamentare.getId(), em, parlamentare);
		}
		return parlamentares;
	}

	private static String buildIn(List<String> categories) {
		if(categories == null) {
			return "";
		}
		String catString = "";
		for (String cat : categories) {
			catString += "'" + cat + "',";
		}
		if(catString.length() > 0){
			catString = catString.substring(0, catString.length() -1);
		}
		return catString;
	}

	public static Parlamentare readParlamentare(String string) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DiCosaParlano");
		EntityManager em = emf.createEntityManager();
		Parlamentare parlamentare = em.find(Parlamentare.class, string);
		assingCat(string, em, parlamentare);
		assingDocs(string, em, parlamentare);
		return parlamentare;
	}
	
	private static void assingDocs(String string, EntityManager em, Parlamentare parlamentare) {
		if(parlamentare != null){
			String query = "select * from DOCUMENTO left join DOCUMENTO_PARLAMENTARE on Documento_ID=DOCUMENTO.id where parlamentari_ID = '" + string + "'";
			Query createNativeQuery = em.createNativeQuery(query, Documento.class);
			List<Documento> documenti = createNativeQuery.getResultList();
			for (Documento documento : documenti) {
				documento.setParlamentari(null);
			}
			parlamentare.setDocumenti(documenti);
		}

	}

	private static void assingCat(String string, EntityManager em, Parlamentare parlamentare) {
		if(parlamentare != null){
			String query = "select * from CATEGORIA left join PARLAMENTARE_CATEGORIA on categories_categoria=categoria where Parlamentare_ID = '" + string + "'";
			Query createNativeQuery = em.createNativeQuery(query, Categoria.class);
			List<Categoria> categorie = createNativeQuery.getResultList();
			parlamentare.setCategories(categorie);
		}
	}



}
