package com.github.jesty.camera.textclassification;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.util.StringUtils;

import com.github.jesty.camera.entities.Categoria;
import com.github.jesty.camera.entities.Documento;
import com.github.jesty.camera.entities.Parlamentare;
import com.github.jesty.camera.web.AbstractDocumentoAction;
import com.github.jesty.persistenceservices.CameraPersist;

public class TextClassificationAction extends AbstractDocumentoAction {

	private String[] moreDetailedCategories = {"Economia, affari e finanza", "Politica"};
	private TextClassificationService service;
	private int threshold;
	
	public TextClassificationAction(TextClassificationService service, int threshold){
		this.service = service;
		this.threshold = threshold;
	}
	
	public void doAction(Map<String, Object> context){
		
		Documento currentDocumento = getCurrentDocumento(context);
		Documento readDocumento = CameraPersist.readDocumento(currentDocumento.getUri());
		
		if(readDocumento != null) {	
			currentDocumento.setCategories(readDocumento.getCategories());
		} else {
			String value = this.service.execute(context, currentDocumento);
			
			JsonFactory factory = new JsonFactory(); 
		    ObjectMapper mapper = new ObjectMapper(factory); 
		    TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>() {};

		    try {
				HashMap<String,Object> map = mapper.readValue(value, typeRef);
				List list = (List) map.get("category_list");
				List<Categoria> categories = currentDocumento.getCategories();
				if(categories == null){
					categories = new LinkedList<Categoria>();
					currentDocumento.setCategories(categories);
				}
				for (Object objectMap : list) {
					Map<String, Object> object = (Map<String, Object>) objectMap;
					Integer currentThreshold = parse(object.get("relevance"));
					if(currentThreshold == null || currentThreshold >= threshold){
						String label = normalize((String) object.get("label"));
						Categoria categoria = new Categoria();
						categoria.setCategoria(label);
						if(!categories.contains(categoria)){
							categories.add(categoria);
						}
					}
				}
				
				//copy in Parlamentare
				List<Parlamentare> parlamentari = currentDocumento.getParlamentari();
				for (Parlamentare parlamentare : parlamentari) {
					parlamentare.setCategories(categories);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			} 
		}

	}
	
	private Integer parse(Object object) {
		return object != null ? Integer.parseInt((String) object) : null;
	}

	private String normalize(String string) {
		String result = null;
		boolean moreDetailedCategory = isMoreDetailedCategory(string);
		int countOccurency = countOccurency(string);
		boolean isSimpleCategory = countOccurency == 0;
		boolean isFirstLevelCategory = countOccurency == 1;
		boolean isSecondLevelOrMoreCategory = countOccurency > 1;
		if(isSimpleCategory){
			result = string;
		} else if(isFirstLevelCategory && !moreDetailedCategory){
			result = extractFirstLevelCategory(string);
		} else if(isFirstLevelCategory && moreDetailedCategory){
			result  = string;
		} else if(isSecondLevelOrMoreCategory && !moreDetailedCategory){
			result = extractFirstLevelCategory(string);
		} else if(isSecondLevelOrMoreCategory && moreDetailedCategory){
			result = extractUntilSecondLevelCategory(string);
		}
		return result.trim();
	}

	private boolean isMoreDetailedCategory(String string) {
		boolean isMoreDetaieldCategory = false;
		for (String category : moreDetailedCategories) {
			if(string.startsWith(category)){
				isMoreDetaieldCategory = true;
				break;
			}
		}
		return isMoreDetaieldCategory;
	}

	private String extractFirstLevelCategory(String string) {
		int i = string.indexOf("-");
		return string.substring(0, i);
	}
	
	private String extractUntilSecondLevelCategory(String string) {
		int firstI = string.indexOf("-");
		int secondI = string.indexOf("-", firstI + 1);
		return string.substring(0, secondI);
	}

	private int countOccurency(String string) {
		int countOccurrencesOf = StringUtils.countOccurrencesOf(string, "-");
		return countOccurrencesOf;
	}

	

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
}
