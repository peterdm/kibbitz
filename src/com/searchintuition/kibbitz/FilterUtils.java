package com.searchintuition.kibbitz;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FilterUtils {

	static final Pattern diacriticPattern = 
			Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	
	static final String[] stopwords = 
			"a,able,about,across,after,all,almost,also,am,among,an,and,any,are,as,at,be,because,been,but,by,can,cannot,could,dear,did,do,does,either,else,ever,every,for,from,get,got,had,has,have,he,her,hers,him,his,how,however,i,if,in,into,is,it,its,just,least,let,like,likely,may,me,might,most,must,my,neither,no,nor,not,of,off,often,on,only,or,other,our,own,rather,said,say,says,she,should,since,so,some,than,that,the,their,them,then,there,these,they,this,tis,to,too,twas,us,wants,was,we,were,what,when,where,which,while,who,whom,why,will,with,would,yet,you,your".split(",");

	static final Pattern acceptWords = Pattern.compile("^\\w");
	
	public static boolean isStopword(String str) {
		return Arrays.binarySearch(stopwords, str) >= 0;
	}
	
	public static boolean startsWithWordCharacter(String str) {
		Matcher m = acceptWords.matcher(str);
		return m.lookingAt();
	}
	
	public static String unAccent(String s) {
		//
		// JDK1.5
		// use sun.text.Normalizer.normalize(s, Normalizer.DECOMP, 0);
		//
		return Normalizer.normalize(s, Form.NFD).replaceAll(
				"\\p{InCombiningDiacriticalMarks}+", "");
	}

	public static void main(String args[]) throws Exception {
		System.out.println(Locale.getDefault());
		String value = "é à î ó ì _ @";
		System.out.println(value);
		System.out.println(FilterUtils.unAccent(value));
		System.out.println(Normalizer.isNormalized(value, Normalizer.Form.NFKD));
		// output : e a i _ @
	}
}
