/**
 * 
 */
package com.searchintuition.kibbitz;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

/**
 * QueryTokenizer Expands "chocolate ice cream sandwich" := 
 * 	"chocolate", "chocolate ice", "chocolate ice cream", "chocolate ice cream sandwich",
 *  "ice", "ice cream", "ice cream sandwich", 
 *  "cream", "cream sandwich",
 *  "sandwich"
 *  
 *  Only adds expansions which start with a word-character [a-z0-9]
 *  to help cut down on clutter that doesn't look useful to autocomplete.
 *  
 * @author Peter
 *
 */
public class QueryTokenizer implements Iterable<String>, Iterator<String> 
{
	private ArrayList<String> indexTerms;
	private int idx = 0;
	private Pattern splitter = Pattern.compile("\\s+");

	
	public QueryTokenizer() {
		indexTerms = new ArrayList<String>(30);
	}
	
	
	public QueryTokenizer setQuery(String query) {
		idx = 0;
		indexTerms.clear();
		tokenize(query);
		return this;
	}
	
	private void tokenize(String query) {
		
		// Split on space
		String[] queryTerms = splitter.split(query);
		
		// Here we're loading all possible ordered grams 
		// (E.g. "chocolate ice cream sandwich" := 
		// "chocolate", "chocolate ice", "chocolate ice cream", "chocolate ice cream sandwich",
		// "ice", "ice cream", "ice cream sandwich", 
		// "cream", "cream sandwich",
		// "sandwich"
		for (int head=0; head<queryTerms.length; head++) {
			
			// insert head term ("[chocolate] ice cream sandwich")
			this.addTerm(queryTerms[head]);
			
			// insert following term multi-grams ("chocolate ice", "chocolate ice cream", "chocolate ice cream sandwich")
			if (queryTerms.length > head+1) {
				StringBuffer buff = new StringBuffer(query.length()*4);
				buff.append(queryTerms[head]);

				for (int tail=head+1; tail<queryTerms.length; tail++) {
					buff.append(" ");
					buff.append(queryTerms[tail]);
					
					this.addTerm(buff.toString());
				}
			}
		}
		
	}
	
	private void addTerm(String term)
	{
		// Only add terms that begin with word characters [a-zA-Z0-9]
		// this way we strip out all the leading punctuation generated
		// by the gram-generator in tokenize.
		
		if (FilterUtils.startsWithWordCharacter(term)) {
			indexTerms.add(term);
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
