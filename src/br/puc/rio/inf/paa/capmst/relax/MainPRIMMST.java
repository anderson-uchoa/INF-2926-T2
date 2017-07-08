package br.puc.rio.inf.paa.capmst.relax;



import java.util.ArrayList;
import java.util.List;

import br.puc.rio.model.Aresta;
import br.puc.rio.model.Graph;

public class MainPRIMMST {

	
	public static void main(String[] args) {

		/**
		 * Exemplo de grafo - matriz de adjacência onde um número diferente de 0
		 * significa o peso da ligação entre os nós da matriz
		 * 
		 * Você poderá substituir por qualquer matriz quadrada não negativa
		 */
		Integer[][] matrizSistema = { { 0, 5, 30, 0, 0, 0 }, { 5, 0, 20, 10, 0, 0 },
				{ 30, 20, 0, 10, 15, 0 }, { 0, 10, 10, 0, 5, 20 },
				{ 0, 0, 15, 5, 0, 15 }, { 0, 0, 0, 20, 15, 0 } };

		
		
		
		Graph graph = new Graph(6);
		graph.matrixAdj = matrizSistema;
		
		/**
		 * Chamada do Algoritmo de Prim
		 */
		
		
		Aresta aresta = new Aresta(0, 1);

		Aresta aresta2 = new Aresta(1, 2);
		Aresta aresta3 = new Aresta(2, 4);
		
		
		
		List<Aresta> arestasRemovidas = new ArrayList<Aresta>();
		arestasRemovidas.add(aresta);
		//arestasRemovidas.add(aresta2);

		List<Aresta> arestasAdicionadas = new ArrayList<Aresta>();
		
		arestasAdicionadas.add(aresta3);
		//arestasAdicionadas.add(aresta2);
		
		
		Integer[][] matrizResultado = PrimAlgorithmMST.prim(graph, arestasRemovidas, arestasAdicionadas);

		
		int soma = PrimAlgorithmMST.somaMST(matrizResultado);
		
		System.out.println("Soma MST= "+ soma);
		
		System.out.println();
		
		
		/**
		 * Impressão da matrizResultado com a resposta do Algoritmo de Prim
		 */
		for (int contadorHorizontal = 0; contadorHorizontal < matrizResultado[0].length; contadorHorizontal++) {
			for (int contadorVertical = 0; contadorVertical < matrizResultado[0].length; contadorVertical++) {

				System.out.print(matrizResultado[contadorHorizontal][contadorVertical] + " ");
			}

			System.out.println();
		}

	}



	
}
