#ifndef LL_H
#define LL_H

#include <iostream>
#include <vector>

namespace cs126linkedlist {
/*
 *  Declare any struct, class, or enum types you need to use here
 */

	// Template linked list class
	template<typename ElementType>
	class LinkedList {
	/*
	 *  Declare any struct, class, or enum types you need to use here
	 */
	private:
		struct LinkedListNode {
			ElementType data;
			LinkedListNode *next;
			LinkedListNode *prev;
		};
		LinkedListNode *start;
		LinkedListNode *end_;
		size_t size_;

	public:
		LinkedList();												   // Default constructor
		explicit LinkedList(const std::vector<ElementType> &values);	// Initilize from vector

		// Big 5
		LinkedList(const LinkedList &source);										   // Copy constructor
		LinkedList(LinkedList &&source) noexcept;									   // Move constructor
		~LinkedList();																  // Destructor
		LinkedList<ElementType> &operator=(const LinkedList<ElementType> &source);	  // Copy assignment operator
		LinkedList<ElementType> &operator=(LinkedList<ElementType> &&source) noexcept;  // Move assignment operator

		void push_front(ElementType value);		 // Push value on front
		void push_back(ElementType value);		  // Push value on back
		ElementType front() const;				  // Access the front value
		ElementType back() const;				   // Access the back valueW
		void pop_front();						   // remove front element
		void pop_back();							// remove back element
		size_t size() const;						   // return number of elements
		bool empty() const;						 // check if empty
		void clear();							   // clear the contents
		void RemoveOdd();						   // remove the odd elements from the list 0 indexed
		bool operator==(const LinkedList<ElementType> &rhs) const;

		// iterator
		class iterator : std::iterator<std::forward_iterator_tag, ElementType> {
			LinkedListNode *current_;
		public:
			iterator() : current_(nullptr) {};

			iterator(LinkedListNode *ptr) { current_ = ptr; };

			iterator &operator++();

			ElementType &operator*() const;

			bool operator!=(const iterator &other) const;
		};

		iterator begin();

		iterator end();

		// const_iterator
		class const_iterator : std::iterator<std::forward_iterator_tag, ElementType> {
			const LinkedListNode *current_;
		public:
			const_iterator() : current_(nullptr) {};

			const_iterator(LinkedListNode *ptr) { current_ = ptr; };

			const_iterator &operator++();

			const ElementType &operator*() const;

			bool operator!=(const const_iterator &other) const;
		};

		const_iterator begin() const;

		const_iterator end() const;
	};

	template<typename ElementType>
	std::ostream &operator<<(std::ostream &os, const LinkedList<ElementType> &list);


} // namespace cs126linkedlist

// Needed for template instantiation
#include "ll.hpp"

#endif //LL_H
