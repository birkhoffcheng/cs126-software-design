#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <unistd.h>
#include "sudoku.hpp"
#define FILE_HEADER "#spf1.0"
/*
 * Write your sudoku program! Do not put all of your code in main.cpp;
 * make new files as necessary.
 *
 * Make sure that the correct .cpp and .h/.hpp files are available to the
 * sudoku and testing executables as necessary.
 * CLion should prompt you to add the right info to the CMakeLists.txt
 * whenever you create new .cpp files.
 */

int main(int argc, char **argv) {
	if (getopt(argc, argv, "f:") == -1) {
		std::cerr<<"Usage: "<<argv[0]<<" -f [file]"<<std::endl;
		return 1;
	}

	char *filename = optarg;
	std::ifstream in(filename);
	if (!in) {
		std::cerr<<filename<<": No such file"<<std::endl;
		return 1;
	}
	
	// Reference: https://stackoverflow.com/questions/7868936/read-file-line-by-line-using-ifstream-in-c
	std::string line;

	if (std::getline(in, line)) {
		if (strcmp(&line[0], FILE_HEADER)) {
			std::cerr<<filename<<": Invalid SPF1.0 file"<<std::endl;
			return 1;
		}
	} else {
		std::cerr<<filename<<": Invalid SPF1.0 file"<<std::endl;
		return 1;
	}

	while (std::getline(in, line)) {
		std::istringstream iss(line);
		Sudoku sudoku;
		iss>>sudoku;
		std::cout<<sudoku<<std::endl;
	}

	return 0;
}
