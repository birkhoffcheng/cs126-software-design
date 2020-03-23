#include "sudoku.hpp"

Sudoku::Sudoku() {}

int Sudoku::digit_at(const int row, const int col) {
	if (row >= BOARD_SIZE || col >= BOARD_SIZE) {
		std::cerr<<"Invalid location"<<std::endl;
		return -1;
	}

	return board[row * BOARD_SIZE + col];
}

void Sudoku::set_digit_at(const int row, const int col, const int number) {
	if (row >= BOARD_SIZE || col >= BOARD_SIZE) {
		std::cerr<<"Invalid location"<<std::endl;
		return;
	}

	board[row * BOARD_SIZE + col] = number;
}

void Sudoku::setup(std::string initial_string) {
	for (int i = 0; i < BOARD_LENGTH; i++) {
		if (initial_string[i] == '_') {
			board[i] = BLANK;
		} else if ('1' <= initial_string[i] && initial_string[i] <= '9') {
			board[i] = initial_string[i] - ASCII_TO_INT_OFFSET;
		} else {
			std::cerr<<"Invalid character!"<<std::endl;
			exit(1);
		}
	}
}

bool Sudoku::solve() {
	if (get_blank_location() == std::make_pair(BOARD_SIZE, BOARD_SIZE)) {
		return true;
	}

	std::pair<int, int> blank_location = get_blank_location();
	int row = blank_location.first;
	int col = blank_location.second;

	for (int number = 1; number <= BOARD_SIZE; number++) {
		if (is_safe(row, col, number)) {
			set_digit_at(row, col, number);
		} else {
			continue;
		}

		if (solve()) {
			return true;
		}

		set_digit_at(row, col, BLANK);
	}

	return false;
}

std::istream &operator>>(std::istream &in, Sudoku &s) {
	std::string buffer;
	in>>buffer;
	s.setup(buffer);
	return in;
}

std::ostream &operator<<(std::ostream &out, Sudoku &s) {
	if (!s.solve()) {
		out<<"Unsolvable"<<std::endl;
		return out;
	}

	for (int row = 0; row < BOARD_SIZE; row++) {
		out<<LINE_SEPARATOR<<std::endl;
		for (int col = 0; col < BOARD_SIZE; col++) {
			out<<" | ";
			int digit = s.digit_at(row, col);
			if (digit == BLANK) {
				out<<' ';
			} else {
				out<<digit;
			}
		}
		
		out<<" |"<<std::endl;
	}

	out<<LINE_SEPARATOR<<std::endl;
	return out;
}

// Depth first search, idea from https://www.geeksforgeeks.org/sudoku-backtracking-7/
// and https://medium.com/@george.seif94/solving-sudoku-using-a-simple-search-algorithm-3ac44857fee8
bool Sudoku::used_in_col(const int col, const int number) {
	for (int row = 0; row < BOARD_SIZE; row++) {
		if (digit_at(row, col) == number) {
			return true;
		}
	}

	return false;
}

bool Sudoku::used_in_row(const int row, const int number) {
	for (int col = 0; col < BOARD_SIZE; col++) {
		if (digit_at(row, col) == number) {
			return true;
		}
	}

	return false;
}

bool Sudoku::used_in_box(const int row, const int col, const int number) {
	int box_start_row = row - row % BOX_SIZE;
	int box_start_col = col - col % BOX_SIZE;
	for (int current_row = box_start_row; current_row < box_start_row + BOX_SIZE; current_row++) {
		for (int current_col = box_start_col; current_col < box_start_col + BOX_SIZE; current_col++) {
			if (digit_at(current_row, current_col) == number) {
				return true;
			}
		}
	}

	return false;
}

bool Sudoku::is_safe(const int row, const int col, const int number) {
	return !used_in_row(row, number) && !used_in_col(col, number) && !used_in_box(row, col, number);
}

std::pair<int, int> Sudoku::get_blank_location() {
	for (int i = 0; i < BOARD_LENGTH; i++) {
		if (board[i] == BLANK) {
			return std::make_pair(i / BOARD_SIZE, i % BOARD_SIZE);
		}
	}

	return std::make_pair(BOARD_SIZE, BOARD_SIZE);
}
