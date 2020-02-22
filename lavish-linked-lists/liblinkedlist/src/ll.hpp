#include <utility>
#include <cassert>
#include "ll.h"

namespace cs126linkedlist {
	template<typename ElementType>
	LinkedList<ElementType>::LinkedList() {
		start = new LinkedListNode;
		end_ = new LinkedListNode;
		start->next = end_;
		start->prev = NULL;
		end_->next = NULL;
		end_->prev = start;
		size_ = 0;
	}

	template<typename ElementType>
	LinkedList<ElementType>::LinkedList(const std::vector<ElementType> &values) {
		start = new LinkedListNode;
		end_ = new LinkedListNode;
		start->next = end_;
		start->prev = NULL;
		end_->next = NULL;
		end_->prev = start;
		size_ = 0;
		size_t length = values.size();
		for (size_t i = 0; i < length; i++) {
			push_back(values[i]);
		}
	}

	// Copy constructor
	template<typename ElementType>
	LinkedList<ElementType>::LinkedList(const LinkedList<ElementType> &source) {
		start = new LinkedListNode;
		end_ = new LinkedListNode;
		start->next = end_;
		start->prev = NULL;
		end_->next = NULL;
		end_->prev = start;
		size_ = 0;
		for (LinkedListNode *current = source.start->next; current != source.end_; current = current->next) {
			push_back(current->data);
		}
	}

	// Move constructor
	template<typename ElementType>
	LinkedList<ElementType>::LinkedList(LinkedList<ElementType> &&source) noexcept {
		start = source.start;
		end_ = source.end_;
		size_ = source.size_;
		source.start = new LinkedListNode;
		source.end_ = new LinkedListNode;
		source.start->next = end_;
		source.start->prev = NULL;
		source.end_->next = NULL;
		source.end_->prev = start;
		source.size_ = 0;
	}

	// Destructor
	template<typename ElementType>
	LinkedList<ElementType>::~LinkedList() {
		clear();
		delete start;
		delete end_;
	}

	// Copy assignment operator
	template<typename ElementType>
	LinkedList<ElementType> &LinkedList<ElementType>::operator=(const LinkedList<ElementType> &source) {
		clear();
		for (LinkedListNode *current = source.start->next; current != source.end_; current = current->next) {
			push_back(current->data);
		}

		return this;
	}

	// Move assignment operator
	template<typename ElementType>
	LinkedList<ElementType> &LinkedList<ElementType>::operator=(LinkedList<ElementType> &&source) noexcept {
		clear();
		LinkedListNode *this_start = start;
		LinkedListNode *this_end = end_;
		start = source.start;
		end_ = source.end_;
		size_ = source.size_;
		source.start = this_start;
		source.end_ = this_end;
		source.size_ = 0;
		return this;
	}

	template<typename ElementType>
	void LinkedList<ElementType>::push_front(ElementType value) {
		start->data = value;
		LinkedListNode *new_node = new LinkedListNode;
		new_node->next = start;
		new_node->prev = NULL;
		start->prev = new_node;
		start = new_node;
		size_++;
	}

	template<typename ElementType>
	void LinkedList<ElementType>::push_back(ElementType value) {
		end_->data = value;
		LinkedListNode *new_node = new LinkedListNode;
		new_node->next = NULL;
		new_node->prev = end_;
		end_->next = new_node;
		end_ = new_node;
		size_++;
	}

	template<typename ElementType>
	ElementType LinkedList<ElementType>::front() const {
		assert(size_ > 0);
		return start->next->data;
	}

	template<typename ElementType>
	ElementType LinkedList<ElementType>::back() const {
		assert(size_ > 0);
		return end_->prev->data;
	}

	template<typename ElementType>
	void LinkedList<ElementType>::pop_front() {
		if (size_ == 0) {
			return;
		}

		start->next->next->prev = start;
		LinkedListNode *tmp = start->next;
		start->next = start->next->next;
		delete tmp;
		size_--;
	}

