package othello;

import javax.swing.*;
import java.awt.*;

public class OthelloGUI extends JFrame {

    private JButton[][] buttons = new JButton[8][8];
    private JLabel status = new JLabel();
    private JLabel score = new JLabel();
    private Object net;
    private int mioColore;
    private boolean partitaTerminata = false;

    public OthelloGUI(Object net, int colore) {
        this.net = net;
        this.mioColore = colore;

        setTitle(colore == 1 ? "OTHELLO NERO" : "OTHELLO BIANCO");
        setSize(600, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel board = new JPanel(new GridLayout(8, 8));
        board.setBackground(new Color(0, 120, 0));

        for (int y = 0; y < 8; y++)
            for (int x = 0; x < 8; x++) {
                JButton b = new JButton();
                b.setBackground(new Color(0, 150, 0));
                final int fx = x, fy = y;
                b.addActionListener(e -> click(fx, fy));
                buttons[y][x] = b;
                board.add(b);
            }

        status.setHorizontalAlignment(JLabel.CENTER);
        score.setHorizontalAlignment(JLabel.CENTER);

        add(board, BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);
        add(score, BorderLayout.NORTH);

        aggiorna();
        setVisible(true);
    }

    private void click(int x, int y) {
        if (partitaTerminata) return;
        if (logica.turno != mioColore) return;
        if (!logica.controllo(x, y)) return;

        invia(x, y);
        applicaMossaRemota(x, y);
    }

    private void invia(int x, int y) {
        if (net instanceof ChatServer)
            ((ChatServer) net).inviaMossa(x, y);
        else
            ((ChatClient) net).inviaMossa(x, y);
    }

    public void applicaMossaRemota(int x, int y) {
        if (!logica.applicaMossa(x, y)) return;

        logica.turno = logica.turno == 1 ? 2 : 1;
        aggiorna();

        if (logica.partitaFinita())
            mostraVincitore();
    }

    private void aggiorna() {

        int n = 0, b = 0;

        for (int y = 0; y < 8; y++)
            for (int x = 0; x < 8; x++) {
                int v = logica.matrix[y][x];
                buttons[y][x].setIcon(v == 0 ? null : pedina(v == 1 ? Color.BLACK : Color.WHITE));
                if (v == 1) n++;
                if (v == 2) b++;
            }

        score.setText("NERO: " + n + "   BIANCO: " + b);
        status.setText(logica.turno == mioColore ? "TOCCA A TE" : "ATTENDI...");
    }

    private Icon pedina(Color c) {
        return new Icon() {
            public int getIconWidth() { return 50; }
            public int getIconHeight() { return 50; }
            public void paintIcon(Component comp, Graphics g, int x, int y) {
                g.setColor(c);
                g.fillOval(x + 5, y + 5, 40, 40);
            }
        };
    }

    private void mostraVincitore() {
        partitaTerminata = true;

        int n = 0, b = 0;
        for (int[] r : logica.matrix)
            for (int c : r) {
                if (c == 1) n++;
                if (c == 2) b++;
            }

        String msg = n > b ? "VINCE NERO" :
                     b > n ? "VINCE BIANCO" :
                             "PAREGGIO";

        JOptionPane.showMessageDialog(this, msg);
    }

    public void vittoriaATavolino() {
        if (partitaTerminata) return;

        partitaTerminata = true;
        String vincitore = mioColore == 1 ? "NERO" : "BIANCO";

        JOptionPane.showMessageDialog(
                this,
                "Avversario disconnesso\nVince " + vincitore + " a tavolino"
        );
    }
}
