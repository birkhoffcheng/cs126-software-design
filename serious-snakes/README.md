# of-snake
## Open Frameworks Snake Implementation
## NOTE: The current implementation is subopmtimal due to limitations in the linked list implementation. I strongly recommend reading through all of the documentation in the header files in addition to this README

1. Application Loop
The application is controlled by open frameworks. The main snake application extends ofBaseApp, the base open frameworks application. 
* The main event loop is composed of
    * update() Which updates the models used to represent objects of the game
    * draw() Which interprets and renders the objects on the players screen
    * keyPressed() Called when the user presses a key
    * windowResized() Called when the user resizes the window, requires the location and dimensions of objects to be correspondingly resized
* Notes   
    * All of our objects sizes are defined relative to the window width (this provides us with a simple way to adapt the size of on screen objects to the users display)
    * We use a naive algorithm to update the position of our snake on the screen. The algorithm follows:
     ```
     snakebody curr = head.next
     while curr:
        curr.position = curr.prev.position
        
     if current_direction == UP:
        head.position += (0, head.height)
    # Update head for remaining directions...
     ```
   *  This is a simple but naive way to update the snake's position, but it has the major side effect of making the animation frame dependent (we can't split up this update process over multiple frames).
