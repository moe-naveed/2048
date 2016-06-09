package com.group2.View;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Josh Voskamp on 11/25/2015.
 */
public class Help extends JPanel {
    private final Color BG_COLOR = new Color(0xbbada0);
    private final Color TEXT_COLOR = new Color(0x776e65);
    public Help(int w, int h){
        setPreferredSize(new Dimension(w,h));
    }

    /**
     * Draws the graphics
     * @param g is a graphics object
     */
    @Override
    public void paint(Graphics g) {
        g.setColor(BG_COLOR);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(TEXT_COLOR);

        //Just a Scaling Factor
        int size = getHeight()/13;
        g.setFont(new Font("Arial", Font.BOLD, size));
        drawCenteredString(g, "Join tiles to create 2048", 0, 0, getWidth(),getHeight()/2);
        drawCenteredString(g, "Arrow Keys to move", 0, 0, getWidth(), getHeight());
        drawCenteredString(g, "ESC to start a new game" , 0, getHeight()/2,getWidth(), getHeight()/2);
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
