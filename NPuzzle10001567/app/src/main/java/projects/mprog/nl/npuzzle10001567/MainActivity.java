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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("TEST", "FR: " + firstRun);
        firstRun = load();
        Log.d("TEST", "FR: " + firstRun);

        timer = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                    Log.d("TEST","SLEEP ENDED");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
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

    public Boolean load(){
        SharedPreferences prefs = this.getSharedPreferences("mainpref", Context.MODE_PRIVATE);
//        clearPreference();

        if (prefs.contains("firstRun")){
        Boolean wasRunBefore = prefs.getBoolean("firstRun",true);
        return wasRunBefore;
        }
        Log.d("TEST","out if");
        return true;
    }

    public void goToLevel() {
        // OWN
        Intent i = new Intent(this, LevelActivity.class);
        startActivity(i);
    }

    public void goToPuzzle() {
        Intent i = new Intent(this, PuzzleActivity.class);
        startActivity(i);
    }

    public void clearPreference(){
        SharedPreferences prefs = this.getSharedPreferences("mainpref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }
}
