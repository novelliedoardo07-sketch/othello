
package othello;

import java.io.*;
import java.net.*;

public class ChatServer {
    public static void main(String[] args) {
        int port = 5000;
        if (args.length > 0) {
            try { port = Integer.parseInt(args[0]); } catch (NumberFormatException ignored) {}
        }

        System.out.println("Avviando server sulla porta " + port + " ...");
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("In attesa di connessione...");
            try (Socket clientSocket = serverSocket.accept();
                 BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
                 PrintWriter toClient = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"), true);
                 BufferedReader console = new BufferedReader(new InputStreamReader(System.in, "UTF-8"))
            ) {
                System.out.println("Connessione stabilita con " + clientSocket.getRemoteSocketAddress());

                // Thread che legge dal client e stampa su console
                Thread reader = new Thread(() -> {
                    try {
                        String line;
                        while ((line = fromClient.readLine()) != null) {
                            System.out.println("CLIENT: " + line);
                            if (line.equalsIgnoreCase("/quit")) {
                                System.out.println("Client ha chiesto di terminare. Chiudo lato server.");
                                break;
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("Lettura dal client terminata: " + e.getMessage());
                    }
                });
                reader.setDaemon(true);
                reader.start();

                // Loop di invio: leggi da console e invia al client
                String input;
                while ((input = console.readLine()) != null) {
                    toClient.println(input);
                    toClient.flush();
                    if (input.equalsIgnoreCase("/quit")) {
                        System.out.println("Hai chiesto di terminare. Chiudo connessione.");
                        break;
                    }
                }

            } catch (IOException e) {
                System.err.println("Errore durante la comunicazione con il client: " + e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("Impossibile aprire ServerSocket: " + e.getMessage());
        }

        System.out.println("Server terminato.");
    }
}
