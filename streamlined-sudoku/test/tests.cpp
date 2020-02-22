#include <cstring>
#include <cstdlib>
#include "sudoku.hpp"
#include "catch.hpp"

TEST_CASE("Test parsing spf files") {
	REQUIRE(system("../src/sudoku -f empty.spf") != 0);
	REQUIRE(system("../src/sudoku -f invalid_header_1.spf") != 0);
	REQUIRE(system("../src/sudoku -f invalid_header_2.spf") != 0);
	REQUIRE(system("../src/sudoku -f invalid_header_3.spf") != 0);
	REQUIRE(system("../src/sudoku -f example.spf") == 0);
	REQUIRE(system("../src/sudoku -f blank.spf") == 0);
}

TEST_CASE("Test digit at") {
	Sudoku s;
	s.setup("85___24__72______9__4_________1_7__23_5___9___4___________8__7__17__________36_4_");
	REQUIRE(s.digit_at(0, 0) == 8);
	REQUIRE(s.digit_at(1, 1) == 2);
	REQUIRE(s.digit_at(2, 8) == 0);
}

TEST_CASE("Test used in column") {
	Sudoku s;
	s.setup("85___24__72______9__4_________1_7__23_5___9___4___________8__7__17__________36_4_");
	REQUIRE(s.used_in_col(1, 2));
	REQUIRE(s.used_in_col(5, 7));
	REQUIRE(!s.used_in_col(4, 4));
	REQUIRE(!s.used_in_col(8, 8));
}

TEST_CASE("Test used in row") {
	Sudoku s;
	s.setup("85___24__72______9__4_________1_7__23_5___9___4___________8__7__17__________36_4_");
	REQUIRE(s.used_in_row(1, 2));
	REQUIRE(s.used_in_row(7, 7));
	REQUIRE(!s.used_in_row(5, 5));
	REQUIRE(!s.used_in_row(6, 6));
}

TEST_CASE("Test used in box") {
	Sudoku s;
	s.setup("85___24__72______9__4_________1_7__23_5___9___4___________8__7__17__________36_4_");
	REQUIRE(s.used_in_box(4, 4, 1));
	REQUIRE(s.used_in_box(7, 7, 7));
	REQUIRE(!s.used_in_box(5, 8, 1));
	REQUIRE(!s.used_in_box(6, 0, 3));
}

TEST_CASE("Test is safe") {
	Sudoku s;
	s.setup("85___24__72______9__4_________1_7__23_5___9___4___________8__7__17__________36_4_");
	REQUIRE(s.is_safe(0, 3, 3));
	REQUIRE(s.is_safe(8, 3, 2));
	REQUIRE(!s.is_safe(0, 2, 2));
	REQUIRE(!s.is_safe(4, 1, 5));
}

TEST_CASE("Test solve sudoku") {
	Sudoku s;
	s.setup("85___24__72______9__4_________1_7__23_5___9___4___________8__7__17__________36_4_");
	REQUIRE(s.solve());
	s.setup("___8_5____3__6___7_9___38___4795_3______71_9____2__5__1____248___9____5______6___");
	REQUIRE(s.solve());
	s.setup("5168497323_76_5___8_97___65135_6_9_7472591__696837__5_253186_746842_75__791_5_6_8");
	REQUIRE(!s.solve());
}
