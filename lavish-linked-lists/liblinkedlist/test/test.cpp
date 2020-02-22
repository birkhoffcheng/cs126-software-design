#include <vector>
#include <sstream>
#include "catch.hpp"
#include "../src/ll.h"

using cs126linkedlist::LinkedList;
using std::vector;
using std::ostringstream;

TEST_CASE("Test empty() and size()") {
	LinkedList<int> *linked_list = new LinkedList<int>();
	REQUIRE(linked_list->empty());
	REQUIRE(linked_list->size() == 0);
	delete linked_list;
}

TEST_CASE("Test push_front() and push_back()") {
	LinkedList<int> *linked_list = new LinkedList<int>();
	linked_list->push_front(1);
	REQUIRE(!linked_list->empty());
	REQUIRE(linked_list->size() == 1);
	REQUIRE(linked_list->front() == 1);
	linked_list->push_back(2);
	REQUIRE(!linked_list->empty());
	REQUIRE(linked_list->size() == 2);
	REQUIRE(linked_list->back() == 2);
	linked_list->push_back(3);
	REQUIRE(!linked_list->empty());
	REQUIRE(linked_list->size() == 3);
	REQUIRE(linked_list->back() == 3);
	linked_list->push_front(0);
	REQUIRE(!linked_list->empty());
	REQUIRE(linked_list->size() == 4);
	REQUIRE(linked_list->front() == 0);
	delete linked_list;
}

TEST_CASE("Test initialization from vector") {
	vector<int> array;
	array.push_back(1);
	array.push_back(2);
	array.push_back(3);
	LinkedList<int> *linked_list = new LinkedList<int>(array);
	REQUIRE(!linked_list->empty());
	REQUIRE(linked_list->size() == 3);
	REQUIRE(linked_list->front() == 1);
	REQUIRE(linked_list->back() == 3);
	linked_list->pop_front();
	REQUIRE(linked_list->front() == 2);
	linked_list->pop_back();
	REQUIRE(linked_list->back() == 2);
	delete linked_list;
}

TEST_CASE("Test copy constructor") {
	LinkedList<int> *linked_list = new LinkedList<int>();
	linked_list->push_front(1);
	linked_list->push_back(2);
	linked_list->push_back(3);
	linked_list->push_front(0);
	LinkedList<int> *copy = new LinkedList<int>(*linked_list);
	REQUIRE(copy->size() == 4);
	REQUIRE(copy->front() == 0);
	REQUIRE(copy->back() == 3);
	REQUIRE(linked_list->size() == 4);
	REQUIRE(linked_list->front() == 0);
	REQUIRE(linked_list->back() == 3);
	delete linked_list;
	delete copy;
}

TEST_CASE("Test copy assignment operator") {
	LinkedList<int> linked_list;
	linked_list.push_front(1);
	linked_list.push_back(2);
	linked_list.push_back(3);
	linked_list.push_front(0);
	LinkedList<int> copy = linked_list;
	REQUIRE(copy.size() == 4);
	REQUIRE(copy.front() == 0);
	REQUIRE(copy.back() == 3);
	REQUIRE(linked_list.size() == 4);
	REQUIRE(linked_list.front() == 0);
	REQUIRE(linked_list.back() == 3);
	REQUIRE(linked_list == copy);
}

TEST_CASE("Test move assignment operator") {
	LinkedList<int> linked_list;
	linked_list.push_front(1);
	linked_list.push_back(2);
	linked_list.push_back(3);
	linked_list.push_front(0);
	LinkedList<int> copy = std::move(linked_list);
	REQUIRE(copy.size() == 4);
	REQUIRE(copy.front() == 0);
	REQUIRE(copy.back() == 3);
	REQUIRE(linked_list.size() == 0);
}

TEST_CASE("Test clear") {
	LinkedList<int> linked_list;
	linked_list.push_front(1);
	linked_list.push_back(2);
	linked_list.push_back(3);
	linked_list.push_front(0);
	linked_list.clear();
	REQUIRE(linked_list.size() == 0);
	REQUIRE(linked_list.empty());
}

TEST_CASE("Test pop") {
	LinkedList<int> linked_list;
	linked_list.push_front(1);
	linked_list.push_back(2);
	linked_list.push_back(3);
	linked_list.push_front(0);
	linked_list.pop_front();
	REQUIRE(linked_list.front() == 1);
	linked_list.pop_back();
	REQUIRE(linked_list.back() == 2);
	linked_list.pop_front();
	REQUIRE(linked_list.front() == 2);
	linked_list.pop_back();
	REQUIRE(linked_list.size() == 0);
	REQUIRE(linked_list.empty());
}

TEST_CASE("Test equal operator") {
	LinkedList<int> linked_list;
	linked_list.push_front(1);
	linked_list.push_back(2);
	linked_list.push_back(3);
	linked_list.push_front(0);
	LinkedList<int> copy = linked_list;
	REQUIRE(linked_list == copy);
	linked_list.clear();
	copy.clear();
	REQUIRE(linked_list == copy);
}

TEST_CASE("Test not equal operator") {
	LinkedList<int> linked_list;
	linked_list.push_front(1);
	linked_list.push_back(2);
	linked_list.push_back(3);
	linked_list.push_front(0);
	LinkedList<int> copy = linked_list;
	copy.pop_back();
	linked_list.pop_front();
	REQUIRE(linked_list != copy);
}

TEST_CASE("Test << operator") {
	LinkedList<int> linked_list;
	linked_list.push_front(1);
	linked_list.push_back(2);
	linked_list.push_back(3);
	linked_list.push_front(0);
	ostringstream oss;
	oss<<linked_list;
	REQUIRE(oss.str() == "0, 1, 2, 3, ");
}

TEST_CASE("Test RemoveOdd function") {
	LinkedList<int> linked_list;
	linked_list.push_back(0);
	linked_list.push_back(1);
	linked_list.push_back(2);
	linked_list.push_back(3);
	linked_list.push_back(4);
	linked_list.RemoveOdd();
	LinkedList<int> expected;
	expected.push_back(0);
	expected.push_back(2);
	expected.push_back(4);
	REQUIRE(linked_list == expected);
}

TEST_CASE("Test move constructor") {
	LinkedList<int> linked_list;
	linked_list.push_back(0);
	linked_list.push_back(1);
	linked_list.push_back(2);
	linked_list.push_back(3);
	linked_list.push_back(4);
	LinkedList<int> backup(linked_list);
	LinkedList<int> move(std::move(linked_list));
	REQUIRE(backup == move);
	REQUIRE(linked_list.empty());
}
