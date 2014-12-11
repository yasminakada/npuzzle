package projects.mprog.nl.npuzzle10001567;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Arrays;


public class PuzzleActivity extends Activity implements View.OnClickListener {
    int dim = 3;
    int scrWidth;
    int scrHeight;
    Thread timer;
    Boolean canPlay = false;

    int clickCounter = 0;
    TextView moveCounter;
    TableLayout tableLayout;
    Bitmap bmp = null;

    int resId;

    ImageView iv;
    Button shuffleButton;
    Button resetButton;
    Puzzle puzzle = new Puzzle();

    Bitmap[] tiles;

    boolean canFinish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        saveFirstRun(false);

        // set default image
        resId = getResources().getIdentifier("puzzle_0", "drawable", getPackageName());

        tableLayout = (TableLayout) findViewById(R.id.tablelayout);

        shuffleButton = (Button) findViewById(R.id.buttonShuffle);
        shuffleButton.setOnClickListener(this);
        shuffleButton.setVisibility(View.INVISIBLE);

        resetButton = (Button) findViewById(R.id.buttonReset);
        resetButton.setOnClickListener(this);
        resetButton.setVisibility(View.INVISIBLE);

        moveCounter = (TextView) findViewById(R.id.textMoves);

        if (initializeAll()) {
            timer = new Thread() {
                public void run() {
                    try {
                        sleep(3000);
                        Log.d("TEST", "SLEEP ENDED");
                        canPlay = true;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                doShuffle(100);
                                Log.d("TEST", Arrays.deepToString(puzzle.puzzleArray));
                                shuffleButton.setVisibility(View.VISIBLE);
                                resetButton.setVisibility(View.VISIBLE);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Log.d("TEST", "CAUGHT EXCPETION");
                    }
                }
            };
            timer.start();

            //internal puzzle
            puzzle.start(dim);
            Log.d("TEST", Arrays.deepToString(puzzle.puzzleArray));
        } else if (loadPuzzle()) {
            Log.d("TEST", "LOADPUZZLE");
            shuffleButton.setVisibility(View.VISIBLE);
            resetButton.setVisibility(View.VISIBLE);
            canPlay = true;
        }
        initializeTable(); // after puzzle.start(dim)
        resetTiles();

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tablelayout);
        tableLayout.invalidate();
    }

    public Boolean initializeAll() {
        // Getting the screen size of the device,
        if (Build.VERSION.SDK_INT >= 13) {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            scrWidth = size.x;
            scrHeight = size.y;
        } else {
            Display display = getWindowManager().getDefaultDisplay();
            scrWidth = display.getWidth();
            scrHeight = display.getHeight();
        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Log.d("TEST", "bundle is not null");
            resId = bundle.getInt("resId");
            dim = bundle.getInt("dim");
            return true;

        } else {
            Log.d("TEST", "bundle is null");
            return false;
        }

    }

    public Bitmap[] devideBitmap(Bitmap bmp, int dim, int imgSize) {
        int counter = 1;
        int step = imgSize / dim;
        Bitmap[] bitmapArray = new Bitmap[dim * dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                int x = i * step;
                int y = j * step;
                Bitmap tempBmp = Bitmap.createBitmap(bmp, y, x, step, step);
                if (counter == dim * dim) {
                    // should be an empty tile.
                    counter = 0;
                }
                bitmapArray[counter] = tempBmp;
                counter++;

            }
            if (bitmapArray[1] == null) {
                Log.d("TEST", "bitmapArray IS NULL");
            }
        }
        Log.d("TEST", Arrays.deepToString(puzzle.puzzleArray));
        return bitmapArray;
    }

    public void initializeTable() {
        int counter = 1;

//        bmp = BitmapFactory.decodeResource(getResources(), resId);

        double resizeWidth = 0.8 * scrWidth;
        int newWidth = (int) resizeWidth;

        bmp = BitmapConstruct.decodeSampledBitmapFromResource(getResources(),resId,newWidth);
        bmp = BitmapConstruct.scaledBitmap(bmp, newWidth);
        tiles = devideBitmap(bmp, dim, newWidth);

        // the preview image is set:
        ImageView preview = (ImageView) findViewById(R.id.prevImage);
        int prevDim = BitmapConstruct.dpToPx(200);
        Bitmap previewImage = BitmapConstruct.scaledBitmap(bmp, prevDim);
        preview.setImageBitmap(previewImage);

        for (int i = 0; i < dim; i++) {

            // Creation row
            final TableRow tableRow = new TableRow(this);

            ImageView image = null;
            for (int j = 0; j < dim; j++) {
                if (counter == dim * dim) {
                    counter = 0;
                }
                image = new ImageView(this);
                image.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                image.setId(counter);
                image.setImageBitmap(tiles[counter]);
                int pad = 2;
                image.setPadding(pad, pad, pad, pad);
                image.setOnClickListener(this);
                counter++;
                tableRow.addView(image);

            }
            tableLayout.addView(tableRow);
        }
    }

    public void resetTiles() {
        int counter = 1;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                int imageId = puzzle.puzzleArray[i][j];
                Log.d("TEST", "" + imageId);
                ImageView image = (ImageView) findViewById(counter);
                image.setImageBitmap(tiles[imageId]);
                if (imageId == 0) {
                    image.setVisibility(View.INVISIBLE);
                } else {
                    image.setVisibility(View.VISIBLE);
                }
                counter++;
                if (counter == dim * dim) {
                    counter = 0;
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
        if (id != 0) {
            switch (id) {
                case R.id.changeEasy:
                    if (dim == 3) {
                        return false;
                    }
                    dim = 3;
                    break;
                case R.id.changeMedium:
                    if (dim == 4) {
                        return false;
                    }
                    dim = 4;
                    break;
                case R.id.changeHard:
                    if (dim == 5) {
                        return false;
                    }
                    dim = 5;
                    break;
                case R.id.changeImage:
                    Intent i = new Intent(this, ImagepickActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("dim", dim);
                    i.putExtras(bundle);

                    canFinish = true;
                    startActivity(i);
                    return false;
            }
            canPlay = false;
            shuffleButton.setVisibility(View.INVISIBLE);
            resetButton.setVisibility(View.INVISIBLE);
            if (tableLayout.getChildCount() > 0)
                tableLayout.removeAllViews();

            clickCounter= 0;
            resetCount(clickCounter);

            puzzle = new Puzzle();
            puzzle.start(dim);
            initializeTable();
            resetTiles();

            timer = new Thread() {
                public void run() {
                    try {
                        sleep(2000);
                        Log.d("TEST", "SLEEP ENDED");
                        canPlay = true;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                doShuffle(100);
                                shuffleButton.setVisibility(View.VISIBLE);
                                resetButton.setVisibility(View.VISIBLE);
                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Log.d("TEST", "CAUGHT EXCPETION");
                    }
                }
            };
            timer.start();

        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TEST", "canFinish? " + canFinish);
        if (canFinish) finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        savePuzzle();
    }

    @Override
    public void onClick(View v) {
//        savePuzzle();
        if (v.getId() == R.id.buttonShuffle) {
            // show win activity
            doShuffle(100);
            clickCounter = 0;
            resetCount(clickCounter);
        } else if (v.getId() == R.id.buttonReset) {
            clickCounter = 999;
            resetCount(clickCounter);
            puzzle.resetStart();
            resetTiles();
        } else if (canPlay) {

            int imageViewId = v.getId();
            Log.d("TEST", "" + imageViewId);
            boolean b = puzzle.clickSwap(imageViewId);
            Log.d("TEST", "" + b);
            if (b == true) {
                resetTiles();
                clickCounter++;
                resetCount(clickCounter);
                if (puzzle.checkWin()) {
                    goToWin();
                }
            }
        }
    }

    private void resetCount(int count) {
        moveCounter.setText("MOVES: " + count);
        moveCounter.invalidate();

    }

    private void goToWin() {
        canFinish = true;
        saveFirstRun(true);
        clearPreference();

        final Intent i = new Intent(this, WinActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("dim", dim);
        bundle.putInt("moves", clickCounter);
        bundle.putInt("image", resId);
        i.putExtras(bundle);
        Thread lastAppear = new Thread() {
            public void run() {
                try {
                    canPlay = false;
                    sleep(500);
                    Log.d("TEST", "SLEEP ENDED");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ImageView lastTile = (ImageView) findViewById(0);
                            lastTile.setVisibility(View.VISIBLE);
                            shuffleButton.setVisibility(View.INVISIBLE);
                            resetButton.setVisibility(View.INVISIBLE);
                            tableLayout.invalidate();
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.d("TEST", "CAUGHT EXCPETION");
                }
            }

        };
        lastAppear.start();
        Thread win = new Thread() {
            public void run() {
                try {
                    canPlay = false;
                    sleep(2000);
                    Log.d("TEST", "SLEEP ENDED");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.d("TEST", "CAUGHT EXCPETION");
                } finally {
                    startActivity(i);
                }
            }

        };
        win.start();

    }

    public void doShuffle(int shuffleTimes) {
        puzzle.shuffle(shuffleTimes);
        resetTiles();
    }

    public void saveFirstRun(Boolean b){
        SharedPreferences prefs = getSharedPreferences("mainpref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstRun", b);
        editor.commit();

    }
    public boolean savePuzzle() {
        SharedPreferences prefs = this.getSharedPreferences("puzzlepref", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("dim", puzzle.dim);
        int counter = 0;
        Log.d("TEST","DIM IN SAVE: "+ dim);
        for(int i=0;i < dim;i++){
            for (int j=0; j < dim; j++ ){
                editor.putInt("p" + counter, puzzle.puzzleArray[i][j]);
                counter++;
            }
        }
        editor.putInt("resId", resId);
        editor.putInt("row0", puzzle.row0);
        editor.putInt("col0", puzzle.col0);
        editor.putInt("clickCounter", clickCounter);
        return editor.commit();
    }

    public boolean loadPuzzle() {
        int savedDim;
        int savedRow0;
        int savedCol0;
        int savedClickCounter;
        int[] array;

        SharedPreferences prefs = this.getSharedPreferences("puzzlepref",MODE_PRIVATE);
        if (prefs.contains("dim")) {
            savedDim = prefs.getInt("dim", MODE_PRIVATE);
            savedClickCounter = prefs.getInt("clickCounter", MODE_PRIVATE);
            savedRow0 = prefs.getInt("row0", MODE_PRIVATE);
            savedCol0 = prefs.getInt("col0", MODE_PRIVATE);
            clickCounter = savedClickCounter;
            resId = prefs.getInt("resId", MODE_PRIVATE);
            array = new int[savedDim * savedDim];

            for (int i = 0; i < savedDim * savedDim; i++)
                array[i] = prefs.getInt("p" + i, MODE_PRIVATE);

            Log.d("TEST", "savedDim:" + savedDim);
            Log.d("TEST", "savedRow0:" + savedRow0);

            puzzle.setPuzzle(savedDim, savedRow0, savedCol0, array);
            dim = savedDim;
            resetCount(clickCounter);
            Log.d("TEST", "" + puzzle.row0);
            Log.d("TEST", "" + savedRow0);

            return true;
        }
        return false;
    }

    public void clearPreference(){
        SharedPreferences prefs = this.getSharedPreferences("puzzlepref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

}
