package projects.mprog.nl.npuzzle10001567;

import android.util.Log;

import java.util.Random;

/**
 * Created by yasmina on 2-12-2014.
 */
public class Puzzle {
    int row0 = -1; // indicates where the zero is
    int col0 = -1; // indicates where the zero is
    int dim;
    int[][] puzzleArray;

    public void start(int dimensions){
        int counter = 1;
        dim = dimensions;
        puzzleArray = new int[dim][dim];
        // set row0 col0 for empty tile at start of the game:
        row0 = dim-1;
        col0 = dim-1;
        // set 1 to dim*dim in the array, in order.
        for (int i=0; i < dim; i++){
            for (int j=0; j < dim; j++){
                puzzleArray[i][j] =  counter;
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
        int v = puzzleArray[row][col];
        puzzleArray[row][col] = 0;
        puzzleArray[row0][col0]= v;
        row0 = row;
        col0 = col;

    }

    public void swap(int row, int col){
        int v = puzzleArray[row][col];

        if (checkSwap(row,col)){
            puzzleArray[row][col] = 0;
            puzzleArray[row0][col0]= v;
            row0 = row;
            col0 = col;
        }
    }

    public boolean checkSwap(int row, int col){
        if ((row0 == row && (col0 == col -1 || col0 == col+1)||
                (col0 == col && (row0 == row -1 || row0 == row+1)))){
            return true;
        }
        return false;
    }

}
