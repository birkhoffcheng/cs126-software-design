#include "catch.hpp"
#include <model.hpp>
#include <iostream>
using std::string;

TEST_CASE("Test initialization and shade of Image class") {
	string image_str = 
	"                            "
	"                            "
	"                            "
	"                            "
	"                            "
	"                +++++##+    "
	"        +++++######+###+    "
	"       +##########+++++     "
	"        #######+##          "
	"        +++###  ++          "
	"           +#+              "
	"           +#+              "
	"            +#+             "
	"            +##++           "
	"             +###++         "
	"              ++##++        "
	"                +##+        "
	"                 ###+       "
	"              +++###        "
	"            ++#####+        "
	"          ++######+         "
	"        ++######+           "
	"       +######+             "
	"    ++######+               "
	"    +####++                 "
	"                            "
	"                            "
	"                            ";
	Image image;
	image.init((char *)image_str.c_str(), 5);
	REQUIRE(image.shade(17, 8) == 0);
	REQUIRE(image.shade(11, 12) == 0);
	REQUIRE(image.shade(20, 18) != 0);
}

TEST_CASE("Test classification accuracy") {
	FILE *fp_images = fopen("../../trainingimages", "r");
	FILE *fp_labels = fopen("../../traininglabels", "r");

	vector<Image> images;
	char *image_str;
	int label;
	while (!feof(fp_images) && !feof(fp_labels)) {
		image_str = new char[812];
		fread(image_str, sizeof(char), 812, fp_images);
		Image image;
		fscanf(fp_labels, "%d", &label);
		image.init(image_str, label);
		delete image_str;
		images.push_back(image);
	}
	
	fclose(fp_labels);
	fclose(fp_images);
	Model *m = new Model;
	m->train(images);
	fp_images = fopen("../../testimages", "r");
	fp_labels = fopen("../../testlabels", "r");
	size_t count = 0;
	size_t correct_count = 0;
	Image image;
	image_str = new char[812];
	while (!feof(fp_images) && !feof(fp_labels)) {
		fread(image_str, sizeof(char), 812, fp_images);
		fscanf(fp_labels, "%d", &label);
		image.init(image_str, 0);
		count++;
		if (m->classify(image) == label) {
			correct_count++;
		}
	}

	delete image_str;
	delete m;
	REQUIRE(correct_count >= count / 2);
}
