package com.github.jesty.camera.webservices;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.jesty.camera.ContextConstants;
import com.github.jesty.camera.entities.Documento;
import com.github.jesty.camera.pipes.WebArticlePipeFake;
import com.github.jesty.camera.pipes.WebArticlePipeReal;

@Controller
public class AnalyzerService {
	
	private static final String TRUE = "true";

	@Autowired
	private WebArticlePipeFake webArticlePipeFake;
	
	@Autowired
	private WebArticlePipeReal webArticlePipeReal;
	
	@RequestMapping(value="/analyze", method = RequestMethod.GET)
	public @ResponseBody Documento analyze(String url, @RequestParam(required=false) String test){
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(ContextConstants.URL, url);
		if(TRUE.equals(test)){
			webArticlePipeFake.execute(context);
		} else {
			webArticlePipeReal.execute(context);
		}
		return (Documento) context.get(ContextConstants.OBJ);
	}
	
	@RequestMapping(value="/analyze/bulk", method = RequestMethod.POST)
	public @ResponseBody List<Documento> analyzeBulk(@RequestBody String[] urls, @RequestParam(required=false) String test){
		List<Documento> result = new LinkedList<Documento>();
		for (String url : urls) {
			try {
				result.add(analyze(url, test));
			} catch (Exception e) {
				Documento exceptionDocument = new Documento();
				exceptionDocument.setText("ERROR: " + e.getMessage());
				result.add(exceptionDocument);
			}
		}
		return result;
	}

}
