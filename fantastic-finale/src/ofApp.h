#pragma once
#include "ofxGui.h"
#include "ofMain.h"
#include "board.hpp"
class ofApp : public ofBaseApp{

	public:
		void setup();
		void update();
		void draw();

		void keyPressed(int key);
		void keyReleased(int key);
		void mouseMoved(int x, int y );
		void mouseDragged(int x, int y, int button);
		void mousePressed(int x, int y, int button);
		void mouseReleased(int x, int y, int button);
		void mouseEntered(int x, int y);
		void mouseExited(int x, int y);
		void windowResized(int w, int h);
		void dragEvent(ofDragInfo dragInfo);
		void gotMessage(ofMessage msg);

		Board board;
		ofRectangle reset_button;
		ofRectangle show_solution_button;
		ofRectangle solving_mode_button;
		ofRectangle solve_button;
		bool show_solution;
		bool solving;
		bool not_solved;
		ofSoundPlayer winning;
		ofSoundPlayer flip;
};
