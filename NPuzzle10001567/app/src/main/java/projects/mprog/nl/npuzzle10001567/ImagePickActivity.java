package projects.mprog.nl.npuzzle10001567;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;


public class ImagepickActivity extends Activity implements OnClickListener{
    ImageButton ibPear;
    ImageButton ibApple;
    int dim;
    int scrWidth;
    int scrHeight;
    Bitmap bmpApple;
    Bitmap bmpPear;
    Bitmap bmpAppleSmall;
    Bitmap bmpPearSmall;
    Double smallRatio = 0.2;

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
        }else{
            Display display = getWindowManager().getDefaultDisplay();
            scrWidth = display.getWidth();
            scrHeight = display.getHeight();
        }

        bmpApple = BitmapFactory.decodeResource(getResources(),R.drawable.apple);
        bmpPear = BitmapFactory.decodeResource(getResources(),R.drawable.pear);
        double d = scrHeight*smallRatio;
        int buttonSize = (int)d;
        bmpAppleSmall = Bitmap.createScaledBitmap(bmpApple,buttonSize, buttonSize, true);
        bmpPearSmall = Bitmap.createScaledBitmap(bmpPear,buttonSize, buttonSize, true);

        // These bitmaps will be passed to PuzzleActivity together with the dimensions
        bmpApple = Bitmap.createScaledBitmap(bmpApple,scrHeight, scrHeight, true);
        bmpPear = Bitmap.createScaledBitmap(bmpPear,scrHeight, scrHeight, true);

        ibApple = (ImageButton) findViewById(R.id.ibApple);
        ibApple.setImageBitmap(bmpAppleSmall);
        ibApple.setOnClickListener(this);

        ibPear = (ImageButton) findViewById(R.id.ibPear);
        ibPear.setImageBitmap(bmpPearSmall);
        ibPear.setOnClickListener(this);

        Bundle gotLevel = getIntent().getExtras();
        dim = gotLevel.getInt("dimensions");
        String dimStr = Integer.toString(dim);
        TextView tv = (TextView) findViewById(R.id.textViewDimensions);
        tv.setText("The board will be " + dimStr + " x " + dimStr);  // should be in the strings part also...
    }

    @Override
    public void onClick(View v) {
        int imagePicked = R.drawable.apple;
        if (v.getId() == R.id.ibApple){
            imagePicked = R.drawable.apple;
        }else if(v.getId() == R.id.ibPear){
            imagePicked = R.drawable.pear;
        }
        goToPuzzle(v,imagePicked);
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
        startActivity(i);

    }
}
