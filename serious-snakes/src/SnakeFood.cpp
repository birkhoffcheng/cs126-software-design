#include <stdlib.h>
#include "SnakeFood.h"
using namespace snakelinkedlist;

const float SnakeFood::kfood_modifier_ = 0.02;

SnakeFood::SnakeFood() {
	int window_width = ofGetWindowWidth();
	int window_height = ofGetWindowHeight();
	window_dims_.set(window_width, window_height);
	float size_d = kfood_modifier_ * window_width;
	food_rect_.setSize(size_d, size_d);

	generator_ = std::mt19937(rand());
	dist_x_ = std::uniform_int_distribution<>(0, window_width - size_d);
	dist_y_ = std::uniform_int_distribution<>(0, window_height - size_d);
	dist_color_ = std::uniform_int_distribution<>(0, 255);
	rebase();
}

void SnakeFood::resize(int w, int h) {
	float size_d = kfood_modifier_ * w;
	food_rect_.setSize(size_d, size_d);

	float new_x = ((food_rect_.getX() / window_dims_.x) * w);
	float new_y = ((food_rect_.getY() / window_dims_.y) * h);
	food_rect_.setPosition(new_x, new_y);

	dist_x_ = std::uniform_int_distribution<>(0, w - size_d);
	dist_y_ = std::uniform_int_distribution<>(0, h - size_d);
	window_dims_.set(w, h);
}

void SnakeFood::rebase() {
	auto x = dist_x_(generator_);
	auto y = dist_y_(generator_);
	food_rect_.setPosition(x, y);

	color_.r = dist_color_(generator_);
	color_.g = dist_color_(generator_);
	color_.b = dist_color_(generator_);
}

ofRectangle SnakeFood::getFoodRect() {
	return food_rect_;
}

ofColor SnakeFood::getColor() {
	return color_;
}