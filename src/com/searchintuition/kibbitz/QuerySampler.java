/**
 * 
 */
package com.searchintuition.kibbitz;

/**
 * @author Peter
 *
 */
public class QuerySampler {

}

// awk 'BEGIN {FS="\t"} $5!="" {print $2}' short.txt | awk 'FNR>1' | awk 'a !~ $0; {a=$0}' | more