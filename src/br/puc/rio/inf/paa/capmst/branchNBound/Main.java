package br.puc.rio.inf.paa.capmst.branchNBound;

import java.util.List;

import br.puc.rio.inf.paa.utils.GraphReader;
import br.puc.rio.model.Graph;

public class Main {

	public static void main(String[] args) {
		List<Graph> graphs = new GraphReader().creatAllInstances();
		DFSCAPMST dfscapmst = new DFSCAPMST(graphs.get(0));
	}
	
}
