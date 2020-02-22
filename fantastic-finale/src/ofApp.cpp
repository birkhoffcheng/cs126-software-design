#include "ofApp.h"

//--------------------------------------------------------------
void ofApp::setup(){
	ofSetWindowShape(500, 600);
	board.reset();
	reset_button.set(20, 525, 100, 50);
	show_solution_button.set(140, 525, 100, 50);
	solving_mode_button.set(260, 525, 100, 50);
	solve_button.set(380, 525, 100, 50);
	show_solution = false;
	solving = false;
	not_solved = false;
	winning.load("airhorn.mp3");
	flip.load("flip.mp3");
}

//--------------------------------------------------------------
void ofApp::update(){

}

//--------------------------------------------------------------
void ofApp::draw(){
	int rect_start_x;
	int rect_start_y;
	uint8_t bit_index;
	if (!solving) {
		rect_start_x = 10;
		rect_start_y = 10;
		bit_index = 0;
		for (int i = 0; i < 5; ++i) {
			rect_start_x = 10;
			for (int j = 0; j < 5; ++j) {
				if (board.get_bit(bit_index)) {
					ofSetColor(255, 255, 255);
				} else {
					ofSetColor(127, 127, 127);
				}

				++bit_index;
				ofDrawRectangle(rect_start_x, rect_start_y, 80, 80);
				rect_start_x += 100;
			}

			rect_start_y += 100;
		}

		if (show_solution) {
			ofSetColor(255, 0, 0);
			rect_start_y = 50;
			bit_index = 0;
			for (int i = 0; i < 5; ++i) {
				rect_start_x = 50;
				for (int j = 0; j < 5; ++j) {
					if (board.get_solution_bit(bit_index)) {
						ofDrawCircle(rect_start_x, rect_start_y, 10, 10);
					}

					++bit_index;
					rect_start_x += 100;
				}

				rect_start_y += 100;
			}
		}

		if (board.solved()) {
			ofSetColor(0, 200, 200);
			ofDrawRectangle(0, 200, 500, 200);
			ofSetColor(255, 255, 255);
			ofDrawBitmapString("CONGRATULATIONS!", 100, 250);
		} else {
			winning.play();
		}
	} else {
		rect_start_x = 10;
		rect_start_y = 10;
		bit_index = 0;
		for (int i = 0; i < 5; ++i) {
			rect_start_x = 10;
			for (int j = 0; j < 5; ++j) {
				if (board.get_bit(bit_index)) {
					ofSetColor(255, 255, 255);
				} else {
					ofSetColor(127, 127, 127);
				}

				++bit_index;
				ofDrawRectangle(rect_start_x, rect_start_y, 80, 80);
				rect_start_x += 100;
			}

			rect_start_y += 100;
		}

		if (show_solution) {
			ofSetColor(255, 0, 0);
			rect_start_y = 50;
			bit_index = 0;
			for (int i = 0; i < 5; ++i) {
				rect_start_x = 50;
				for (int j = 0; j < 5; ++j) {
					if (board.get_solution_bit(bit_index)) {
						ofDrawCircle(rect_start_x, rect_start_y, 10, 10);
					}

					++bit_index;
					rect_start_x += 100;
				}

				rect_start_y += 100;
			}
		}

		if (not_solved) {
			ofSetColor(0, 200, 200);
			ofDrawRectangle(0, 200, 500, 200);
			ofSetColor(255, 255, 255);
			ofDrawBitmapString("No Solution Available!", 100, 250);
		}
	}

	ofSetColor(0, 200, 255);
	ofDrawRectangle(reset_button);
	ofDrawRectangle(show_solution_button);
	ofDrawRectangle(solving_mode_button);
	ofDrawRectangle(solve_button);
	ofSetColor(255, 255, 255);
	ofDrawBitmapString("Reset", 50, 550);
	ofDrawBitmapString("Solution", 160, 550);
	ofDrawBitmapString("Solving Mode", 260, 550);
	ofDrawBitmapString("Solve", 410, 550);
}

//--------------------------------------------------------------
void ofApp::keyPressed(int key){

}

//--------------------------------------------------------------
void ofApp::keyReleased(int key){

}

//--------------------------------------------------------------
void ofApp::mouseMoved(int x, int y ){

}

//--------------------------------------------------------------
void ofApp::mouseDragged(int x, int y, int button){

}

//--------------------------------------------------------------
void ofApp::mousePressed(int x, int y, int button){
	not_solved = false;
	if (y <= 500) {
		flip.setPosition(0.5);
		flip.play();
		if (solving) {
			int col = x / 100;
			int row = y / 100;
			board.flip(row * 5 + col);
		} else {
			int col = x / 100;
			int row = y / 100;
			board.press_button(row * 5 + col);
		}
	} else if (reset_button.inside(x, y)) {
		board.reset();
		show_solution = false;
		solving = false;
	} else if (show_solution_button.inside(x, y)) {
		show_solution = !show_solution;
	} else if (solving_mode_button.inside(x, y)) {
		solving = !solving;
		show_solution = false;
		if (solving) {
			board.blank();
		} else {
			board.reset();
		}
	} else if (solve_button.inside(x, y)) {
		if (board.solve()) {
			show_solution = true;
		} else {
			not_solved = true;
		}
	}
}

//--------------------------------------------------------------
void ofApp::mouseReleased(int x, int y, int button){

}

//--------------------------------------------------------------
void ofApp::mouseEntered(int x, int y){

}

//--------------------------------------------------------------
void ofApp::mouseExited(int x, int y){

}

//--------------------------------------------------------------
void ofApp::windowResized(int w, int h){

}

//--------------------------------------------------------------
void ofApp::gotMessage(ofMessage msg){

}

//--------------------------------------------------------------
void ofApp::dragEvent(ofDragInfo dragInfo){

}
