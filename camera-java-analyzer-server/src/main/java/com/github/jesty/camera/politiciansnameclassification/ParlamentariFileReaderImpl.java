package com.github.jesty.camera.politiciansnameclassification;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParlamentariFileReaderImpl implements ParlamentariFileReader {

	private static final String TAB = ",";

	private static final String UTF_8 = "UTF-8";

	private final File file;

	public ParlamentariFileReaderImpl(File artistList) {
		this.file = artistList;
	}

	@Override
	public List<ParlamentareAlgorithm> listAll() {
		return parseFile();
	}

	public List<ParlamentareAlgorithm> parseFile() {
		List<ParlamentareAlgorithm> artists = new ArrayList<ParlamentareAlgorithm>();
		Scanner scanner = null;
		try {
			scanner = new Scanner(new FileInputStream(this.file), UTF_8);
			while (scanner.hasNextLine()) {
				String nextLine = scanner.nextLine();
				String[] split = nextLine.split(TAB);
				int i = 0;
				if(split.length >= 3){
					String name = split[2];
					String surname = split[1];
					String url = split[0];;
					artists.add(new ParlamentareAlgorithm(i++, extracted(surname + " " + name), name, surname, url));
					artists.add(new ParlamentareAlgorithm(i++, extracted(name + " " + surname), name, surname, url));
					artists.add(new ParlamentareAlgorithm(i++, extracted("on." + " " + surname), name, surname, url));
					artists.add(new ParlamentareAlgorithm(i++, extracted("onorevole " + " " + surname), name, surname, url));
				} 
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (scanner != null) scanner.close();
		}
		return artists;
	}

	private String extracted(String name) {
		return new StringBuilder(" ").append(name).append(" ").toString();
	}

}
