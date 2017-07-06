package br.puc.rio.inf.paa.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class GraphReader {

	public List<Graph> creatAllInstances() {

		List<String> fileNames = this.getAllFileNames();

		List<Graph> graphInstances = new ArrayList<Graph>();

		for (String fileName : fileNames) {

			Graph instance = createInstance(fileName);
			graphInstances.add(instance);
		}

		return graphInstances;
	}

	private Graph createInstance(String fileName) {
		// TODO Auto-generated method stub
//		Path path = Paths.get(fileName);
//		
//		List<String> lines;
//		
//		try {
//			lines = Files.readAllLines(path);
//		 //   Graph graph = 
//			for (int j = 1; j < lines.size(); j++) {
//
//				String wordsItems[] = lines.get(j).trim().split("\\s+");
//
//				
//			}
//			
//		}catch(IOException e){
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		return null;

	}

	public List<String> getAllFileNames() {

		List<String> fileNames = new ArrayList<String>();

		try (Stream<Path> paths = Files.walk(Paths.get("../INF-2926T2/input"))) {
			paths.forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					fileNames.add(filePath.toString());
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fileNames;

	}

}
