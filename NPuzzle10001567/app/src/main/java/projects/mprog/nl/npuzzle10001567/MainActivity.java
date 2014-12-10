package projects.mprog.nl.npuzzle10001567;

import android.app.Activity;
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
    Boolean firstStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        load();
        timer = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                    Log.d("TEST","SLEEP ENDED");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("TEST", "in thread: " + firstStart);
                            if (firstStart) {
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


    @Override
    protected void onPause() {
        super.onPause();
        save();
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

    public void goToLevel() {
        // OWN
        firstStart = false;
        Intent i = new Intent(this, LevelActivity.class);
        startActivity(i);
    }

    private void goToPuzzle() {
        Intent i = new Intent(this, PuzzleActivity.class);
        startActivity(i);
    }

    public boolean save() {
        SharedPreferences prefs = this.getSharedPreferences("mainpref", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Log.d("TEST","Saved: " + firstStart);
        editor.putBoolean("firstStart", firstStart);
        return editor.commit();
    }

    public boolean load() {

        SharedPreferences prefs = this.getSharedPreferences("mainpref",MODE_PRIVATE);
        try {
            if (prefs.getBoolean("firstStart", true)){
                firstStart = true;
            }else{
                firstStart = false;
            }
            Log.d("TEST","Loaded: " + firstStart);
            return firstStart;

        }catch (Exception e){
            Log.d("TEST", "MAIN: some exception");
            e.printStackTrace();
        }
        firstStart = true;
        return true;

    }
}
