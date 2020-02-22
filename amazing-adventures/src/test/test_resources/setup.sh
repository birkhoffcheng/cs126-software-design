#!/bin/bash
if [ -d 'src/main/java/org' ]
then
	exit 0
fi
tar -xf src/test/test_resources/dependencies.tar.gz
cd src/main/java/
javac Main.java
