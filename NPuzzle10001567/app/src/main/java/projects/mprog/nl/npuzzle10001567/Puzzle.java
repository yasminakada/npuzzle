package projects.mprog.nl.npuzzle10001567;

import android.util.Log;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by yasmina on 2-12-2014.
 */
public class Puzzle {
    int row0 = -1; // indicates where the zero is, needed for shuffle
    int col0 = -1; // indicates where the zero is, needed for shuffle
    int dim;
    int[][] puzzleArray;
    int[][] winArray;

    // Creates an array (puzzle) to play the game with
    // and creates the winning array.
    public void start(int dimensions){

        int counter = 1;
        dim = dimensions;
        puzzleArray = new int[dim][dim];
        winArray = new int[dim][dim];

        // row0 and col0 are always positioned at the last tile in the beginning.
        row0 = dim-1;
        col0 = dim-1;

        // Set the array values from 1 to dim*dim.
        // With the last tile getting value 0.
        for (int i=0; i < dim; i++){
            for (int j=0; j < dim; j++){
                puzzleArray[i][j] =  counter;
                winArray[i][j] =  counter;
                counter++;
                if (counter == dim*dim){
                    counter = 0;
                }
            }
        }
    }

    // Shuffling is focussed on swapping tiles with the empty tiles.
    // This is done by picking a tile adjecent to the empty tile randomly.
    public void shuffle(int swaps) {
        Random rand = new Random();
        int r = 0; // Holds random value.
        int row = row0;
        int col = col0;

        // Swap with tiles randomly, do not swap with non existent tiles
        for (int j = 0; j < swaps; j++) {
            r = rand.nextInt(2);
            if (r == 0) { // Swap left or right.
                if (row == 0) { // Can only swap left.
                    row++;
                } else if (row == dim-1) { // Can only swap right.
                    row--;
                } else { // can swap right or left.
                    r = rand.nextInt(2);
                    if (r == 0) {
                        row--;
                    } else {
                        row++;
                    }
                }
            } else { // Swap up or down.
                if (col == 0) { // Can only swap down.
                    col++;
                } else if (col == dim-1){ // Can only swap up.
                    col--;
                } else { // Can swap down or up.
                    r = rand.nextInt(2);
                    if (r == 0) {
                        col--;
                    } else {
                        col++;
                    }
                }
            }
            swapEmpty(row,col);
        }
    }

    // Swap empty tile with some other tile. Only used for shuffling.
    public void swapEmpty(int row, int col){
        int v = puzzleArray[row][col];
        puzzleArray[row][col] = 0;
        puzzleArray[row0][col0]= v;
        row0 = row;
        col0 = col;
    }

    // Swap tile when clicked.
    // Only swap when a move is possible (tile is adjacent to empty tile).
    public boolean clickSwap(int imageViewId){
        int[] pos = findPosition(imageViewId);
        int row = pos[0];
        int col = pos[1];
        boolean b = swap(row,col); // check if swap is possible then swap.
        return b;
    }

    // Swap a tile if the swap is possible (tile is adjacent to empty tile).
    public boolean swap(int row, int col){
        int v = puzzleArray[row][col];

        if (checkSwap(row,col)){
            puzzleArray[row][col] = 0;
            puzzleArray[row0][col0]= v;
            row0 = row;
            col0 = col;
            return true;
        }
        return false;
    }

    // Checks if swap is possible (tile is adjacent to empty tile).
    public boolean checkSwap(int row, int col){
        if ((row0 == row && (col0 == col -1 || col0 == col+1)||
                (col0 == col && (row0 == row -1 || row0 == row+1)))){
            return true;
        }
        return false;
    }

    // Finds the row and col of an imageView
    // is used for swapping tiles.
    public int[] findPosition(int imageViewId){
        int[] pos = new int[2];
        int counter = 1;
        for (int i=0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if(counter == imageViewId){
                    pos[0] = i;
                    pos[1] = j;
                    return pos;
                }
                counter++;
                if (counter == dim * dim){
                    counter = 0;
                }

            }
        }
        return pos;
    }

    // Sets a puzzle to a state (array in order).
    // is used to set a puzzle to a saved gamestate.
    public void setPuzzle(int dimensions, int newRow0, int newCol0, int[] array){
        int counter = 0;
        dim = dimensions;
        resetStart();

        // Set row0 col0 for empty tile at start of the game:
        row0 = newRow0;
        col0 = newCol0;

        // Set array from 1 to dim*dim, in order.
        for (int i=0; i < dim; i++){
            for (int j=0; j < dim; j++){
                puzzleArray[i][j] = array[counter];
                counter++;
            }
        }
    }

    // Checks if a gamestate is a winning state.
    public boolean checkWin(){
        if (Arrays.deepEquals(puzzleArray,winArray)){
            return true;
        }
        return false;
    }

    // Resets puzzle to a completed state (start).
    public void resetStart(){
        start(dim);
    }
}
