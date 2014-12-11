package projects.mprog.nl.npuzzle10001567;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity{
    Thread timer;
    Boolean firstRun = true;
    // true when it is the first time the app was run.
    // When puzzle is started, this will become false.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // true when it is the first time the app was run.
        // When puzzle is started, this will become false.
        firstRun = load();
        timer = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                    Log.d("TEST","SLEEP ENDED");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // first time run: give player choices
                            // not first time: go to puzzle, retrieve saved puzzle.
                            if (firstRun) {
                                goToLevel();
                            }else{
                                goToPuzzle();
                            }

                        }
                    });

                }catch(InterruptedException e){
                    e.printStackTrace();
                    Log.d("TEST","CAUGHT EXCPETION");
                }
            }

        };timer.start();
    }

    // Will always finish when the app is closed on startup.
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    // Retrieves information on if the app has been run before.
    // Should be inserted in Boolean firstStart of this Main class.
    public Boolean load(){
        SharedPreferences prefs = this.getSharedPreferences("mainpref", Context.MODE_PRIVATE);
        if (prefs.contains("firstRun")){
        Boolean wasRunBefore = prefs.getBoolean("firstRun",true);
        return wasRunBefore;
        }
        Log.d("TEST","out if");
        return true;
    }

    // clears saved data on if the app is run before. Was needed for debugging mainly.
    public void clearPreference(){
        SharedPreferences prefs = this.getSharedPreferences("mainpref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

    public void goToLevel() {
        Intent i = new Intent(this, LevelActivity.class);
        startActivity(i);
    }

    public void goToPuzzle() {
        Intent i = new Intent(this, PuzzleActivity.class);
        startActivity(i);
    }


}
