package othello;

import javax.swing.*;
import java.awt.*;

public class OthelloGUI extends JFrame {

    private JButton[][] buttons = new JButton[8][8];
    private JLabel status = new JLabel();
    private Object net;          // server o client
    private int mioColore;        // 1 = nero, 2 = bianco

    public OthelloGUI(Object net, int colore) {
        this.net = net;
        this.mioColore = colore;

        setTitle(colore == 1 ? "Othello SERVER (NERO)" : "Othello CLIENT (BIANCO)");
        setSize(600, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel board = new JPanel(new GridLayout(8, 8));
        board.setBackground(new Color(0, 120, 0));

        // CREA GRIGLIA CON STILE
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                JButton b = new JButton();
                b.setFocusPainted(false);
                b.setBackground(new Color(0, 150, 0));
                b.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                final int fx = x;
                final int fy = y;
                b.addActionListener(e -> handleClick(fx, fy));

                buttons[y][x] = b;
                board.add(b);
            }
        }

        status.setHorizontalAlignment(JLabel.CENTER);
        status.setFont(new Font("Arial", Font.BOLD, 18));

        add(board, BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);

        aggiornaGUI();
        setVisible(true);
    }

    // ==========================================================
    // CLICK LOCALE
    // ==========================================================

    private void handleClick(int x, int y) {

        if (logica.turno != mioColore) return;

        if (!logica.controllo(x, y)) {
            JOptionPane.showMessageDialog(this, "Mossa non valida!");
            return;
        }

        invia(x, y);
        applicaMossaRemota(x, y);
    }

    private void invia(int x, int y) {
        if (net instanceof ChatServer)
            ((ChatServer) net).inviaMossa(x, y);
        else
            ((ChatClient) net).inviaMossa(x, y);
    }

    // ==========================================================
    // MOSSA REMOTA
    // ==========================================================

    public void applicaMossaRemota(int x, int y) {

        if (!logica.controllo(x, y)) return;

        logica.applicaMossa(x, y);
        logica.turno = (logica.turno == 1 ? 2 : 1);
        aggiornaGUI();
    }

    // ==========================================================
    // AGGIORNA GRAFICA
    // ==========================================================

    private void aggiornaGUI() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                JButton b = buttons[y][x];
                int v = logica.matrix[y][x];

                if (v == 1) {
                    b.setIcon(creaPedina(Color.BLACK));
                } else if (v == 2) {
                    b.setIcon(creaPedina(Color.WHITE));
                } else {
                    b.setIcon(null);
                }
            }
        }

        status.setText(
                logica.turno == mioColore ? "TOCCA A TE" : "ATTENDI..."
        );
    }

    // ==========================================================
    // DISEGNO PEDINA
    // ==========================================================

    private Icon creaPedina(Color c) {
        return new Icon() {
            public int getIconWidth() { return 50; }
            public int getIconHeight() { return 50; }

            public void paintIcon(Component comp, Graphics g, int x, int y) {
                g.setColor(c);
                g.fillOval(x + 5, y + 5, 40, 40);
                g.setColor(Color.BLACK);
                g.drawOval(x + 5, y + 5, 40, 40);
            }
        };
    }
}
