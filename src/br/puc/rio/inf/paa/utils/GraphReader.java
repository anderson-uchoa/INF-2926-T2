package br.puc.rio.inf.paa.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import br.puc.rio.model.Graph;

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
		Path path = Paths.get(fileName);
		
		List<String> lines;
		
		Graph graph = null;
		
		try {
			lines = Files.readAllLines(path);
			
			int quantityNodes = 0;
			List<Double> weights = new LinkedList<>();
		    
		    //Get size graph from first line
		    String words[] = lines.get(0).trim().split("\\s+");
		    quantityNodes = Integer.parseInt(words[0]);
		    graph = new Graph(quantityNodes);
		    
		    //Get weigths from others lines 
			for (int i = 1; i < lines.size(); i++) {

				String wordsLines[] = lines.get(i).trim().split("\\s+");
				
			    for(int j = 0; j < wordsLines.length; j ++){      
				   Double number = Double.parseDouble(wordsLines[j]);
				 
				   weights.add(number);
				   
			    }
			}
			graph.createNodes(weights);
		
		}catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return graph;

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
