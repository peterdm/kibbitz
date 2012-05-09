package com.searchintuition.kibbitz;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TermFile {

	private File termFile = null;

	
	public TermFile(String filename) {
		this.termFile = new File(filename);
	}
	
	public Map<String, Integer> prefixSearch(String query) {
	
		// Trim any leading/trailing whitespace
		query = query.trim();
		
		Map<String, Integer> results = 
				new HashMap<String, Integer>(100);
		
		for( String result : this.binarySearch(query)) {
			String[] detail = result.split("\t");
			results.put(detail[0], Integer.parseInt(detail[1]));
		}
		
		return results;
	}
	
	
	/**
	 * **
	 * Based on ExternalBinarySearch example from 
	 * http://stackoverflow.com/questions/736556/binary-search-in-a-sorted-memory-mapped-file-in-java
	 * @param string
	 * @return
	 */
	public List<String> binarySearch(String string) {

	    List<String> result = new ArrayList<String>();
	    try {
	        RandomAccessFile raf = new RandomAccessFile(this.termFile, "r");

	        long low = 0;
	        long high = this.termFile.length();

	        long p = -1;
	        while (low < high) {
	            long mid = (low + high) / 2;
	            p = mid;
	            while (p >= 0) {
	                raf.seek(p);

	                char c = (char) raf.readByte();
	                //System.out.println(p + "\t" + c);
	                if (c == '\n')
	                    break;
	                p--;
	            }
	            if (p < 0)
	                raf.seek(0);
	            String line = raf.readLine();
	            //System.out.println("-- " + mid + " " + line);
	            if (compare(line, string) < 0)
	                low = mid + 1;
	            else
	                high = mid;
	        }

	        p = low;
	        while (p >= 0) {
	            raf.seek(p);
	            if (((char) raf.readByte()) == '\n')
	                break;
	            p--;
	        }

	        if (p < 0)
	            raf.seek(0);

	        while (true) {
	            String line = raf.readLine();
	            if (line == null || !line.startsWith(string))
	                break;
	            result.add(line);
	        }

	        raf.close();
	    } catch (IOException e) {
	        System.out.println("IOException:");
	        e.printStackTrace();
	    }
	    return result;
	}
	
	private int compare(String entry, String toMatch) {
		if (entry==null) return 1;  // weird edge case causing problems with accented chars
		String entryTerm = entry.split("\t")[0];
		return entryTerm.compareTo(toMatch);
	}
	
}
