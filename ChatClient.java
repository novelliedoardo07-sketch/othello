package othello;
import java.io.*;
import java.net.*;
public class ChatClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;
        if (args.length > 0) host = args[0];
        if (args.length > 1) {
            try { port = Integer.parseInt(args[1]); } catch (NumberFormatException ignored) {}
        }

        System.out.println("Connessione a " + host + ":" + port + " ...");
        try (Socket socket = new Socket(host, port);
             BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
             PrintWriter toServer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in, "UTF-8"))
        ) {
            System.out.println("Connesso al server.");

            // Thread che legge dal server e stampa su console
            Thread reader = new Thread(() -> {
                try {
                    String line;
                    while ((line = fromServer.readLine()) != null) {
                        System.out.println("SERVER: " + line);
                        if (line.equalsIgnoreCase("/quit")) {
                            System.out.println("Server ha chiesto di terminare. Chiudo lato client.");
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Lettura dal server terminata: " + e.getMessage());
                }
            });
            reader.setDaemon(true);
            reader.start();

            // Loop di invio: leggi da console e invia al server
            String input;
            while ((input = console.readLine()) != null) {
                toServer.println(input);
                toServer.flush();
                if (input.equalsIgnoreCase("/quit")) {
                    System.out.println("Hai chiesto di terminare. Chiudo connessione.");
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Errore di connessione/IO: " + e.getMessage());
        }

        System.out.println("Client terminato.");
    }
}
