#include "image.hpp"
void Image::init(char *input_string, int c) {
	classification = c;
	int row = 0;
	for (row = 0; row < IMAGE_SIZE; row++) {
		memcpy(pixels[row], input_string + (IMAGE_SIZE + 1) * row, IMAGE_SIZE);
	}
}

size_t Image::shade(size_t row, size_t col) {
	if (row >= IMAGE_SIZE || col >= IMAGE_SIZE) {
		return 0;
	}

	if (pixels[row][col] == '#') {
		return 2;
	} else if (pixels[row][col] == '+') {
		return 1;
	} else {
		return 0;
	}
}
