#!/bin/bash
cd src/main/java
echo '7d974bb30a29cde1505086d3b1b5538e44e54595 output.txt' > sha1sum.txt
echo -e 'H7UXEInIjJig+QRFEXtgg65Ufe56\nquit' | java Main -f ../../test/test_resources/default.json > output.txt && sha1sum -c sha1sum.txt &> /dev/null
if [[ $? -ne 0 ]]
then
	rm -f output.txt sha1sum.txt
	exit 1
fi

echo '0507cf699b538c87f6c281942e46d5db4bdf1590 output.txt' > sha1sum.txt
echo -e 'go CkkcB0PZ3DqvHoAJYdE\nquit' | java Main -f ../../test/test_resources/default.json > output.txt && sha1sum -c sha1sum.txt &> /dev/null
if [[ $? -ne 0 ]]
then
	rm -f output.txt sha1sum.txt
	exit 1
fi

rm -f output.txt sha1sum.txt