	template<typename ElementType>
	void LinkedList<ElementType>::pop_back() {
		if (size_ == 0) {
			return;
		}

		end_->prev->prev->next = end_;
		LinkedListNode *tmp = end_->prev;
		end_->prev = end_->prev->prev;
		delete tmp;
		size_--;
	}

	template<typename ElementType>
	size_t LinkedList<ElementType>::size() const {
		return size_;
	}

	template<typename ElementType>
	bool LinkedList<ElementType>::empty() const {
		return size_ == 0;
	}

	template<typename ElementType>
	void LinkedList<ElementType>::clear() {
		while (size_ > 0) {
			pop_front();
		}
	}

	template<typename ElementType>
	std::ostream &operator<<(std::ostream &os, const LinkedList<ElementType> &list) {
		for (auto i = list.begin(); i != list.end(); ++i) {
			os<<*i<<", ";
		}

		return os;
	}

	template<typename ElementType>
	void LinkedList<ElementType>::RemoveOdd() {
		size_t index;
		LinkedListNode *tmp;
		LinkedListNode *current = start->next;
		for (index = 0; index < size_ && current; index++) {
			if (index % 2 != 0) {
				tmp = current;
				if (current->next) {
					current->next->prev = current->prev;
				}

				current->prev->next = current->next;
				current = current->next;
				delete tmp;
			} else {
				current = current->next;
			}
		}

		size_ = (size_ + 1) / 2;
	}

	template<typename ElementType>
	bool LinkedList<ElementType>::operator==(const LinkedList<ElementType> &rhs) const {
		if (start == rhs.start && size_ == rhs.size_) {
			return true;
		} else if (size_ != rhs.size_) {
			return false;
		}
		
		LinkedListNode *lhs_current = start->next;
		LinkedListNode *rhs_current = rhs.start->next;
		while (lhs_current->next && rhs_current->next && lhs_current->data == rhs_current->data) {
			lhs_current = lhs_current->next;
			rhs_current = rhs_current->next;
		}

		return lhs_current->next == rhs_current->next;
	}

	template<typename ElementType>
	bool operator!=(const LinkedList<ElementType> &lhs, const LinkedList<ElementType> &rhs) {
		return !(lhs == rhs);
	}

	template<typename ElementType>
	typename LinkedList<ElementType>::iterator &LinkedList<ElementType>::iterator::operator++() {
		if (current_) {
			current_ = current_->next;
		}
		
		return *this;
	}

	template<typename ElementType>
	ElementType &LinkedList<ElementType>::iterator::operator*() const {
		assert(current_ != nullptr);
		return current_->data;
	}

	template<typename ElementType>
	bool LinkedList<ElementType>::iterator::operator!=(const LinkedList<ElementType>::iterator &other) const {
		return current_ != other.current_;
	}

	template<typename ElementType>
	typename LinkedList<ElementType>::iterator LinkedList<ElementType>::begin() {
		return iterator(start->next);
	}

	template<typename ElementType>
	typename LinkedList<ElementType>::iterator LinkedList<ElementType>::end() {
		return iterator(end_);
	}

	template<typename ElementType>
	typename LinkedList<ElementType>::const_iterator &LinkedList<ElementType>::const_iterator::operator++() {
		if (current_) {
			current_ = current_->next;
		}

		return *this;
	}

	template<typename ElementType>
	const ElementType &LinkedList<ElementType>::const_iterator::operator*() const {
		assert(current_ != nullptr);
		return current_->data;
	}

	template<typename ElementType>
	bool
	LinkedList<ElementType>::const_iterator::operator!=(const LinkedList<ElementType>::const_iterator &other) const {
		return current_ != other.current_;
	}

	template<typename ElementType>
	typename LinkedList<ElementType>::const_iterator LinkedList<ElementType>::begin() const {
		return const_iterator(start->next);
	}

	template<typename ElementType>
	typename LinkedList<ElementType>::const_iterator LinkedList<ElementType>::end() const {
		return const_iterator(end_);
	}
}
