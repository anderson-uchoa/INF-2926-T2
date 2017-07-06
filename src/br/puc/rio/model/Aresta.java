package br.puc.rio.model;

public class Aresta {

	public int origem;
	public int destino;
	public int peso;
	public boolean visitado;

	public Aresta(int origem, int destino, int peso) {
		this.origem = origem;
		this.destino = destino;
		this.peso = peso;
		this.visitado = false;
	}

}
