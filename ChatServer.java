package othello;

import javax.swing.*;
import java.io.*;
import java.net.*;

public class ChatServer {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private OthelloGUI gui;

    public ChatServer(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server avviato, in attesa del client...");
        socket = serverSocket.accept();
        System.out.println("Client connesso");

        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

        gui = new OthelloGUI(this, 1); // NERO
        avviaReader();
    }

    public void inviaMossa(int x, int y) {
        out.println(x + " " + y);
    }

    private void avviaReader() {
        Thread t = new Thread(() -> {
            try {
                String msg;
                while ((msg = in.readLine()) != null) {
                    String[] p = msg.split(" ");
                    int x = Integer.parseInt(p[0]);
                    int y = Integer.parseInt(p[1]);

                    SwingUtilities.invokeLater(() ->
                            gui.applicaMossaRemota(x, y)
                    );
                }
            } catch (Exception e) {
                System.out.println("Client disconnesso");
            }
        });
        t.setDaemon(true);
        t.start();
    }

    public static void main(String[] args) throws Exception {
        new ChatServer(5000);
    }
}

