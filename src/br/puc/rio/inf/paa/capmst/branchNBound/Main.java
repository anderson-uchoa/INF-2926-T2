package br.puc.rio.inf.paa.capmst.branchNBound;

import java.util.ArrayList;
import java.util.List;

import br.puc.rio.inf.paa.utils.CsvWriter;
import br.puc.rio.inf.paa.utils.GraphReader;
import br.puc.rio.model.Graph;
import java.io.IOException;
import java.nio.charset.Charset;

public class Main {

	public static void main(String[] args) {

		String nameCSV = "nameCMst.csv";

		CsvWriter writer = new CsvWriter(nameCSV, ',', Charset.forName("ISO-8859-1"));

		List<Graph> graphs = new GraphReader().creatAllInstances();

		int count = 0;
		int numInstance = 0;

		int timeout = 3000;
		double temp_final = 0.0;
		double durationEnd = 0.0;
		double cpuTime = 0.0;

		try {
			
			writer.write("Instance");
			writer.write("BestSolution");
			writer.write("Optimal");
			writer.write("CPU");
			writer.write("Lower_limit_root");
			writer.write("Lower_limit_execution");

			writer.endRecord();

		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Graph graph : graphs) {

			// graph.print();
			DFSCAPMST dfscapmst = new DFSCAPMST(graph, 10);

			double temp_inicio = System.currentTimeMillis();

			while (durationEnd <= timeout) {
				 dfscapmst.search();
				temp_final = System.currentTimeMillis();
				durationEnd = temp_final - temp_inicio;
				count++;
				System.out.println("AQUI TEM CAPIROTO");
			}
			
			System.out.println("SAIU CAPIROTO");
			numInstance++;
			try {
				cpuTime = (durationEnd / count);

				cpuTime = cpuTime / 100;

				writer.write(graph.name);

				writer.write(String.valueOf(dfscapmst.getBestSolution()));

				if (durationEnd > timeout) {
					writer.write("Not optimal");
				} else {
					writer.write("Optimal");
				}

				writer.write(String.valueOf(cpuTime));

				writer.write("Limite_inferior_no_raiz");

				writer.write("Limite_superior_execucao");

				writer.endRecord();

			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("No Instance: " + numInstance);
			System.out.println(graph.name);
			System.out.println("BS: " + dfscapmst.getBestSolution() + "CPU: " + cpuTime);
			count = 0;
			durationEnd = 0;

			break;

		}

	}

}
