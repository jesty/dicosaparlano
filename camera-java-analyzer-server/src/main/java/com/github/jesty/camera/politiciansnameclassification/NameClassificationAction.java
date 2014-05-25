package com.github.jesty.camera.politiciansnameclassification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.list.SetUniqueList;

import com.github.jesty.camera.entities.Documento;
import com.github.jesty.camera.entities.Parlamentare;
import com.github.jesty.camera.web.AbstractDocumentoAction;
import com.jesty.github.jesty.textanalysis.search.AhoCorasick;
import com.jesty.github.jesty.textanalysis.search.MatchResult;

public class NameClassificationAction extends AbstractDocumentoAction {
	
	private static AhoCorasick<ParlamentareAlgorithm> TREE;

	public void init(){
		if(TREE == null){
			alreadyInit();
		}
	}

	public void alreadyInit() {
		List<ParlamentareAlgorithm> listAll = new ParlamentareAhoCoharisckRepository().listAll();
		TREE = new AhoCorasick<ParlamentareAlgorithm>(listAll);
	}
	
	public void doAction(Map<String, Object> context){
		Documento documento = getCurrentDocumento(context);
		String text = documento.getText();
		List<MatchResult<ParlamentareAlgorithm>> matchs = matchs(text);
		Set<ParlamentareAlgorithm> analyze = this.analyze(text, matchs);
		documento.setText(removePolitician(text, matchs));
		if(analyze.size() <= 0){
			throw new RuntimeException("Stop indexing, no politicians found");
		} else {
			for (ParlamentareAlgorithm parlamentare : analyze) {
				Parlamentare parlamentareDb = new Parlamentare();
				parlamentareDb.setId(parlamentare.getUri());
				parlamentareDb.setNome(parlamentare.getName());
				parlamentareDb.setCognome(parlamentare.getSurname());
				documento.getParlamentari().add(parlamentareDb);
			}
		}
		
	}
	
	public String removePolitician(String text, List<MatchResult<ParlamentareAlgorithm>> matchs){
		char[] charArray = text.toCharArray();
		for (MatchResult<ParlamentareAlgorithm> matchResult : matchs) {
			for(int i = matchResult.start(); i< matchResult.end();i++){
				charArray[i] = '#';
			}
		}
		return new String(charArray).replace("#", "");
	}
	
	public Set<ParlamentareAlgorithm> analyze(String text, List<MatchResult<ParlamentareAlgorithm>> matchs) {
		SetUniqueList result = SetUniqueList.decorate(new ArrayList<ParlamentareAlgorithm>());
		int lastEnd = 0;
		for (MatchResult<ParlamentareAlgorithm> matchResult : matchs) {
			
			ParlamentareAlgorithm match = matchResult.value();
			//filtra termini duplicati
			if(matchResult.start() + 1< lastEnd) { 
				if(lastEnd < matchResult.end()) {
					result.remove(result.size()-1);
				} else {
					continue; 
				}
			}
			result.add(match);
			lastEnd = matchResult.end();
        }
		return result.asSet();
    }

	private List<MatchResult<ParlamentareAlgorithm>> matchs(String text) {
		List<MatchResult<ParlamentareAlgorithm>> search = TREE.search(addSpaces(text.toUpperCase()));
		return search;
	}
	
	private String addSpaces(String value){
		return new StringBuilder(" ").append(value).append(" ").toString();
	}

}
