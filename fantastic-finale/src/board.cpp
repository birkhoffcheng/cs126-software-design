#include "board.hpp"

Board::Board() {
	board = 0;
	width = 5;
	height = 5;
}

bool Board::get_bit(uint8_t index) {
	#ifdef DEBUG
	assert(index < BOARD_SIZE_LIMIT);
	#endif
	return (board >> index) & 1 == 1;
}

bool Board::get_solution_bit(uint8_t index) {
	#ifdef DEBUG
	assert(index < BOARD_SIZE_LIMIT);
	#endif
	return (solution >> index) & 1 == 1;
}

void Board::flip(uint8_t index) {
	#ifdef DEBUG
	assert(index < BOARD_SIZE_LIMIT);
	#endif
	uint32_t mask = 1 << index;
	board ^= mask;
}

void Board::press_button(uint8_t bit_index) {
	flip(bit_index);
	uint8_t row = bit_index / width;
	uint8_t col = bit_index % width;
	if (row != 0)
		flip(bit_index - width);
	if (row != height - 1)
		flip(bit_index + width);
	if (col != 0)
		flip(bit_index - 1);
	if (col != width - 1)
		flip(bit_index + 1);
}

bool Board::solve() {
	uint32_t board_copy = board;
	uint32_t solution_max = 1 << (width * height + 1);
	for (solution = 0; solution < solution_max; ++solution) {
		board = board_copy;
		for (int i = 0; i < width * height; ++i) {
			if (get_solution_bit(i)) {
				press_button(i);
			}
		}

		if (board == 0) {
			board = board_copy;
			return true;
		}
	}

	board = board_copy;
	solution = 0;
	return false;
}

void Board::reset() {
	uint32_t mask = UINT32_MAX;
	mask >>= BOARD_SIZE_LIMIT - width * height;
	uint32_t random;
	FILE *fp = fopen("/dev/urandom", "rb");
	fread(&random, sizeof(random), 1, fp);
	fclose(fp);
	solution = random & mask;
	for (int i = 0; i < width * height; ++i) {
		if (get_solution_bit(i)) {
			press_button(i);
		}
	}
}

bool Board::solved() {
	return board == 0;
}

void Board::blank() {
	board = 0;
	solution = 0;
}
