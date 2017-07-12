package br.puc.rio.model;

import java.util.List;

public class Graph {
	
	public int quantityNodes = 0;
	public Integer matrixAdj[][];
	public String name;
	
	
	
	public Graph(int quantityNodes) {
		
		this.quantityNodes = quantityNodes;
		this.matrixAdj = new Integer[quantityNodes+1][quantityNodes+1];
	}


	public void createNodes(List<Integer> weights) {
		int count = 0;
		for(int i = 0; i < this.quantityNodes; i++){
			for (int j = 0; j < this.quantityNodes; j++) {
				if (count < weights.size()) {
					this.matrixAdj[i][j] = weights.get(count);
					count++;
				}
			}
		}
	}

	public void setMatrixAdj(Integer[][] matrixAdj) {
		this.matrixAdj = matrixAdj;
	}
	
	public void print(){
		for(int i = 0; i < this.quantityNodes; i++){
			String row = "";
			for (int j = 0; j < this.quantityNodes; j++) {
				row+=this.matrixAdj[i][j]+"\t";
			}
			System.out.println(row);
		}
	}
	
	
	
}
