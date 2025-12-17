package othello;

import javax.swing.*;
import java.io.*;
import java.net.*;

public class ChatClient {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private OthelloGUI gui;

    public ChatClient(String host, int port) throws IOException {
        socket = new Socket(host, port);
        System.out.println("Connesso al server");

        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

        gui = new OthelloGUI(this, 2); // BIANCO
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
                System.out.println("Connessione chiusa");
            }
        });
        t.setDaemon(true);
        t.start();
    }

    public static void main(String[] args) throws Exception {
        new ChatClient("localhost", 5000);
    }
}
