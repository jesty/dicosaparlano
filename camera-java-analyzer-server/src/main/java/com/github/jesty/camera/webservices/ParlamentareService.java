package com.github.jesty.camera.webservices;

import java.io.IOException;
import java.sql.Timestamp;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.jesty.camera.entities.Anagrafe;
import com.github.jesty.camera.entities.Luogo;
import com.github.jesty.camera.entities.Parlamentare;
import com.github.jesty.camera.politiciansnameclassification.NameClassificationAction;
import com.github.jesty.persistenceservices.CameraPersist;


@Controller
public class ParlamentareService {

	@RequestMapping(value="/parlamentare", method = RequestMethod.POST)
	public @ResponseBody void addParlamentare(@RequestParam(required=true)String json){
		ObjectMapper m = new ObjectMapper();
		JsonFactory f = m.getJsonFactory();
		try {
			JsonParser parser = f.createJsonParser(json);
			JsonNode obj = m.readTree(parser);
			
			try{
				if (obj.isArray()){
					for (JsonNode subnode: obj){						
						
						CameraPersist.persistParlamentare(readParlamentare(subnode));

					}
				}else {
	
						CameraPersist.persistParlamentare(readParlamentare(obj));

				}
			}catch (JsonParseException e){	
				
				System.err.println("problema di parsing per la data");
				
			}catch (Exception e){
				
				//TODO
				e.printStackTrace();
			}

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(ContextConstants.OBJ, myJson);
		webArticlePipe.execute(context);
		return (Documento) context.get(ContextConstants.OBJ);
		*/
		new NameClassificationAction().alreadyInit();
	}
	
	public Parlamentare readParlamentare (JsonNode obj) throws Exception{
		
		//Info Parlamentare
		Anagrafe anagrafica = null;
		Luogo lNascita= null;
		
		String idPersona = obj.path("idPersona").getTextValue();
		String nome = obj.path("nome").getTextValue();
		String cognome = obj.path("cognome").getTextValue();
		String genere = obj.path("genere").getTextValue();
		String descrizione = obj.path("descrizione").getTextValue();
		Timestamp dataNascita = null;
			
		//Info Luogo di nascita
		if (obj.path("dataNascita").getTextValue()!=null){
			
			dataNascita = new Timestamp(Long.parseLong(obj.path("dataNascita").getTextValue()));
			 
		}
		
		
		if (obj.path("luogoNascita")!=null){
			
			JsonNode luogoNascita = obj.path("luogoNascita");
			String luogoId;
			double lat=0;
			double lon=0;
			
			if (luogoNascita.path("luogoId").getTextValue()!=null){		
				luogoId = luogoNascita.path("luogoId").getTextValue();
				
				if(luogoNascita.path("lat").getTextValue()!=null){
					lat = Double.parseDouble(luogoNascita.path("lat").getTextValue());
				}
				
				if(luogoNascita.path("lon").getTextValue()!=null){
					lon = Double.parseDouble(luogoNascita.path("lon").getTextValue());
				}
				
				
				String comune = luogoNascita.path("comune").getTextValue();
				String provincia = luogoNascita.path("provincia").getTextValue();
				String regione = luogoNascita.path("regione").getTextValue();
				String nazione = luogoNascita.path("nazione").getTextValue();
			
				lNascita = new Luogo(luogoId, lat, lon, regione, comune, provincia, nazione);	
			}
			
		}
		
		anagrafica = new Anagrafe(idPersona, dataNascita, lNascita);

		
		//Info Elezione (ora di Parlamentare)
		JsonNode elezione = obj.path("elezione");
			//da aggiungere per un eventuale entità per l'elezione
			String elezioneId = elezione.path("elezioneId").getTextValue();
			String collegio = elezione.path("collegio").getTextValue();
			Timestamp elezioneData = new Timestamp(Long.parseLong(elezione.path("elezioneData").getTextValue()));		
	
			
		//Instanzio gli oggetti dai valori ottenuti
		
		

		//Istanzio il parlamentare
		Parlamentare parlamentare = new Parlamentare(idPersona, nome, cognome, genere, descrizione, collegio, elezioneData);
		if(anagrafica != null){
			if(anagrafica.getLuogoDiNascita() != null){
				parlamentare.setLuogo(anagrafica.getLuogoDiNascita().getCitta());
			}
			parlamentare.setDataDiNascita(anagrafica.getData());
		}
	
		return parlamentare;
		
	}
	
	
	
}
