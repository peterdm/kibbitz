/**
 * 
 */
package com.searchintuition.kibbitz.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import com.searchintuition.kibbitz.QueryLogReader;

/**
 * @author Peter
 *
 */
public class ParseTest {

	final String testFile = "/Users/Peter/etsy/infochimps_aol-search-data/AOL-user-ct-collection/user-ct-test-collection-01.txt";
		
	
	/**
	 * Test method for {@link com.searchintuition.kibbitz.QueryLogReader#readLine()}.
	 */
	@Test
	public final void testReadLine() {
		QueryLogReader reader = null;
		
		try {
			reader = new QueryLogReader(new FileReader(testFile));

			assertEquals("rentdirect.com",				reader.readLine());
			assertEquals("www.prescriptionfortime.com", reader.readLine());
			assertEquals("staple.com",					reader.readLine());
			assertEquals("www.newyorklawyersite.com",	reader.readLine());
			assertEquals("westchester.gov",				reader.readLine());
			assertEquals("space.comhttp",				reader.readLine());
		} catch (IOException ioe) {
			fail("QueryLogReader threw an IOException: " + ioe);
		} finally {
			try {
				reader.close();
			} catch (IOException ioe) {
				//squash
			}
		}
	}

	/**
	 * Test method for {@link com.searchintuition.kibbitz.QueryLogReader#readLine(boolean)}.
	 */
	@Test
	public final void testReadLineBoolean() {		
		QueryLogReader reader = null;
		
		try {
			reader = new QueryLogReader(new FileReader(testFile));

			assertEquals("westchester.gov",				reader.readLine(true));
			assertEquals("207 ad2d 530",				reader.readLine(true));
			assertEquals("vera.org",					reader.readLine(true));
			assertEquals("lottery",						reader.readLine(true));
		} catch (IOException ioe) {
			fail("QueryLogReader threw an IOException: " + ioe);
		} finally {
			try {
				reader.close();
			} catch (IOException ioe) {
				//squash
			}
		}
	}

}
