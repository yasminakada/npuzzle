package projects.mprog.nl.npuzzle10001567;

/**
 * Created by yasmina on 2-12-2014.
 */
public class Puzzle {

    public static int[][] getArray(int dim){
        int[][] array = new int[0][];
        int counter = 1;

        for (int i=0; i < dim; i++){
            for (int j=0; i < dim; i++){
                array[i][j] =  counter;
                counter++;
                if (counter == dim*dim){
                    counter = 0;
                }

            }
        }
        return array;
    }
    public static int[][] shuffleArray(int[][] a){
        int swaps = 100;

        
        return a;
    }

    public static int[][] swapArray(int[][] a){


        return a;
    }
}
