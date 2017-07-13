package br.puc.rio.inf.paa.capmst.kruskal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.puc.rio.inf.paa.utils.DisjointSets;
import br.puc.rio.model.Edge;
import br.puc.rio.model.Graph;

public class SmartModifiedKruskalMST {

	public static List<Edge> run(Graph graph, List<Edge> edgesIn, List<Edge> edgesOut, int capacity){

		List<Edge> edges = SmartModifiedKruskalMST.getEdges(graph);

		int minimumEdgesRoot = SmartModifiedKruskalMST.getMinimumEdgesFromRoot(graph, capacity);
		int qtyEdgesInRoot = SmartModifiedKruskalMST.edgesRoot(graph, edgesIn);

		List<Edge> edgesRoot = SmartModifiedKruskalMST.getEdgesRoot(graph, edges);

		List<Edge> edgesRootCandidates = SmartModifiedKruskalMST.edgesCandidates(edgesRoot, edgesIn, edgesOut);

		int slots = minimumEdgesRoot - qtyEdgesInRoot;
		slots = Math.min(slots, edgesRootCandidates.size());

		List<Edge> edgesMST = null;
		List<Edge> currentEdgesMST = null;
		int bestResult = Integer.MAX_VALUE;
		int currentResult = 0;

		if(slots > 0){
			List<int[]> indexes = SmartModifiedKruskalMST.getAllCombinations(edgesRootCandidates.size(), Math.min(slots, edgesRootCandidates.size()));

			for(int[] arr:indexes){
				List<Edge> edgesRootAdd = SmartModifiedKruskalMST.getElementsByIndexes(edgesRootCandidates, arr);
				edgesRootAdd.addAll(edgesIn);
				currentEdgesMST = SmartModifiedKruskalMST.exec(graph, edgesRootAdd, edgesOut, capacity);
				currentResult = SmartModifiedKruskalMST.value(currentEdgesMST);
				if(currentResult < bestResult){
					bestResult = currentResult;
					edgesMST = currentEdgesMST;
				}
			}
		}else{
			edgesMST = SmartModifiedKruskalMST.exec(graph, edgesIn, edgesOut, capacity);
		}

		return edgesMST;
	}

	private static List<Edge> exec(Graph graph, List<Edge> edgesIn, List<Edge> edgesOut, int capacity){

		List<Edge> edges = SmartModifiedKruskalMST.getEdges(graph);
		Collections.sort(edges);	

		List<Edge> edgesMST = new ArrayList<Edge>();
		DisjointSets set = new DisjointSets(graph.quantityNodes);

		for(Edge edge:edgesIn){
			edgesMST.add(edge);
			set.union(set.find(edge.origem), set.find(edge.destino));
		}

		List<Edge> edgesRemoved = new ArrayList<Edge>();

		for(Edge edge1:edgesOut){
			for(Edge edge2:edges){
				if(edge1.equals(edge2)){
					edgesRemoved.add(edge2);
				}
			}
		}

		for(Edge edge:edgesRemoved){
			edges.remove(edge);
		}

		for(Edge edge:edges){
			if(set.find(edge.origem) != set.find(edge.destino)){
				if(SmartModifiedKruskalMST.checkFeasibleCapacity(edgesMST, edge, graph.quantityNodes, capacity)){
					edgesMST.add(edge);
					set.union(set.find(edge.origem), set.find(edge.destino));
				}
			}
		}

		return edgesMST;
	}

	private static int getMinimumEdgesFromRoot(Graph graph, int capacity){

		return (int) Math.ceil((double) (graph.quantityNodes-1)/capacity);
	}

	private static int edgesRoot(Graph graph, List<Edge> edges){
		int count = 0;
		for(Edge edge:edges){
			if(edge.destino == graph.quantityNodes-1 || edge.origem == graph.quantityNodes-1){
				count++;
			}
		}
		return count;
	}

	private static List<Edge> getEdgesRoot(Graph graph, List<Edge> edges){
		List<Edge> edgesRoot = new ArrayList<Edge>();
		for(Edge edge:edges){
			if(edge.destino == graph.quantityNodes-1 || edge.origem == graph.quantityNodes-1){
				edgesRoot.add(edge);
			}
		}
		return edgesRoot;
	}

	private static List<Edge> edgesCandidates(List<Edge> edges,  List<Edge> edgesIn, List<Edge> edgesOut){

		List<Edge> edgesCandidates = new ArrayList<Edge>();

		for(Edge edge:edges){
			edgesCandidates.add(edge);
		}

		edgesCandidates.removeAll(edgesIn);
		edgesCandidates.removeAll(edgesOut);

		return edges;
	}

	private static List<Edge> getEdges(Graph graph){
		List<Edge> edges = new ArrayList<Edge>();
		for(int i = 0; i < graph.quantityNodes; i++){
			for(int j = 0; j < graph.quantityNodes; j++){
				if(i > j){
					edges.add(new Edge(i, j, graph.matrixAdj[i][j]));
				}
			}
		}
		return edges;
	}

