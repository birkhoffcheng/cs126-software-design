#pragma once
#include <cstring>
#include <cstdlib>
#include <cmath>
/*
 * We've given you a starter class to represent an image.
 * You are totally allowed to delete, change, move, rename, etc. this class
 * however you like! In fact, we encourage it! It only exists as a starting
 * point of reference.
 *
 * You can delete this comment once you're done with it.
 */
constexpr size_t NUM_PROBABILITIES = 3;
constexpr size_t IMAGE_SIZE = 28;

class Image {
public:
	char pixels[IMAGE_SIZE][IMAGE_SIZE];
	int classification;
	void init(char *input_string, int c);
	size_t shade(size_t row, size_t col);
};
