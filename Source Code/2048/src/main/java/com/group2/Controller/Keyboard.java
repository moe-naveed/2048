package com.group2.Controller;

import com.group2.Model.Board;
import com.group2.View.GameView;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Josh Voskamp on 10/19/2015.
 */

public class Keyboard extends KeyAdapter {
    private final GameView GUI;
    private final Board BOARD;
    /**
     * Constructor Creates a controller that links a GameView and Board together
     * @param gui a GameView
     * @param board a Board
    */
    public Keyboard(GameView gui, Board board){
        this.GUI = gui;
        this.BOARD = board;
    }
    /**
     * Modifies the model based on the key pressed, updates GameView to display any changes made
     * @param e KeyEvent
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            BOARD.resetGame();
        }

        if (BOARD.getGameState() == Board.State.IN_PROGRESS) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    BOARD.left();
                    break;
                case KeyEvent.VK_RIGHT:
                    BOARD.right();
                    break;
                case KeyEvent.VK_DOWN:
                    BOARD.down();
                    break;
                case KeyEvent.VK_UP:
                    BOARD.up();
                    break;
            }
        }

        GUI.repaint();
    }
}