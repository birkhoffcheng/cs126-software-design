#!/bin/bash
cd src/main/java
echo 'a90ffa8975a1d1f09d78ad626431162c296484eb output.txt' > sha1sum.txt
echo -e 'go right\nuse Lockpick with Down\ngo Down' | java Main -f ../../test/test_resources/default.json > output.txt && sha1sum -c sha1sum.txt &> /dev/null
if [[ $? -ne 0 ]]
then
	rm -f output.txt sha1sum.txt
	exit 1
fi

echo -e 'go right\nuse Lockpick with Down\ngo Down' | java Main --file ../../test/test_resources/default.json > output.txt && sha1sum -c sha1sum.txt &> /dev/null
if [[ $? -ne 0 ]]
then
	rm -f output.txt sha1sum.txt
	exit 1
fi

echo -e 'go right\nuse Lockpick with Down\ngo Down' | java Main -u https://birkhoff.ch/default.json > output.txt && sha1sum -c sha1sum.txt &> /dev/null
if [[ $? -ne 0 ]]
then
	rm -f output.txt sha1sum.txt
	exit 1
fi

echo -e 'go right\nuse Lockpick with Down\ngo Down' | java Main --url https://birkhoff.ch/default.json > output.txt && sha1sum -c sha1sum.txt &> /dev/null
if [[ $? -ne 0 ]]
then
	rm -f output.txt sha1sum.txt
	exit 1
fi

rm -f output.txt sha1sum.txt

java Main -h | grep -i usage
if [[ $? -ne 0 ]]
then
	exit 1
fi

java Main --help | grep -i usage
if [[ $? -ne 0 ]]
then
	exit 1
fi
