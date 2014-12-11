package projects.mprog.nl.npuzzle10001567;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ImagepickActivity extends Activity implements OnClickListener{

    int dim;
    int scrWidth;
    int scrHeight;
    Bitmap[] allBitmaps = null;
    Boolean canFinish = false;

    Double smallRatio = 0.2;
    int[] mapIdToRes = new int[12];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagepick);

        // Getting the screen size of the device,
        if (Build.VERSION.SDK_INT >= 13){
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            scrWidth = size.x;
            scrHeight = size.y;
            Log.d("Test", "w-h : " + scrWidth + "-" + scrHeight);
        }else{
            Display display = getWindowManager().getDefaultDisplay();
            scrWidth = display.getWidth();
            scrHeight = display.getHeight();
        }
        setAll(); // sets the images in place.

        Bundle gotLevel = getIntent().getExtras();
        dim = gotLevel.getInt("dim");
        String dimStr = Integer.toString(dim);
        TextView tv = (TextView) findViewById(R.id.textViewDimensions);
        tv.setText("The board will be " + dimStr + " x " + dimStr);  // should be in the strings part also...
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (int i=0; i<allBitmaps.length;i++){
            if (allBitmaps[i] != null) allBitmaps[i].recycle();
        }
        if (canFinish) finish();
    }

    public void setAll(){
        int counter = 0;
        int sizeInDp = 150;
//        int imgDim = BitmapConstruct.dpToPx(sizeInDp);
        int imgDim = scrWidth/4;
        int maxImages = 12;
        Bitmap bitmap = null;
        allBitmaps = new Bitmap[maxImages];

        LinearLayout LL = (LinearLayout) findViewById(R.id.puzzleContainer);
        LinearLayout subLL = new LinearLayout(this);
        while(true){
            if (counter % 3 == 0){
                subLL = new LinearLayout(this);
                subLL.setOrientation(LinearLayout.HORIZONTAL);
                LL.addView(subLL);
            }

            String resName = "puzzle_" + counter;
            int resId = getResources().getIdentifier(resName, "drawable", getPackageName());
            Log.d("TEST", "res name:"+ resName );
            Log.d("TEST", "resId:" + resId);

            if (resId !=0) {
                try{
//                    BitmapFactory.Options options = new BitmapFactory.Options();
//                    options.inJustDecodeBounds = true;
//                    BitmapFactory.decodeResource(getResources(), resId, options);
//                    int imageHeight = options.outHeight;
//                    int imageWidth = options.outWidth;
//                    String imageType = options.outMimeType;

                    bitmap = BitmapConstruct.decodeSampledBitmapFromResource(getResources(),resId,imgDim);
                    bitmap = BitmapConstruct.scaledBitmap(bitmap, imgDim);

//                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),resId);
//                    bitmap = BitmapConstruct.scaledBitmap(bitmap, imgDim);

                    ImageButton button = new ImageButton(this);
                    button.setId(counter);
                    button.setOnClickListener(this);
                    button.setPadding(10,10,10,10);
                    button.setImageBitmap(bitmap);
                    subLL.addView(button);
                    mapIdToRes[counter] = resId;
                    allBitmaps[counter] = bitmap;


                }catch (OutOfMemoryError e) {
                    e.printStackTrace();
                    Log.d("TEST", "##NOT ALL IMAGES COULD BE BITMAPPED, too little memory");
                    break;
                }

            }else{
                Log.d("TEST", "##End while loop, no more pictures");
                break;
            }

            counter++;
            if (counter > maxImages){
                Log.d("TEST", "##End while loop, no more than 12 pictures");
                break;
            }
        }

    }

    @Override
    public void onClick(View v) {
        int imagePickedId;
        imagePickedId = mapIdToRes[v.getId()];
        goToPuzzle(v,imagePickedId);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_imagepick, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToPuzzle(View v, int resId){
        Intent i = new Intent(this,PuzzleActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("resId", resId);
        bundle.putInt("dim",dim);
        i.putExtras(bundle);
        canFinish = true;
        startActivity(i);

    }
}
