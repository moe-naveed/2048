package com.group2.View;

import javax.swing.*;
import java.awt.*;

import com.group2.Model.Board;
import com.group2.Model.Tile;


/**
 * Created by Josh Voskamp on 10/19/2015.
 */

public class GameView extends JPanel{
    private final Color BG_COLOR = new Color(0xbbada0);
    private final String FONT_NAME = "Arial";
    private final int GRID_WIDTH;
    private final int TILE_SIZE;
    private final int TILES_MARGIN;
    private final Board BOARD;

    /**
    * Initializes the Game Board's title and margin size
    * @param board a Board object
    */
    public GameView(Board board){
        this.BOARD = board;
        this.GRID_WIDTH = board.getGRID_WIDTH();
        //Divide the screen resolution by 7, then round to the nearest 10
        this.TILE_SIZE = Math.round((GameWindow.screenHeight() / (GRID_WIDTH+2) + 5)/10) * 10;
        //1 TILE_MARGIN is 1/20 of a TILE_SIZE
        this.TILES_MARGIN = TILE_SIZE/20;
    }

    /**
     * Returns the size needed to display the gui, including all the tiles, margins between tiles and score bar at the bottom
     * @return Dimension
     */
    @Override
    public Dimension getPreferredSize() {
        int w = (TILE_SIZE * GRID_WIDTH) + (TILES_MARGIN * ((GRID_WIDTH+1)));
        int h = (TILE_SIZE * GRID_WIDTH) + (TILES_MARGIN * ((GRID_WIDTH+1))) + (TILE_SIZE / 2);
        return new Dimension(w,h);
    }

    /**
     * Draws the graphics
     * @param g is a graphics object
     */
    @Override
    public void paint(Graphics g) {
        g.setColor(BG_COLOR);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        for (int y = 0; y < GRID_WIDTH; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                //TODO FIGURE OUT WHY THE X AND Y need to be reversed when drawing the tiles/values
                drawTile(g, BOARD.getGrid()[x][y], y, x);
            }
        }
    }

    /**
     * Sets Font based on Type and size specified
     * @param g is a graphics object
     * @param Type is a string
     * @param Size is an int
     */
    private void setFont(Graphics g, String Type,int Size){
        if (Type.equals("BOLD")){
            g.setFont(new Font(FONT_NAME, Font.BOLD, Size));
        }
        else if (Type.equals("PLAIN")){
            g.setFont(new Font(FONT_NAME, Font.PLAIN, Size));
        }
    }

    /**
     *Sets Color based on r,g,b values and an alpha value which indicated transparency
     * @param gr is a graphics object
     * @param r is an int
     * @param g is an int
     * @param b is an int
     * @param a is an int
     */
    private void setColor(Graphics gr, int r, int g, int b, int a){
        if (a == 0){
            gr.setColor(new Color (r, g, b));
        }
        if (a != 0){
            gr.setColor(new Color (r, g, b, a));
        }
    }

    /**
    * Sets Board up and displays notifications for when the game is lost or won or can be restarted.
    * @param g2 a Graphics object
    * @param tile a tile object
    * @param x an int argument
    * @param y an int argument
    */
    private void drawTile(Graphics g2, Tile tile, int x, int y) {
        Graphics2D g = ((Graphics2D) g2);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        int value = tile.getValue();
        int xOffset = offsetCoors(x);
        int yOffset = offsetCoors(y);
        g.setColor(tile.getBackground());

        g.fillRoundRect(xOffset, yOffset, TILE_SIZE, TILE_SIZE, 14, 14);
        g.setColor(tile.getForeground());

        //TODO Restructure into a regular if
        final int NUMBER_SIZE = value < 100 ? (int)(TILE_SIZE*0.7) : value < 1000 ? (int)(TILE_SIZE*0.5) : (int)(TILE_SIZE*0.4);

        setFont(g, "BOLD", NUMBER_SIZE); //sets font based on size of the number

        String s = String.valueOf(value);

        if (value != 0) {
            drawCenteredString(g,s,xOffset,yOffset,TILE_SIZE,TILE_SIZE);
        }


        if (BOARD.getGameState() != Board.State.IN_PROGRESS) {
            setColor(g, 255, 255, 255, 30);
            g.fillRect(0, 0, getWidth(), getHeight());

            setFont(g, "PLAIN", TILE_SIZE/5);
            setColor(g, 128, 128, 128, 128);


            drawCenteredString(g,"Press ESC to play again",0,0,this.getWidth(),TILE_SIZE+TILES_MARGIN);

            setColor(g, 78, 139, 202, 0);
            setFont(g, "BOLD", TILE_SIZE/2);

            if (BOARD.getGameState() == Board.State.WIN) {
                drawCenteredString(g,"You won!",0,0,this.getWidth(),this.getHeight());
            }
            if (BOARD.getGameState() == Board.State.LOSE) {
                drawCenteredString(g,"You lose!",0,0,this.getWidth(),this.getHeight());
            }
        }

        setColor(g, 128, 128, 128, 128);

        // /This needs to be scaled
        setFont(g, "PLAIN", TILE_SIZE/4);

        //Change this line to scale
        drawCenteredString(g,("Score: " + BOARD.getScore()),0,this.getHeight()-TILE_SIZE/2,this.getWidth(),TILE_SIZE/2);

    }

    /**
     * Calculates the coordinate to start drawing a tile at using the tile number.
     * @param arg an int argument
     * @return int, the coordinates
     */
    private int offsetCoors(int arg) {
        return arg * (this.TILES_MARGIN + this.TILE_SIZE) + this.TILES_MARGIN;
    }

    /**
     * Displays the given string centered within the given rectangle
     * @param g a graphics object
     * @param s a string argument
     * @param x an integer argument
     * @param y an integer argument
     * @param width an integer argument
     * @param height an integer argument
     */
    private void drawCenteredString(Graphics g, String s, int x, int y, int width, int height) {
        // Find the size of string s in the font of the Graphics context "page"
        FontMetrics fm   = g.getFontMetrics(g.getFont());
        java.awt.geom.Rectangle2D rect = fm.getStringBounds(s, g);
        int textHeight = (int)(rect.getHeight());
        int textWidth  = (int)(rect.getWidth());

        // Center text horizontally and vertically within provided rectangular bounds
        int textX = x + (width - textWidth)/2;
        int textY = y + (height - textHeight)/2 + fm.getAscent();
        g.drawString(s, textX, textY);
    }

}