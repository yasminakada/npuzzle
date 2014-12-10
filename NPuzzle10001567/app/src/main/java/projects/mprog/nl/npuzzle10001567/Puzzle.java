package projects.mprog.nl.npuzzle10001567;

import android.util.Log;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by yasmina on 2-12-2014.
 */
public class Puzzle {
    int row0 = -1; // indicates where the zero is
    int col0 = -1; // indicates where the zero is
    int dim;
    int[][] puzzleArray;
    int[][] winArray;

    public void start(int dimensions){
        int counter = 1;
        dim = dimensions;
        puzzleArray = new int[dim][dim];
        winArray = new int[dim][dim];
        // set row0 col0 for empty tile at start of the game:
        row0 = dim-1;
        col0 = dim-1;
        // set 1 to dim*dim in the array, in order.
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
    public void shuffle(int swaps) {
        Random rand = new Random();
        int r = 0;
        int row = row0;
        int col = col0;

        for (int j = 0; j < swaps; j++) {
            r = rand.nextInt(2);
            if (r == 0) {
                if (row == 0) {
                    row++;
                } else if (row == dim-1) {
                    row--;
                } else {
                    r = rand.nextInt(2);
                    if (r == 0) {
                        row--;
                    } else {
                        row++;
                    }
                }
            } else {
                if (col == 0) {
                    col++;
                } else if (col == dim-1){
                    col--;
                } else {
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

    public void swapEmpty(int row, int col){
        // focussed on swapping from the empty tile. Is used for shuffling.
        int v = puzzleArray[row][col];
        puzzleArray[row][col] = 0;
        puzzleArray[row0][col0]= v;
        row0 = row;
        col0 = col;

    }

    public boolean clickSwap(int imageViewId){
        int[] pos = findPosition(imageViewId);
        int row = pos[0];
        int col = pos[1];
        boolean b = swap(row,col);
        return b;
    }

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

    public boolean checkSwap(int row, int col){
        Log.d("TEST", "Row=" + row + "Col=" + col);
        Log.d("TEST", "Row0=" + row0 + "Col0=" + col0);
        if ((row0 == row && (col0 == col -1 || col0 == col+1)||
                (col0 == col && (row0 == row -1 || row0 == row+1)))){
            Log.d("TEST", "Should be able to swap");
            return true;
        }
        return false;
    }

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
    public void setPuzzle(int dimensions, int newRow0, int newCol0, int[] array){
        int counter = 0;
        dim = dimensions;
        resetStart();
        // set row0 col0 for empty tile at start of the game:
        row0 = newRow0;
        col0 = newCol0;
        // set 1 to dim*dim in the array, in order.
        for (int i=0; i < dim; i++){
            for (int j=0; j < dim; j++){
                puzzleArray[i][j] = array[counter];
                counter++;
            }
        }
    }

    public boolean checkWin(){
        if (Arrays.deepEquals(puzzleArray,winArray)){
            return true;
        }
        return false;
    }
    public void resetStart(){
        start(dim);
    }
}
