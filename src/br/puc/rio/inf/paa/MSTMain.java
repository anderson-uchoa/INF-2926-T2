package br.puc.rio.inf.paa;

import java.util.List;


import br.puc.rio.inf.paa.utils.GraphReader;
import br.puc.rio.model.Graph;

public class MSTMain {

	public static void main(String args[]) {

		GraphReader reader = new GraphReader();

		List<Graph> graphs = reader.creatAllInstances();

		Integer[][] matrizResultado = null;

		
//		for (int i = 0; i < graphs.size(); i++) {
//
//			matrizResultado = PrimAlgorithmMST.prim(graphs.get(i));
//
//		}

	}

}
