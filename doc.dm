Design Doc
==========

Classes
-------
I expect that the app will have a small number of screens:

+ Mainscreen
+ Highscorescreen
+ Puzzlescreen
+ Levelscreen
+ AddHighscorescreen

### Mainscreen:
Gives the user options to go to another screen by clicking a button. The activities will be:
+ Going to puzzlescreen (through levelscreen)
+ Going to highscore screen

### Highscorescreen:
Shows the top 3 of the highest scores. Going back to the mainscreen is the only activity.

### Levelscreen
Will be shown before the puzzlescreen. The user gets to choose a game or difficulty (to be determined). The other activity is that it will go to the chosen game after a game/difficulty has been chosen.

### Puzzlescreen
The puzzle will be played in this screen. The pieces can be moved bij sliding to an empty spot. Every move will be counted and shown. When the puzzle is complete the number of moves will be saved when it is in the top 3 highest scores. Also when it is a new highscore this will be communicated to the user with a message.

### AddHighscorescreen
When the player's score is a highscore, the player needs to insert his/her name in this screen. This screen goes to show the highscorescreen afterwards.

![Screen to screen overview](/path/to/img.jpg)