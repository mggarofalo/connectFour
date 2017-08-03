# connectFour
## Connect Four game (test project)

**This is a test project. This readme will be updated with various notes and progress markers as I go through, including a to-do list and project outline.**

## About
Connect Four is played on a 7x6 board. This program will take player and computer input, visually represent the game board in console output, track the moves, and identify wins.

The moves will be stored in a List and, ideally, synced to a RDB. The program will be able identify danger and, ideally, strategic opportunities to allow the computer opponent to play intelligently, but I'm not 100% sure I can make that last part happen, given my limited knowledge of Connect Four strategy. I'll see what I can do.

## Component List

#### Initial variables
- Board size (default 7c6r, but there's no reason that couldn't be altered). Note: it's been altered. Any size board, although the formatting is still a little weird.
- Level 1/2: Human/AI selector for P1 and P2 (the player will be a controller and its input will come from the command line or from a decision engine of some kind); in this case, we will also initialize first player selection
- Boss: Difficulty selector for each AI that governs whether it plays randomly except to block
	
#### Level 2: AIPlayer (stub)
- [ ] Checks for threats from all moves since its last turn.
- [ ] Find the move that will make the longest line where four are possible.
- [ ] If no such move is possible, try playing adjacent to a single opponent token where four are possible.
- [ ] If no such move is possible, try playing in a random open space where four are possible.
- [ ] If no such move is possible, play in a random open space.

#### PlayerInterface
- [X] Takes console input and passes to board.
- [X] Displays message to player if the requested move is invalid.
- [X] Checks return value from board and passes play to the other player (0), displays tie message (1), or displays win (2) or loss (3) message.

#### Board
- [X] Model is an int\[rows\]\[columns\], where -1 means empty and 0 or 1 is player ID.
- [X] Takes column from player or move from AI.
- [X] Checks the column's validity (it must be a real column, the coordinate BoardSquare(c, r) must be empty).
- [X] Returns value to player, triggering message.
- [X] Finds the lowest open row in the given column.
- [X] Displays the board by iterating through the matrix and printing output to the console.
- [X] Otherwise returns (0) to the player.
- [X] Fix formatting for 10+ columns.

#### Game
- [X] Keeps track of current turn.
- [X] Checks for a win or tie. If the game is over, returns tie (-1) or win (player ID + 1) to the player. Otherwise returns 0 and the game continues.
- [X] Calls player action (listener for human, decision method for AI).

#### MoveLog
- [X] Model is an ArrayList of BoardMoves. BoardMoves contain a a date and time (java.util.Date), a player ID (0 or 1), and a BoardSquare<c, r>.

#### BoardSquare
- [X] Model is two ints (r, c).
- [X] Methods to get row and column.



## Automated flow loop after board initialization and turn order decision:
1. The game checks win conditions and displays the current board
2. The player controller displays any applicable message
3. The player or AI generates a move and submits it to the board object
4. The board object checks the validity of the move and rejects (GOTO 3) or accepts the move
5. The board logs the move as coordinates + player + timestamp.
6. The board updates the model. GOTO 1.