package projects.mprog.nl.npuzzle10001567;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;



/**
 * Created by yasmina on 3-12-2014.
 */
public class BitmapConstruct {
    public static void something(){
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeResource(getResources(), R.id.myimage, options);
//        int imageHeight = options.outHeight;
//        int imageWidth = options.outWidth;
//        String imageType = options.outMimeType;
    }
    public static Bitmap scaledBitmap(Bitmap bmp,int imgDim){
        int h = bmp.getHeight();
        int w = bmp.getWidth();
        int startx = 0;
        int starty = 0;
        if (h>w){
            starty = (int) (h-w)/2;
            h = w;
        }else if(w>h){
            startx = (int) (w-h)/2;
            w = h;
        }
        bmp = Bitmap.createBitmap(bmp,startx,starty,h,h);
        bmp = Bitmap.createScaledBitmap(bmp,imgDim,imgDim,true);
        return bmp;
    }

//    public void devideAndAssign(Bitmap bmp, int dim, int imgSize){
//        int counter = 1;
//        int step = imgSize/dim;
//        for (int i = 0; i<dim; i++){
//            for(int j =0; j<dim; j++){
//                int x = i * step;
//                int y = j * step;
//                Bitmap tempBmp = Bitmap.createBitmap(bmp,x,y,step,step);
//            }
//
//        }
//
//    }
//    public void assignToImageView(Bitmap bmp, int viewId){
//
//    }


}
