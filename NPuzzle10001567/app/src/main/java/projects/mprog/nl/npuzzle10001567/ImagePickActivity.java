package projects.mprog.nl.npuzzle10001567;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;


public class ImagepickActivity extends Activity implements OnClickListener{
    ImageButton mountains_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagepick);
        mountains_button = (ImageButton) findViewById(R.id.imageButtonMountains);
        mountains_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String imagePicked = null;
        if (v.getId() == R.id.imageButtonMountains){
            imagePicked = "mountains";
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

    public void goToPuzzle(View v, String imageName){
        Intent i = new Intent(this,PuzzleActivity.class);
        startActivity(i);

    }
}
