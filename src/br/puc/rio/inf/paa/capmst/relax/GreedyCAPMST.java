package br.puc.rio.inf.paa.capmst.relax;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


import br.puc.rio.model.Graph;

public class GreedyCAPMST {

	Graph graph;
	Integer[][] matrixResult;
	int n;
	int cont = 0;
	Set<Integer> treeSet;
	
	public GreedyCAPMST(Graph graph){
		this.graph = graph;
		this.n = graph.quantityNodes;
		this.treeSet =  new TreeSet<Integer>();
	}
	
	
	public Integer[][] buildCMST(int root, int capacity){
		
	   
	    matrixResult = initMatrixResult(n);
	    int indexMinimum = -1;
	    int minimumDistance = Integer.MAX_VALUE;
	    treeSet.add(root);
	    for (int i = 0; i < n; i++) {
			if (root != i 
					&& graph.matrixAdj[root][i] != 0 
					&& graph.matrixAdj[root][i] < minimumDistance) {
				
				if(!treeSet.contains(i)){
					minimumDistance =  graph.matrixAdj[root][i];
					indexMinimum = i;
				}
				
			}
			
			if(i + 1 == n && treeSet.size() !=  n - 1){
				List<Integer> subTree = new ArrayList<>();
				subTree.add(indexMinimum);
				treeSet.add(indexMinimum);
				matrixResult[root][indexMinimum] = graph.matrixAdj[root][indexMinimum];
						
				buildSubTree(capacity, subTree, indexMinimum, minimumDistance);
				i = 0;
			}
		}
	
		return matrixResult;
		
	}
	
	
	public List<Integer> buildSubTree(int capacity, List<Integer> subTree,int currentRoot, int distanceFromRoot){
		
		if(subTree.size() == capacity){
			return subTree;
		}
		if(subTree.size() < capacity){
			int indexMinimum = 0;
			int minimumDistance = Integer.MAX_VALUE;
			
			for(int i = 0; i < n; i++){
				if (currentRoot != i 
						&& graph.matrixAdj[currentRoot][i] != 0
						&& graph.matrixAdj[currentRoot][i] != Integer.MAX_VALUE
						&& distanceFromRoot + graph.matrixAdj[currentRoot][i] < minimumDistance) {
					
					
					if(!treeSet.contains(i)){
						minimumDistance = distanceFromRoot + graph.matrixAdj[currentRoot][i];
						indexMinimum = i;
				    }
				}
			}
			if(minimumDistance != Integer.MAX_VALUE){
				distanceFromRoot = minimumDistance;
				// Add node in matrix result
				matrixResult[currentRoot][indexMinimum] = graph.matrixAdj[currentRoot][indexMinimum];
				
				// Add vertex in vertex tree
				treeSet.add(indexMinimum);

				// add node in subtree
				int lastIndexTree = subTree.size();
				subTree.add(lastIndexTree, indexMinimum);

				buildSubTree(capacity, subTree, indexMinimum, distanceFromRoot);
			}
		}
		
		
		
		return subTree;
		
	}
	
	
	public static Integer[][] initMatrixResult(int n){

		Integer[][] matrix = new Integer[n][n];
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				
				matrix[i][j] = Integer.MAX_VALUE;
			}
		}
		
		return matrix;
	}
	
	
	
	
	public static void main(String[] args){
		
		Set<Integer> setInt = new TreeSet<>();
		
		
		setInt.add(0);
		setInt.add(1);
		
		if(setInt.contains(new Integer(0))){
			System.out.println("tem --");
		}
		
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
	   GreedyCAPMST greedyCAPMST = new GreedyCAPMST(graph);
	   Integer[][] result = greedyCAPMST.buildCMST(1, 5);
	    
	   for(int i =0; i < 6 ; i++){
	    	for(int j = 0; j < 6; j++ ){
	    		System.out.print(result[i][j] + " ");
	    	}
	    	System.out.println();
	   }
	    
		
		
	}
	
	
}
