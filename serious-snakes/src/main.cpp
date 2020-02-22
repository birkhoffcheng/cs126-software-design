#include "ofMain.h"
#include "ofApp.h"

#define DISPLAY_MODE OF_WINDOW // Can be OF_WINDOW or OF_FULLSCREEN

/*
CS126 Snake
       ---_ ......._-_--.
      (|\ /      / /| \  \
      /  /     .'  -=-'   `.
     /  /    .'             )
   _/  /   .'        _.)   /
  / o   o        _.-' /  .'
  \          _.-'    / .'*|
   \______.-'//    .'.' \*|
    \|  \ | //   .'.' _ |*|
     `   \|//  .'.'_ _ _|*|
      .  .// .'.' | _ _ \*|
      \`-|\_/ /    \ _ _ \*\
       `/'\__/      \ _ _ \*\
      /^|            \ _ _ \*
     '  `             \ _ _ \ 
                       \_
*/
int main() {
	ofSetupOpenGL(640, 480, DISPLAY_MODE); // setup the GL context
	ofSetFrameRate(12); // An appropriate framerate that moves the snake at a good speed
						// Due to the simple nature of our objects rendering things this slow should not cause visual discomfort or lage
	
	// this kicks off the running of my app
	ofRunApp(new snakelinkedlist::snakeGame());
}
