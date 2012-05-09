#!/bin/sh

############
# Runs a java program PrefixServer (not actually a server yet) 
# which processes an input file of partial queries to complete
# and writes the suggestions out to stdout.
#
# Usage: $ ./aol-query-suggest.sh <file-with-queries-to-complete> <max-sugg-per-query>
#
############

CLASSPATH=$CLASSPATH:../dist/kibbitz.jar

java -Xms256m -Xmx256m -classpath $CLASSPATH com.searchintuition.kibbitz.PrefixServer $1 $2 $3


