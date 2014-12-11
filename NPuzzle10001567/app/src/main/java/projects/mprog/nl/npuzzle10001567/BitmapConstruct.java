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

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqDim) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        int dim;

        if (height >= width){
            dim = width;
        }else{
            dim = height;
        }

        if (dim > reqDim) {

//            final int halfHeight = height / 2;
//            final int halfWidth = width / 2;
            final int halfDim = dim /2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfDim / inSampleSize) > reqDim) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqDim) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqDim);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}

