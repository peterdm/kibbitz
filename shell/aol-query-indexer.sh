#!/bin/sh

# Distill only outputs queries.  And queries that generated clickthrus
# It also surpresses consecutive searchs that are the same.
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

cat ../data/queries-*.txt > ../data/queries.txt

# Expand index to include forward word-grams
java -classpath ../dist/kibbitz.jar com.searchintuition.kibbitz.MultiGramFileGenerator ../data/queries.txt ../data/multigrams.txt

# Sort all grams and group by occurance (takes about 20+min)
sort ../data/multigrams.txt | uniq -c > ../data/sortedgrams.txt

# Strip out all grams that only occurred once
egrep -v "^   1 " ../data/sortedgrams.txt > ../data/repeatedgrams.txt

# Reverse column order so RandomAccessFileReader doesn't have to peek past the count
# to see the terms (and trim leading/trailing whitespace
awk 'BEGIN{FS=" "} {cnt=$1; $1=""; print $0 "\t" cnt }' ../data/repeatedgrams.txt | awk '{ gsub(/^[ \t]+|[ \t]+$/, ""); print }' > ../data/index.txt 
