package projects.mprog.nl.npuzzle10001567;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        easy = (Button) findViewById(R.id.button_easy);
        easy.setOnClickListener(this);

        medium = (Button) findViewById(R.id.button_medium);
        medium.setOnClickListener(this);

        hard = (Button) findViewById(R.id.button_hard);
        hard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String dif = "THIS IS TEMP";
        Log.d("DEB","ONCLICK has been called");
        if (v.getId() == R.id.button_easy){
            dif = "EASY";
        }
        else if (v.getId() == R.id.button_medium){
            dif = "MEDIUM";
        }
        else if (v.getId() == R.id.button_hard) {
            dif = "HARD";
        }
        showToast(v,dif);
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
        Toast t = Toast.makeText(this, "You have chosen difficulty: " + level ,Toast.LENGTH_SHORT);
        t.show();
    }
}
