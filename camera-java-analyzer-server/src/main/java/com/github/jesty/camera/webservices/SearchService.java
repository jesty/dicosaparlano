package com.github.jesty.camera.webservices;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.jesty.camera.entities.Parlamentare;
import com.github.jesty.persistenceservices.CameraPersist;

@Controller
public class SearchService {
	
	@RequestMapping("/search")
	public @ResponseBody List<Parlamentare> search(@RequestParam(required=false)List<String> categoria, @RequestParam(required=false) List<String> luogo){
		return CameraPersist.search(categoria, luogo);
	}
	
	
	@RequestMapping("/p")
	public @ResponseBody Parlamentare read(@RequestParam String id){
		return CameraPersist.readParlamentare(id);
	}
}
