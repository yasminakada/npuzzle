package projects.mprog.nl.npuzzle10001567;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.Arrays;


public class PuzzleActivity extends Activity implements View.OnClickListener {
    int dim;
    int scrWidth;
    int scrHeight;
    Thread timer;
    Boolean canPlay = false;

    TableLayout tableLayout;
    Bitmap bmp = null;
    // next two bmp are scaled in initalizeTable()
    Bitmap tinybmp;
    Bitmap changebmp;
    int resId;
    ImageView iv;
    Button shuffleButton;
    Puzzle puzzle = new Puzzle();

    BitmapFactory.Options options = new BitmapFactory.Options();
    int imageHeight;
    int imageWidth;
    String imageType;

    Bitmap[] tiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        tableLayout = (TableLayout) findViewById(R.id.tablelayout);
        tinybmp = BitmapFactory.decodeResource(getResources(),R.drawable.pear);
        changebmp = BitmapFactory.decodeResource(getResources(),R.drawable.apple);

        shuffleButton = (Button) findViewById(R.id.button);
        shuffleButton.setOnClickListener(this);


        initializeAll(); // will set up all starting variables from putExtras and screen width height
//        initializeTable();
        timer = new Thread(){
            public void run(){
                try{
                    sleep(5000);
                    Log.d("TEST","SLEEP ENDED");
                    canPlay = true;

                }catch(InterruptedException e){
                    e.printStackTrace();
                    Log.d("TEST","CAUGHT EXCPETION");
                }
            }

        };timer.start();

        ImageView temp = (ImageView) findViewById(R.id.tempimage);
        Bitmap test = BitmapFactory.decodeResource(getResources(),resId);
        test = BitmapConstruct.scaledBitmap(test,scrHeight);
        temp.setImageBitmap(test);

        //internal puzzle
        puzzle.start(dim);
        Log.d("TEST", Arrays.deepToString(puzzle.puzzleArray));
//        puzzle.shuffle(100); // could also be used to change difficulty.
        Log.d("TEST", Arrays.deepToString(puzzle.puzzleArray));
        initializeTable();
        resetTiles();
        TableLayout tableLayout = (TableLayout) findViewById(R.id.tablelayout);
        tableLayout.invalidate();
    }

    public Bitmap[] devideBitmap(Bitmap bmp, int dim, int imgSize){
        int counter = 1;
        int step = imgSize/dim;
        int placement;
        Bitmap[]  bitmapArray = new Bitmap[dim*dim];
        for (int i = 0; i<dim; i++){
            for(int j =0; j<dim; j++){
                int x = i * step;
                int y = j * step;
                Bitmap tempBmp = Bitmap.createBitmap(bmp,y,x,step,step);
                if (counter==dim * dim){
                    // should be an empty tile.
                    counter = 0;
                }
                bitmapArray[counter] = tempBmp;
                counter++;
//                placement = puzzle.puzzleArray[i][j];
//                ImageView iv = (ImageView) findViewById(counter);

            }
            if (bitmapArray[1] ==null){
                Log.d("TEST","bitmapArray IS NULL");
            }
        }
        Log.d("TEST", Arrays.deepToString(puzzle.puzzleArray));
        return bitmapArray;
    }

//    public Bitmap[] assignBitmapToArray(){
//
//    }

    public void checkImage(int resId){
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), resId, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        String imageType = options.outMimeType;

    }

    public void initializeTable(){
        int counter = 1;
        bmp = BitmapFactory.decodeResource(getResources(),resId);
        double resizeWidth= 0.8 * scrWidth;
        int newWidth = (int) resizeWidth;
        bmp = BitmapConstruct.scaledBitmap(bmp,newWidth);
        tiles = devideBitmap(bmp,dim,newWidth);

        for (int i = 0; i < dim; i++) {

            // Creation row
            final TableRow tableRow = new TableRow(this);
//            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

            ImageView image = null;
            for (int j = 0; j < dim; j++) {
                if (counter == dim * dim) {
                    counter = 0;
                }
                image = new ImageView(this);
                image.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                image.setId(counter);
                image.setImageBitmap(tiles[counter]);
                int pad =2;
                image.setPadding(pad,pad,pad,pad);
//                image.setClickable(true);
                image.setOnClickListener(this);
                counter++;
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
//        // testing tile change
//        ImageView temp1 = (ImageView) findViewById(dim*dim-1);
//        ImageView temp2 = (ImageView) findViewById(0);
//
//        Bitmap bitmap1 = ((BitmapDrawable)temp1.getDrawable()).getBitmap();
//        Bitmap bitmap2 = ((BitmapDrawable)temp1.getDrawable()).getBitmap();
//
//        if (temp1==null){
//            Log.d("TEST","temp1 is null");
//        }else {
//            temp1.setImageBitmap(bitmap2);
//            temp2.setImageBitmap(bitmap1);
//        }

    }

    public void resetTiles() {
        int counter = 1;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                int imageId = puzzle.puzzleArray[i][j];
                Log.d("TEST","" + imageId);
                ImageView image = (ImageView) findViewById(counter);
                image.setImageBitmap(tiles[imageId]);
                if (imageId == 0){
                    image.setVisibility(View.INVISIBLE);
                }else{
                    image.setVisibility(View.VISIBLE);
                }
                counter++;
                if (counter == dim*dim){
                    counter=0;
                }
            }
        tableLayout.invalidate();
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
            doShuffle();
        }else if (canPlay){

            int imageViewId = v.getId();
            Log.d("TEST","" + imageViewId);
            boolean b = puzzle.clickSwap(imageViewId);
            Log.d("TEST","" + b);
            if (b == true){
                resetTiles();
            }
        }
    }

    public void doShuffle() {
       puzzle.shuffle(150);
       resetTiles();
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
            resId = bundle.getInt("resId");
            dim = bundle.getInt("dim");
//            iv = (ImageView) findViewById(R.id.imageView);

        }else{
            Log.d("TEST", "bundle is null");
        }

        try{
            bmp = BitmapFactory.decodeResource(getResources(), resId);
            bmp = Bitmap.createScaledBitmap(bmp,scrHeight, scrHeight, true);
            iv.setImageBitmap(bmp);

        }catch(Exception e){
            Log.d("TEST", "<><> Failed setting image");
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