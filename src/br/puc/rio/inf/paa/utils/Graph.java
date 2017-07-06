package br.puc.rio.inf.paa.utils;

import java.util.List;

public class Graph {
	
	int quantityNodes = 0;
	Double matrixAdj[][];
	
	
	
	public Graph(int quantityNodes) {
		
		this.quantityNodes = quantityNodes;
		this.matrixAdj = new Double[quantityNodes][quantityNodes];
	}


	public void createNodes(List<Double> weights) {
		// TODO Auto-generated method stub
		
		int count = 0;
		for(int i = 0; i< quantityNodes; i++){
			
			for (int j = 0; j < quantityNodes; j++) {
				
				if (count < weights.size()) {
					matrixAdj[i][j] = weights.get(count);
					System.out.println(weights.get(count));
					count++;
				}
			}
		}
		
		

		
	}

	
	
}
