Design Doc
==========

Screens and Classes
-------
I expect that the app will have a small number of screens:

+ Mainscreen
+ Puzzlescreen
+ Levelscreen
+ Picturescreen
+ Extra:
  + Highscorescreen 
  + AddHighscorescreen

#### Mainscreen:
Gives the user options to go to another screen by clicking a button. The activities will be:
+ Going to puzzlescreen (through levelscreen)
+ Going to highscorescreen

#### Levelscreen
Will be shown before the puzzlescreen. The user gets to choose difficulty (easy/medium/hard).
+ Going back to Mainscreen
+ User can pick difficulty (this translates into a number of tiles, for the puzzlescreen)
+ After choice is made, picture screen will pop up.

#### Picturescreen
Shows a number of pictures the user can choose from for the puzzle.
+ Going back to levelscreen
+ User can pick an image for the puzzel (this will be used for the puzzlescreen)

#### Puzzlescreen
The puzzle will be played in this screen. The pieces can be moved bij sliding to an empty spot. Every move will be counted and shown. When the puzzle is complete the number of moves will be saved when it is in the top 3 highest scores. Also when it is a new highscore this will be communicated to the user with a message.
+ A menu/options button that pauses the game and opens up a pop-up:
  + Reset the game
  + Change difficulty (causes a reset)
  + Quit the game
+ Counter which counts the number of moves the player has done.
+ The puzzle is shown
+ When the puzzle is complete, the score will be compared to the highscores.
+ When a highscore has been beaten, the addhighscorescreen will be opened.

**Classes for the puzzle**
For the game a class is needed which builds the puzzle and keeps track of the users actions.
+ CLASS: Puzzle
  1. Initialize: number of tiles and chosen image to builds the puzzle
  2. Cutting the image into tiles, leaving the lower-right corner out.
  3. The tiles should be numbered, and get a position on the screen. This will be saved within the class.
  4. Tiles can be moved if there is an empty space immediately adjacent to the tile, by swiping the tile to the empty space. This should update the tile positions and the number of moves the user has made.
  5. Recognizes when the puzzle is complete.


![Screen to screen overview](https://raw.githubusercontent.com/yasminakada/npuzzle/master/puzzle_screens.jpg)
*The image is not up to date*

###Extra screens and classes

The following screens and classes are just an extra and are not part of the actual assignment. However it might be nice to include them in the app.

#### Highscorescreen:
Shows the top 3 of the highest scores. Going back to the mainscreen is the only activity.
+ Needs to access saved highscores
+ Going back to mainscreen

#### AddHighscorescreen
When the player's score is a highscore, the player needs to insert his/her name in this screen. This screen goes to show the highscorescreen afterwards.
+ Editable textfield for the name, on the 

API and frameworks
------------------
I expect using the API's
+ 


