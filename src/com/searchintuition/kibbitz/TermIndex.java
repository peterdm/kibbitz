/**
 * 
 */
package com.searchintuition.kibbitz;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import org.ardverk.collection.PatriciaTrie;
import org.ardverk.collection.StringKeyAnalyzer;

/**
 * @author Peter
 *
 */
public class TermIndex {
	
	final String serializedFilename = "trie.ser";
	protected PatriciaTrie<String, Integer> trie;
	
	public TermIndex() {
		trie = new PatriciaTrie<String, Integer>(StringKeyAnalyzer.CHAR);
	}
	
	public void load() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = null;
		
		try {
			FileInputStream fis = new FileInputStream(serializedFilename);
			ois = new ObjectInputStream(fis);
			trie = (PatriciaTrie<String, Integer>)ois.readObject();
		} finally {
		    if(ois != null)
		    	try { ois.close();} catch (IOException ioe) {}
		}
	}
	
	public void save() throws IOException {
		ObjectOutputStream oos = null;
		
		try {
			FileOutputStream fos = new FileOutputStream(serializedFilename);
		    oos = new ObjectOutputStream(fos);
		    oos.writeObject(trie);
		} finally {
		    if(oos != null)
		    	try { oos.close();} catch (IOException ioe) {}
		}
	}
	
	public void loadFromQueryLog(String filename) throws IOException {
		// Create a new QueryLogReader, use readLine(true)
		QueryLogReader reader;
		
		reader = new QueryLogReader(new FileReader(filename));
		
		String query;
		
		// the *true* flag only returns queries which have clickthrus
		while ((query = reader.readLine(true)) != null) {			
			for(String term : new QueryTokenizer(query)) {
				addTerm(term);
			}
		}
	}
	
	public int addTerm(String term) {
		
		if(trie.containsKey(term)) {
			// increment count
			int count = trie.get(term);
			trie.put(term, ++count);
			return count;
		} else {
			// insert
			trie.put(term, 1);
		}
		
		return 1;
	}
	
	public int getTermCount(String term) {
		if (!trie.containsKey(term))
			return 0;
		
		return trie.get(term);
	}
	
	public void showTerms() {
		for (Map.Entry<String, Integer> cursor : trie.entrySet()) {		
			System.out.println(cursor.getKey() + "(" + cursor.getValue() + ")");
		}
	}
	
	public boolean containsTerm(String key) {
		return trie.containsKey(key);
	}
	
	
	public Map<String, Integer> prefixMap(String prefix) {
		return trie.prefixMap(prefix);
	}
	
	public boolean equals(TermIndex ti) {
		return (trie.equals(ti.trie));
	}
	
	public int size() {
		return trie.size();
	}

}
