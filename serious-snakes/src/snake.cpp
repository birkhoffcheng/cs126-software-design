#include "snake.h"

using namespace snakelinkedlist;

const float Snake::kbody_size_modifier_ = 0.02;

SnakeBody* Snake::getHead() const {
	return head_;
};

ofVec2f Snake::getBodySize() const {
	return body_size_;
};

Snake::Snake() {
	int width = ofGetWindowWidth();
	int height = ofGetWindowHeight();
	screen_dims_.set(width, height);

	float body_d = kbody_size_modifier_ * width;
	body_size_.set(body_d, body_d);

	current_direction_ = RIGHT; // Snake starts out moving right

	head_ = new SnakeBody();
	head_->position.set(0, 2 * body_d);
	head_->color = ofColor(0, 100, 0);
	head_->next = nullptr;
}

Snake& Snake::operator=(const Snake& other) {
	screen_dims_ = other.screen_dims_;
	body_size_ = other.body_size_;

	snake_body.clear();
	current_direction_ = RIGHT;

	// Copy linked list over by creating new links
	head_ = new SnakeBody();

	SnakeBody* curr = head_;
	SnakeBody* other_body = other.head_;
	while (other_body) {
		curr->position = other_body->position;
		curr->color = other_body->color;
		if (other_body->next)
		{
			curr->next = new SnakeBody();
		}

		curr = curr->next;
		other_body = other_body->next;
	}

	return *this;
}

Snake::~Snake() {
	SnakeBody* curr = head_;
	while (curr) {
		SnakeBody* next = curr->next;
		delete curr;
		curr = next;
	}
}

void Snake::update() { 
	// Move the rest of the snake up one position
	SnakeBody* prev = head_;
	SnakeBody* curr = head_->next;
	ofVec2f curr_old_pos = head_->position;
	while (curr) {
		ofVec2f old_pos_temp = curr->position;
		curr->position = curr_old_pos;
		curr_old_pos = old_pos_temp;

		prev = curr;
		curr = curr->next;
	}

	// Move the head one body square in the direction the snake is moving
	switch (current_direction_) {
		case UP:
			head_->position.set(head_->position.x, head_->position.y - body_size_.y);
			break;
		case DOWN:
			head_->position.set(head_->position.x, head_->position.y + body_size_.y);
			break;
		case LEFT:
			head_->position.set(head_->position.x - body_size_.x, head_->position.y);
			break;
		case RIGHT:
			head_->position.set(head_->position.x + body_size_.x, head_->position.y);
			break;
	}
}

bool Snake::isDead() const {
	// Snake is dead if the head is off screen
	if (head_->position.x < 0
		|| head_->position.y < 0
		|| head_->position.x > screen_dims_.x - body_size_.x
		|| head_->position.y > screen_dims_.y - body_size_.y) {
		return true;
	}

	// If the snake's head is intersecting with any piece of its body it is dead
	ofRectangle head_rect(head_->position.x, head_->position.y, body_size_.x, body_size_.y);
	for (SnakeBody* curr = head_->next; curr; curr = curr->next) {
		ofRectangle body_rect(curr->position.x, curr->position.y, body_size_.x, body_size_.y);
		if (head_rect.intersects(body_rect)) {
			return true;
		}
	}
	
	// Snake is not dead yet :D
	return false;
}

void Snake::eatFood(ofColor newBodyColor) {
	// Add a new link to our naive linked list
	snake_body.push_back(1);

	// Set up our new body piece
	SnakeBody* new_body = new SnakeBody();
	new_body->color = newBodyColor;
	new_body->next = nullptr;

	// find the current tail of the snake
	SnakeBody* last_body = head_;
	while (last_body->next) {
		last_body = last_body->next;
	}

	// The current position of the new tail is one unit in the opposite direction of the snakes current movement
	switch (current_direction_) {
		case UP:
			new_body->position.set(last_body->position.x, last_body->position.y + body_size_.y);
			break;
		case DOWN:
			new_body->position.set(last_body->position.x, last_body->position.y - body_size_.y);
			break;
		case LEFT:
			new_body->position.set(last_body->position.x + body_size_.x, last_body->position.y);
			break;
		case RIGHT:
			new_body->position.set(last_body->position.x - body_size_.x, last_body->position.y);
			break;
	}

	// Attach a new tail to the snake
	last_body->next = new_body;
}

// Resize the snake based on the ratio of old to new position
void Snake::resize(int w, int h) {
	int width = ofGetWindowWidth();
	int height = ofGetWindowHeight();

	for (SnakeBody* curr = head_; curr != NULL; curr = curr->next) {
		float new_x = ((curr->position.x / screen_dims_.x) * w);
		float new_y = ((curr->position.y / screen_dims_.y) * h);
		curr->position.set(new_x, new_y);
	}
	screen_dims_.set(width, height);

	float body_d = kbody_size_modifier_ * width;
	body_size_.set(body_d, body_d);
}

int Snake::getFoodEaten() const {
	return snake_body.size();
}

SnakeDirection Snake::getDirection() const {
	return current_direction_;
}

void Snake::setDirection(SnakeDirection newDirection) {
	current_direction_ = newDirection;
}
