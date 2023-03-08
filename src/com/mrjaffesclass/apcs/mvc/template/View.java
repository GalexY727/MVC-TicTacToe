package com.mrjaffesclass.apcs.mvc.template;
import com.mrjaffesclass.apcs.messenger.*;

import javax.swing.*;

public class View extends JFrame implements MessageHandler {

    private final Messenger mvcMessaging;

    private String turn = "X";
    private JPanel tictactoePanel;
    private JButton topMid;
    private JButton midMid;
    private JButton bottomMid;
    private JButton rightMid;
    private JButton leftMid;
    private JButton rightTop;
    private JButton rightBottom;
    private JButton leftTop;
    private JButton leftBottom;
    private JLabel ticTacToeLabel;

    public View(Messenger messages) {
        mvcMessaging = messages;   // Save the calling controller instance
        initComponents();         // Initialize the GUI components
    }

    private void initComponents() {
        setContentPane(tictactoePanel);
        setTitle("Tic Tac Toe");
        setSize(300, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        topMid.addActionListener(e -> {
            setTile(topMid);
        });
        midMid.addActionListener(e -> {
            setTile(midMid);
        });
        bottomMid.addActionListener(e -> {
            setTile(bottomMid);
        });
        rightMid.addActionListener(e -> {
            setTile(rightMid);
        });
        leftMid.addActionListener(e -> {
            setTile(leftMid);
        });
        rightTop.addActionListener(e -> {
            setTile(rightTop);
        });
        rightBottom.addActionListener(e -> {
            setTile(rightBottom);
        });
        leftTop.addActionListener(e -> {
            setTile(leftTop);
        });
        leftBottom.addActionListener(e -> {
            setTile(leftBottom);
        });

    }

    /**
     * Initialize the model here and subscribe
     * to any required messages
     */
    public void init() {
        this.mvcMessaging.subscribe("boardChange", this);
        this.mvcMessaging.subscribe("gameOver", this);
    }

    @Override
    public void messageHandler(String messageName, Object messagePayload) {
        if (messagePayload != null) {
            System.out.println("MSG: received by view: "+messageName+" | "+messagePayload.toString());
        } else {
            System.out.println("MSG: received by view: "+messageName+" | No data sent");
        }
        if (messageName.equals("boardChange")) {
            // Get the message payload and cast it as a 2D string array since we
            // know that the model is sending out the board data with the message
            String[][] board = (String[][])messagePayload;
            // Now set the button text with the contents of the board
            leftTop.setText(board[0][0]);
            topMid.setText(board[0][1]);
            rightTop.setText(board[0][2]);
            leftMid.setText(board[1][0]);
            midMid.setText(board[1][1]);
            rightMid.setText(board[1][2]);
            leftBottom.setText(board[2][0]);
            bottomMid.setText(board[2][1]);
            rightBottom.setText(board[2][2]);
        }
    }


    public void closeIfWinner() {
        if (!isWinner() && isFull()) {
            JOptionPane.showMessageDialog(null, "Draw");
            dispose();
        } else if (isWinner()) {
            JOptionPane.showMessageDialog(null, "Winner is " + (turn.equals("X") ? "O" : "X"));
            dispose();
        }
    }

    public boolean isFull() {
        for (JButton button : new JButton[]{topMid, midMid, bottomMid, rightMid, leftMid, rightTop, rightBottom, leftTop, leftBottom}) {
            if (button.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void setTile(JButton button) {
        if (!button.getText().isEmpty()) {
            return;
        }
        switch (turn) {
            case "X" -> {
                button.setText("X");
                turn = "O";
            }
            case "O" -> {
                button.setText("O");
                turn = "X";
            }
        }
        this.mvcMessaging.notify("playerMove", button.getName());
    }

    public boolean isWinner(){
        if (    topMid.getText().equals(midMid.getText()) &&
                midMid.getText().equals(bottomMid.getText()) &&
                !topMid.getText().isEmpty())
            return true;

        if (    rightTop.getText().equals(rightMid.getText()) &&
                rightMid.getText().equals(rightBottom.getText()) &&
                !rightTop.getText().isEmpty())
            return true;

        if (    leftTop.getText().equals(leftMid.getText()) &&
                leftMid.getText().equals(leftBottom.getText()) &&
                !leftTop.getText().isEmpty())
            return true;

        if (    leftTop.getText().equals(midMid.getText()) &&
                midMid.getText().equals(rightBottom.getText()) &&
                !leftTop.getText().isEmpty())
            return true;

        if (    rightTop.getText().equals(midMid.getText()) &&
                midMid.getText().equals(leftBottom.getText()) &&
                !rightTop.getText().isEmpty())
            return true;

        if (    leftTop.getText().equals(topMid.getText()) &&
                topMid.getText().equals(rightTop.getText()) &&
                !leftTop.getText().isEmpty())
            return true;

        if (    leftMid.getText().equals(midMid.getText()) &&
                midMid.getText().equals(rightMid.getText()) &&
                !leftMid.getText().isEmpty())
            return true;

        if (    leftBottom.getText().equals(bottomMid.getText()) &&
                bottomMid.getText().equals(rightBottom.getText()) &&
                !leftBottom.getText().isEmpty())
            return true;

        return false;
    }
}
