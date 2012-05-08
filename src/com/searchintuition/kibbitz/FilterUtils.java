package com.searchintuition.kibbitz;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FilterUtils {

	static final Pattern diacriticPattern = 
			Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	
	static final String[] stopwords = 
			"a,able,about,across,after,all,almost,also,am,among,an,and,any,are,as,at,be,because,been,but,by,can,cannot,could,dear,did,do,does,either,else,ever,every,for,from,get,got,had,has,have,he,her,hers,him,his,how,however,i,if,in,into,is,it,its,just,least,let,like,likely,may,me,might,most,must,my,neither,no,nor,not,of,off,often,on,only,or,other,our,own,rather,said,say,says,she,should,since,so,some,than,that,the,their,them,then,there,these,they,this,tis,to,too,twas,us,wants,was,we,were,what,when,where,which,while,who,whom,why,will,with,would,yet,you,your".split(",");

	static final Pattern acceptWords = Pattern.compile("^\\w");
	
	/* 
	 * From http://stackoverflow.com/questions/1008802/converting-symbols-accent-letters-to-english-alphabet
	 */
	public static String deAccent(String str) {
	    String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD); 
	    return diacriticPattern.matcher(nfdNormalizedString).replaceAll("");
	}

	
	public static boolean isStopword(String str) {
		return Arrays.binarySearch(stopwords, str) >= 0;
	}
	
	public static boolean startsWithWordCharacter(String str) {
		Matcher m = acceptWords.matcher(str);
		return m.lookingAt();
	}
}
