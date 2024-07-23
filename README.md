
## Dice Chess
# Getting Started

### Compiling
Run `gradle build` from the project directory

### Run Game
Either run using gradle run or by running the main class in 'src/main/java/com/view/Menu'



## File guide

### main module
The `GameLogic` package contains all the backend for the game.
- The `Board` package contains the chessboard as well as the subpackage `Pieces` which contains the chess pieces.
- The `Players` package contains the logic for the player entities in the game
- The `Game` class stores a merges all the logic of the game
- The `EvalFunctions` package contains heuristics for the AGENT players. Inside the package there is a gentic algorithim with a main method to train the heuristic weights 

The `view` package contains all the frontend ui for the game.
- The `GamePanels` package contains all the components to make the main chess UI
- The `MainChess` class merges all the game panels to create a UI to play dice chess
- The `Menu` starting point for the application and shows a simple UI which has the rules of the game as well as a way to start the game
