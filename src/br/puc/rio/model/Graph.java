package br.puc.rio.model;

import java.util.List;

public class Graph {
	
	public int quantityNodes = 0;
	public Integer matrixAdj[][];
	
	
	
	public Graph(int quantityNodes) {
		
		this.quantityNodes = quantityNodes;
		this.matrixAdj = new Integer[quantityNodes][quantityNodes];
	}


	public void createNodes(List<Integer> weights) {
		// TODO Auto-generated method stub
		
		int count = 0;
		for(int i = 0; i< quantityNodes; i++){
			
			for (int j = 0; j < quantityNodes; j++) {
				
				if (count < weights.size()) {
					matrixAdj[i][j] = weights.get(count);
					count++;
				}
			}
		}
		
	}


	public void setMatrixAdj(Integer[][] matrixAdj) {
		this.matrixAdj = matrixAdj;
	}
	
	
	

	
	
}
