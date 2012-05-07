#!/bin/sh

awk -f distill.awk ../infochimps_aol-search-data/AOL-user-ct-collection/user-ct-test-collection-01.txt > ../data/queries-01.txt 
awk -f distill.awk ../infochimps_aol-search-data/AOL-user-ct-collection/user-ct-test-collection-02.txt > ../data/queries-02.txt 
awk -f distill.awk ../infochimps_aol-search-data/AOL-user-ct-collection/user-ct-test-collection-03.txt > ../data/queries-03.txt 
awk -f distill.awk ../infochimps_aol-search-data/AOL-user-ct-collection/user-ct-test-collection-04.txt > ../data/queries-04.txt 
awk -f distill.awk ../infochimps_aol-search-data/AOL-user-ct-collection/user-ct-test-collection-05.txt > ../data/queries-05.txt 
awk -f distill.awk ../infochimps_aol-search-data/AOL-user-ct-collection/user-ct-test-collection-06.txt > ../data/queries-06.txt 
awk -f distill.awk ../infochimps_aol-search-data/AOL-user-ct-collection/user-ct-test-collection-07.txt > ../data/queries-07.txt 
awk -f distill.awk ../infochimps_aol-search-data/AOL-user-ct-collection/user-ct-test-collection-08.txt > ../data/queries-08.txt 
awk -f distill.awk ../infochimps_aol-search-data/AOL-user-ct-collection/user-ct-test-collection-09.txt > ../data/queries-09.txt 
awk -f distill.awk ../infochimps_aol-search-data/AOL-user-ct-collection/user-ct-test-collection-10.txt > ../data/queries-10.txt 

cat ../data/queries-*.txt > ../data/queries.txt
