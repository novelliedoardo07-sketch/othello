package othello;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.Enumeration;

public class ChatServer {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private OthelloGUI gui;

    public ChatServer(int port) throws IOException {

        ServerSocket serverSocket = new ServerSocket(port);

        String ip = getPublicIP();
        JOptionPane.showMessageDialog(
                null,
                "Server avviato\nIP: " + ip + "\nPorta: " + port,
                "Server Othello",
                JOptionPane.INFORMATION_MESSAGE
        );

        socket = serverSocket.accept();

        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

        gui = new OthelloGUI(this, 1); // NERO
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

    public static String getPublicIP() {
        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            while (nets.hasMoreElements()) {
                NetworkInterface net = nets.nextElement();
                if (net.isLoopback() || !net.isUp()) continue;

                for (InetAddress addr : java.util.Collections.list(net.getInetAddresses())) {
                    if (addr instanceof Inet4Address)
                        return addr.getHostAddress();
                }
            }
        } catch (Exception ignored) {}
        return "IP non disponibile";
    }

    public static void main(String[] args) throws Exception {
        new ChatServer(5000);
    }
}
