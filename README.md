## Build Instructions
### Compile
* Navigate to the 'src' directory
* `javac tictactoe/*.java tictactoe/applet/*.java`
### Executable .jar
* After compiling, while still in the 'src' directory
    * `jar -cvfe TicTacToe.jar tictactoe.applet.TicTacToePanel tictactoe/*.class tictactoe/applet/*.class`
### Applet
* After compiling, while still in the 'src' directory
    * `jar -cvfe TicTacToeApplet.jar tictactoe.applet.TicTacToeApplet tictactoe/*.class tictactoe/applet/*.class`
    * Move to same directory as 'index.html'

## Execution
### Standalone
* Run 'TicTacToe.jar'
### Applet
* Move 'index.html' and 'TicTacToeApplet.jar' to the same directory
* Open 'index.html'
Deployed at http://www2.hawaii.edu/~ttaomae/apps/tic_tac_toe/


## Known Bugs
  -Board does not always update correctly