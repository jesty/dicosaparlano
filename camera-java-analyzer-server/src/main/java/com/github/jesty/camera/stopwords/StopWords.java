package com.github.jesty.camera.stopwords;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;


public class StopWords {
	
	@Autowired
	private static Map<String, StopWord> stopWords = new HashMap<String, StopWord>();
	
	public void build(){
		try {
			List<String> lines = readLines(new File("stop.txt"));
			String verbType = null;
			for (String line : lines) {
				//TODO: fix
				StopWord stop = new StopWord("");
				String lineTrimmed = line.trim();
				if(lineTrimmed.equals("")){
					continue;
				} else if(lineTrimmed.contains("|") && !lineTrimmed.startsWith("|")){
					StringTokenizer tokenizer = new StringTokenizer(lineTrimmed, "|");
					stop.setWord(tokenizer.nextToken().trim());
					stop.setType(tokenizer.hasMoreTokens() ? tokenizer.nextToken().trim() : "undefined");
				} else if("| forms of avere, to have (not including the infinitive):".equals(lineTrimmed)){
					verbType = "avere";
				} else if("| forms of essere, to be (not including the infinitive):".equals(lineTrimmed)){
					verbType = "essere";
				} else if("| forms of fare, to do (not including the infinitive, fa, fat-):".equals(lineTrimmed)){
					verbType = "fare";
				} else if("| forms of stare, to be (not including the infinitive):".equals(line)){
					verbType = "stare";
				} else if(verbType != null){
					stop.setWord(lineTrimmed);
					stop.setType(verbType);
				} 
				if(StringUtils.hasText(stop.getWord())) {
					stopWords.put(stop.getWord(), stop); 
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<String> analyze(String[] input){
		List<String> words = new LinkedList<String>();
		for (String string : input) {
			String lowerCaseString = string.toLowerCase();
			if(!stopWords.containsKey(lowerCaseString)){
				words.add(string);
			}
		}
		return words;
	}
	
	private List<String> readLines(File file) throws IOException{
		//this.getClass().getClassLoader()
		InputStream    fis;
		BufferedReader br;
		String         line;
		List<String> result = new LinkedList<String>();
//		fis = new FileInputStream(file);
		fis = this.getClass().getClassLoader()
                .getResourceAsStream("stop.txt");

		br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
		while ((line = br.readLine()) != null) {
			result.add(line);
		}
		return result;
	}

}
