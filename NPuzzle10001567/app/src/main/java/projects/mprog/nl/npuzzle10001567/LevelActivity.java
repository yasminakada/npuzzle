package projects.mprog.nl.npuzzle10001567;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class LevelActivity extends Activity implements OnClickListener {
    Button easy;
    Button medium;
    Button hard;
    int dim = 4; // Indicates the dimensions of the game. Default 4x4.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        // All buttons are set:
        easy = (Button) findViewById(R.id.button_easy);
        easy.setOnClickListener(this);

        medium = (Button) findViewById(R.id.button_medium);
        medium.setOnClickListener(this);

        hard = (Button) findViewById(R.id.button_hard);
        hard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String dif = null;

        if (v.getId() == R.id.button_easy){
            dif = "EASY";
            dim = 3;
        }
        else if (v.getId() == R.id.button_medium){ // this area needs to be rewritten, het komt dubbel voor!!!!!!!!!!!!!!!!!!!!!!
            dif = "MEDIUM";
            dim = 4;
        }
        else if (v.getId() == R.id.button_hard) {
            dif = "HARD";
            dim = 5;
        }
        showToast(v,dif);
        goToImages();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_level, menu);
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

    public void showToast(View v, String level) {
        // Show a Toast and pass an int (number of tiles) to ImagePick
        Toast t = Toast.makeText(this, "You have chosen difficulty: " + level ,Toast.LENGTH_LONG);
        t.show();

    }
    public void goToImages(){
        Bundle lev = new Bundle();
        lev.putInt("dim", dim);
        Intent i = new Intent(this,ImageSelectionActivity.class);
        i.putExtras(lev);
        startActivity(i);
    }
}
