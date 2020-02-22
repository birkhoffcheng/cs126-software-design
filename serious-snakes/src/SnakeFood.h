#pragma once
#include <random>
#include "ofMain.h"

namespace snakelinkedlist {

class SnakeFood {
private:
	ofVec2f window_dims_; // The object must be aware of current window dimensions for resizing things appropriately
	std::mt19937 generator_; // Generator used for pseudorandom number generation for food color and position
	std::uniform_int_distribution<> dist_x_; // Generates valid x positions in current window dimension
	std::uniform_int_distribution<> dist_y_; // Generates valid y positions in current window dimension
	std::uniform_int_distribution<> dist_color_; // Generates valid rgb values

	static const float kfood_modifier_; // What proportion of the window width the food square should be in size
	ofRectangle food_rect_; // The rectangle which represents the actual food pellet on the game board
	ofColor color_; // The color of the food rectangle

public:
	SnakeFood(); // Default constructor, sets up generator devices and rarndomly places food at a valid location
	void rebase(); // Called once the snake has successfully eaten food, replaces the foods color and location
	void resize(int w, int h); // Called by application resize, resizes food rect to new window dimensions
	ofRectangle getFoodRect(); // Gets the rectangle that represents the food object
	ofColor getColor(); // Gets the color of the current food object
};
} // namespace snakelinkedlist