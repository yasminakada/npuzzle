package projects.mprog.nl.npuzzle10001567;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class PuzzleActivity extends Activity implements View.OnClickListener {
    Button winButton;
    String imageName;
    ImageView iv;
    Bitmap bmp = null;
    int dim;
    int scrWidth;
    int scrHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        winButton = (Button) findViewById(R.id.button_win);
        winButton.setOnClickListener(this);

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
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            Log.d("TEST", "bundle is not null");
            imageName = bundle.getString("image");
            dim = bundle.getInt("dim");
            iv = (ImageView) findViewById(R.id.imageView);

        }else{
            Log.d("TEST", "bundle is null");
        }

        try{
            if (imageName.equals("apple")){
                Log.d("PRINT", "<><>STRING COMPARE WORKED: apple");
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.apple);
            }else if(imageName.equals("pear")){
                Log.d("PRINT", "<><>STRING COMPARE WORKED: pear");
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.pear);
            }
            bmp = Bitmap.createScaledBitmap(bmp,scrHeight, scrHeight, true);
            iv.setImageBitmap(bmp);

        }catch(Exception e){
            Log.d("PRINT", "<><> FAILED STRING COMPARE");
        }

        int[][] array = Puzzle.getArray(dim);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_puzzle, menu);
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_win){
            // show win activity
            goToWin(v);
        }
    }

    public void goToWin(View v) {
        Intent i = new Intent(this,WinActivity.class);

        startActivity(i);
    }

}
