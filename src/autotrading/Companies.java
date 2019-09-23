package autotrading;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;

import autotrading.tools.SourceFiles;

public class Companies extends ArrayList<Empresa> {

	private static final long serialVersionUID = -8946141718478069608L;

	public static Companies readFromDirectory(String source) {
		SourceFiles cotizaciones = new SourceFiles(source);
		Companies companies = new Companies();
		
		Iterator<Path> i = cotizaciones.filePaths.iterator();
		while(i.hasNext())
			companies.add(new Empresa(i.next()));
		return companies;
	}
	
}
