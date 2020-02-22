#!/bin/bash
cd src/main/java
echo 'ba346e1b0df7b6c36e31b6c07fe373bdc8769d67 output.txt' > sha1sum.txt
echo -e 'pickup Key2\nquit' | java Main -f ../../test/test_resources/default.json > output.txt && sha1sum -c sha1sum.txt &> /dev/null
if [[ $? -ne 0 ]]
then
	rm -f output.txt sha1sum.txt
	exit 1
fi

echo '1d6fca67a673b71313f30c52d188e3d8f4c2a18d output.txt' > sha1sum.txt
echo -e 'pickup book\nquit' | java Main -f ../../test/test_resources/default.json > output.txt && sha1sum -c sha1sum.txt &> /dev/null
if [[ $? -ne 0 ]]
then
	rm -f output.txt sha1sum.txt
	exit 1
fi

rm -f output.txt sha1sum.txt
