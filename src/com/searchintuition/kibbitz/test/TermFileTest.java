package com.searchintuition.kibbitz.test;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.searchintuition.kibbitz.TermFile;

public class TermFileTest {

	@Test
	public final void testBinarySearch() {
		String termFile = "/Users/Peter/etsy/data/index.txt";
		
		TermFile terms = new TermFile(termFile);
		for(String term : terms.binarySearch("real")) {
			System.out.println(term);
		}
		fail("Not yet implemented"); // TODO
	}

}
