package projects.mprog.nl.npuzzle10001567;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;


public class ImagepickActivity extends Activity implements OnClickListener{
    ImageButton ibApple;
    ImageButton ibPear;
    int dim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagepick);

        ibApple = (ImageButton) findViewById(R.id.ibApple);
        ibApple.setOnClickListener(this);

        ibPear = (ImageButton) findViewById(R.id.ibPear);
        ibPear.setOnClickListener(this);

        Bundle gotLevel = getIntent().getExtras();
        dim = gotLevel.getInt("dimensions");
        String dimStr = Integer.toString(dim);
        TextView tv = (TextView) findViewById(R.id.textViewDimensions);
        tv.setText("The board will be " + dimStr + " x " + dimStr);  // should be in the strings part also...
    }

    @Override
    public void onClick(View v) {
        String imagePicked = null;
        if (v.getId() == R.id.ibApple){
            imagePicked = "apple";
        }else if(v.getId() == R.id.ibPear){
            imagePicked = "pear";
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
        Bundle pickedImage = new Bundle();
        pickedImage.putString("image", imageName);
        i.putExtras(pickedImage);
        startActivity(i);

    }
}
