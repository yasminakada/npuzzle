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
    int maxImages = 12;
    Bitmap[] allBitmaps = null;
    Boolean canFinish = false;

    int[] mapIdToRes = new int[maxImages];

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

        // Get info from LevelActivity.
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

    // Sets all images that can be turned into a puzzle
    // and shows them as buttons on the screen.
    public void setAll(){
        int counter = 0;
        int pad = 10; //padding
        int maxImagesOnRow = 3;
        int imgDim = scrWidth/4;
        Bitmap bitmap = null;
        allBitmaps = new Bitmap[maxImages];

        LinearLayout LL = (LinearLayout) findViewById(R.id.puzzleContainer);
        LinearLayout subLL = new LinearLayout(this);

        while(true){
            if (counter % maxImagesOnRow == 0){
                subLL = new LinearLayout(this);
                subLL.setOrientation(LinearLayout.HORIZONTAL);
                LL.addView(subLL);
            }

            String resName = "puzzle_" + counter;
            int resId = getResources().getIdentifier(resName, "drawable", getPackageName());
            if (resId !=0) {
                try{
                    // Load images efficiently:
                    bitmap = BitmapConstruct.decodeSampledBitmapFromResource(getResources(),resId,imgDim);
                    bitmap = BitmapConstruct.scaledBitmap(bitmap, imgDim);

                    ImageButton button = new ImageButton(this);
                    button.setId(counter);
                    button.setOnClickListener(this);
                    button.setPadding(pad,pad,pad,pad);
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
                Log.d("TEST", "##End while loop, maximum images reached");
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

    // Starts PuzzleActivity, with chosen image and difficulty.
    // After goToPuzzle is called, this activity will be finished.
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
