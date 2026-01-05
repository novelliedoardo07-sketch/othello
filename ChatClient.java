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

        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

        gui = new OthelloGUI(this, 2); // BIANCO
        avviaReader();
    }

    public void inviaMossa(int x, int y) {
        out.println(x + " " + y);
    }

    private void avviaReader() {
        new Thread(() -> {
            try {
                String msg;
                while ((msg = in.readLine()) != null) {
                    String[] p = msg.split(" ");
                    gui.applicaMossaRemota(
                            Integer.parseInt(p[0]),
                            Integer.parseInt(p[1])
                    );
                }
            } catch (Exception e) {
                gui.vittoriaATavolino();
            }
        }).start();
    }

    public static void main(String[] args) {

        JTextField ip = new JTextField();
        JTextField port = new JTextField("5000");

        Object[] msg = {
                "IP Server:", ip,
                "Porta:", port
        };

        if (JOptionPane.showConfirmDialog(null, msg, "Connessione", JOptionPane.OK_CANCEL_OPTION)
                != JOptionPane.OK_OPTION)
            return;

        try {
            new ChatClient(ip.getText(), Integer.parseInt(port.getText()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Connessione fallita");
        }
    }
}
