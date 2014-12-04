package projects.mprog.nl.npuzzle10001567;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class WinActivity extends Activity implements View.OnClickListener{

    int dim = 3;
    int moves;
    int imageId;
    boolean canFinish = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            dim = bundle.getInt("dim");
            moves = bundle.getInt("moves");
            imageId = bundle.getInt("image");
        }


        TextView movesDone = (TextView) findViewById(R.id.textView4);
        movesDone.setText("You have completed the game in "+ moves + " moves.");

        ImageView iv = (ImageView) findViewById(R.id.imageView);
        int imageDim = BitmapConstruct.dpToPx(400);

        Bitmap bmp = BitmapFactory.decodeResource(getResources(),imageId);
        bmp = BitmapConstruct.scaledBitmap(bmp,imageDim);
        iv.setImageBitmap(bmp);

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
            Intent i = new Intent(this,ImagepickActivity.class);
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
