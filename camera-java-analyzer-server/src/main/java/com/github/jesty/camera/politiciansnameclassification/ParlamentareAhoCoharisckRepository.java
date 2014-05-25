package com.github.jesty.camera.politiciansnameclassification;

import java.util.ArrayList;
import java.util.List;

import com.github.jesty.camera.entities.Parlamentare;
import com.github.jesty.camera.webservices.AutocompleteServices;

public class ParlamentareAhoCoharisckRepository implements ParlamentariFileReader {


	@Override
	public List<ParlamentareAlgorithm> listAll() {
		return parseFile();
	}

	public List<ParlamentareAlgorithm> parseFile() {
		List<ParlamentareAlgorithm> artists = new ArrayList<ParlamentareAlgorithm>();
			List<Parlamentare> parlamentari = new AutocompleteServices().listAllParlamentare(null);
			int i = 0;
			for (Parlamentare parlamentare : parlamentari) {
				String name = parlamentare.getNome();
				String surname = parlamentare.getCognome();
				String url = parlamentare.getId();
				artists.add(new ParlamentareAlgorithm(i++, extracted(surname + " " + name), name, surname, url));
				artists.add(new ParlamentareAlgorithm(i++, extracted(name + " " + surname), name, surname, url));
				artists.add(new ParlamentareAlgorithm(i++, extracted("on." + " " + surname), name, surname, url));
				artists.add(new ParlamentareAlgorithm(i++, extracted("onorevole " + " " + surname), name, surname, url));
			}
		return artists;
	}

	private String extracted(String name) {
		return new StringBuilder(" ").append(name).append(" ").toString();
	}

}
