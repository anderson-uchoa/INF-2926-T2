package br.puc.rio.inf.paa.capmst.relax;

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

		/**
		 * Chamada do Algoritmo de Prim
		 */
		Integer[][] matrizResultado = PrimAlgorithmMST.prim(matrizSistema);

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