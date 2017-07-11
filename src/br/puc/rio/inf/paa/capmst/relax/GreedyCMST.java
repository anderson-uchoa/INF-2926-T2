package br.puc.rio.inf.paa.capmst.relax;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import br.puc.rio.inf.paa.utils.GraphReader;
import br.puc.rio.model.Graph;

public class GreedyCMST {

	Graph graph;
	Integer[][] matrixResult;
	int n;
	int cont = 0;
	Set<Integer> treeSet;
	int sumEdgesCMST;
	
	public GreedyCMST(Graph graph){
		this.graph = graph;
		this.n = graph.quantityNodes;
		this.treeSet =  new TreeSet<Integer>();
	}
	
	public static int buildCMST(Graph graph, int capacity){
		GreedyCMST greedyCMST = new GreedyCMST(graph);
		greedyCMST.buildCMST(1, capacity);
		return greedyCMST.sumEdgesCMST;
	}
	
	public Integer[][] buildCMST(int root, int capacity){
		
		   
	    matrixResult = initMatrixResult(n);
	    int indexMinimum = -1;
	    int minimumDistance = Integer.MAX_VALUE;
	    treeSet.add(root);
	    
		List<Integer> subTree = new ArrayList<>();
		
	    for (int i = 1; i < n; i++) {
			if (root != i && graph.matrixAdj[root][i] != 0) {
				
				
					while (!subTree.contains(i) && matrixResult[root][i] == Integer.MAX_VALUE){
						
						int distanceFromRoot = 0;
						subTree.clear();
						subTree = buildSubTree(root, capacity, subTree, root, i, distanceFromRoot);
					}
		
				
				
			}

		}
	    calculateDistance(root);
		return matrixResult;
		
	}
	

	private void calculateDistance(int root) {
		// TODO Auto-generated method stub
		
		Integer[] edges = new Integer[n];
		for(int i = 1; i< n; i++){
			
			if(root != i){
				edges[i] = edges[i] == null ? matrixResult[root][i] : edges[i];
				
				for(int j = 1; j<n ; j++){
					
					if(i != j && matrixResult[i][j] != Integer.MAX_VALUE ){
						
						if(matrixResult[root][i] + matrixResult[i][j] <= matrixResult[root][j]){
						
							matrixResult[root][j] = matrixResult[root][i] + matrixResult[i][j];
							edges[j] = matrixResult[i][j]; 
						}
						
					}
				}
			}
		
		}
		sumEdgesCMST = 0;
		for(int i = 0; i< n; i++){
			if(edges[i] != null){
				sumEdgesCMST = sumEdgesCMST + edges[i];
			}
		}
	
	}

	public List<Integer> buildSubTree(int root, int capacity, List<Integer> subTree, int currentNode, int destin, int distanceFromRoot){
		
		if (subTree.size() == capacity) {
			return subTree;
		}
		if (subTree.size() < capacity) {
			int indexMinimum = 0;
			int minimumDistance = Integer.MAX_VALUE;

			for (int i = 1; i < n; i++) {
				if (currentNode != i 
					&& graph.matrixAdj[currentNode][i] != 0
					&& graph.matrixAdj[currentNode][i] <= matrixResult[currentNode][i]) {

					if (graph.matrixAdj[currentNode][i] < minimumDistance ) {
						minimumDistance = graph.matrixAdj[currentNode][i];
						indexMinimum = i;
					}
					if(graph.matrixAdj[currentNode][i] < Integer.MAX_VALUE  
						&& i == destin){
						minimumDistance = graph.matrixAdj[currentNode][i];
						indexMinimum = i;
						break;
					}
					

				}
			}
			if (minimumDistance != Integer.MAX_VALUE) {

				// Add node in matrix result
				matrixResult[currentNode][indexMinimum] = graph.matrixAdj[currentNode][indexMinimum];
				distanceFromRoot = distanceFromRoot + minimumDistance;
		
		
				// add node in subtree
				int lastIndexTree = subTree.size();
				subTree.add(lastIndexTree, indexMinimum);

				if (indexMinimum != destin) {
					buildSubTree(root, capacity, subTree, indexMinimum, destin, distanceFromRoot);
				}
				else{
					if(matrixResult[root][destin] > distanceFromRoot){
						matrixResult[root][destin] = distanceFromRoot;
					}
				}
			}
		}

		return subTree;
		
	}

	
	
	public static Integer[][] initMatrixResult(int n){

		Integer[][] matrix = new Integer[n][n];
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				if(i == j || i==0 || j==0){
					matrix[i][j] = 0; //diagonal da matriz ou coluna/linha = 0 (ignored)
				}else{
					matrix[i][j] = Integer.MAX_VALUE;
				}
				
			}
		}
		
		return matrix;
	}
	

	
	
	public static void main(String[] args){
		//teste1();
		GraphReader reader = new GraphReader();
		
		List<Graph> graphs = reader.creatAllInstances();
		
		for (int i = 0; i < graphs.size(); i++) {
				System.out.println("Size Graph: " + graphs.get(i).matrixAdj.length);
				
				
				
				System.out.println("Sum Weights CMST : "+ buildCMST(graphs.get(i), 3));
				
				
		}

	}
	
	
	public static void teste1(){
		
		Integer[][] matrixTest = initMatrixResult(6);
		matrixTest[1][2] = 10;
		matrixTest[1][3] = 3;
		matrixTest[1][4] = 6;
		
	    matrixTest[2][5] = 1;
	    
	    matrixTest[3][2] = 3;
	    matrixTest[3][4] = 1;
	    
	    matrixTest[4][3] = 2;
	    matrixTest[4][5] = 3;
	    
	    
	   Graph graph = new Graph(6);
	   
	   graph.setMatrixAdj(matrixTest);
	   GreedyCMST greedyCMST = new GreedyCMST(graph);
	   Integer[][] result = greedyCMST.buildCMST(1, 5);
	    
	   for(int i =0; i < 6 ; i++){
	    	for(int j = 0; j < 6; j++ ){
	    		System.out.print(result[i][j] + " ");
	    	}
	    	System.out.println();
	   }
	    
	}
	
	
	public static void teste2(){
		Set<Integer> setInt = new TreeSet<>();
		Integer[][] matrixTest = initMatrixResult(6);
		matrixTest[1][2] = 10;
		matrixTest[1][5] = 5;
		
		
	    matrixTest[2][1] = 10;
	    matrixTest[2][3] = 3;
	    matrixTest[2][4] = 1;
	    
	    matrixTest[3][2] = 3;
	    matrixTest[3][5] = 7;
	    
	    matrixTest[4][2] = 1;
	    matrixTest[4][5] = 3;
	    
	    
	    matrixTest[5][1] = 5;
	    matrixTest[5][3] = 7;
	    matrixTest[5][4] = 3;
	    
	    
	    
	    Graph graph = new Graph(6);
		   
		   graph.setMatrixAdj(matrixTest);
		   GreedyCMST greedyCAPMST = new GreedyCMST(graph);
		   Integer[][] result = greedyCAPMST.buildCMST(1, 3);
		    
		   for(int i = 0; i < 6 ; i++){
		    	for(int j = 0; j < 6; j++ ){
		    		System.out.print(result[i][j] + " ");
		    	}
		    	System.out.println();
		   }
		
	}
	
	
	
	
}
