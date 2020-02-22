#!/bin/bash
cd src/main/java
echo 'a90ffa8975a1d1f09d78ad626431162c296484eb output.txt' > sha1sum.txt
echo -e 'go right\nuse Lockpick with Down\ngo Down' | java Main -f ../../test/test_resources/default.json > output.txt && sha1sum -c sha1sum.txt &> /dev/null
if [[ $? -ne 0 ]]
then
	rm -f output.txt sha1sum.txt
	exit 1
fi

echo '2aee772e04f592b678e17d7d35f962a81eeb6e12 output.txt' > sha1sum.txt
echo -e 'go right\nuse hammer with Down\nquit' | java Main -f ../../test/test_resources/default.json > output.txt && sha1sum -c sha1sum.txt &> /dev/null
if [[ $? -ne 0 ]]
then
	rm -f output.txt sha1sum.txt
	exit 1
fi

rm -f output.txt sha1sum.txt
