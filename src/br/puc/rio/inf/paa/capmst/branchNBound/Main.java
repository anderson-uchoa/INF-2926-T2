package br.puc.rio.inf.paa.capmst.branchNBound;

import java.util.List;
import java.util.concurrent.TimeUnit;

import br.puc.rio.inf.paa.utils.CsvWriter;

import br.puc.rio.inf.paa.utils.GraphReader;
import br.puc.rio.model.Graph;
import java.io.IOException;
import java.nio.charset.Charset;

public class Main {

	// public static double timeOut = 5000;

	public static double timeOut = TimeUnit.MINUTES.toMillis(20);

	public static void main(String[] args) {

		String nameCSV = "CAPMST10.csv";

		CsvWriter writer = new CsvWriter(nameCSV, ',', Charset.forName("ISO-8859-1"));

		List<Graph> graphs = new GraphReader().creatAllInstances();

		try {
			writer.write("Instance");
			writer.write("BestSolution");
			writer.write("Optimal");
			writer.write("CPU");
			writer.write("Lower_limit_root");
			writer.write("Lower_limit_execution");
			writer.write("Edges");
			writer.endRecord();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (Graph graph : graphs) {

			if (graph.quantityNodes > 17) {

				DFSCAPMST dfscapmst = new DFSCAPMST(graph, 10);

				dfscapmst.search();

				String edgesSet = dfscapmst.getEdges();

				try {

					double hours = TimeUnit.MILLISECONDS.toMinutes((long) dfscapmst.timeResult);
					writer.write(graph.name);
					writer.write(String.valueOf(dfscapmst.getBestSolution()));
					writer.write(String.valueOf(dfscapmst.isOptimal));
					writer.write(String.valueOf(hours));
					writer.write(String.valueOf(dfscapmst.getInitialLowerBound()));
					writer.write(String.valueOf(dfscapmst.getInitialLowerBound()));

					writer.write(edgesSet);

					writer.endRecord();

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		writer.close();

	}
}
