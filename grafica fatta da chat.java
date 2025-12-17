package othello;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OthelloGUI extends JFrame {

    private JButton[][] buttons = new JButton[8][8];
    private JLabel status = new JLabel();

    public OthelloGUI() {
        setTitle("Othello");
        setSize(600, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel board = new JPanel(new GridLayout(8, 8));
        board.setBackground(new Color(0, 120, 0));

        // CREA GRIGLIA
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                JButton b = new JButton();
                b.setFocusPainted(false);
                b.setBackground(new Color(0, 150, 0));
                b.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                final int fx = x;
                final int fy = y;

                b.addActionListener(e -> handleMove(fx, fy));

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
    // GESTIONE MOSSA
    // ==========================================================

    private void handleMove(int x, int y) {

        if (!logica.controllo(x, y)) {
            JOptionPane.showMessageDialog(this, "Mossa non valida!");
            return;
        }

        logica.applicaMossa(x, y);
        logica.turno = (logica.turno == 1 ? 2 : 1);

        if (!logica.haMosse(logica.turno)) {
            logica.turno = (logica.turno == 1 ? 2 : 1);

            if (logica.partitaFinita()) {
                finePartita();
                return;
            }
        }

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

        status.setText("Turno del giocatore " + logica.turno);
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

    // ==========================================================
    // FINE PARTITA
    // ==========================================================

    private void finePartita() {
        int g1 = 0, g2 = 0;

        for (int y = 0; y < 8; y++)
            for (int x = 0; x < 8; x++) {
                if (logica.matrix[y][x] == 1) g1++;
                if (logica.matrix[y][x] == 2) g2++;
            }

        String risultato;
        if (g1 > g2) risultato = "Vince il giocatore 1!";
        else if (g2 > g1) risultato = "Vince il giocatore 2!";
        else risultato = "Pareggio!";

        JOptionPane.showMessageDialog(this,
                risultato + "\n\nGiocatore 1: " + g1 +
                "\nGiocatore 2: " + g2,
                "Fine partita",
                JOptionPane.INFORMATION_MESSAGE);

        System.exit(0);
    }

    // ==========================================================
    // MAIN GRAFICO
    // ==========================================================

    public static void main(String[] args) {
        SwingUtilities.invokeLater(OthelloGUI::new);
    }
}
