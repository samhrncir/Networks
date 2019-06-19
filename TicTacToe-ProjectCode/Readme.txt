Authors: Colin Wlodkowski, Sam Hrncir
Authors: Sam Hrncir and Colin Wlodkowski

--- READ ME ---

Instructions:
Both the server and the client run on the command line.  Open your prefered 
command line application and naviagte to the repository that holds the client 
or server file.

RUNNING THE SERVER:
This server needs to be running before clients start their applications.

In the command line, enter the command "java TicTacToeServer" from within
the repository that the TicTacToe.class file is located.

Close the server with ctrl+C



RUNNING THE CLIENT:
Check the java file of TicTacToeClient to ensure the IP address is the IP
of the server.

If you needed to modify the IP in TicTacToeClient.java, recompile it by
entering the command "javac TicTacToeClient.java" in the command line.

Ensure the server is running before starting the TicTacToeClient app.
Start the client with the command "java TicTacToeClient" in the command
line.



PLAYING THE GAME:
The goal of this game, Tic Tac Toe, is to have 3 marks in a row.  This 
can be along a row, column, or diagonal.  

Players take turns placing marks on the grid.

To place a mark, input the x y corrdinate of the grid space where you would 
like to place your mark and press the enter key.

The game is over when a player wins or if there is a tie.
A tie happens when the grid is full with marks and there is no row of 3.

When a game is over, the application completes and players are disconnected 
from the server.

To play again, restart the TicTacToeClient app.

-End-