package br.puc.rio.inf.paa.capmst.branchNBound;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import br.puc.rio.inf.paa.capmst.kruskal.KruskalMST;
import br.puc.rio.inf.paa.utils.DisjointSets;
import br.puc.rio.model.Edge;
import br.puc.rio.model.Graph;

public class DFSCAPMST {
	
	private NodeCAPMST root;
	private Graph graph;
	private List<Edge> edges;
	private int capacity;
	private int bestSolution;
	private List<Edge> edgeSolution;
	
	public DFSCAPMST(Graph graph, int capacity){
		System.out.println("QUANTIDADE: "+graph.quantityNodes);
		this.setBestSolution(Integer.MAX_VALUE);
		this.capacity = capacity;
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
			
//			System.out.println("Edges IN: ");
//			for(Edge edge:node.getEdgesIn()){
//				System.out.println("Edge IN: "+edge.origem+" -> "+edge.destino);
//			}
//			System.out.println("Edges OUT: ");
//			for(Edge edge:node.getEdgesOut()){
//				System.out.println("Edge OUT: "+edge.origem+" -> "+edge.destino);
//			}
			
			List<Edge> solution = KruskalMST.run(graph, node.getEdgesIn(), node.getEdgesOut());
			int lowerBound = this.value(solution);
			
//			System.out.println("lowerBound: "+lowerBound);
//			System.out.println("Ciclo: "+this.hasCycle(node.getEdgesIn()));
//			System.out.println("Isolado: "+this.hasIsolatedVertex(node.getEdgesOut()));
			
//			System.out.println("\n-------------------------------------------------------------------\n");
			
			if(this.isFeasible(solution)){
				if(lowerBound < this.bestSolution){
					this.bestSolution = lowerBound;
					this.edgeSolution = solution;
					System.out.println("FEASIBLE SOLUTION: "+this.bestSolution);
				}
			}else if(lowerBound < this.bestSolution && !this.hasCycle(node.getEdgesIn()) && !this.hasIsolatedVertex(node.getEdgesOut())){
				if(node.getLevel() < this.edges.size()){
					stack.push(this.partitionRight(node));
					stack.push(this.partitionLeft(node));
				}				
			}
		}
		
		System.out.println("Melhor Solução: "+this.bestSolution);
		for(Edge edge:this.edgeSolution){
			System.out.println(edge.origem+" - "+edge.destino);
		}
		System.out.println(this.isFeasible(edgeSolution));
		NodeTree tree = this.createNodes(edgeSolution);
		printTree(tree);
		
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
		rightChild.addEdgeOut(this.edges.get(node.getLevel()));
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
	
	private boolean hasIsolatedVertexAllEdges(List<Edge> edgesOut){
		
		int[] counts = new int[this.graph.quantityNodes];
		
		for(Edge edge:edgesOut){
			counts[edge.destino]++;
			counts[edge.origem]++;
		}
		
		for(int i:counts){
			if(i == 0){
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isFeasible(List<Edge> edges){
		
		if(this.hasIsolatedVertexAllEdges(edges)) return false;
		
		NodeTree root = this.createNodes(edges);
		this.size(root);
		
		if(root.getSize() != this.graph.quantityNodes) return false;
		
		for(NodeTree node:root.getChilds()){
			if(node.getSize() > this.capacity){
				return false;
			}
		}
		
		return true;
	}
	
	private NodeTree createNodes(List<Edge> edges){
		
		List<NodeTree> nodes = new ArrayList<NodeTree>();
		
		for(int i = 0; i < this.graph.quantityNodes; i++){
			nodes.add(i, new NodeTree(i));
		}
		
		NodeTree root = nodes.get(this.graph.quantityNodes-1);
		
		List<NodeTree> actualNodes = new ArrayList<NodeTree>();
		actualNodes.add(root);
		boolean[] visited = new boolean[this.graph.quantityNodes];
		visited[this.graph.quantityNodes-1] = true;
		
		while(!actualNodes.isEmpty()){
			NodeTree actualNode = actualNodes.get(0);
			actualNodes.remove(0);
			for(Edge edge:edges){
				if(actualNode.getLabel() == edge.destino && !visited[edge.origem]){
					actualNode.addChild(nodes.get(edge.origem));
					nodes.get(edge.origem).setParent(actualNode);
					visited[edge.origem] = true;
					actualNodes.add(nodes.get(edge.origem));
				}else if(actualNode.getLabel() == edge.origem && !visited[edge.destino]){
					actualNode.addChild(nodes.get(edge.destino));
					nodes.get(edge.destino).setParent(actualNode);
					visited[edge.destino] = true;
					actualNodes.add(nodes.get(edge.destino));
				}
			}
		}
		
		return root;
	}
	
	private void printTree(NodeTree tree){
		System.out.println("Raiz: "+tree.getLabel());
		for(NodeTree tree2:tree.getChilds()){
			System.out.println("Filho: "+tree2.getLabel());
			this.printTree(tree2);
		}
	}
	
	private int size(NodeTree node){
		if(node.getChilds().size() == 0){
			node.setSize(1);
			return node.getSize();
		}else{
			int soma = 0;
			for(NodeTree child:node.getChilds()){
				soma+=this.size(child);
			}
			node.setSize(soma+1);
			return node.getSize();
		}
	}
	
	private int value(List<Edge> edges){
		int sum = 0;
		for(Edge edge:edges){
			sum+=edge.peso;
		}
		return sum;
	}

	public int getBestSolution() {
		return bestSolution;
	}

	public void setBestSolution(int bestSolution) {
		this.bestSolution = bestSolution;
	}

	public List<Edge> getEdgeSolution() {
		return edgeSolution;
	}

	public void setEdgeSolution(List<Edge> edgeSolution) {
		this.edgeSolution = edgeSolution;
	}
	
}
