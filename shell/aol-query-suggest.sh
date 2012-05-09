#!/bin/sh

CLASSPATH=$CLASSPATH:../dist/kibbitz.jar

java -version

java -classpath $CLASSPATH com.searchintuition.kibbitz.PrefixServer $1 $2


