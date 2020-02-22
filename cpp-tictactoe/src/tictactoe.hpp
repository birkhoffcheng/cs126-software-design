#pragma once

#include <string>

enum class Evaluation {
    InvalidInput,
    NoWinner,
    Xwins,
    Owins,
    UnreachableState
};

Evaluation EvaluateBoard(const std::string &board_state);
std::string toLower(std::string str);
uint countAppearances(std::string str, char ch);
bool reachable(std::string str);
char verticalWins(std::string str);
char horizontalWins(std::string str);
char diagonalWins(std::string str);
