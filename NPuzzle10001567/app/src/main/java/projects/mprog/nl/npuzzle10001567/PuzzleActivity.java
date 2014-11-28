package projects.mprog.nl.npuzzle10001567;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class PuzzleActivity extends Activity implements View.OnClickListener {
    Button winButton;
    String imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        winButton = (Button) findViewById(R.id.button_win);
        winButton.setOnClickListener(this);

        Bundle gotImage = getIntent().getExtras();
        imageName = gotImage.getString("image");

        String uri = "@drawable/" + imageName + ".jpg";
        Log.d("draw",uri);
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        Drawable res = getResources().getDrawable(imageResource);
        iv.setImageDrawable(res);
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
