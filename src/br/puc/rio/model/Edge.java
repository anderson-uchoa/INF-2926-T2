package br.puc.rio.model;

public class Edge implements Comparable<Edge>{

	public int origem;
	public int destino;
	public int peso;
	public boolean visitado;

	public Edge(int origem, int destino, int peso) {
		this.origem = origem;
		this.destino = destino;
		this.peso = peso;
		this.visitado = false;
	}

	@Override
	public int compareTo(Edge o) {
		if(this.peso > o.peso) return 1;
		else if(this.peso < o.peso) return -1;
		return 0;
	}

}
