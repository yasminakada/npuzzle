package projects.mprog.nl.npuzzle10001567;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class PuzzleActivity extends Activity implements View.OnClickListener {

    int scrWidth;
    int scrHeight;
    int dim = 3;
    int clickCounter = 0;
    int resId;
    int shuffleTimes = 100;
    Boolean canPlay = false;

    TextView moveCounter;
    TableLayout tableLayout;
    Button shuffleButton;
    Button resetButton;

    Bitmap[] tiles;
    Puzzle puzzle = new Puzzle();
    Thread timer;

    boolean canFinish = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        // Save: Game has been run before.
        saveFirstRun(false);

        // Set default image.
        resId = getResources().getIdentifier("puzzle_0", "drawable", getPackageName());

        moveCounter = (TextView) findViewById(R.id.textMoves);
        tableLayout = (TableLayout) findViewById(R.id.tablelayout);

        // Set the shufflebutton and the resetButton.
        shuffleButton = (Button) findViewById(R.id.buttonShuffle);
        shuffleButton.setOnClickListener(this);
        shuffleButton.setVisibility(View.INVISIBLE);

        resetButton = (Button) findViewById(R.id.buttonReset);
        resetButton.setOnClickListener(this);
        resetButton.setVisibility(View.INVISIBLE);

        // If a bundle has been received from a previous activity
        // this will be used to build the puzzle.
        if (initializeAll()) {
            timer = new Thread() {
                public void run() {
                    try {
                        sleep(3000);
                        canPlay = true;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                doShuffle(shuffleTimes);
                                shuffleButton.setVisibility(View.VISIBLE);
                                resetButton.setVisibility(View.VISIBLE);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            timer.start();
            puzzle.start(dim); //Internal puzzle will be build

        // If no bundle is received, load the saved puzzle.
        } else if (loadPuzzle()) {
            shuffleButton.setVisibility(View.VISIBLE);
            resetButton.setVisibility(View.VISIBLE);
            canPlay = true;
        }
        initializeTable(); // Build views to display puzzle.
        resetTiles(); // Shows the tiles.

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tablelayout);
        tableLayout.invalidate();
    }

    // Gets and sets screendimensions and bundle passed by previous activity
    // if such exists, returns true if bundle is not null.
    public Boolean initializeAll() {
        // Getting the screen size of the device
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
            resId = bundle.getInt("resId");
            dim = bundle.getInt("dim");
            return true;
        } else {
            return false;
        }

    }

    // Devides the bitmap into puzzle pieces accordingly
    // returns a bitmap array with the image pices in the right order.
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
                    counter = 0;    // Last tile should be the empty tile (always index 0)
                }
                bitmapArray[counter] = tempBmp;
                counter++;
            }
        }
        return bitmapArray;
    }

    public void initializeTable() {
        int counter = 1;
        Bitmap bmp = null;
        int pad = 2; // padding for the images

        // Width according to screensize.
        double resizeWidth = 0.8 * scrWidth;
        int newWidth = (int) resizeWidth;
        int previewSize = scrWidth / 4;

        // Load image efficiently:
        bmp = BitmapConstruct.decodeSampledBitmapFromResource(getResources(),resId,newWidth);
        bmp = BitmapConstruct.scaledBitmap(bmp, newWidth);
        tiles = devideBitmap(bmp, dim, newWidth);         // Set tiles with corresponding imagepiece (array).

        // Preview image is set:
        ImageView preview = (ImageView) findViewById(R.id.prevImage);
        Bitmap previewImage = BitmapConstruct.decodeSampledBitmapFromResource(getResources(),resId,previewSize);
        previewImage = BitmapConstruct.scaledBitmap(previewImage, previewSize);
        preview.setImageBitmap(previewImage);

        for (int i = 0; i < dim; i++) {
            // Create row, according to dimensions.
            final TableRow tableRow = new TableRow(this);
            // Create new Imageview, to display part of the image.
            ImageView image = null;
            for (int j = 0; j < dim; j++) {
                if (counter == dim * dim) {
                    counter = 0;
                }
                image = new ImageView(this);
                image.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                image.setId(counter);
                image.setImageBitmap(tiles[counter]);
                image.setPadding(pad, pad, pad, pad);
                image.setOnClickListener(this);

                tableRow.addView(image);
                // Make last tile invisible.
                if (counter == 0) image.setVisibility(View.INVISIBLE);
                counter++;
            }
            tableLayout.addView(tableRow);
        }
    }

    public void resetTiles() {
        int counter = 1;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                int imageId = puzzle.puzzleArray[i][j];
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
                    // Run ImagePickActivity and finish this activity.
                    Intent i = new Intent(this, ImagepickActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("dim", dim);
                    i.putExtras(bundle);

                    canFinish = true;
                    startActivity(i);
                    return false;
            }
            // Player should not be able to play until game is reset.
            canPlay = false;
            shuffleButton.setVisibility(View.INVISIBLE);
            resetButton.setVisibility(View.INVISIBLE);
            if (tableLayout.getChildCount() > 0)
                tableLayout.removeAllViews();
            clickCounter= 0;
            resetCount(clickCounter);

            // New puzzle. Reset puzzle and tiles.
            puzzle = new Puzzle();
            puzzle.start(dim);
            initializeTable();
            resetTiles();

            timer = new Thread() {
                public void run() {
                    try {
                        sleep(2000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                canPlay = true; // Player can play the game.
                                doShuffle(shuffleTimes);
                                shuffleButton.setVisibility(View.VISIBLE);
                                resetButton.setVisibility(View.VISIBLE);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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
        if (canFinish) finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        savePuzzle();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonShuffle) {
            doShuffle(shuffleTimes);
            clickCounter = 0; // Reset counter to 0.
            resetCount(clickCounter);
        } else if (v.getId() == R.id.buttonReset) {
            clickCounter = 9999; // Shortcut to win, will lead to a lot of moves.
            resetCount(clickCounter);
            // Get the puzzle in completed state.
            puzzle.resetStart();
            resetTiles();

        // Only when the game can be played, the tiles will be movable.
        } else if (canPlay) {
            int imageViewId = v.getId();
            // Swap image if it can be swapped.
            // Check if image is swapped
            boolean isSwapped = puzzle.clickSwap(imageViewId);
            if (isSwapped) {
                resetTiles();
                clickCounter++;
                resetCount(clickCounter);
                // Game can only be won when a tile has been clicked
                // and the puzzle is in the final state.
                // Resetbutton will not win the game.
                if (puzzle.checkWin()) {
                    goToWin();
                }
            }
        }
    }

    // Reset the moves the player has done.
    private void resetCount(int count) {
        moveCounter.setText("MOVES: " + count);
        moveCounter.invalidate();

    }

    // Start WinActivity. This activity can be finished on pause.
    // The image puzzle will be shown some time, before the winActivity
    // is started.
    private void goToWin() {
        canFinish = true;
        saveFirstRun(true); // Reset the game, as if it has never been run before.
        clearSavedGame(); // Remove the saved files. It is not relevant after winning.

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
                }
            }

        };
        lastAppear.start();
        Thread win = new Thread() {
            public void run() {
                try {
                    canPlay = false;
                    sleep(2000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(i);
                }
            }

        };
        win.start();

    }

    // Shuffle the tiles, and reset the screen.
    public void doShuffle(int shuffleTimes) {
        puzzle.shuffle(shuffleTimes);
        resetTiles();
    }

    // Remember (save) that the app has been run before.
    // When app is run at a later time the previous gamestate will be loaded
    // if this game has not been won.
    public void saveFirstRun(Boolean b){
        SharedPreferences prefs = getSharedPreferences("mainpref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstRun", b);
        editor.commit();

    }

    // Save the state of the puzzle so puzzle can be resumed
    // when the app has been closed.
    public boolean savePuzzle() {
        SharedPreferences prefs = this.getSharedPreferences("puzzlepref", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("dim", puzzle.dim);
        int counter = 0;
        // The elements in the puzzleArray are saved individually.
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

    // Loads game if a game has been saved previously.
    public boolean loadPuzzle() {
        int savedDim;
        int savedRow0;
        int savedCol0;
        int savedClickCounter;
        int[] array;

        SharedPreferences prefs = this.getSharedPreferences("puzzlepref",MODE_PRIVATE);

        // Check if a game is saved by looking up if the dimensions are saved.
        // If there is no saved data, default game is played. (this never happens)
        if (prefs.contains("dim")) {
            savedDim = prefs.getInt("dim", MODE_PRIVATE);
            savedClickCounter = prefs.getInt("clickCounter", MODE_PRIVATE);
            savedRow0 = prefs.getInt("row0", MODE_PRIVATE);
            savedCol0 = prefs.getInt("col0", MODE_PRIVATE);
            clickCounter = savedClickCounter;
            resId = prefs.getInt("resId", MODE_PRIVATE);
            array = new int[savedDim * savedDim];

            // Individual puzzle pieces are retrieved and inserted in an array.
            for (int i = 0; i < savedDim * savedDim; i++)
                array[i] = prefs.getInt("p" + i, MODE_PRIVATE);

            // Set the puzzle back to saved gamestate.
            puzzle.setPuzzle(savedDim, savedRow0, savedCol0, array);
            dim = savedDim;
            resetCount(clickCounter);
            return true;
        }
        return false;
    }

    // Remove the saved gamestate/puzzle data from memory.
    public void clearSavedGame(){
        SharedPreferences prefs = this.getSharedPreferences("puzzlepref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

}
