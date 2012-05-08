package com.searchintuition.kibbitz;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PrefixServer {

	final int CACHE_SIZE = 25000;
	final String termFile = "/Users/Peter/etsy/data/index.txt";

	private TermFile terms = null;
	private int numberOfSuggestions;

	Map<String, List<String>> cache = null;

	public PrefixServer() {
		this(10);
	}

	public PrefixServer(int numberOfSuggestions) {

		this.numberOfSuggestions = numberOfSuggestions;

		this.cache = Collections
				.synchronizedMap(new LruCache<String, List<String>>(CACHE_SIZE));

		this.terms = new TermFile(this.termFile);
	}

	public List<String> runQuery(String prefix) {

		if (cache.containsKey(prefix)) {
			return cache.get(prefix);
		}

		// prefixSearch is going to find all the matching rows in the term index
		// file
		Map<String, Integer> completions = terms.prefixSearch(prefix);

		// scorer will score and order those rows
		TermScorer scorer = new TermScorer(prefix, completions);

		List<Map.Entry<String, Integer>> scoredTerms = scorer.getTerms(this.numberOfSuggestions);

		List<String> result = new ArrayList<String>(scoredTerms.size());

		for (Map.Entry<String, Integer> term : scoredTerms) {
			result.add(term.getKey());
		}

		cache.put(prefix, result);

		return result;
	}

	public void runQueries(String queryFile) {

		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(queryFile);
			br = new BufferedReader(fr);

			String query;
			while ((query = br.readLine()) != null) {
				System.out.println("--- " + query + " ---");
				for(String result : this.runQuery(query)) {
					System.out.println(result);
				}
			}

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				fr.close();
			} catch (IOException e) {}
		}
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			usage();
			System.exit(0);
		}
		
		PrefixServer server = new PrefixServer(Integer.parseInt(args[1]));
		server.runQueries(args[0]);
	}
	
	protected static void usage() {
		System.out.println("Usage: java PrefixServer <queryFile> <top-N-results");
		return;
	}

	/**
	 * From
	 * http://stackoverflow.com/questions/221525/how-would-you-implement-an-
	 * lru-cache-in-java-6
	 */
	private class LruCache<A, B> extends LinkedHashMap<A, B> {
		private final int maxEntries;

		public LruCache(final int maxEntries) {
			super(maxEntries + 1, 1.0f, true);
			this.maxEntries = maxEntries;
		}

		/**
		 * Returns <tt>true</tt> if this <code>LruCache</code> has more entries
		 * than the maximum specified when it was created.
		 * 
		 * <p>
		 * This method <em>does not</em> modify the underlying <code>Map</code>;
		 * it relies on the implementation of <code>LinkedHashMap</code> to do
		 * that, but that behavior is documented in the JavaDoc for
		 * <code>LinkedHashMap</code>.
		 * </p>
		 * 
		 * @param eldest
		 *            the <code>Entry</code> in question; this implementation
		 *            doesn't care what it is, since the implementation is only
		 *            dependent on the size of the cache
		 * @return <tt>true</tt> if the oldest
		 * @see java.util.LinkedHashMap#removeEldestEntry(Map.Entry)
		 */
		@Override
		protected boolean removeEldestEntry(final Map.Entry<A, B> eldest) {
			return super.size() > maxEntries;
		}
	}
}
