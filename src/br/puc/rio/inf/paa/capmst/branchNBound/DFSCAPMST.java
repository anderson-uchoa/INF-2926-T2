package br.puc.rio.inf.paa.capmst.branchNBound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import br.puc.rio.inf.paa.capmst.kruskal.KruskalMST;
import br.puc.rio.inf.paa.utils.DisjointSets;
import br.puc.rio.model.Edge;
import br.puc.rio.model.Graph;

public class DFSCAPMST {
	
	private NodeCAPMST root;
	private Graph graph;
	private List<Edge> edges;
	
	public DFSCAPMST(Graph graph) {
		this.graph = graph;
		this.edges = new ArrayList<Edge>();
		this.getEdgesFromGraph();
		Collections.sort(this.edges);
		this.search();
	}
	
	public void search(){
		
		this.root = new NodeCAPMST(0);
		
		Stack<NodeCAPMST> stack = new Stack<NodeCAPMST>();
		stack.push(root);
		
		while(!stack.isEmpty()){
			NodeCAPMST node = stack.pop();
			
//			Calcular lower bound do nó
			
//			Testar se a solução encontrada é viável
			
//				Testar se essa solução é a melhor solução encontrada, se sim, atualizar a melhor solução
			
//				Caso na seja a melhor solução, não gerar
//			if(node.getLevel() < 40){
//				stack.push(this.partitionLeft(node));
//				stack.push(this.partitionRight(node));
//			}
			
		}
		
		List<Edge> edgesIn = new ArrayList<Edge>();
		List<Edge> edgesOut = new ArrayList<Edge>();
		
		edgesIn.add(new Edge(1,0,10));
		edgesIn.add(new Edge(3,0,8));
		edgesIn.add(new Edge(3,1,8));
		edgesOut.add(new Edge(2,0,5));
		edgesOut.add(new Edge(2,1,5));
		edgesOut.add(new Edge(3,2,5));
		
		System.out.println(this.hasCycle(edgesIn));
		System.out.println(this.hasIsolatedVertex(edgesOut));
		
	}
	
	private void getEdgesFromGraph(){
		for(int i = 0; i < this.graph.quantityNodes; i++){
			for(int j = 0; j < this.graph.quantityNodes; j++){
				if(i > j){
					this.edges.add(new Edge(i, j, this.graph.matrixAdj[i][j]));
				}
			}
			
		}
	}
	
	private NodeCAPMST partitionLeft(NodeCAPMST node){
		NodeCAPMST leftChild = new NodeCAPMST(node.getLevel()+1);
		leftChild.setEdgesIn(node.getEdgesIn());
		leftChild.setEdgesOut(node.getEdgesOut());
		leftChild.addEdgeIn(this.edges.get(node.getLevel()));
		return leftChild;
	}
	
	private NodeCAPMST partitionRight(NodeCAPMST node){
		NodeCAPMST rightChild = new NodeCAPMST(node.getLevel()+1);
		rightChild.setEdgesIn(node.getEdgesIn());
		rightChild.setEdgesOut(node.getEdgesOut());
		rightChild.addEdgeIn(this.edges.get(node.getLevel()));
		return rightChild;
	}
	
	private boolean hasCycle(List<Edge> edgesIn){
		DisjointSets set = new DisjointSets(this.graph.quantityNodes);
		
		for(Edge edge:edgesIn){
			if(set.find(edge.origem) != set.find(edge.destino)){
				set.union(set.find(edge.origem), set.find(edge.destino));
			}else{
				return true;
			}
		}
		
		return false;
	}
	
	private boolean hasIsolatedVertex(List<Edge> edgesOut){
		
		int[] counts = new int[this.graph.quantityNodes];
		
		for(Edge edge:edgesOut){
			counts[edge.destino]++;
			counts[edge.origem]++;
		}
		
		for(int i:counts){
			if(i == this.graph.quantityNodes-1){
				return true;
			}
		}
		
		return false;
	}
	
}
