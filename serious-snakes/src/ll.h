#ifndef LL_H
#define LL_H

#include <iostream>
#include <vector>

namespace cs126linkedlist {
	template<typename ElementType>
	class LinkedList {
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
		LinkedList();
		explicit LinkedList(const std::vector<ElementType> &values);

		LinkedList(const LinkedList &source);
		LinkedList(LinkedList &&source) noexcept;
		~LinkedList();
		LinkedList<ElementType> &operator=(const LinkedList<ElementType> &source);
		LinkedList<ElementType> &operator=(LinkedList<ElementType> &&source) noexcept;

		void push_front(ElementType value);
		void push_back(ElementType value);
		ElementType front() const;
		ElementType back() const;
		void pop_front();
		void pop_back();
		size_t size() const;
		bool empty() const;
		void clear();
		void RemoveOdd();
		bool operator==(const LinkedList<ElementType> &rhs) const;

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
}

#include "ll.hpp"
#endif
