#include "model.hpp"
void Model::train(vector<Image> images) {
	size_t num_class[NUM_CLASSES] = {0};
	for (size_t i = 0; i < images.size(); i++) {
		num_class[images[i].classification]++;
	}

	for (size_t i = 0; i < NUM_CLASSES; i++) {
		prob_class[i] = (double)num_class[i] / images.size();
	}

	size_t num_appearances[IMAGE_SIZE][IMAGE_SIZE][NUM_CLASSES][NUM_PROBABILITIES] = {0};
	for (size_t i = 0; i < images.size(); i++) {
		for (size_t row = 0; row < IMAGE_SIZE; row++) {
			for (size_t col = 0; col < IMAGE_SIZE; col++) {
				num_appearances[row][col][images[i].classification][images[i].shade(row, col)]++;
			}
		}
	}

	for (size_t row = 0; row < IMAGE_SIZE; row++) {
		for (size_t col = 0; col < IMAGE_SIZE; col++) {
			for (size_t class_id = 0; class_id < NUM_CLASSES; class_id++) {
				for (size_t probability = 0; probability < NUM_PROBABILITIES; probability++) {
					probs[row][col][class_id][probability] = (K + num_appearances[row][col][class_id][probability]) / (2 * K + num_class[class_id]);
				}
			}
		}
	}
}

int Model::classify(Image image) {
	double possibilities[NUM_CLASSES];
	for (size_t class_id = 0; class_id < NUM_CLASSES; class_id++) {
		possibilities[class_id] = log(prob_class[class_id]);
		for (size_t row = 0; row < IMAGE_SIZE; row++) {
			for (size_t col = 0; col < IMAGE_SIZE; col++) {
				possibilities[class_id] += log(probs[row][col][class_id][image.shade(row, col)]);
			}
		}
	}

	size_t max_id = 0;
	for (size_t class_id = 0; class_id < NUM_CLASSES; class_id++) {
		if (possibilities[class_id] > possibilities[max_id]) {
			max_id = class_id;
		}
	}

	return max_id;
}

void Model::output(char *filename) {
	FILE *fp = fopen(filename, "w");
	if (!fp) {
		fprintf(stderr, "Unable to open file: %s\n", filename);
		exit(1);
	}

	for (size_t row = 0; row < IMAGE_SIZE; row++) {
		for (size_t col = 0; col < IMAGE_SIZE; col++) {
			for (size_t class_id = 0; class_id < NUM_CLASSES; class_id++) {
				for (size_t prob_id = 0; prob_id < NUM_PROBABILITIES; prob_id++) {
					fprintf(fp, "%zx ", probs[row][col][class_id][prob_id]);
				}
			}
		}
	}

	fprintf(fp, "\n");
	for (size_t class_id = 0; class_id < NUM_CLASSES; class_id++) {
		fprintf(fp, "%zx ", prob_class[class_id]);
	}

	fprintf(fp, "\n");
	fclose(fp);
}


void Model::input(char *filename) {
	FILE *fp = fopen(filename, "r");
	if (!fp) {
		fprintf(stderr, "Unable to open file: %s\n", filename);
		exit(1);
	}

	double prob;
	for (size_t row = 0; row < IMAGE_SIZE; row++) {
		for (size_t col = 0; col < IMAGE_SIZE; col++) {
			for (size_t class_id = 0; class_id < NUM_CLASSES; class_id++) {
				for (size_t prob_id = 0; prob_id < NUM_PROBABILITIES; prob_id++) {
					fscanf(fp, "%zx", &prob);
					probs[row][col][class_id][prob_id] = prob;
				}
			}
		}
	}

	for (size_t class_id = 0; class_id < NUM_CLASSES; class_id++) {
		fscanf(fp, "%zx", &prob);
		prob_class[class_id] = prob;
	}

	fclose(fp);
}
