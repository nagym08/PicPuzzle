package com.obuda.nik.picpuzzle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.Toast;

import com.obuda.nik.picpuzzle.adapters.ImageAdapter;
import com.obuda.nik.picpuzzle.game.Difficulty;
import com.obuda.nik.picpuzzle.game.Game;
import com.obuda.nik.picpuzzle.game.GameState;
import com.obuda.nik.picpuzzle.game.ImageFactory;
import com.obuda.nik.picpuzzle.handlers.HighscoreHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class GameActivity extends AppCompatActivity {

    GridView gridView;
    Button button;
    Game game;
    Bitmap picture;
    Chronometer timer;
    HighscoreHandler handler;
    ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        timer  = (Chronometer) findViewById(R.id.timer);

        /////////////////////FOR ALARM CLOCK ////////////////////////////
        /////////////////////////////////////////////////////////////////
        final Window win= getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        ////////////////////////////////////////////////////////////////

        Difficulty difficulty=Difficulty.valueOf(getIntent().getStringExtra("difficulty").toUpperCase());
        Uri picUri = getIntent().getParcelableExtra("pictureUri");
        Bitmap pic = null;
        if(picUri != null){
            try {
                InputStream image_stream = getContentResolver().openInputStream(picUri);
                pic = BitmapFactory.decodeStream(image_stream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            pic=BitmapFactory.decodeResource(getResources(),R.drawable.logo);
        }

        gridView= (GridView) findViewById(R.id.gridView);
        button= (Button) findViewById(R.id.newGame_btn);
        game=new Game();

        picture = ImageFactory.modifyImage(pic,this.getResources().getDisplayMetrics(),
                getResources().getConfiguration().orientation);

        game.Init(difficulty, picture);

        if(savedInstanceState!=null){
            try {
                this.game.loadGameState((GameState) savedInstanceState.getParcelable("gameState"));
                timer.setBase(SystemClock.elapsedRealtime() - game.getElapsedTime());
            }catch (Exception e){
                Log.d("Exception",e.getMessage());
            }
        }

        handler = new HighscoreHandler(this, game.getDifficulty());

        if(!game.puzzleSolved())
            timer.start();

        adapter=new ImageAdapter(this,game.toArray());
        gridView.setAdapter(adapter);
        gridView.setNumColumns(difficulty.getValue());

        if(!game.puzzleSolved())
            gridView.setOnItemClickListener(this.itemClickListener());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                game.Init(game.getDifficulty(),picture);
                adapter.setTiles(game.toArray());
                gridView.invalidateViews();
                gridView.setOnItemClickListener(GameActivity.this.itemClickListener());
                timer.setBase(SystemClock.elapsedRealtime());
                timer.start();
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(!game.puzzleSolved())
            game.setElapsedTime(SystemClock.elapsedRealtime() - timer.getBase());
        outState.putParcelable("gameState",this.game.getGameState());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(RESULT_OK);
    }

    private AdapterView.OnItemClickListener itemClickListener()
    {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                game.move(adapter.getTiles()[i].getID());
                adapter.setTiles(game.toArray());
                gridView.invalidateViews();
                if(game.puzzleSolved()) {
                    timer.stop();
                    long elapsedTime = SystemClock.elapsedRealtime() - timer.getBase();
                    if(handler.isTimeHighscore(elapsedTime)){
                        handler.SaveHighscore(elapsedTime);
                        handler.SavePreferences();
                    }
                    game.setElapsedTime(elapsedTime);
                    if(getIntent().getBooleanExtra("alarm",false))
                        finish();
                    else{
                        gridView.setOnItemClickListener(null);
                        Toast.makeText(GameActivity.this,"Well Done!",Toast.LENGTH_SHORT).show(); //TODO strings.xml
                    }
                }
            }
        };
    }

    /*
    private void setOrientation(Bitmap bitmapToAdjust, Uri imagePath) throws IOException {
        ExifInterface exif = new ExifInterface(imagePath.getPath());

        exif.setAttribute(ExifInterface.TAG_ORIENTATION, "3");
        exif.saveAttributes();


        int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        //int rotationInDegrees = exifToDegrees(rotation);
        int rotationInDegrees = 90;

        Matrix matrix = new Matrix();

        matrix.preRotate(rotationInDegrees);


        if(rotation != 0f){
            matrix.preRotate(rotationInDegrees);
        }

        Bitmap adjustedBitmap = Bitmap.createBitmap(
                bitmapToAdjust,
                0,
                0,
                bitmapToAdjust.getWidth(),
                bitmapToAdjust.getHeight(),
                matrix,
                true);

        return adjustedBitmap;

    }

    private int exifToDegrees(int exifOrientation){
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) { return 90; }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {  return 180; }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {  return 270; }
        return 0;
    }
    */
}
