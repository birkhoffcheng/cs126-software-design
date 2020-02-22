#!/bin/bash
cd src/main/java
echo '539d4537565e23c849e61fd644b31343bf29fc2a output.txt' > sha1sum.txt
echo -e 'drop Lockpick\nquit' | java Main -f ../../test/test_resources/default.json > output.txt && sha1sum -c sha1sum.txt &> /dev/null
if [[ $? -ne 0 ]]
then
	rm -f output.txt sha1sum.txt
	exit 1
fi

echo '060690b9cf7ede9b8fcfa8476baf3b7c4d491d4b output.txt' > sha1sum.txt
echo -e 'drop phone\nquit' | java Main -f ../../test/test_resources/default.json > output.txt && sha1sum -c sha1sum.txt &> /dev/null
if [[ $? -ne 0 ]]
then
	rm -f output.txt sha1sum.txt
	exit 1
fi

rm -f output.txt sha1sum.txt
