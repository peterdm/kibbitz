#!/bin/sh

CLASSPATH=../dist/kibbitz.jar

echo $CLASSPATH $1 $2
java -classpath $CLASSPATH com.searchintuition.kibbitz.PrefixServer $1 $2


