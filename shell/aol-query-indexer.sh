#!/bin/sh

############
# Extracts meaningful (clickedthru) queries from the AOL corpus.
# Only the first of a consecutive series of (the same) query is retained.
# Word-gram expansion is performed, terms are counted, and a final
# alphabetized file (index.txt) is produced in the following format:
#
# <search-query1>TAB<document frequency>
# <search-query2>TAB<document frequency>
# ...
# 
############

export LC_ALL=C

INPUTFILE=../data/queries.txt
OUTPUTFILE=../data/multigrams.txt
SORTED_OUTPUTFILE=../data/sortedgrams.txt
REPEATED_OUTPUTFILE=../data/repeatedgrams.txt
INDEXFILE=../data/index.txt

mkdir -p ../data

# Distill only outputs queries.  And queries that generated clickthrus
# It also surpresses consecutive searchs that are the same.
echo "Extracting searches (with clickthrus) from AOL search data"
awk -f awk/distill.awk ../infochimps_aol-search-data/AOL-user-ct-collection/user-ct-test-collection-01.txt > ../data/queries-01.txt 
awk -f awk/distill.awk ../infochimps_aol-search-data/AOL-user-ct-collection/user-ct-test-collection-02.txt > ../data/queries-02.txt 
awk -f awk/distill.awk ../infochimps_aol-search-data/AOL-user-ct-collection/user-ct-test-collection-03.txt > ../data/queries-03.txt 
awk -f awk/distill.awk ../infochimps_aol-search-data/AOL-user-ct-collection/user-ct-test-collection-04.txt > ../data/queries-04.txt 
awk -f awk/distill.awk ../infochimps_aol-search-data/AOL-user-ct-collection/user-ct-test-collection-05.txt > ../data/queries-05.txt 
awk -f awk/distill.awk ../infochimps_aol-search-data/AOL-user-ct-collection/user-ct-test-collection-06.txt > ../data/queries-06.txt 
awk -f awk/distill.awk ../infochimps_aol-search-data/AOL-user-ct-collection/user-ct-test-collection-07.txt > ../data/queries-07.txt 
awk -f awk/distill.awk ../infochimps_aol-search-data/AOL-user-ct-collection/user-ct-test-collection-08.txt > ../data/queries-08.txt 
awk -f awk/distill.awk ../infochimps_aol-search-data/AOL-user-ct-collection/user-ct-test-collection-09.txt > ../data/queries-09.txt 
awk -f awk/distill.awk ../infochimps_aol-search-data/AOL-user-ct-collection/user-ct-test-collection-10.txt > ../data/queries-10.txt 

cat ../data/queries-*.txt > $INPUTFILE

# Expand index to include forward word-grams
echo "Creating multi-word grams from search file to capture intra-query occurances"
java -classpath ../dist/kibbitz.jar com.searchintuition.kibbitz.MultiGramFileGenerator $INPUTFILE $OUTPUTFILE

# Sort all grams and group by occurance (takes about 20+min)
echo "Creating a unique query file with document frequency counts"
sort $OUTPUTFILE | uniq -c > $SORTED_OUTPUTFILE

# Strip out all grams that only occurred once
echo "Keep only queries that have occurred seen than once"
egrep -v "^   1 " $SORTED_OUTPUTFILE > $REPEATED_OUTPUTFILE

# Reverse column order so RandomAccessFileReader doesn't have to peek past the count
# to see the terms (and trim leading/trailing whitespace)
echo "Reverse column order to be <search-term><tab><frequency> for binary-search"
awk 'BEGIN{FS=" "} {cnt=$1; $1=""; print $0 "\t" cnt }' $REPEATED_OUTPUTFILE | awk '{ gsub(/^[ \t]+|[ \t]+$/, ""); print }' > $INDEXFILE 

echo "Index file generated: $INDEXFILE"
