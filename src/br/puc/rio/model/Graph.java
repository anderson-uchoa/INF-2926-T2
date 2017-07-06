package br.puc.rio.model;

public class Graph {

	 public int quantityNodes = 0;
	 public int matrixAdj[][];
	 public Aresta listAdj[][];

	public Graph(int quantityNodes) {

		this.matrixAdj = new int[quantityNodes][quantityNodes];
	}

	


}
