#include "ll.h"
#include "ofMain.h"
#include "snakebody.h"
#pragma once

namespace snakelinkedlist {

// Enum that represents all possible directions that the snake can be moving
typedef enum {
	UP = 0,
	DOWN,
	RIGHT,
	LEFT
} SnakeDirection;

/* 
Node that represents a segment of the snake body. 
This is due to the limitations of ll
1. We can only place a specific type of node in the ll
2. ll provides no convenient way to update the data in a list
    We could expose the internal structure of the list but 
    the underlying data and implementation of a data structure
    is meant to be private and exposing it is a critical sin.
    A linked list that had an iterator interface would remedy this issue
*/
struct SnakeBody { // TODO replace with templated ll
	SnakeBody* next;
	ofVec2f position;
	ofColor color;
};

class Snake {
private:
	SnakeDirection current_direction_; // The current direction of the snake
	ofVec2f screen_dims_; // The current screen dimensions (needed to calculate values on resize()
	static const float kbody_size_modifier_; // The proportion of the screen width a body square is
	ofVec2f body_size_; // the size of a snake body piece based on kbody_size_modifier_
	SnakeBody* head_; // The head of the linked list, we are forced to do this because snakebody 
	                  // does not support all of the data we must keep track of
    
	cs126linkedlist::LinkedList<int> snake_body; // our linked list implementation, we might as well use it to keep track of score
	                       // But ideally it should store all of the information of our snake once it is improved

public:
	SnakeBody* getHead() const; //TODO remove this once iterators are added (publicly exposes snakes internal structure :'()
	Snake(); // Default constructor, initializes and places length 1 snake
	Snake& operator=(const Snake& other); // copy constructor
	~Snake(); // destructor
	ofVec2f getBodySize() const; // gets the size of a body segment, used for rendering
	bool isDead() const; // Determines if the current state of the snake is dead
	void update(); // updates the snake one body square in the current direction
	void eatFood(ofColor new_body_color); // the snake has eaten a food while travelling in a certain direction.
	void resize(int w, int h); // Resizes the snake to a new width and height
	int getFoodEaten() const; // Gets the number of food items the snake has eaten
	SnakeDirection getDirection() const; // Gets the Snake's current direction
	void setDirection(SnakeDirection new_direction); // Sets the Snake's direction
};
} // namespace snakelinkedlist
