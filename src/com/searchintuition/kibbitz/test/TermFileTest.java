package com.searchintuition.kibbitz.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.searchintuition.kibbitz.TermFile;
import com.searchintuition.kibbitz.TermScorer;

public class TermFileTest {

	@Test
	public final void testBinarySearch() {
		String query = "real";
		String termFile = "/Users/Peter/etsy/data/index.txt";
		
		TermFile terms = new TermFile(termFile);
		for(String term : terms.binarySearch(query)) {
			assertTrue(term.startsWith(query));
		}
	}
	
	
	@Test
	public final void testScoring() {
		String query = "real";
		String termFile = "/Users/Peter/etsy/data/index.txt";
		
		TermFile terms = new TermFile(termFile);		
		Map<String, Integer> completions = terms.prefixSearch(query);
		
		TermScorer scorer = new TermScorer(query, completions);

		for (Map.Entry<String, Integer> cursor : scorer.getTerms(5)) {		
			assertTrue(cursor.getKey().startsWith("real estate"));
		}
	}

}
