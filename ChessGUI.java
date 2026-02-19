import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ChessGUI extends JFrame implements ActionListener {
    JButton[][] buttons = new JButton[8][8];
    String[][] board = new String[8][8];
    boolean playerTurn = true;
    int selectedRow = -1, selectedCol = -1;
    Random rand = new Random();

    public ChessGUI() {
        setTitle("Chess Game - Player vs Computer");
        setSize(600, 600);
        setLayout(new GridLayout(8, 8));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setupBoard();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                buttons[i][j] = new JButton(board[i][j]);
                buttons[i][j].setFont(new Font("Segoe UI Symbol", Font.PLAIN, 32));
                buttons[i][j].addActionListener(this);
                buttons[i][j].setBackground((i + j) % 2 == 0 ? Color.WHITE : Color.GRAY);
                add(buttons[i][j]);
            }
        }

        setVisible(true);
    }

    void setupBoard() {
        String[] black = {"♜","♞","♝","♛","♚","♝","♞","♜"};
        String[] white = {"♖","♘","♗","♕","♔","♗","♘","♖"};

        board[0] = black.clone();
        board[7] = white.clone();

        for (int i = 1; i < 7; i++) {
            board[i] = new String[8];
            Arrays.fill(board[i], "");
        }

        for (int i = 0; i < 8; i++) {
            board[1][i] = "♟";
            board[6][i] = "♙";
        }
    }

    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        int r = -1, c = -1;

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (buttons[i][j] == clicked) {
                    r = i;
                    c = j;
                }

        if (playerTurn) {
            if (selectedRow == -1 && !board[r][c].equals("") && isWhite(board[r][c])) {
                selectedRow = r;
                selectedCol = c;
                buttons[r][c].setBackground(Color.YELLOW);
            } else if (selectedRow != -1) {
                if (board[r][c].equals("♚")) {
                    JOptionPane.showMessageDialog(this, "You Win!");
                    System.exit(0);
                }

                board[r][c] = board[selectedRow][selectedCol];
                board[selectedRow][selectedCol] = "";
                resetColors();
                updateBoard();
                selectedRow = -1;
                playerTurn = false;
                computerMove();
            }
        }
    }

    void computerMove() {
        int fr, fc, tr, tc;

        while (true) {
            fr = rand.nextInt(8);
            fc = rand.nextInt(8);

            if (isBlack(board[fr][fc])) {
                tr = rand.nextInt(8);
                tc = rand.nextInt(8);

                if (!isBlack(board[tr][tc])) {
                    if (board[tr][tc].equals("♔")) {
                        JOptionPane.showMessageDialog(this, "Computer Wins!");
                        System.exit(0);
                    }

                    board[tr][tc] = board[fr][fc];
                    board[fr][fc] = "";
                    updateBoard();
                    playerTurn = true;
                    break;
                }
            }
        }
    }

    void updateBoard() {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                buttons[i][j].setText(board[i][j]);
    }

    void resetColors() {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                buttons[i][j].setBackground((i + j) % 2 == 0 ? Color.WHITE : Color.GRAY);
    }

    boolean isWhite(String p) {
        return p.equals("♙") || p.equals("♖") || p.equals("♘") || p.equals("♗") || p.equals("♕") || p.equals("♔");
    }

    boolean isBlack(String p) {
        return p.equals("♟") || p.equals("♜") || p.equals("♞") || p.equals("♝") || p.equals("♛") || p.equals("♚");
    }

    public static void main(String[] args) {
        new ChessGUI();
    }
}