	public static List<int[]> getAllCombinations(int n, int m){

		int[] indexes = new int[n];

		for(int i = 0; i < indexes.length; i++){
			indexes[i] = i;
		}

		List<int[]> subsets = new ArrayList<>();

		int[] s = new int[m];                  // here we'll keep indices 
		// pointing to elements in input array

		if (m <= n) {
			// first index sequence: 0, 1, 2, ...
			for (int i = 0; (s[i] = i) < m - 1; i++);  
			subsets.add(SmartModifiedKruskalMST.getSubset(indexes, s));
			for(;;) {
				int i;
				// find position of item that can be incremented
				for (i = m - 1; i >= 0 && s[i] == indexes.length - m + i; i--); 
				if (i < 0) {
					break;
				}
				s[i]++;                    // increment this item
				for (++i; i < m; i++) {    // fill up remaining items
					s[i] = s[i - 1] + 1; 
				}
				subsets.add(getSubset(indexes, s));
			}
		}

		return subsets;
	}

	private static List<Edge> getElementsByIndexes(List<Edge> edges, int[] arr){
		List<Edge> res = new ArrayList<Edge>();
		for(int i:arr){
			res.add(edges.get(i));
		}
		return res;
	}

	private static int[] getSubset(int[] input, int[] subset) {
		int[] result = new int[subset.length]; 
		for (int i = 0; i < subset.length; i++) 
			result[i] = input[subset[i]];
		return result;
	}

	public static int value(List<Edge> edges){
		int sum = 0;
		for(Edge edge:edges){
			sum+=edge.peso;
		}
		return sum;
	}

	public static boolean checkFeasibleCapacity(List<Edge> edges, Edge edgeCandidate, int qtyVertices, int capacity){
		List<Set<Integer>> setsList = new ArrayList<Set<Integer>>();

		for(Edge edge:edges){
			SmartModifiedKruskalMST.addVerticeToSet(setsList, edge, qtyVertices);
		}
		SmartModifiedKruskalMST.addVerticeToSet(setsList, edgeCandidate, qtyVertices);
				
		for(Set<Integer> set: setsList){
			if(SmartModifiedKruskalMST.containsSubRoot(edges, set, qtyVertices)){
				if(set.size() > capacity){
					return false;
				}
			}
		}

		return true;
	}

	public static void addVerticeToSet(List<Set<Integer>> setsList, Edge edge, int qtyVertices){
		
		int index = -1;
		
		if(SmartModifiedKruskalMST.contains(setsList, edge.origem) && SmartModifiedKruskalMST.contains(setsList, edge.destino)){
			int index1 = SmartModifiedKruskalMST.getIndexSet(setsList, edge.origem);
			int index2 = SmartModifiedKruskalMST.getIndexSet(setsList, edge.destino);
			Set<Integer> setAux = setsList.get(index2);
			setsList.get(index1).addAll(setAux);
			setsList.remove(index2);
		}else if(SmartModifiedKruskalMST.contains(setsList, edge.origem)){
			index = SmartModifiedKruskalMST.getIndexSet(setsList, edge.origem);
			if(edge.origem != qtyVertices-1){
				setsList.get(index).add(new Integer(edge.origem));
			}
			if(edge.destino != qtyVertices-1){
				setsList.get(index).add(new Integer(edge.destino));
			}
		}else if(SmartModifiedKruskalMST.contains(setsList, edge.destino)){
			index = SmartModifiedKruskalMST.getIndexSet(setsList, edge.destino);
			if(edge.origem != qtyVertices-1){
				setsList.get(index).add(new Integer(edge.origem));
			}
			if(edge.destino != qtyVertices-1){
				setsList.get(index).add(new Integer(edge.destino));
			}
		}else if(!SmartModifiedKruskalMST.contains(setsList, edge.origem) && !SmartModifiedKruskalMST.contains(setsList, edge.destino)){
			Set<Integer> set = new HashSet<Integer>();
			if(edge.origem != qtyVertices-1){
				set.add(new Integer(edge.origem));
			}
			if(edge.destino != qtyVertices-1){
				set.add(new Integer(edge.destino));
			}
			
			setsList.add(setsList.size(), set);
		}

	}

	public static boolean contains(List<Set<Integer>> setsList, int vertex){
		boolean contains = false;
		for(Set<Integer> set:setsList){
			if(set.contains(new Integer(vertex))){
				contains = true;
				break;
			}
		}
		return contains;
	}
	
	private static int getIndexSet(List<Set<Integer>> setsList, int vertex){
		for(int i = 0; i < setsList.size(); i++){
			for(Integer integer:setsList.get(i)){
				if(integer.equals(vertex)){
					return i;
				}
			}
			
		}
		return -1;
	}
	
	public static void printSets(List<Set<Integer>> setsList, List<Edge> edges, int qtyVertices){
		String out = "";
		for(Set<Integer> set:setsList){
			out = "";
			for(Integer integer:set){
				out+=integer+", ";
			}
			out+=SmartModifiedKruskalMST.containsSubRoot(edges, set, qtyVertices);
			System.out.println(out);
		}
		
	}
	
	public static boolean containsSubRoot(List<Edge> edges, Set<Integer> set, int qtyVertices){
		
		for(Integer integer:set){
			for(Edge edge:edges){
				if((edge.origem == qtyVertices-1 && integer.equals(edge.destino)) ||
						(edge.destino == qtyVertices-1 && integer.equals(edge.origem))){
					return true;
				}
			}
		}
		
		return false;
	}

}
