package com.group2.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoardTest {
    private Board board;

    @Before
    public void setUp(){
        board = new Board();
        cleanBoard();
    }

    @After
    public void tearDown(){
        board = null;
    }

    @Test
    public void testLeft(){
        setTile(0,0,2);
        setTile(0,1,2);
        board.left();
        assertEquals(getTile(0, 0), 4);
        cleanBoard();
        setTile(0,0,1024);
        setTile(0,1,1024);
        board.left();
        assertEquals(getTile(0,0),2048);
        cleanBoard();
        setTile(3,0,512);
        board.left();
        assertEquals(getTile(3,0),512);
    }

    @Test
    public void testRight(){
        setTile(0,0,2);
        setTile(0,1,2);
        board.right();
        assertEquals(getTile(0,3),4);
        cleanBoard();
        setTile(3,3,128);
        board.right();
        assertEquals(getTile(3,3),128);
    }

    @Test
    public void testUp(){
        setTile(0,1,2);
        setTile(1,1,2);
        board.up();
        assertEquals(getTile(0,1),4);
        cleanBoard();
        setTile(0,2,256);
        board.up();
        assertEquals(getTile(0,2),256);
    }

    @Test
    public void testDown(){
        setTile(0,1,16);
        setTile(1,1,16);
        board.down();
        assertEquals(getTile(3,1),32);
        cleanBoard();
        setTile(3,1,64);
        board.down();
        assertEquals(getTile(3,1),64);
    }

    @Test
    public void getGameState(){
        assertEquals(Board.State.IN_PROGRESS,board.getGameState());
        //Winning State
        setTile(0,0,1024);
        setTile(0,1,1024);
        board.left();
        assertEquals(Board.State.WIN,board.getGameState());
        cleanBoard();

        //Losing State
        setTile(0,0,32);
        setTile(0,1,256);
        setTile(0,2,64);
        setTile(0,3,2);
        setTile(1,0,8);
        setTile(1,1,64);
        setTile(1,2,8);
        setTile(1,3,16);
        setTile(2,0,16);
        setTile(2,1,8);
        setTile(2,2,4);
        setTile(2,3,8);
        setTile(3,0,2);
        setTile(3,1,4);
        setTile(3,2,16);
        setTile(3,3,2);
        board.left();
        assertEquals(Board.State.LOSE, board.getGameState());
        cleanBoard();
    }

    //Clears entire board
    public void cleanBoard(){
        board.resetGame();
        Tile[][] temp = board.getGrid();
        for(int i = 0; i < temp.length; i++){
            for(int j = 0; j < temp[i].length;j++){
                temp[i][j].setValue(0);
            }
        }
    }

    public void setTile(int row, int col, int v){
        board.getGrid()[row][col].setValue(v);
    }

    public int getTile(int row, int col){
        return board.getGrid()[row][col].getValue();
    }

}