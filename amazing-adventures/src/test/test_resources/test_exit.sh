#!/bin/bash
cd src/main/java
echo '9f03165f3d28bc44f01a37b3162cfe29cd2b08b4 output.txt' > sha1sum.txt
echo QuiT | java Main -f ../../test/test_resources/default.json > output.txt && sha1sum -c sha1sum.txt &> /dev/null
if [[ $? -ne 0 ]]
then
	rm -f output.txt sha1sum.txt
	exit 1
fi

echo quit | java Main -f ../../test/test_resources/default.json > output.txt && sha1sum -c sha1sum.txt &> /dev/null
if [[ $? -ne 0 ]]
then
	rm -f output.txt sha1sum.txt
	exit 1
fi

echo eXiT | java Main -f ../../test/test_resources/default.json > output.txt && sha1sum -c sha1sum.txt &> /dev/null
if [[ $? -ne 0 ]]
then
	rm -f output.txt sha1sum.txt
	exit 1
fi

echo 'exit' | java Main -f ../../test/test_resources/default.json > output.txt && sha1sum -c sha1sum.txt &> /dev/null
if [[ $? -ne 0 ]]
then
	rm -f output.txt sha1sum.txt
	exit 1
fi
rm -f output.txt sha1sum.txt
