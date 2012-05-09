/**
 * 
 */
package com.searchintuition.kibbitz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * TermScorer holds an ordered List of suggested query-completions.
 * The sorting/scoring strategy in use below is FrequencyAndNGramComparator.
 * 
 * The way to use this is to pass in the unordered result set from 
 * the prefix-search of the index-file.  Then getTerms() returns an
 * ordered list of the desired size.
 * 
 * 
 * @author Peter
 *
 */
public class TermScorer {

	String query = null;
	List<Map.Entry<String, Integer>> completions = null;
	
	// Given a collection of term suggestions
	// score, order and return the requested amount.
	public TermScorer(String query, Map<String, Integer> prefixMap) {
		this.query = query;
		
		// re-order the map by frequency and n-gramminess
		this.completions = 
				new ArrayList<Map.Entry<String, Integer>>(prefixMap.entrySet());
		
		Collections.sort(
				this.completions, 
				new FrequencyAndNGramComparator(query));
	}
	
	public List<Map.Entry<String,Integer>> getTerms(int numberToReturn) {
		if (this.completions.size() < numberToReturn)
			return this.completions;
		
		return this.completions.subList(0, numberToReturn-1);
	}

	public List<Map.Entry<String,Integer>> getTerms() {
		return this.completions;
	}

	
}

/**
 * This scorer strategy is as follows:
 * 
 * Shorter suggestions are better if they are close to the query length
 * and generate high-clickthroughs.  So:
 * 
 * score = (query.length / suggestion.length) * suggestion_doc_freq
 * 
 * @author Peter
 *
 */
class FrequencyAndLengthComparator implements Comparator<Map.Entry<String,Integer>> {
	private String query = null;
	
	public FrequencyAndLengthComparator(String query) {
		this.query = query;
	}
	
	public int compare(Map.Entry<String,Integer> term1, Map.Entry<String,Integer> term2) {
		if (this.score(term1) < this.score(term2)) {
			return 1;
		} else if (this.score(term1) == this.score(term2)) {
			return 0;
		} else {
			return -1;
		}
	}
	
	private double score(Map.Entry<String,Integer> term) {
		double score = Math.log((double)query.length()/(double)term.getKey().length()+1) * Math.log(term.getValue()+1);
		//System.out.println(term.getKey()+":"+score);
		return score;
	}
}


/**
 * This scorer strategy is as follows:
 * 
 * Longer suggestions are better if they generate high-clickthroughs
 * They are a bit like guided search.  So:
 * 
 * score = log(# of spaces + 1)  * suggestion_doc_freq
 * 
 * @author Peter
 *
 */
class FrequencyAndNGramComparator implements Comparator<Map.Entry<String,Integer>> {
	private String query = null;
	private Pattern splitter = Pattern.compile("\\s+");
	
	public FrequencyAndNGramComparator(String query) {
		this.query = query;
	}
	
	public int compare(Map.Entry<String,Integer> term1, Map.Entry<String,Integer> term2) {
		if (this.score(term1) < this.score(term2)) {
			return 1;
		} else if (this.score(term1) == this.score(term2)) {
			return 0;
		} else {
			return -1;
		}
	}
	
	private double score(Map.Entry<String,Integer> term) {
		int numTokens = splitter.split(term.getKey()).length;
		double score = Math.log((double)numTokens+1) * Math.log(term.getValue()+1);
		//System.out.println(term.getKey()+":"+score);
		return score;
	}
}
