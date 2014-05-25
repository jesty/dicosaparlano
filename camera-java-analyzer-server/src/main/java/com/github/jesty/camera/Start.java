package com.github.jesty.camera;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.github.jesty.camera.pipes.Pipe;
import com.github.jesty.camera.pipes.WebArticlePipeReal;

public class Start {
	
	public static void main(String[] args) throws Exception {
		Pipe webArticlePipe = new WebArticlePipeReal();
		webArticlePipe.init();
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
//		map.put(ContextConstants.URL, "http://www.ilfattoquotidiano.it/2014/05/24/festival-di-cannes-2014-ultimo-giorno-di-gara-e-se-la-palma-fosse-donna/998255/");
//		map.put(ContextConstants.URL, "http://news.centrodiascolto.it/video/tg2/2014-05-20/politica/rai-fico-chiede-di-cambiare");
//		map.put(ContextConstants.URL, "http://documenti.camera.it/leg17/resoconti/commissioni/bollettini/html/2013/07/11/14/comunic.htm#data.20130711.com14.bollettino.sede00020.tit00010");
		map.put(ContextConstants.URL, "http://documenti.camera.it/apps/commonServices/getDocumento.ashx?sezione=bollettini&tipoDoc=comunicato&idlegislatura=17&anno=2013&mese=08&giorno=08&idcommissione=48&pagina=data.20130808.com48.bollettino.sede00010.tit00010&ancora=data.20130808.com48.bollettino.sede00010.tit00010");
//		map.put(ContextConstants.URL, "http://news.centrodiascolto.it/video/tg3/2014-05-20/politica/e-legge-il-decreto-casa-protestano-i-movimenti-la-casa");
		

		webArticlePipe.execute(map);
	}
	
	private static void applyPipe(List<Action> webPipe, Map<String, Object> context) throws Exception {
		for (Action action : webPipe) {
			action.doAction(context);
		}
	}

	private static List<Action> initAddBefore(List<Action> base, Action ...actions){
		List<Action> result = init(actions);
		result.addAll(base);
		return result;
	}
	
	private static List<Action> init(Action ...actions){
		List<Action> result = new LinkedList<Action>(Arrays.asList(actions));
		for (Action action : actions) {
			action.init();
		}
		return result;
	}

}
