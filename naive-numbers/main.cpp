#include <image.hpp>
#include <model.hpp>
#include <iostream>
using std::vector;
using std::cerr;
using std::cout;
using std::endl;
constexpr size_t IMAGE_BLOCK_SIZE = (IMAGE_SIZE + 1) * IMAGE_SIZE;

void train(char *images_filename, char *labels_filename, char *model_filename) {
	FILE *fp_images = fopen(images_filename, "r");
	FILE *fp_labels = fopen(labels_filename, "r");
	if (!fp_images || !fp_labels) {
		cerr<<"Unable to open file"<<endl;
		return;
	}

	vector<Image> images;
	char *image_str;
	int label;
	while (!feof(fp_images) && !feof(fp_labels)) {
		image_str = new char[IMAGE_BLOCK_SIZE];
		fread(image_str, sizeof(char), IMAGE_BLOCK_SIZE, fp_images);
		Image image;
		fscanf(fp_labels, "%d", &label);
		image.init(image_str, label);
		delete image_str;
		images.push_back(image);
	}
	
	fclose(fp_labels);
	fclose(fp_images);
	
	Model *m = new Model;
	if (m) {
		m->train(images);
		m->output(model_filename);
	}

	delete m;
}

void classify(char *images_filename, char *labels_filename, char *model_filename) {
	FILE *fp_images = fopen(images_filename, "r");
	FILE *fp_labels = fopen(labels_filename, "r");
	if (!fp_images || !fp_labels) {
		cerr<<"Unable to open file"<<endl;
		return;
	}

	size_t count = 0;
	size_t correct_count = 0;
	Model *m = new Model;
	if (!m) {
		cerr<<"Unable to open model file"<<endl;
		return;
	}

	m->input(model_filename);
	Image image;
	int label;
	char *image_str = new char[IMAGE_BLOCK_SIZE];
	while (!feof(fp_images) && !feof(fp_labels)) {
		fread(image_str, sizeof(char), IMAGE_BLOCK_SIZE, fp_images);
		fscanf(fp_labels, "%d", &label);
		image.init(image_str, 0);
		count++;
		if (m->classify(image) == label) {
			correct_count++;
		}
	}

	delete image_str;
	delete m;
	cout<<correct_count<<" out of "<<count<<" correct"<<endl;
}

int main(int argc, char **argv) {
	if (argc < 5) {
		cerr<<"Insufficient arguments!"<<endl;
		return 1;
	}

	if (argv[1][0] == 't') {
		train(argv[2], argv[3], argv[4]);
	} else if (argv[1][0] == 'c') {
		classify(argv[2], argv[3], argv[4]);
	}

	return 0;
}
