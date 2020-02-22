#!/bin/bash
cd src/main/java
echo 'a2a9d8b88b670db6977ca23f66836afb3714d806 output.txt' > sha1sum.txt
echo -e 'go right\nquit' | java Main -f ../../test/test_resources/default.json > output.txt && sha1sum -c sha1sum.txt &> /dev/null
if [[ $? -ne 0 ]]
then
	rm -f output.txt sha1sum.txt
	exit 1
fi

echo 'f9770e6c6a0acf59c6c898575eaf7ac5a4a9de81 output.txt' > sha1sum.txt
echo -e 'go left\nquit' | java Main -f ../../test/test_resources/default.json > output.txt && sha1sum -c sha1sum.txt &> /dev/null
if [[ $? -ne 0 ]]
then
	rm -f output.txt sha1sum.txt
	exit 1
fi

echo 'ea3fde37404e6a309d699f64094685359aa0e770 output.txt' > sha1sum.txt
echo -e 'go right\ngo left\nquit' | java Main -f ../../test/test_resources/default.json > output.txt && sha1sum -c sha1sum.txt &> /dev/null
if [[ $? -ne 0 ]]
then
	rm -f output.txt sha1sum.txt
	exit 1
fi

rm -f output.txt sha1sum.txt
