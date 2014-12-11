package projects.mprog.nl.npuzzle10001567;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class WinActivity extends Activity implements View.OnClickListener{

    int dim = 4; // Default dimensions 4x4.
    int moves;
    int resId;
    boolean canFinish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        // Retrieve data from intent.
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            dim = bundle.getInt("dim");
            moves = bundle.getInt("moves");
            resId = bundle.getInt("image");
        }

        // Show number of moves on the screen
        TextView movesDone = (TextView) findViewById(R.id.textView4);
        movesDone.setText("You have completed the game in "+ moves + " moves.");

        // Show image of the puzzle.
        int imageDim = BitmapConstruct.dpToPx(500);
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        Bitmap bmp = BitmapConstruct.decodeSampledBitmapFromResource(getResources(),resId,imageDim);
        bmp = BitmapConstruct.scaledBitmap(bmp,imageDim);
        iv.setImageBitmap(bmp);

        // Set button to pick new image.
        Button button = (Button) findViewById(R.id.buttonPickImage);
        button.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_win, menu);
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
        if (v.getId()== R.id.buttonPickImage){
            canFinish = true; // WinActivity may be destroyed when paused.
            Intent i = new Intent(this,ImageSelectionActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("dim",dim);
            i.putExtras(bundle);
            startActivity(i);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(canFinish){
            finish();
        }
    }
}
