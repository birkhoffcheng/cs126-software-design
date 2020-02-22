#!/bin/bash
cd src/main/java
echo 'b2ae33c7952c4551aad27b442270b8557d10df16 output.txt' > sha1sum.txt
echo -e 'pickup Teleporter\nteleport to Basement' | java Main -f ../../test/test_resources/default.json > output.txt && sha1sum -c sha1sum.txt &> /dev/null
if [[ $? -ne 0 ]]
then
	rm -f output.txt sha1sum.txt
	exit 1
fi

echo '1e3c6fc5678608fd46d6c8cac2bd2f49b71a0b73 output.txt' > sha1sum.txt
echo -e 'pickup Teleporter\nteleport to Sky\nquit' | java Main -f ../../test/test_resources/default.json > output.txt && sha1sum -c sha1sum.txt &> /dev/null
if [[ $? -ne 0 ]]
then
	rm -f output.txt sha1sum.txt
	exit 1
fi

rm -f output.txt sha1sum.txt
