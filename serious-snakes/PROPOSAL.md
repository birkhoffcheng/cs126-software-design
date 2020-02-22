# Final Project Proposal
## Project Summary
I am planning to write a GUI Lights Out solver/player. Lights Out is an electronic game released by Tiger Electronics. The game consists of a five by five grid of lights. When the game starts, a random number or a stored pattern of these lights is switched on. Pressing any of the lights will toggle it and the four adjacent lights. The goal of the puzzle is to switch all the lights off, preferably in as few button presses as possible.

I plan to have my game support boards of arbitrary dimensions. It would have two modes, solving mode and playing mode. In solving modes, the user first chooses the dimensions of the board, and then toggle the lights that are initially on. Then the user clicks solve, and the program will try to solve it. If it is solvable, the application will display the lights to toggle to solve the board. If it is unsolvable, the program will display "Unsolvable." In playing mode, the program will randomly generate a game board, check to make sure the board is solvable, and then let the user solve it. It will time the user and record the number of steps the user takes. And when the user solves the board, it will display, "Congratulations!"

## Motivation
The reason why I want to do this project is that this project will allow me to explore different graph algorithms, including brute-force search, breadth-first search, and depth-first search. I have written a command-line program that solves lights out using brute-force search before. Since I know more graph algorithms now, I want to implement other algorithms to solve the problem more efficiently.

## External Libraries
I will use [ofxBasicSoundPlayer](https://github.com/arturoc/ofxBasicSoundPlayer) to play sound effect every time the user toggles a light and play a cheering sound when the user solves the board.

## Timeline
### Week 1
Implement the lights out solving algorithm
### Week 2
Implement the UI for the solving mode
### Week 3
Implement the UI for the playing mode
### Week 4
Implement the sound effect

## Misc
If I accomplish the above-mentioned goals early, I will implement an ncurses interface as well so if the user doesn't have a GUI, they can run the program in CLI mode as well.
