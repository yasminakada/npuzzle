package projects.mprog.nl.npuzzle10001567;

import java.util.Random;

/**
 * Created by yasmina on 2-12-2014.
 */
public class Puzzle {
    int row0 = -1; // indicates where the zero is
    int col0 = -1; // indicates where the zero is
    int[][] puzzleArray = new int[0][];
    int dim;

    public void start(int dimensions){
        int counter = 1;
        dim = dimensions;

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
    public void shuffleArray(int swaps) {
        Random rand = new Random();
        int r = 0;
        int row = row0;
        int col = col0;

        for (int j = 0; j < swaps; j++) {
            r = rand.nextInt(1);
            if (r == 0) {
                if (row == 0) {
                    row++;
                } else if (row == 2) {
                    row--;
                } else {
                    r = rand.nextInt(1);
                    if (r == 0) {
                        row--;
                    } else {
                        row++;
                    }
                }
            } else {
                if (col == 0) {
                    col++;
                } else if (col == 2) {
                    col--;
                } else {
                    r = rand.nextInt(1);
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

//     GARBAGE:
//    public boolean checkSwap(int row, int col){
//        // checks if a tile can be swapped with the empty tile
//        // only when it is adjacent to the empty tile
//        // returns true if a swap can take place
//
//        if (puzzleArray[row][col] == 0) {
//            return true;
//        }
//        return false;
//    }

//        GARBAGE:
//    public void swapTile(int row, int col, int dim){
//        int v = puzzleArray[row][col];
//
//        for (int i = row-1; i<row+3; i =i+2){
//            if(i > 0 && i < dim){
//                if (checkSwap(i,col)){
//                    puzzleArray[row][col] = 0;
//                    row0 = row;
//                    col0 = col;
//                    puzzleArray[i][col]= v;
//                    return;
//                }
//            }
//        }
//        for (int i = col-1; i<col+3; i =i+2){
//            if(i > 0 && i < dim){
//                if (checkSwap(row,i)){
//                    puzzleArray[row][col] = 0;
//                    row0 = row;
//                    col0 = col;
//                    puzzleArray[row][i]= v;
//                    return;
//                }
//            }
//        }
//    }
//

}
