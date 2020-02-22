#include <cstdio>
#include <cstdint>
#include <cstdlib>
#include <cassert>
#include <iostream>
#ifndef BOARD_H
#define BOARD_H

constexpr uint8_t BOARD_SIZE_LIMIT = 32;

class Board {
private:
	uint32_t board;
	uint32_t solution;
	uint8_t width;
	uint8_t height;

public:
	Board();
	bool get_bit(uint8_t index);
	bool get_solution_bit(uint8_t index);
	void flip(uint8_t index);
	void press_button(uint8_t bit_index);
	bool solve();
	void reset();
	bool solved();
	void blank();
};

#endif
