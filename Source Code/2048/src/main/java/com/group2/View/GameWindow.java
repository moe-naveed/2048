package com.group2.View;

import com.group2.Controller.Keyboard;
import com.group2.Model.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Josh Voskamp on 11/25/2015.
 */
public class GameWindow extends JFrame {
    private final Font MENU_FONT;
    public GameWindow(){
        setTitle("2048");
        Board board = new Board(4);
        GameView game = new GameView(board);
        add(game);
        addKeyListener(new Keyboard(game, board));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        MENU_FONT = new Font("Arial",0,screenHeight()/75);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Define and add two drop down menu to the menubar
        JMenu boardSizeMenu = new JMenu("Board Size");
        JMenu optionsMenu = new JMenu("Options");

        menuBar.add(boardSizeMenu);
        menuBar.add(optionsMenu);

        JMenuItem help = new JMenuItem("Help");
        JMenuItem new4x4 = new JMenuItem("4 x 4");
        JMenuItem new8x8 = new JMenuItem("8 x 8");
        JMenuItem new16x16 = new JMenuItem("16 x 16");

        help.addActionListener(addHelpClick());
        new4x4.addActionListener(addMenuClick(4));
        new8x8.addActionListener(addMenuClick(8));
        new16x16.addActionListener(addMenuClick(16));

        optionsMenu.add(help);
        boardSizeMenu.add(new4x4);
        boardSizeMenu.add(new8x8);
        boardSizeMenu.add(new16x16);

        boardSizeMenu.setFont(MENU_FONT);
        optionsMenu.setFont(MENU_FONT);
        help.setFont(MENU_FONT);
        new8x8.setFont(MENU_FONT);
        new4x4.setFont(MENU_FONT);
        new16x16.setFont(MENU_FONT);

        pack();
        setVisible(true);
    }

    //Creates the ActionListeners for each new board size
    private ActionListener addMenuClick(int w){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                getContentPane().removeAll();
                Board board = new Board(w);
                GameView game = new GameView(board);
                add(game);
                addKeyListener(new Keyboard(game, board));
                pack();
                setLocationRelativeTo(null);
            }
        };
    }

    //Creates the ActionListeners for each new board size
    private ActionListener addHelpClick(){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JFrame help = new JFrame("Help");
                help.add(new Help(screenHeight()/3, screenHeight()/3));
                help.setVisible(true);
                help.setResizable(false);
                help.pack();
                help.setLocationRelativeTo(null);
            }
        };
    }

    /**
     * Returns the screen height to be used in scaling the game window.
     * @return int, height of screen
     */
    public static int screenHeight(){
        return (int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight();
    }

}
