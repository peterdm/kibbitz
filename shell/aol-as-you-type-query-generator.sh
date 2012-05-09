#!/bin/sh

##########
# Generates a randomly truncated, random sample 
# of the queries contained in the AOL dataset.
#
# The queries are truncated to 3 or more characters to 
# simulate realistic use of auto-complete based on this corpus.
##########

export LC_ALL=C

awk -f awk/randoms.awk  ../data/queries.txt > ../data/randoms.txt
