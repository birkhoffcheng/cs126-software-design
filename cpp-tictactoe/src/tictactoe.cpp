#include "tictactoe.hpp"
#define STRING_LENGTH 9
#define BOARD_SIZE 3
#define TO_LOWER_OFFSET 0x20

Evaluation EvaluateBoard(const std::string &board_state) {
	if (board_state.size() != STRING_LENGTH) {
		return Evaluation::InvalidInput;
	}

	std::string board = toLower(board_state);
	if (!reachable(board)) {
		return Evaluation::UnreachableState;
	}

	char winner = verticalWins(board);
	if (winner == 'u') {
		return Evaluation::UnreachableState;
	} else if (winner == 'x') {
		return Evaluation::Xwins;
	} else if (winner == 'o') {
		return Evaluation::Owins;
	}

	winner = horizontalWins(board);
	if (winner == 'u') {
		return Evaluation::UnreachableState;
	} else if (winner == 'x') {
		return Evaluation::Xwins;
	} else if (winner == 'o') {
		return Evaluation::Owins;
	}

	winner = diagonalWins(board);
	if (winner == 'x') {
		return Evaluation::Xwins;
	} else if (winner == 'o') {
		return Evaluation::Owins;
	}

	return Evaluation::NoWinner;
}

std::string toLower(std::string str) {
	std::string lower;
	size_t length = str.size();
	for (size_t i = 0; i < length; i++) {
		char ch = str[i];
		if ('A' <= ch && ch <= 'Z') {
			ch += TO_LOWER_OFFSET;
		}

		lower += ch;
	}

	return lower;
}

uint countAppearances(std::string str, char ch) {
	uint count = 0;
	for (uint i = 0; i < STRING_LENGTH; i++) {
		if (str[i] == ch) {
			count++;
		}
	}
	return count;
}

bool reachable(std::string str) {
	uint numX = countAppearances(str, 'x');
	uint numO = countAppearances(str, 'o');
	return numX == numO || numX - numO == 1;
}

char verticalWins(std::string str) {
	bool xWins = false;
	bool oWins = false;
	for (uint col = 0; col < BOARD_SIZE; col++) {
		if (str[col] == str[col + BOARD_SIZE] && str[col] == str[col + BOARD_SIZE * 2]) {
			switch (str[col]) {
				case 'x':
					xWins = true;
					break;
				case 'o':
					oWins = true;
					break;
			}
		}
	}

	uint numX = countAppearances(str, 'x');
	uint numO = countAppearances(str, 'o');
	if ((xWins && oWins) || (xWins && numX <= numO) || (oWins && numX > numO)) {
		return 'u';
	} else if (xWins) {
		return 'x';
	} else if (oWins) {
		return 'o';
	} else {
		return '\0';
	}
}

char horizontalWins(std::string str) {
	bool xWins = false;
	bool oWins = false;
	for (uint row = 0; row < BOARD_SIZE; row++) {
		if (str[row * BOARD_SIZE] == str[row * BOARD_SIZE + 1] && str[row * BOARD_SIZE] == str[row * BOARD_SIZE + 2]) {
			switch (str[row * BOARD_SIZE]) {
				case 'x':
					xWins = true;
					break;
				case 'o':
					oWins = true;
					break;
			}
		}
	}

	uint numX = countAppearances(str, 'x');
	uint numO = countAppearances(str, 'o');
	if ((xWins && oWins) || (xWins && numX <= numO) || (oWins && numX > numO)) {
		return 'u';
	} else if (xWins) {
		return 'x';
	} else if (oWins) {
		return 'o';
	} else {
		return '\0';
	}
}

char diagonalWins(std::string str) {
	if (str[0] == str[BOARD_SIZE + 1] && str[0] == str[(BOARD_SIZE + 1) * 2]) {
		switch (str[0]) {
			case 'x':
				return 'x';
			case 'o':
				return 'o';
		}
	} else if (str[BOARD_SIZE - 1] == str[BOARD_SIZE + 1] && str[BOARD_SIZE - 1] == str[BOARD_SIZE - 1 + (BOARD_SIZE - 1) * 2]) {
		switch (str[BOARD_SIZE - 1]) {
			case 'x':
				return 'x';
			case 'o':
				return 'o';
		}
	}
	
	return '\0';
}
