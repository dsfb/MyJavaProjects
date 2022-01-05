package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe extends JFrame implements ActionListener {
    private int gameState = 0;
    private String playerTurn = "X";
    private JLabel statusLabel;
    private boolean xTime = true;

    private JButton buttonA1;
    private JButton buttonB1;
    private JButton buttonC1;

    private JButton buttonA2;
    private JButton buttonB2;
    private JButton buttonC2;

    private JButton buttonA3;
    private JButton buttonB3;
    private JButton buttonC3;

    private JButton resetButton;

    private int counter = 9;

    public TicTacToe() {
        super("Tic Tac Toe");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);

        JPanel content = new JPanel();
        content.setLayout(new BorderLayout(0, 2));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3, 0, 0));

        buttonA1 = new JButton(" ");
        buttonA1.setName("ButtonA1");
        buttonA1.setFocusPainted(false);
        buttonB1 = new JButton(" ");
        buttonB1.setName("ButtonB1");
        buttonB1.setFocusPainted(false);
        buttonC1 = new JButton(" ");
        buttonC1.setName("ButtonC1");
        buttonC1.setFocusPainted(false);
        buttonA2 = new JButton(" ");
        buttonA2.setName("ButtonA2");
        buttonA2.setFocusPainted(false);
        buttonB2 = new JButton(" ");
        buttonB2.setName("ButtonB2");
        buttonB2.setFocusPainted(false);
        buttonC2 = new JButton(" ");
        buttonC2.setName("ButtonC2");
        buttonC2.setFocusPainted(false);
        buttonA3 = new JButton(" ");
        buttonA3.setName("ButtonA3");
        buttonA3.setFocusPainted(false);
        buttonB3 = new JButton(" ");
        buttonB3.setName("ButtonB3");
        buttonB3.setFocusPainted(false);
        buttonC3 = new JButton(" ");
        buttonC3.setName("ButtonC3");
        buttonC3.setFocusPainted(false);

        buttonA1.addActionListener(this);
        buttonA1.setActionCommand("a1");
        buttonB1.addActionListener(this);
        buttonB1.setActionCommand("b1");
        buttonC1.addActionListener(this);
        buttonC1.setActionCommand("c1");
        buttonA2.addActionListener(this);
        buttonA2.setActionCommand("a2");
        buttonB2.addActionListener(this);
        buttonB2.setActionCommand("b2");
        buttonC2.addActionListener(this);
        buttonC2.setActionCommand("c2");
        buttonA3.addActionListener(this);
        buttonA3.setActionCommand("a3");
        buttonB3.addActionListener(this);
        buttonB3.setActionCommand("b3");
        buttonC3.addActionListener(this);
        buttonC3.setActionCommand("c3");

        buttonPanel.add(buttonA3);
        buttonPanel.add(buttonB3);
        buttonPanel.add(buttonC3);
        buttonPanel.add(buttonA2);
        buttonPanel.add(buttonB2);
        buttonPanel.add(buttonC2);
        buttonPanel.add(buttonA1);
        buttonPanel.add(buttonB1);
        buttonPanel.add(buttonC1);

        content.add(buttonPanel, BorderLayout.CENTER);

        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BorderLayout());
        this.statusLabel = new JLabel();
        this.statusLabel.setName("LabelStatus");
        statusPanel.add(this.statusLabel, BorderLayout.WEST);

        resetButton = new JButton("Reset");
        resetButton.setActionCommand("reset");
        resetButton.addActionListener(this);
        resetButton.setName("ButtonReset");
        statusPanel.add(resetButton, BorderLayout.EAST);
        this.changeStatusLabel();
        content.add(statusPanel, BorderLayout.SOUTH);

        setContentPane(content);
        setVisible(true);
    }

    private void changePlayerTurn() {
        if (playerTurn.equals("X")) {
            playerTurn = "O";
        } else {
            playerTurn = "X";
        }
        counter--;
    }

    private boolean processRow(int rowIndex, String player) {
        switch (rowIndex) {
            case 1:
                return player.equals(this.buttonA1.getText()) &&
                        this.buttonB1.getText().equals(this.buttonA1.getText()) &&
                        this.buttonC1.getText().equals(this.buttonA1.getText());
            case 2:
                return player.equals(this.buttonA2.getText()) &&
                        this.buttonB2.getText().equals(this.buttonA2.getText()) &&
                        this.buttonC2.getText().equals(this.buttonA2.getText());
            case 3:
                return player.equals(this.buttonA3.getText()) &&
                        this.buttonB3.getText().equals(this.buttonA3.getText()) &&
                        this.buttonC3.getText().equals(this.buttonA3.getText());
            default:
                return false;
        }
    }

    private boolean processColumn(int rowIndex, String player) {
        switch (rowIndex) {
            case 1:
                return player.equals(this.buttonA1.getText()) &&
                        this.buttonA2.getText().equals(this.buttonA1.getText()) &&
                        this.buttonA3.getText().equals(this.buttonA1.getText());
            case 2:
                return player.equals(this.buttonB1.getText()) &&
                        this.buttonB2.getText().equals(this.buttonB1.getText()) &&
                        this.buttonB3.getText().equals(this.buttonB2.getText());
            case 3:
                return player.equals(this.buttonC1.getText()) &&
                        this.buttonC2.getText().equals(this.buttonC1.getText()) &&
                        this.buttonC3.getText().equals(this.buttonC1.getText());
            default:
                return false;
        }
    }

    private boolean processDiagonals(String player) {
        if (this.buttonA1.getText().equals(player)) {
            return this.buttonA1.getText().equals(this.buttonB2.getText()) &&
                    this.buttonB2.getText().equals(this.buttonC3.getText());
        } else if (this.buttonC1.getText().equals(player)) {
            return this.buttonC1.getText().equals(this.buttonB2.getText()) &&
                    this.buttonB2.getText().equals(this.buttonA3.getText());
        }

        return false;
    }

    private boolean playerWin(String player) {
        boolean result = false;
        for (int i = 1; i <= 3 && !result; i++) {
            result = processRow(i, player);
        }

        for (int i = 1; i <= 3 && !result; i++) {
            result = processColumn(i, player);
        }

        if (!result) {
            result = processColumn(1, player) ||
                    processColumn(2, player) ||
                    processColumn(3, player);
        }

        return result || processDiagonals(player);
    }

    private boolean xWin() {
        return playerWin("X");
    }

    private boolean oWin() {
        return playerWin("O");
    }

    private boolean isDraw() {
        return !xWin() && !oWin() && counter == 0;
    }

    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "a1":
                if (buttonA1.getText().equals(" ") && gameState <= 1) {
                    buttonA1.setText(this.playerTurn);
                    this.processTurn();
                }
                break;
            case "b1":
                if (buttonB1.getText().equals(" ") && gameState <= 1) {
                    buttonB1.setText(this.playerTurn);
                    this.processTurn();
                }
                break;
            case "c1":
                if (buttonC1.getText().equals(" ") && gameState <= 1) {
                    buttonC1.setText(this.playerTurn);
                    this.processTurn();
                }
                break;
            case "a2":
                if (buttonA2.getText().equals(" ") && gameState <= 1) {
                    buttonA2.setText(this.playerTurn);
                    this.processTurn();
                }
                break;
            case "b2":
                if (buttonB2.getText().equals(" ") && gameState <= 1) {
                    buttonB2.setText(this.playerTurn);
                    this.processTurn();
                }
                break;
            case "c2":
                if (buttonC2.getText().equals(" ") && gameState <= 1) {
                    buttonC2.setText(this.playerTurn);
                    this.processTurn();
                }
                break;
            case "a3":
                if (buttonA3.getText().equals(" ") && gameState <= 1) {
                    buttonA3.setText(this.playerTurn);
                    this.processTurn();
                }
                break;
            case "b3":
                if (buttonB3.getText().equals(" ") && gameState <= 1) {
                    buttonB3.setText(this.playerTurn);
                    this.processTurn();
                }
                break;
            case "c3":
                if (buttonC3.getText().equals(" ") && gameState <= 1) {
                    buttonC3.setText(this.playerTurn);
                    this.processTurn();
                }
                break;
            case "reset":
                this.buttonA1.setText(" ");
                this.buttonA2.setText(" ");
                this.buttonA3.setText(" ");
                this.buttonB1.setText(" ");
                this.buttonB2.setText(" ");
                this.buttonB3.setText(" ");
                this.buttonC1.setText(" ");
                this.buttonC2.setText(" ");
                this.buttonC3.setText(" ");
                this.gameState = 0;
                this.counter = 9;
                this.playerTurn = "X";
                this.changeStatusLabel();
            default:
                break;
        }
    }

    private void processTurn() {
        if (this.gameState == 0) {
            this.gameState = 1;
        }

        if (this.gameState == 1) {
            this.changePlayerTurn();
        }

        if (xWin()) {
            this.gameState = 2;
        } else if (oWin()) {
            this.gameState = 3;
        }

        if (isDraw()) {
            this.gameState = 4;
        }

        this.changeStatusLabel();
    }

    private void changeStatusLabel() {
        switch (gameState) {
            case 0:
                this.statusLabel.setText("Game is not started");
                break;
            case 1:
                this.statusLabel.setText("Game in progress");
                break;
            case 2:
                this.statusLabel.setText("X wins");
                break;
            case 3:
                this.statusLabel.setText("O wins");
                break;
            case 4:
                this.statusLabel.setText("Draw");
                break;
            default:
                break;
        }
    }
}