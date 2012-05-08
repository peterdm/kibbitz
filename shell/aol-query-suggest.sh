#!/bin/sh
export LC_ALL=C

CLASSPATH=../dist/kibbitz.jar

java -classpath $CLASSPATH com.searchintuition.kibbitz.PrefixServer $1 $2


