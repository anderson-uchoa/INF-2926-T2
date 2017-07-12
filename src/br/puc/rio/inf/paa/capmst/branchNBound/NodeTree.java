package br.puc.rio.inf.paa.capmst.branchNBound;

import java.util.ArrayList;
import java.util.List;

public class NodeTree {
	
	private int label;
	private NodeTree parent;
	private List<NodeTree> childs;
	private int size;
	
	public NodeTree(int label, NodeTree parent){
		this.label = label;
		this.parent = parent;
		this.childs = new ArrayList<NodeTree>();
	}
	
	public NodeTree(int label){
		this.label = label;
		this.childs = new ArrayList<NodeTree>();
	}
	
	public void addChild(NodeTree child){
		this.childs.add(child);
	}

	public int getLabel() {
		return label;
	}

	public void setLabel(int label) {
		this.label = label;
	}

	public NodeTree getParent() {
		return parent;
	}

	public void setParent(NodeTree parent) {
		this.parent = parent;
	}

	public List<NodeTree> getChilds() {
		return childs;
	}

	public void setChilds(List<NodeTree> childs) {
		this.childs = childs;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
}
