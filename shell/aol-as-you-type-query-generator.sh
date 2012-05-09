#!/bin/sh
export LC_ALL=C

awk -f awk/randoms.awk  ../data/queries.txt > ../data/randoms.txt
