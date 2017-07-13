package br.puc.rio.inf.paa.capmst.branchNBound;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import br.puc.rio.inf.paa.capmst.kruskal.KruskalMST;
import br.puc.rio.inf.paa.capmst.kruskal.ModifiedKruskalMST;
import br.puc.rio.inf.paa.capmst.kruskal.SmartModifiedKruskalMST;
import br.puc.rio.inf.paa.capmst.relax.GreedyCMST;
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
	public int initialLowerBound;

	public double timeEnd = 0.0;
	public double timeResult = 0.0;
	public boolean isOptimal = true;

	public DFSCAPMST(Graph graph, int capacity) {
		System.out.println("QUANTIDADE: " + graph.quantityNodes);
		this.setBestSolution(Integer.MAX_VALUE);
		this.capacity = capacity;
		this.graph = graph;
		this.edges = new ArrayList<Edge>();
		this.getEdgesFromGraph();
		Collections.sort(this.edges);

	}

	public void search() {
		Stack<NodeCAPMST> stack = new Stack<NodeCAPMST>();

		GreedyCMST greedyCMST = new GreedyCMST(graph);
		Integer[][] matrixResult = greedyCMST.buildCMST(this.graph.quantityNodes - 1, this.capacity);
		this.bestSolution = greedyCMST.sumEdgesCMST;

		this.edgeSolution = getGreedyEdges(matrixResult);
		System.out.println(bestSolution);

		this.root = new NodeCAPMST(0);

		this.root.setLowerBound(
				this.value(KruskalMST.run(graph, root.getEdgesIn(), root.getEdgesOut())));
		System.out.println("Lower bound inicial: " + this.root.getLowerBound());
		initialLowerBound = this.root.getLowerBound();
		stack.push(root);

		while (!stack.isEmpty()) {
			NodeCAPMST node = stack.pop();

			if (timeResult <= Main.timeOut) {
				if (node.getLevel() < this.edges.size()) {
					List<NodeCAPMST> childs = this.partition(node);

					for (NodeCAPMST child : childs) {
						List<Edge> solution = KruskalMST.run(graph, node.getEdgesIn(), node.getEdgesOut());
						child.setLowerBound(this.value(solution));
						// System.out.println("Lower Bound:
						// "+child.getLowerBound());
						if (this.isFeasible(solution) && child.getLowerBound() < this.bestSolution) {
							this.edgeSolution = solution;
							this.bestSolution = child.getLowerBound();
							System.out.println("BEST PARTIAL SOLUTION: " + this.bestSolution);
							System.out.println("LOWER BOUND: " + this.minLowerBound(stack));
							continue;
						}
						if (child.getLowerBound() < this.bestSolution && !this.hasCycle(node.getEdgesIn())
								&& !this.hasIsolatedVertex(node.getEdgesOut()) && this.checkFeasibleCapacity(
										child.getEdgesIn(), this.graph.quantityNodes, this.capacity)) {
							stack.push(child);
						}
					}
				}
			} else {
				isOptimal = false;
			}
			timeEnd = System.currentTimeMillis();
		}

		System.out.println("Melhor Solução: " + this.bestSolution);

	}

	private List<Edge> getGreedyEdges(Integer[][] matrixResult) {
		// TODO Auto-generated method stub
		List<Edge> edges = new ArrayList<>();

		for (int i = 0; i < matrixResult[0].length; i++) {
			for (int j = 0; j < matrixResult[0].length; j++) {

				if (matrixResult[i][j] != 0 && matrixResult[i][j] != Integer.MAX_VALUE) {
					Edge edge = new Edge(i, j, matrixResult[i][j]);
					edges.add(edge);
				}
			}
		}
		return edges;
	}

	public int minLowerBound(Stack stack) {

		Iterator<NodeCAPMST> iterator = stack.iterator();

		int smaller = Integer.MAX_VALUE;

		while (iterator.hasNext()) {
			int value = iterator.next().getLowerBound();
			// System.out.println("Pilha: "+value);
			if (value < smaller) {
				smaller = value;
			}
		}

		return smaller;
	}

	private List<NodeCAPMST> partition(NodeCAPMST node) {
		List<NodeCAPMST> nodes = new ArrayList<NodeCAPMST>();

		nodes.add(this.partitionLeft(node));
		nodes.add(this.partitionRight(node));

		return nodes;
	}

	private void getEdgesFromGraph() {
		for (int i = 0; i < this.graph.quantityNodes; i++) {
			for (int j = 0; j < this.graph.quantityNodes; j++) {
				if (i > j) {
					this.edges.add(new Edge(i, j, this.graph.matrixAdj[i][j]));
				}
			}
		}
	}

	private NodeCAPMST partitionLeft(NodeCAPMST node) {
		NodeCAPMST leftChild = new NodeCAPMST(node.getLevel() + 1);
		leftChild.setEdgesIn(node.getEdgesIn());
		leftChild.setEdgesOut(node.getEdgesOut());
		leftChild.addEdgeIn(this.edges.get(node.getLevel()));
		return leftChild;
	}

	private NodeCAPMST partitionRight(NodeCAPMST node) {
		NodeCAPMST rightChild = new NodeCAPMST(node.getLevel() + 1);
		rightChild.setEdgesIn(node.getEdgesIn());
		rightChild.setEdgesOut(node.getEdgesOut());
		rightChild.addEdgeOut(this.edges.get(node.getLevel()));
		return rightChild;
	}

	private boolean hasCycle(List<Edge> edgesIn) {
		DisjointSets set = new DisjointSets(this.graph.quantityNodes);

		for (Edge edge : edgesIn) {
			if (set.find(edge.origem) != set.find(edge.destino)) {
				set.union(set.find(edge.origem), set.find(edge.destino));
			} else {
				return true;
			}
		}

		return false;
	}

	private boolean hasIsolatedVertex(List<Edge> edgesOut) {

		int[] counts = new int[this.graph.quantityNodes];

		for (Edge edge : edgesOut) {
			counts[edge.destino]++;
			counts[edge.origem]++;
		}

		for (int i : counts) {
			if (i == this.graph.quantityNodes - 1) {
				return true;
			}
		}

		return false;
	}

	private boolean hasIsolatedVertexAllEdges(List<Edge> edgesOut) {

		int[] counts = new int[this.graph.quantityNodes];

		for (Edge edge : edgesOut) {
			counts[edge.destino]++;
			counts[edge.origem]++;
		}

		for (int i : counts) {
			if (i == 0) {
				return true;
			}
		}

		return false;
	}

	private boolean isFeasible(List<Edge> edges) {

		if (this.hasIsolatedVertexAllEdges(edges))
			return false;

		NodeTree root = this.createNodes(edges);
		this.size(root);

		if (root.getSize() != this.graph.quantityNodes)
			return false;

		for (NodeTree node : root.getChilds()) {
			if (node.getSize() > this.capacity) {
				return false;
			}
		}

		return true;
	}

	private NodeTree createNodes(List<Edge> edges) {

		List<NodeTree> nodes = new ArrayList<NodeTree>();

		for (int i = 0; i < this.graph.quantityNodes; i++) {
			nodes.add(i, new NodeTree(i));
		}

		NodeTree root = nodes.get(this.graph.quantityNodes - 1);

		List<NodeTree> actualNodes = new ArrayList<NodeTree>();
		actualNodes.add(root);
		boolean[] visited = new boolean[this.graph.quantityNodes];
		visited[this.graph.quantityNodes - 1] = true;

		while (!actualNodes.isEmpty()) {
			NodeTree actualNode = actualNodes.get(0);
			actualNodes.remove(0);
			for (Edge edge : edges) {
				if (actualNode.getLabel() == edge.destino && !visited[edge.origem]) {
					actualNode.addChild(nodes.get(edge.origem));
					nodes.get(edge.origem).setParent(actualNode);
					visited[edge.origem] = true;
					actualNodes.add(nodes.get(edge.origem));
				} else if (actualNode.getLabel() == edge.origem && !visited[edge.destino]) {
					actualNode.addChild(nodes.get(edge.destino));
					nodes.get(edge.destino).setParent(actualNode);
					visited[edge.destino] = true;
					actualNodes.add(nodes.get(edge.destino));
				}
			}
		}

		return root;
	}

	private void printTree(NodeTree tree) {
		System.out.println("Raiz: " + tree.getLabel());
		for (NodeTree tree2 : tree.getChilds()) {
			System.out.println("Filho: " + tree2.getLabel());
			this.printTree(tree2);
		}
	}

	private int size(NodeTree node) {
		if (node.getChilds().size() == 0) {
			node.setSize(1);
			return node.getSize();
		} else {
			int soma = 0;
			for (NodeTree child : node.getChilds()) {
				soma += this.size(child);
			}
			node.setSize(soma + 1);
			return node.getSize();
		}
	}

	private int value(List<Edge> edges) {
		int sum = 0;
		for (Edge edge : edges) {
			sum += edge.peso;
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

	public int getInitialLowerBound() {
		return initialLowerBound;
	}

	public void setInitialLowerBound(int initialLowerBound) {
		this.initialLowerBound = initialLowerBound;
	}

	public boolean checkFeasibleCapacity(List<Edge> edges, int qtyVertices, int capacity) {
		List<Set<Integer>> setsList = new ArrayList<Set<Integer>>();

		for (Edge edge : edges) {
			this.addVerticeToSet(setsList, edge, qtyVertices);
		}

		for (Set<Integer> set : setsList) {
			if (this.containsSubRoot(edges, set, qtyVertices)) {
				if (set.size() > capacity) {
					return false;
				}
			} else {
				if (set.size() + 1 > capacity) {
					return false;
				}
			}
		}

		return true;
	}

	public void addVerticeToSet(List<Set<Integer>> setsList, Edge edge, int qtyVertices) {

		int index = -1;

		if (this.contains(setsList, edge.origem) && this.contains(setsList, edge.destino)) {
			int index1 = this.getIndexSet(setsList, edge.origem);
			int index2 = this.getIndexSet(setsList, edge.destino);
			Set<Integer> setAux = setsList.get(index2);
			setsList.get(index1).addAll(setAux);
			setsList.remove(index2);
		} else if (this.contains(setsList, edge.origem)) {
			index = this.getIndexSet(setsList, edge.origem);
			if (edge.origem != qtyVertices - 1) {
				setsList.get(index).add(new Integer(edge.origem));
			}
			if (edge.destino != qtyVertices - 1) {
				setsList.get(index).add(new Integer(edge.destino));
			}
		} else if (this.contains(setsList, edge.destino)) {
			index = this.getIndexSet(setsList, edge.destino);
			if (edge.origem != qtyVertices - 1) {
				setsList.get(index).add(new Integer(edge.origem));
			}
			if (edge.destino != qtyVertices - 1) {
				setsList.get(index).add(new Integer(edge.destino));
			}
		} else if (!this.contains(setsList, edge.origem) && !this.contains(setsList, edge.destino)) {
			Set<Integer> set = new HashSet<Integer>();
			if (edge.origem != qtyVertices - 1) {
				set.add(new Integer(edge.origem));
			}
			if (edge.destino != qtyVertices - 1) {
				set.add(new Integer(edge.destino));
			}

			setsList.add(setsList.size(), set);
		}

	}

	public boolean contains(List<Set<Integer>> setsList, int vertex) {
		boolean contains = false;
		for (Set<Integer> set : setsList) {
			if (set.contains(new Integer(vertex))) {
				contains = true;
				break;
			}
		}
		return contains;
	}

	private int getIndexSet(List<Set<Integer>> setsList, int vertex) {
		for (int i = 0; i < setsList.size(); i++) {
			for (Integer integer : setsList.get(i)) {
				if (integer.equals(vertex)) {
					return i;
				}
			}

		}
		return -1;
	}

	public void printSets(List<Set<Integer>> setsList, List<Edge> edges, int qtyVertices) {
		String out = "";
		for (Set<Integer> set : setsList) {
			out = "";
			for (Integer integer : set) {
				out += integer + ", ";
			}
			out += this.containsSubRoot(edges, set, qtyVertices);
			System.out.println(out);
		}

	}

	public boolean containsSubRoot(List<Edge> edges, Set<Integer> set, int qtyVertices) {

		for (Integer integer : set) {
			for (Edge edge : edges) {
				if ((edge.origem == qtyVertices - 1 && integer.equals(edge.destino))
						|| (edge.destino == qtyVertices - 1 && integer.equals(edge.origem))) {
					return true;
				}
			}
		}

		return false;
	}

	public String getEdges() {
		// TODO Auto-generated method stub
		String edgesText = "";

		for (Edge edge : edgeSolution) {
			edgesText = edgesText + "(" + edge.origem + "," + edge.destino + ") ";
		}

		if (edgesText.isEmpty()) {
			edgesText = "Erro ao encontrar arestas";
		}

		return edgesText;
	}

}
