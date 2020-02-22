#define CATCH_CONFIG_MAIN
#define CATCH_CONFIG_FAST_COMPILE
#include "catch.hpp"
#include "../src/board.hpp"

TEST_CASE("Test board creation and flipping bits") {
	Board board;
	board.flip(0);
	REQUIRE(board.get_bit(0));
	board.flip(0);
	REQUIRE_FALSE(board.get_bit(0));
	board.flip(2);
	board.flip(3);
	board.flip(15);
	board.flip(23);
	REQUIRE(board.get_bit(2));
	REQUIRE(board.get_bit(3));
	REQUIRE(board.get_bit(15));
	REQUIRE(board.get_bit(23));
	REQUIRE_FALSE(board.get_bit(8));
	REQUIRE_FALSE(board.get_bit(10));
	REQUIRE_FALSE(board.get_bit(17));
	REQUIRE_FALSE(board.get_bit(24));
}

TEST_CASE("Test press_button") {
	Board board;
	board.press_button(12);
	REQUIRE(board.get_bit(7));
	REQUIRE(board.get_bit(11));
	REQUIRE(board.get_bit(12));
	REQUIRE(board.get_bit(13));
	REQUIRE(board.get_bit(17));
}

TEST_CASE("Test solved") {
	Board board;
	REQUIRE(board.solved());
}

TEST_CASE("Test solve and get_solution_bit") {
	Board board;
	board.press_button(3);
	board.press_button(13);
	board.press_button(18);
	board.press_button(24);
	REQUIRE(board.solve());
	for (int i = 0; i < 25; ++i) {
		if (board.get_solution_bit(i)) {
			board.press_button(i);
		}
	}

	REQUIRE(board.solved());
}

TEST_CASE("Test blank") {
	Board board;
	board.reset();
	REQUIRE_FALSE(board.solved());
	board.blank();
	REQUIRE(board.solved());
}
