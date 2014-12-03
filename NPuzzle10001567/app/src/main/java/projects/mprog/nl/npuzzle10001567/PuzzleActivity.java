package projects.mprog.nl.npuzzle10001567;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Arrays;


public class PuzzleActivity extends Activity implements View.OnClickListener {
    int dim;
    int scrWidth;
    int scrHeight;
    TableLayout tableLayout;
    Bitmap bmp = null;
    // next two bmp are scaled in initalizeTable()
    Bitmap tinybmp;
    Bitmap changebmp;
    String imageName;
    ImageView iv;
    Button winButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        tableLayout = (TableLayout) findViewById(R.id.tablelayout);
        tinybmp = BitmapFactory.decodeResource(getResources(),R.drawable.pear);
        changebmp = BitmapFactory.decodeResource(getResources(),R.drawable.apple);

        winButton = (Button) findViewById(R.id.button);
        winButton.setOnClickListener(this);

        initializeAll(); // will set up all starting variables from putExtras and screen width height
        initializeTable();



        //internal puzzle
        Puzzle game = new Puzzle();
        game.start(dim);
        Log.d("GAMECHECK", Arrays.deepToString(game.puzzleArray));
        game.shuffle(100); // could also be used to change difficulty.
        Log.d("GAMECHECK", Arrays.deepToString(game.puzzleArray));

    }

    public void initializeTable(){
        int counter = 0;
        tinybmp = Bitmap.createScaledBitmap(tinybmp,150,150,true);
        changebmp = Bitmap.createScaledBitmap(changebmp,150,150,true);
        for (int i = 0; i < dim; i++) {

            // Creation row
            final TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

            ImageView image = null;
            for (int j = 0; j < dim; j++) {
                counter++;
                if (counter == dim * dim) {
                    counter = 0;
                }
                image = new ImageView(this);
                image.setId(counter);
                image.setImageBitmap(tinybmp);
                int pad =2;
                image.setPadding(pad,pad,pad,pad);
//                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                lp.setMargins(20,20,20,20);
//                image.setLayoutParams(lp);


                tableRow.addView(image);
            }
//            // Creation textView
//            final TextView text = new TextView(this);
//            text.setText("Test" + i);
//            text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

//            // Creation  button
//            final Button button = new Button(this);
//            button.setText("Delete");
//            button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    final TableRow parent = (TableRow) v.getParent();
//                    tableLayout.removeView(parent);
//                }
//            });


//            tableRow.addView(text);
//            tableRow.addView(button);

            tableLayout.addView(tableRow);
        }
        // testing tile change.
        ImageView tempIm = (ImageView) findViewById(5);
        if (tempIm==null){
            Log.d("TEST","tempIm is null");
        }else {
            tempIm.setImageBitmap(changebmp);
        }

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
        if (v.getId() == R.id.button){
            // show win activity
            goToWin(v);
        }
    }

    public void goToWin(View v) {
        Intent i = new Intent(this,WinActivity.class);

        startActivity(i);
    }


    public void initializeAll() {
        // Getting the screen size of the device,
        if (Build.VERSION.SDK_INT >= 13){
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            scrWidth = size.x;
            scrHeight = size.y;
        }else{
            Display display = getWindowManager().getDefaultDisplay();
            scrWidth = display.getWidth();
            scrHeight = display.getHeight();
        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            Log.d("TEST", "bundle is not null");
            imageName = bundle.getString("image");
            dim = bundle.getInt("dim");
//            iv = (ImageView) findViewById(R.id.imageView);

        }else{
            Log.d("TEST", "bundle is null");
        }

        try{
            if (imageName.equals("apple")){
                Log.d("PRINT", "<><>STRING COMPARE WORKED: apple");
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.apple);
            }else if(imageName.equals("pear")){
                Log.d("PRINT", "<><>STRING COMPARE WORKED: pear");
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.pear);
            }
            bmp = Bitmap.createScaledBitmap(bmp,scrHeight, scrHeight, true);
            iv.setImageBitmap(bmp);

        }catch(Exception e){
            Log.d("PRINT", "<><> FAILED STRING COMPARE");
        }

    }
}
class Adapter extends BaseAdapter{

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}