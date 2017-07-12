package br.puc.rio.inf.paa.capmst.kruskal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.puc.rio.inf.paa.utils.DisjointSets;
import br.puc.rio.model.Edge;
import br.puc.rio.model.Graph;

public class KruskalMST {
	
	public static List<Edge> run(Graph graph, List<Edge> edgesIn, List<Edge> edgesOut){
		List<Edge> edges = KruskalMST.getEdges(graph);
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
				edgesMST.add(edge);
//				System.out.println(edge.origem+" -> "+edge.destino);
				set.union(set.find(edge.origem), set.find(edge.destino));
			}
		}
		
		return edgesMST;
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
	
}
