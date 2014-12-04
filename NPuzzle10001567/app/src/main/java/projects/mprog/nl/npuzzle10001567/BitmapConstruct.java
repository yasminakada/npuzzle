package projects.mprog.nl.npuzzle10001567;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;



/**
 * Created by yasmina on 3-12-2014.
 */
public class BitmapConstruct {
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }


    public static Bitmap scaledBitmap(Bitmap bmp, int imgDim) {
        int h = bmp.getHeight();
        int w = bmp.getWidth();
        int startx = 0;
        int starty = 0;
        if (h > w) {
            starty = (int) (h - w) / 2;
            h = w;
        } else if (w > h) {
            startx = (int) (w - h) / 2;
            w = h;
        }
        bmp = Bitmap.createBitmap(bmp, startx, starty, h, h);
        bmp = Bitmap.createScaledBitmap(bmp, imgDim, imgDim, true);
        return bmp;
    }
}

