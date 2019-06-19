// Authors: Sam Hrncir, Colin Wlodkowski
// TicTacToe code adapted and copied from https://www.coderslexicon.com/a-beginner-tic-tac-toe-class-for-java/

public class TicTacToe {

    private char[][] board; 
    private char currentPlayerMark;
    private String playerOne;
    private String playerTwo;
    private String currentPlayer;
    private boolean status;

    public TicTacToe() {
        board = new char[3][3];
        currentPlayerMark = 'x';
        status = true;
    }

    // get method for currentPlayer
    public String getCurrentPlayer() {
        return currentPlayer;
    }

    // Set/Reset the board back to all empty values.
    public void initializeBoard() {
        // Loop through rows
        for (int i = 0; i < 3; i++) {
            // Loop through columns
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    // Set which player is playerOne/playerTwo. Used with filter request to determine player mark and turn order. 
    public String setPlayer(String player) {
        if (playerOne == null) {
            playerOne = player;
            currentPlayer = playerOne;
            return "you have been set as X\n";
        }else if (playerTwo == null) {
            playerTwo = player;
            if (currentPlayer == null) {
                currentPlayer = playerTwo;
            }
            return "you have been set as O\n";
        } else {
            return "Game full! Try again later\n";
        }
    }

    // Mutator for status field.
    public void setStatus(boolean b) {
        status = b;
    }

    // Get method for status field. 
    public boolean getStatus() {
        return status;
    }
	
    // Print the current board (could be replaced by GUI implementation later)
    public String printBoard() {
        String boardString = "   0   1   2\n";
        boardString = boardString + " -------------\n";
		
        for (int i = 0; i < 3; i++) {
            boardString = boardString + i  + "| ";
            for (int j = 0; j < 3; j++) {
                boardString = boardString + board[i][j] + " | ";
            }
            boardString = boardString + "\n";
            boardString = boardString + " -------------\n";
        }
        return boardString;
    }
	
	
    // Loop through all cells of the board and if one is found to be empty (contains char '-') then return false.
    // Otherwise the board is full.
    public boolean isBoardFull() {
        boolean isFull = true;
		
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    isFull = false;
                }
            }
        }
		
        return isFull;
    }
	
	
    // Returns true if there is a win, false otherwise.
    // This calls our other win check functions to check the entire board.
    public boolean checkForWin() {
        return (checkRowsForWin() || checkColumnsForWin() || checkDiagonalsForWin());
    }
	
	
    // Loop through rows and see if any are winners.
    private boolean checkRowsForWin() {
        for (int i = 0; i < 3; i++) {
            if (checkRowCol(board[i][0], board[i][1], board[i][2]) == true) {
                return true;
            }
        }
        return false;
    }
	
	
    // Loop through columns and see if any are winners.
    private boolean checkColumnsForWin() {
        for (int i = 0; i < 3; i++) {
            if (checkRowCol(board[0][i], board[1][i], board[2][i]) == true) {
                return true;
            }
        }
        return false;
    }
	
	
    // Check the two diagonals to see if either is a win. Return true if either wins.
    private boolean checkDiagonalsForWin() {
        return ((checkRowCol(board[0][0], board[1][1], board[2][2]) == true) || (checkRowCol(board[0][2], board[1][1], board[2][0]) == true));
    }
	
	
    // Check to see if all three values are the same (and not empty) indicating a win.
    private boolean checkRowCol(char c1, char c2, char c3) {
        return ((c1 != '-') && (c1 == c2) && (c2 == c3));
    }
	
	
    // Change player marks back and forth.
    public void changePlayerMark() {
        if (currentPlayerMark == 'x') {
            currentPlayerMark = 'o';
        }
        else {
            currentPlayerMark = 'x';
        }
        System.out.println("    PlayerMark changed to: " + currentPlayerMark);
    }


    // Places a mark at the cell specified by row and col with the mark of the current player.
    public String placeMark(int row, int col) {
        // Make sure that row and column are in bounds of the board.
        if ((col >= 0) && (col < 3)) {
            if ((row >= 0) && (row < 3)) {
                if (board[col][row] == '-') {
                    board[col][row] = currentPlayerMark;
                    changePlayerMark();
                    changeCurrentPlayer();
                    return "";
                }
            }
        }
        return "Mark is not a valid spot on board, please try again\n";
    }

    // Changes the current player from playerOne to playerTwo or vise versa. 
    public void changeCurrentPlayer() {
        if (currentPlayer.equals(playerOne)) {
            currentPlayer = playerTwo;
        } else {
            currentPlayer = playerOne;
        }
        System.out.println("    Current Player set as: " + currentPlayer);
    }

}


