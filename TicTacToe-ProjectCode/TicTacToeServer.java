// Authors: Sam Hrncir, Colin Wlodkowski

import java.io.*;
import java.net.*;
import java.util.*;

@SuppressWarnings("unused")
public final class TicTacToeServer {
    public static void main(String argv[]) throws Exception {
        System.out.println("Starting TicTacToeServer");

        // Set the port number
        int port = 7777;

        // Establish the listen socket.
        @SuppressWarnings("resource")
		ServerSocket welcomeSocket = new ServerSocket(port);

        //process player service requests in an infinite loop.
        while (true) {
            int pairCounter = 0;
            // create game instance
            TicTacToe gameInstance = new TicTacToe();
            gameInstance.initializeBoard();

            // Point only two players to the above gameInstance.
            while (pairCounter != 2) {
                // Listen for a TCP connection request.
                Socket connectionSocket = welcomeSocket.accept();
                // Construct an object to process the HTTP request message.
                PlayerRequest request = new PlayerRequest(connectionSocket, gameInstance, connectionSocket.getRemoteSocketAddress().toString());
                // Create a new thread to process the request.
                Thread thread = new Thread(request);
                // Start the thread.
                thread.start();
                pairCounter = pairCounter + 1;
            }
        }
    }
}



/**
 * This class manages information between the client, server, and game.
 */
final class PlayerRequest implements Runnable {
    final static String END = "Noah is the best";
    private Socket socket;
    private DataOutputStream os;
    private BufferedReader br;
    private TicTacToe game;
    private String player;


    // Constructor
    public PlayerRequest(Socket socket, TicTacToe game, String player) throws Exception {
        this.socket = socket;
        // Get a reference to the socket's input and output streams.
        InputStream is = socket.getInputStream();
        // Set up input stream filters.
        br = new BufferedReader(new InputStreamReader(is));
        os = new DataOutputStream(socket.getOutputStream());

        this.game = game;
        this.player = player;

        // Add the player to the game.
        game.setPlayer(player);
        System.out.println("Added player: " + player);
    }


    // Implement the run() method of the Runnable interface.
    public void run() {

            try {
                while (true) {
                    processTurn();
                }
            } catch (Exception e) {
                System.out.println(e);
                game.setStatus(false);
            }
    }


    /**
     * This method runs each turn for the player.  The methods waits until it is the players turn, then checks to see
     * if the game was won or there was a tie on the past turn, or if the game is still active.
     * @throws Exception
     */
    private void processTurn() throws Exception {
        //waite for turn
        waitForTurn();

        os.writeBytes(game.printBoard() + "\n");

        if(game.checkForWin() == true) {
            os.writeBytes("You lose.\nGood bye.\n" + "close\n" );
            // Close streams and socket.
            close();
        } else if (game.isBoardFull()) {
	    os.writeBytes("Draw! \nGoodbye. \n" + "close\n" );
            close();
        } else if (!game.getStatus()) {
            os.writeBytes("Opponent disconnected.\nGood bye.\n" + "close\n" );
            close();
        }

        os.writeBytes("Please place a mark: x y\n" + END + "\n");
        // Get the request line of the HTTP request message.
        String requestLine = br.readLine();

        // Display the request line.
        System.out.println();
        System.out.println("RECEIVED FROM CLIENT: " + requestLine);

        manageClientInput(requestLine);
    }


    // Close streams and socket
    private void close() throws IOException {
        os.close();
        br.close();
        socket.close();
    }


    /**
     * Method that manages the input taken in from the client.  Firsts check the input is formatted correctly.  If it is
     * then the input is used by the TicTacToe class to make a mark on the board.  Method then checks if the recent mark
     * resulted in a win or tie.  If end of game, closes the streams and sockets.
     * @param requestln input String from the client
     * @throws Exception
     */
    private void manageClientInput(String requestln) throws Exception {
        // Cheak if player input is of valid format
        if (game.getCurrentPlayer().equals(player)){
            String[] spotArray = requestln.split(" ");
            if (requestln.length() != 3) {
                os.writeBytes("Invalid move, Please format your move: x y\n" + END + "\n");
                return;
            }
            try {
                int row = Integer.parseInt(spotArray[0]);
                int col = Integer.parseInt(spotArray[1]);
                os.writeBytes(game.placeMark(row, col));
                os.writeBytes(game.printBoard() + "\n");
                if(game.checkForWin() == true) {
                    os.writeBytes("You win!!!\nGood bye.\n" + "close\n" );
                    // Close streams and socket.
                    close();
                } else if (game.isBoardFull()) {
		    os.writeBytes("Draw! \nGoodbye. \n" + "close\n" );
                    close();
                }
            } catch (Exception e) {
                os.writeBytes("Invalid move, please use integers of the form: x y\n" + END + "\n");
            }
        } else {
            os.writeBytes("Please wait for the other player to finish their turn.\n" + END + "\n");
        }
    }


    /**
     * Method that has the player to wait until the game lists the player as current player.
     * @throws Exception
     */
    private void waitForTurn() throws Exception {
        System.out.println(player + " waiting");
        os.writeBytes("Please wait...\n\n");
        while (game.getStatus()) {
            Thread.sleep(1000);
            String CurrentPlayerListed = game.getCurrentPlayer();
            System.out.println("Scan current player: " + CurrentPlayerListed);
            if (CurrentPlayerListed != null && CurrentPlayerListed.equals(player)) {
                System.out.println(player + " stopped waiting");
                break;
            }
        }
    }

}


