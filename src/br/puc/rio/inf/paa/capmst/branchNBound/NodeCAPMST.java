package br.puc.rio.inf.paa.capmst.branchNBound;

import java.util.ArrayList;
import java.util.List;

import br.puc.rio.model.Edge;

public class NodeCAPMST {
	
	private List<Edge> edgesIn;
	private List<Edge> edgesOut;
	private int lowerBound;
	private int level;
	
	public NodeCAPMST(int level) {
		this.edgesIn = new ArrayList<Edge>();
		this.edgesOut = new ArrayList<Edge>();
		this.level = level;
	}
	
	public void addEdgeIn(Edge edge){
		this.edgesIn.add(edge);
	}
	
	public void addEdgeOut(Edge edge){
		this.edgesOut.add(edge);
	}

	public List<Edge> getEdgesIn() {
		return edgesIn;
	}

	public void setEdgesIn(List<Edge> edgesIn) {
		this.edgesIn = edgesIn;
	}

	public List<Edge> getEdgesOut() {
		return edgesOut;
	}

	public void setEdgesOut(List<Edge> edgesOut) {
		this.edgesOut = edgesOut;
	}

	public int getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(int lowerBound) {
		this.lowerBound = lowerBound;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
}
