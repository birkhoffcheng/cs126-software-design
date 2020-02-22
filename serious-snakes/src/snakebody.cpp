#include "snakebody.h"

namespace snakelinkedlist {
    
    std::ostream& operator<<(std::ostream& os, const SnakeBodySegment& list) {
        os << list.data_;
        return os;
    }
    
    bool operator==(const SnakeBodySegment& lhs, const SnakeBodySegment& rhs) {
        return (lhs.data_ == rhs.data_);
    }
    
    bool operator!=(const SnakeBodySegment& lhs, const SnakeBodySegment& rhs) {
        return !(lhs == rhs);
    }
    
} // namespace snakelinkedlist
