#!/bin/bash
cd src/main/java
echo '705c8c38f2bfd07a50b4b3eb3e832c7c9f73f5ae output.txt' > sha1sum.txt
echo -e 'examine\nquit' | java Main -f ../../test/test_resources/default.json > output.txt && sha1sum -c sha1sum.txt &> /dev/null
if [[ $? -ne 0 ]]
then
	rm -f output.txt sha1sum.txt
	exit 1
fi

rm -f output.txt sha1sum.txt
