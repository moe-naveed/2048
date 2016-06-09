package com.group2.Model;

import java.util.Random;

/**
 * Created by Josh Voskamp on 10/19/2015.
 */

public class Board {

    public enum State {
        WIN,LOSE,IN_PROGRESS
    }

    private final int TARGET = 2048;
    private final int GRID_WIDTH;
    private Tile[][] grid;
    private State gameState;
    private Random random = new Random();
    private int score = 0;
    private int highTile = 0;

    /**
    * Creates a fresh Game Board
    */
    public Board(int w) {
        GRID_WIDTH = w;
        grid = new Tile[GRID_WIDTH][GRID_WIDTH];
        resetGame();
    }

    public Board() {
        GRID_WIDTH = 4;
        grid = new Tile[GRID_WIDTH][GRID_WIDTH];
        resetGame();
    }

    /**
    * Sets the parameters for resetting the game
    */
    public void resetGame() {
        gameState = State.IN_PROGRESS;
        this.score = 0;
        this.highTile = 0;
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_WIDTH; y++) {
                grid[x][y] = new Tile(0);
            }
        }
        addNewTile();
        addNewTile();
    }

    /**
     * Adds a new tile randomly to any empty locations
     */
    private void addNewTile() {
        int value = (random.nextInt(10) < 9) ?  2 : 4;

        boolean locationFound = false;
        while(!locationFound) {
            int x = random.nextInt(GRID_WIDTH);
            int y = random.nextInt(GRID_WIDTH);
            if (grid[x][y].getValue()==0) {
                grid[x][y] = new Tile(value);
                locationFound = true;
            }
        }
    }

    /**
     * Performs the left shift on the board
     */
    public void left(){
        int[][] old = copy();
        moveLeft();
        //Check if tiles can join
        for (int i = 0; i < GRID_WIDTH; i++) {
            for (int j = 1; j < GRID_WIDTH; j++) {
                int value = grid[i][j].getValue();
                if (grid[i][j-1].getValue() == value) {
                    grid[i][j-1].setValue(value*2);
                    updateScore(value*2);
                    grid[i][j].setValue(0); //temporarily 0 which will go away after another moveLeft()
                }
            }
        }
        moveLeft();
        if(isMoved(old) && !isGridFull()){
            addNewTile();
        }
        updateStatus();
    }

    /**
     * Helper method for performing the left shift
     * Moves all tiles to the left, does not join tiles
     */
    private void moveLeft() {
        //move tiles with values as far left as possible
        for (int i = 0; i < GRID_WIDTH; i++) {
            int[] row = new int[GRID_WIDTH];
            for (int j = 0; j < GRID_WIDTH; j++) {
                if (grid[i][j].getValue() != 0) {
                    int curr = 0;
                    while(row[curr] != 0) {
                        curr++;
                    }
                    row[curr] = grid[i][j].getValue();
                }
            }
            for(int k = 0; k < GRID_WIDTH; k++){
                grid[i][k].setValue(row[k]);
            }

        }
    }

    /**
     * Rotates the board Clockwise to allow the left logic to be applied
     */
    private void rotateCW() {
        Tile[][] rotated = new Tile[GRID_WIDTH][GRID_WIDTH];
        for (int r = 0; r < GRID_WIDTH; r++) {
            for (int c = 0; c < GRID_WIDTH; c++) {
                rotated[c][GRID_WIDTH-1-r] = grid[r][c];
            }
        }
        grid = rotated;
    }

    /**
     * Rotates the board Counter Clockwise to allow the left logic to be applied
     */
    private void rotateCCW() {
        Tile[][] rotated = new Tile[GRID_WIDTH][GRID_WIDTH];
        for (int r = 0; r < GRID_WIDTH; r++) {
            for (int c = 0; c < GRID_WIDTH; c++) {
                rotated[GRID_WIDTH-1-c][r] = grid[r][c];
            }
        }
        grid = rotated;
    }

    /**
     * Performs the left shift on the board
     */
    public void right(){
        rotateCCW();
        rotateCCW();
        left();
        rotateCW();
        rotateCW();
    }

    /**
     * Performs the left shift on the board
     */
    public void up(){
        rotateCCW();
        left();
        rotateCW();
    }

    /**
     * Performs the left shift on the board
     */
    public void down(){
        rotateCW();
        left();
        rotateCCW();
    }

    /**
     * Makes a copy of the grids values in an int array
     */
    private int[][] copy() {
        int[][] copy = new int[GRID_WIDTH][GRID_WIDTH];
        for (int i = 0; i < GRID_WIDTH; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                copy[i][j] = grid[i][j].getValue();
            }
        }
        return copy;
    }

    /**
     * Compares the current grid values to the values stored in the given int array
     * @param old an int array containing the previous state of the grids values
     * @return returns true if any of the values are in different indexes, else returns false
     */
    private boolean isMoved(int[][] old){
        for(int x = 0; x < GRID_WIDTH; x++){
            for(int y = 0; y < GRID_WIDTH; y++){
                if(grid[x][y].getValue() != old[x][y]){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Updates the score of the game, updates the highTile if a new highTile is created
     * @param value adds the given int to the current score
     */
    private void updateScore(int value){
        if(value > this.highTile){
            this.highTile = value;
        }
        this.score = this.score + value;
    }

    /**
     * Checks the status of the grid, and changes the state of the game
     */
    private void updateStatus(){
        if(!isMovePossible() && isGridFull()){
            gameState = State.LOSE;
        }
        if(highTile == TARGET){
            gameState = State.WIN;
        }
    }

    /**
     * Returns true if the grid is full of all non 0 tiles, else returns false
     */
    private boolean isGridFull() {
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_WIDTH; y++) {
                if (grid[x][y].getValue() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns true IF there are any tiles that can be joined, ELSE returns false
     */
    private boolean isMovePossible() {
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < (GRID_WIDTH - 1); y++) {
                int y2 = y + 1;
                if (grid[x][y].getValue() == grid[x][y2].getValue()) {
                    return true;
                }
            }
        }

        for (int y = 0; y < GRID_WIDTH; y++) {
            for (int x = 0; x < (GRID_WIDTH - 1); x++) {
                int x2 = x + 1;
                if (grid[x][y].getValue() == grid[x2][y].getValue()) {
                    return true;
                }
            }
        }

        return false;
    }

    public Tile[][] getGrid() {
        return grid;
    }

    public int getScore() {
        return score;
    }

    public State getGameState() {
        return gameState;
    }

    public int getGRID_WIDTH() {
        return GRID_WIDTH;
    }
}