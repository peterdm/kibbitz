package com.searchintuition.kibbitz;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MultiGramFileGenerator {

	public static void generateMultiGramFile(String inputQueryFilename, String outputGramFilename) throws IOException {
		// Create a new QueryLogReader, use readLine(true)  TODO: rewrite this in AWK
		BufferedReader reader;
		BufferedWriter writer;
		
		reader = new BufferedReader(new FileReader(inputQueryFilename));
		writer = new BufferedWriter(new FileWriter(outputGramFilename));
		
		String query;
		QueryTokenizer tok = new QueryTokenizer();

		while ((query = reader.readLine()) != null) {		
			for(String term : tok.setQuery(query)) {
				writer.append(term);  
				writer.newLine();
			}
		}
	}
	
	public static void main(String[] args) {
		if (args.length != 2) {
			usage();
			return;
		}
		
		String inputFile = args[0];
		String outputFile = args[1];
		if (!new File(inputFile).exists()) {
			usage();
			return;
		}
		
		try {
			generateMultiGramFile(inputFile, outputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void usage() {
		System.out.println("Usage: java MultiGramFileGenerator <input-file-with-queries> <output-file-for-word-grams>");
	}
}
