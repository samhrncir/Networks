// Authors: Sam Hrncir, Colin Wlodkowski

import java.io.*;
import java.net.*;

class TicTacToeClient {

    public static void main(String argv[]) throws Exception {

        String move;
        String modifiedSentence;
        final String END = "Noah is the best"; // END is used to end all inputs recieved from server, used to determine the end of a line.

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        Socket clientSocket = new Socket("172.18.9.53", 7777);

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(
                clientSocket.getInputStream()));

        while (true) {

            // Listen for input from the server until the server sends END or tells the client to close.
            // Displays all data from the server.  Client just Displays server input, and allows the user to write input
            // to the server.
            boolean endFlag = false;
            while (!endFlag){
                String currLine = inFromServer.readLine();
                if (currLine.equals(END)) {
                    endFlag = true;
                } else if (currLine.equals("close")){
                    inFromServer.close();
                    outToServer.close();
                    clientSocket.close();
                    System.exit(0);
                } else {
                    System.out.println(currLine);
                }
            }

            // Allow the user to write input to the server.
            move = inFromUser.readLine();
            outToServer.writeBytes(move + "\n");
        }
    }
}




