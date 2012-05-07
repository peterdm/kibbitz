/**
 * 
 */
package com.searchintuition.kibbitz.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.searchintuition.kibbitz.TermIndex;
import com.searchintuition.kibbitz.TermScorer;

/**
 * @author Peter
 *
 */
public class TrieTest {

	final static String testFile = "/Users/Peter/etsy/data/queries.txt";
	TermIndex ti;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	
		// Load up the index from the text files and serialize it to disk
		TermIndex termi = new TermIndex();
		try {
			termi.loadFromQueryLog(testFile);
			termi.save();
			//ti.showTerms();

		} catch (IOException ioe) {
			throw ioe;
		} finally {
			termi = null;
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Create a new TermIndex
		
		ti = new TermIndex();
		
		try {
			ti.load();
			//ti.showTerms();

		} catch (IOException ioe) {
			throw ioe;
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		ti = null;
	}

	@Test
	public final void testContainsTerm() {
		assertTrue(ti.containsTerm("jesse"));
		assertTrue(ti.containsTerm("jesse mccartney"));
		assertFalse(ti.containsTerm("jesse mc"));
	}

	@Test
	public final void testGetTermCount() {
		assertEquals(8, ti.getTermCount("jesse mccartney"));
		assertEquals(0, ti.getTermCount("some nonexistent term"));
	}
	
	@Test
	public final void testPrefixMap() {
		Map<String, Integer> completions = ti.prefixMap("re");
		
		assertTrue(completions.containsKey("real"));
		assertTrue(completions.containsKey("real estate"));
		assertTrue(completions.containsKey("real estate settlement"));
		assertTrue(completions.containsKey("real estate settlement services"));
		assertTrue(completions.containsKey("remax"));
		assertTrue(completions.containsKey("reset"));
		assertTrue(completions.containsKey("resources"));
		assertTrue(completions.containsKey("retirement"));
		assertFalse(completions.containsKey("rocky"));
	}


	@Test
	public final void testScoring() {
		String query = "re";
		Map<String, Integer> completions = ti.prefixMap(query);
		
		TermScorer scorer = new TermScorer(query, completions);
		
		/*
		System.out.println("Alpha sort");
		for (Map.Entry<String, Integer> cursor : completions.entrySet()) {		
			System.out.println(cursor.getKey() + "(" + cursor.getValue() + ")");
		}
		
		System.out.println("=========");
		System.out.println("Scored sort");
		for (Map.Entry<String, Integer> cursor : scorer.getTerms()) {		
			System.out.println(cursor.getKey() + "(" + cursor.getValue() + ")");
		}
		*/
		
		assertEquals(scorer.getTerms().get(0).getKey(), "real estate");
		
	}
	
	/*
	@Test
	public final void testSerialization() {
		
		try {
			ti.save();
			
			TermIndex t2 = new TermIndex();
			t2.load();
			assertEquals(ti.size(),t2.size());
			assertTrue(t2.containsTerm("jesse mccartney"));
			assertFalse(t2.containsTerm("jesse mc"));
			
			Map<String, Integer> completions = t2.prefixMap("re");
			assertTrue(completions.containsKey("retirement"));
			assertFalse(completions.containsKey("rocky"));			
			
		} catch (IOException ioe) {
			fail("IOException: " + ioe);
		} catch (ClassNotFoundException cnfe) {
			fail("ClassNotFoundException: " + cnfe);
		}
	}
	*/
}
