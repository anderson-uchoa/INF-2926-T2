package br.puc.rio.inf.paa.capmst.branchNBound;

import java.util.List;

import br.puc.rio.inf.paa.utils.DisjointSets;
import br.puc.rio.inf.paa.utils.GraphReader;
import br.puc.rio.model.Graph;

public class Main {

	public static void main(String[] args) {
		List<Graph> graphs = new GraphReader().creatAllInstances();
		Integer[][] matriz = new Integer[4][4];
		matriz[0][0] = Integer.MAX_VALUE;
		matriz[0][1] = 10;
		matriz[0][2] = 5;
		matriz[0][3] = 8;
		matriz[1][0] = 10;
		matriz[1][1] = Integer.MAX_VALUE;
		matriz[1][2] = 5;
		matriz[1][3] = 2;
		matriz[2][0] = 5;
		matriz[2][1] = 5;
		matriz[2][2] = Integer.MAX_VALUE;
		matriz[2][3] = 7;
		matriz[3][0] = 8;
		matriz[3][1] = 2;
		matriz[3][2] = 7;
		matriz[3][3] = Integer.MAX_VALUE;
		graphs.get(0).setMatrixAdj(matriz);
		graphs.get(0).quantityNodes = 4;
//		graphs.get(0).print();
		DFSCAPMST dfscapmst = new DFSCAPMST(graphs.get(0));
		
//		DisjointSets sets = new DisjointSets(4);
//		sets.union(3, 1);
//		sets.union(2, 0);
//		sets.union(2, 1);
//		System.out.println(sets.find(3) != sets.find(2));
	}
	
}
