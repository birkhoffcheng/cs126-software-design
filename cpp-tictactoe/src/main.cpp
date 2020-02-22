#define CATCH_CONFIG_MAIN
#include "catch.hpp"

#include "tictactoe.hpp"

// See: https://github.com/catchorg/Catch2/blob/master/docs/tutorial.md
// For documentation on how to write tests with Catch2

TEST_CASE("Incomplete boards have no winner") {
	REQUIRE(EvaluateBoard("O...X.X..") == Evaluation::NoWinner);
	REQUIRE(EvaluateBoard(".........") == Evaluation::NoWinner);
}

TEST_CASE("Invalid input with more than nine characters") {
	REQUIRE(EvaluateBoard(".............") == Evaluation::InvalidInput);
	REQUIRE(EvaluateBoard("AAAAAAAAAAAAAAAAAAAAA") == Evaluation::InvalidInput);
}

TEST_CASE("Invalid input with less than nine characters") {
	REQUIRE(EvaluateBoard("...") == Evaluation::InvalidInput);
	REQUIRE(EvaluateBoard("") == Evaluation::InvalidInput);
}

TEST_CASE("Unreachable state all X or all O") {
	REQUIRE(EvaluateBoard("XXXXXXXXX") == Evaluation::UnreachableState);
	REQUIRE(EvaluateBoard("xxxxxxxxx") == Evaluation::UnreachableState);
	REQUIRE(EvaluateBoard("XXXxxxXXX") == Evaluation::UnreachableState);
	REQUIRE(EvaluateBoard("OOOOOOOOO") == Evaluation::UnreachableState);
	REQUIRE(EvaluateBoard("ooooooooo") == Evaluation::UnreachableState);
	REQUIRE(EvaluateBoard("oooOOOooo") == Evaluation::UnreachableState);
}

TEST_CASE("Unreachable state too many Xs") {
	REQUIRE(EvaluateBoard("XXXX...O.") == Evaluation::UnreachableState);
	REQUIRE(EvaluateBoard("xXxXXooo.") == Evaluation::UnreachableState);
}

TEST_CASE("Unreachable state too many Os") {
	REQUIRE(EvaluateBoard("OoooXxx..") == Evaluation::UnreachableState);
	REQUIRE(EvaluateBoard("OooOOxxxx") == Evaluation::UnreachableState);
}

TEST_CASE("Unreachable state O plays after X wins") {
	REQUIRE(EvaluateBoard("xxxoo.o..") == Evaluation::UnreachableState);
}

TEST_CASE("X horizontal win") {
	REQUIRE(EvaluateBoard("xxxoo....") == Evaluation::Xwins);
	REQUIRE(EvaluateBoard("oo.xxx...") == Evaluation::Xwins);
	REQUIRE(EvaluateBoard("oo....xxx") == Evaluation::Xwins);
}

TEST_CASE("O horizontal win") {
	REQUIRE(EvaluateBoard("xx.ooo..x") == Evaluation::Owins);
	REQUIRE(EvaluateBoard("ooo.xxx..") == Evaluation::Owins);
	REQUIRE(EvaluateBoard("x...xxooo") == Evaluation::Owins);
}

TEST_CASE("X vertical win") {
	REQUIRE(EvaluateBoard("xo.xo.x..") == Evaluation::Xwins);
	REQUIRE(EvaluateBoard("ox.ox..x.") == Evaluation::Xwins);
	REQUIRE(EvaluateBoard("oox..x..x") == Evaluation::Xwins);
}

TEST_CASE("O vertical win") {
	REQUIRE(EvaluateBoard("ox.ox.o.x") == Evaluation::Owins);
	REQUIRE(EvaluateBoard("xoxxo..o.") == Evaluation::Owins);
	REQUIRE(EvaluateBoard("xxo.xo..o") == Evaluation::Owins);
}

TEST_CASE("X diagonal win") {
	REQUIRE(EvaluateBoard("xo.ox...x") == Evaluation::Xwins);
	REQUIRE(EvaluateBoard("oox.x.x..") == Evaluation::Xwins);
}

TEST_CASE("O diagonal win") {
	REQUIRE(EvaluateBoard("ox.xo.x.o") == Evaluation::Owins);
	REQUIRE(EvaluateBoard("x.o.oxo.x") == Evaluation::Owins);
}
