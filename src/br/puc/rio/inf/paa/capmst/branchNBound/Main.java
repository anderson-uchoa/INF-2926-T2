package br.puc.rio.inf.paa.capmst.branchNBound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import br.puc.rio.inf.paa.capmst.kruskal.KruskalMST;
import br.puc.rio.inf.paa.capmst.kruskal.ModifiedKruskalMST;
import br.puc.rio.inf.paa.utils.DisjointSets;
import br.puc.rio.inf.paa.utils.GraphReader;
import br.puc.rio.model.Edge;
import br.puc.rio.model.Graph;

public class Main {

	public static void main(String[] args) {
		List<Graph> graphs = new GraphReader().creatAllInstances();

		//		Graph graph = new Graph(5);
		//		graph.matrixAdj[0][0] = Integer.MAX_VALUE;
		//		graph.matrixAdj[0][1] = 10;
		//		graph.matrixAdj[0][2] = 2;
		//		graph.matrixAdj[0][3] = 5;
		//		graph.matrixAdj[0][4] = 7;
		//		graph.matrixAdj[1][0] = 10;
		//		graph.matrixAdj[1][1] = Integer.MAX_VALUE;
		//		graph.matrixAdj[1][2] = 5;
		//		graph.matrixAdj[1][3] = 8;
		//		graph.matrixAdj[1][4] = 7;
		//		graph.matrixAdj[2][0] = 2;
		//		graph.matrixAdj[2][1] = 5;
		//		graph.matrixAdj[2][2] = Integer.MAX_VALUE;
		//		graph.matrixAdj[2][3] = 2;
		//		graph.matrixAdj[2][4] = 4;
		//		graph.matrixAdj[3][0] = 5;
		//		graph.matrixAdj[3][1] = 8;
		//		graph.matrixAdj[3][2] = 2;
		//		graph.matrixAdj[3][3] = Integer.MAX_VALUE;
		//		graph.matrixAdj[3][4] = 1;
		//		graph.matrixAdj[4][0] = 7;
		//		graph.matrixAdj[4][1] = 7;
		//		graph.matrixAdj[4][2] = 4;
		//		graph.matrixAdj[4][3] = 1;
		//		graph.matrixAdj[4][4] = Integer.MAX_VALUE;
		//		
		//		DFSCAPMST dfscapmst = new DFSCAPMST(graph, 2);
		//		
		//		DisjointSets set = new DisjointSets(5);
		//		set.union(4, 3);
		//		set.union(2, 0);
		//		System.out.println(set.find(2) != set.find(3));
		//		
		for(Graph graph:graphs){
			//				graph.print();
			DFSCAPMST dfscapmst = new DFSCAPMST(graph, 10);

		}
		//		
		//		List<Edge> edgesIn = new ArrayList<Edge>();
		//		List<Edge> edgesOut = new ArrayList<Edge>();
		//		edgesIn.add(new Edge(4, 0, 7));
		//		
		//		System.out.println(ModifiedKruskalMST.value(KruskalMST.run(graph, edgesIn, edgesOut)));
		//		for(Edge edge:KruskalMST.run(graph, edgesIn, edgesOut)){
		//			System.out.println(edge.origem+" -> "+edge.destino+", "+edge.peso);
		//		}
		//		System.out.println("-----------------------------------------------");
		//		System.out.println(ModifiedKruskalMST.value(ModifiedKruskalMST.run(graph, edgesIn, edgesOut, 1)));
		//		for(Edge edge:ModifiedKruskalMST.run(graph, edgesIn, edgesOut, 1)){
		//			System.out.println(edge.origem+" -> "+edge.destino+", "+edge.peso);
		//		}

		//		ModifiedKruskalMST.getAllCombinations(5, 0);
	}

}
