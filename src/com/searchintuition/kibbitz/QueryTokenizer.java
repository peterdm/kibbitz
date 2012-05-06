/**
 * 
 */
package com.searchintuition.kibbitz;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Peter
 *
 */
public class QueryTokenizer implements Iterable<String>, Iterator<String> 
{
	private ArrayList<String> indexTerms;
	int idx = 0;
	
	public QueryTokenizer(String query) {
		indexTerms = new ArrayList<String>();
		tokenize(query);
	}
	
	private void tokenize(String query) {
		
		// Split on space
		String[] queryTerms = query.split("\\s+");
		
		// Here we're loading all possible ordered grams 
		// (E.g. "chocolate ice cream sandwich" := 
		// "chocolate", "chocolate ice", "chocolate ice cream", "chocolate ice cream sandwich",
		// "ice", "ice cream", "ice cream sandwich", 
		// "cream", "cream sandwich",
		// "sandwich"
		for (int head=0; head<queryTerms.length; head++) {
			
			// insert head term ("[chocolate] ice cream sandwich")
			indexTerms.add(queryTerms[head]);
			
			// insert following term multi-grams ("chocolate ice", "chocolate ice cream", "chocolate ice cream sandwich")
			if (queryTerms.length > head+1) {
				StringBuffer buff = new StringBuffer(queryTerms[head]);

				for (int tail=head+1; tail<queryTerms.length; tail++) {
					buff.append(" ");
					buff.append(queryTerms[tail]);
					
					indexTerms.add(buff.toString());
				}
			}
		}
		
	}
	
	
	public Iterator<String> iterator() {
		return this;
	}
	
	public boolean hasNext() {
		return this.idx < indexTerms.size();
	}
	
	public String next() {
		if (this.idx == indexTerms.size()) {
			throw new NoSuchElementException();
		}
		
		return indexTerms.get(this.idx++);
	}
	
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
}
