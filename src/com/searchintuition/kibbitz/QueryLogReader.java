/**
 * 
 */
package com.searchintuition.kibbitz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * @author Peter
 *
 * Behaves like a BufferedReader but enforces the following rule with readLine()
 *   1.  Skips consecutive queries where userid and query are the same returning only the first
 *   occurance (since this indicates the user queried once, but explored multiple search results).
 *   
 * (Not thread-safe)    
 */
public class QueryLogReader extends BufferedReader
{

	private String prevQuery;


	public QueryLogReader(Reader in) {
		super(in);
		
		try {
			super.readLine(); // trim header
		} catch (IOException ioe) { 
			// do nothing
		}	
	}

	public QueryLogReader(Reader in, int sz)
	{
		super(in, sz);
		
		try {
			super.readLine(); // trim header
		} catch (IOException ioe) { 
			// do nothing
		}
	}
	
	public String readLine() throws IOException {
		return this.readLine(false);
	}
	
	public String readLine(final boolean requireClickThrough) throws IOException {

		String line = super.readLine();
		
		if (line==null)
			return null;
		
		String[] fields = line.split("\t", 4);  // Save a split since we only care that there was a clickthru
		String query = fields[1];
		
		if (requireClickThrough && fields[3].trim().isEmpty()) {
			// No clickthrough from this line, skip it.
			return this.readLine(requireClickThrough);
		}
		
		if (prevQuery!=null && (prevQuery.equals(query))) {
			// This line has the same query as the previous
			// (Based on the file ordering, the same user is being assumed as well to reduce string-comparisons)
			return this.readLine(requireClickThrough);
		} 

		prevQuery = query;
		return query;
	}

}
