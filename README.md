# connectFour
Connect Four game

This is a test project. This readme will be updated with various notes and progress markers as I go through, including a to-do list and project outline.

Connect Four is played on a 7x6 board. This program will take player and computer input, visually represent the game board in console output, track the moves, and identify wins.

The moves will be stored in a List and, ideally, synced to a RDB. The program will be able identify danger and, ideally, strategic opportunities to allow the computer opponent to play intelligently, but I'm not 100% sure I can make that last part happen, given my limited knowledge of Connect Four strategy. I'll see what I can do.

Level 1: Playable Connect Four game with 2 players, a command line interface, and win/tie detection.
Level 2: "AI" for a computer player that blocks (closes a half-open three and half-closes an open two) when possible or else plays on its longest playable run.
Level 3: Learn how to make a single-page application backed by Java. Learn how to host a SPA. Connect to a free RDB and sync the move object, delivering hash snippet for game identification and resumption. Perhaps allow login and win percentage tracking as well.
Boss Level: I probably won't be able to do this because I don't think I'll have time to read the giant Connect Four Masters Thesis linked from Wikipedia. Having said that, it looks really interesting.


Component List

Initial variables
	Board size (default 7x6y, but there's no reason that couldn't be altered)
	Level 1: Human/AI selector for P1 and P2 (the player will be a controller and its input will come from the command line or from a decision engine of some kind); in this case, we will also initialize first player selection
	Boss: Difficulty selector for each AI that governs whether it plays randomly except to block
	

PlayerInterface
Takes console input and passes to board for validity check.
Checks return value from board and passes play to the other player (0), displays invalidity message (1), displays tie message (2), or displays win message (3).

Level 1: AIPlayer (stub)
Passes AI move to input collector.
Reads the board directly. Most efficient method might be to assess the available moves and check them in order of strength for validity, executing the first valid one. This is where my lack of knowledge about Connect Four strategy will show up most obviously.

Board
Model is a Map<Pair<x, y>, v>, where v indicates the player (null = open).
Takes column from player.
Checks the column's validity (it must be a real column, the coordinate Pair<rowCount, selectedColumn> must be empty).
Returns (1) to player if invalid, triggering message.
Finds the lowest open row in the given column.
Changes the value of the Pair<lowestOpenRow, selectedColumn> to the player's identifier.
Displays the board by iterating through the map and printing output to the console.
Checks for a win or tie. If the game is over, returns tie (2) or win (3) to the player.
Otherwise returns (0) to the player.

MoveLog
Model is a List<Object[]>. The array will contain a a timestamp (import java.util.Date), a player ID (0 or 1), and a Pair<x, y> (coordinates).



Automated flow loop after board initialization and turn order decision:
1) The board checks win conditions and displays the current board
2) The board displays any applicable message
3) The player or AI generates a move and submits it to the board object
4) The board object checks the validity of the move and rejects (GOTO 3) or accepts the move
5) The board logs the move as coordinates + player + timestamp
5) The board updates the model. GOTO 1.