
package br.puc.rio.model;

public class Aresta {

	public int origem;
	public int destino;
	

	public Aresta(int origem, int destino) {
		this.origem = origem;
		this.destino = destino;
		}


	@Override
	public String toString() {
		return "Aresta [origem=" + origem + ", destino=" + destino + "]";
	}

	
}
