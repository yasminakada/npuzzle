package projects.mprog.nl.npuzzle10001567;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;



/**
 * Created by yasmina on 3-12-2014.
 */
public class BitmapConstruct {

    public static Bitmap scale(Bitmap bmp,int scrHeight){
        int h = bmp.getHeight();
        int w = bmp.getWidth();
        int startx = 0;
        int starty = 0;
        if (h>w){
            starty = (int) (h-w)/2;
        }else if(w>h){
            startx = (int) (w-h)/2;
        }
        Bitmap newBmp = Bitmap.createBitmap(bmp,startx,starty,scrHeight,scrHeight);
        return newBmp;
    }

    public static void devideAndAssign(Bitmap bmp, int dim, int scrHeight){
        int counter = 1;
        for (int i = 0; i<dim; i++){
            for(int j =0; j<dim; j++){
//                Bitmap tempBmp =
            }

        }

    }
    public static void assignToImageView(Bitmap bmp, int viewId){

    }


}
