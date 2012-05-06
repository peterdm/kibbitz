/**
 * 
 */
package com.searchintuition.kibbitz.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.searchintuition.kibbitz.TermIndex;

/**
 * @author Peter
 *
 */
public class TrieTest {

	final String testFile = "/Users/Peter/etsy/infochimps_aol-search-data/AOL-user-ct-collection/short.txt";
	TermIndex ti;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Create a new TermIndex
		
		ti = new TermIndex();
		
		try {
			ti.loadFromQueryLog(testFile);
			ti.showTerms();

		} catch (IOException ioe) {
			// squash
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
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
}
