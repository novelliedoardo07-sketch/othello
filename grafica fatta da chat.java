import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OthelloGUI extends JFrame {

    private int turno = 1;
    private int[][] board = new int[8][8];
    private JButton[][] buttons = new JButton[8][8];

    public OthelloGUI() {
        setTitle("Othello");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 8));

        initBoard();
        initButtons();
        refreshBoard();
    }

    private void initBoard() {
        for (int y = 0; y < 8; y++)
            for (int x = 0; x < 8; x++)
                board[y][x] = 0;

        board[3][3] = 1;
        board[4][4] = 1;
        board[3][4] = 2;
        board[4][3] = 2;
    }

    private void initButtons() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                final int fx = x;
                final int fy = y;

                JButton b = new JButton();
                b.setBackground(Color.GREEN);
                b.setOpaque(true);
                b.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                b.addActionListener(e -> {
                    if (isValidMove(fx, fy)) {
                        makeMove(fx, fy);
                        turno = (turno == 1 ? 2 : 1);
                        refreshBoard();
                    }
                });

                buttons[y][x] = b;
                add(b);
            }
        }
    }

    private boolean isValidMove(int x, int y) {
        if (board[y][x] != 0) return false;
        return flips(x, y, false);
    }

    private void makeMove(int x, int y) {
        flips(x, y, true);
    }

    private boolean flips(int x, int y, boolean mode) {
        boolean moved = false;

        int[][] dirs = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1},
            {-1, -1}, {1, -1}, {-1, 1}, {1, 1}
        };

        for (int[] d : dirs) {
            if (flipDirection(x, y, d[0], d[1], mode))
                moved = true;
        }

        return moved;
    }

    private boolean flipDirection(int x, int y, int dx, int dy, boolean mode) {
        int enemy = (turno == 1 ? 2 : 1);
        int cx = x + dx;
        int cy = y + dy;

        boolean foundEnemy = false;

        while (cx >= 0 && cx < 8 && cy >= 0 && cy < 8) {
            if (board[cy][cx] == enemy) {
                foundEnemy = true;
                cx += dx;
                cy += dy;
                continue;
            }

            if (board[cy][cx] == turno) {
                if (!foundEnemy) return false;

                if (mode) {
                    int fx = x + dx;
                    int fy = y + dy;

                    while (fx != cx || fy != cy) {
                        board[fy][fx] = turno;
                        fx += dx;
                        fy += dy;
                    }

                    board[y][x] = turno;
                }
                return true;
            }

            if (board[cy][cx] == 0) return false;
        }

        return false;
    }

    private void refreshBoard() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                JButton b = buttons[y][x];

                if (board[y][x] == 1) {
                    b.setBackground(Color.BLACK);
                } else if (board[y][x] == 2) {
                    b.setBackground(Color.WHITE);
                } else {
                    b.setBackground(Color.GREEN);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OthelloGUI().setVisible(true));
    }
}
