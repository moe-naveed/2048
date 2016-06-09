package com.group2.Model;

import java.awt.*;

/**
 * Created by Josh Voskamp on 10/19/2015.
 */

public class Tile {
    private int value = 0;

    /**
    * Creates a tile with a specified value
    * @param x an int argument
    */
    public Tile(int x) {
        value = x;
    }
    /**
     * Gets the Foreground Colour, returns a different color based on the value
     * @return colour
     */
    public Color getForeground() {
        if (value <16){
            return new Color(0x776e65);
        }
        else return new Color(0xf9f6f2);
        //return value < 16 ? new Color(0x776e65) :  new Color(0xf9f6f2);
    }
    /**
    * Sets the tile to a specific colour depending on the value of the tile.
    * @return colour object, the colour of the tile
    */
    public Color getBackground() {
        switch (value) {
            case 2:    return new Color(0xeee4da);
            case 4:    return new Color(0xede0c8);
            case 8:    return new Color(0xf2b179);
            case 16:   return new Color(0xf59563);
            case 32:   return new Color(0xf67c5f);
            case 64:   return new Color(0xf65e3b);
            case 128:  return new Color(0xedcf72);
            case 256:  return new Color(0xedcc61);
            case 512:  return new Color(0xedc850);
            case 1024: return new Color(0xedc53f);
            case 2048: return new Color(0xedc22e);
        }
        return new Color(0xcdc1b4);
    }
    /**
    * Sets a function to return its value when called.
    * @return int, the value
    */
    public int getValue() {
        return value;
    }

    public void setValue(int v){
        value = v;
    }
}
