#include <cstdlib>
#include <cstring>
#include <iostream>
#include <utility>
#ifndef SUDOKU
#define SUDOKU
#define BOARD_SIZE 9
#define BOARD_LENGTH 81
#define ASCII_TO_INT_OFFSET 0x30
#define LINE_SEPARATOR "======================================="
#define BOX_SIZE 3
#define BLANK 0
class Sudoku {
private:
	int *board;

public:
	Sudoku();
	~Sudoku();
	int digit_at(const int row, const int col);
	void set_digit_at(const int row, const int col, const int number);
	void setup(std::string initial_string);
	bool solve();
	friend std::istream &operator>>(std::istream &in, Sudoku &s);
	friend std::ostream &operator<<(std::ostream &out, Sudoku &s);
	bool used_in_col(const int col, const int number);
	bool used_in_row(const int row, const int number);
	bool used_in_box(const int row, const int col, const int number);
	bool is_safe(const int row, const int col, const int number);
	std::pair<int, int> get_blank_location();
};
#endif
