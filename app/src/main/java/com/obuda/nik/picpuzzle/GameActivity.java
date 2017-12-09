package com.obuda.nik.picpuzzle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.obuda.nik.picpuzzle.adapters.ImageAdapter;
import com.obuda.nik.picpuzzle.game.Difficulty;
import com.obuda.nik.picpuzzle.game.Game;
import com.obuda.nik.picpuzzle.game.GameState;
import com.obuda.nik.picpuzzle.game.ImageFactory;
import com.obuda.nik.picpuzzle.handlers.HighscoreHandler;

public class GameActivity extends AppCompatActivity {

    GridView gridView;
    Button button;
    Game game;
    Bitmap picture;
    Chronometer timer;
    HighscoreHandler handler;

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

        final ImageAdapter adapter;
        Difficulty difficulty=Difficulty.valueOf(getIntent().getStringExtra("difficulty").toUpperCase());
        Bitmap pic=BitmapFactory.decodeResource(getResources(),R.drawable.logo);

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
        timer.start();

        adapter=new ImageAdapter(this,game.toArray());
        gridView.setAdapter(adapter);
        gridView.setNumColumns(difficulty.getValue());

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                    finish();
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                game.Init(game.getDifficulty(),picture);
                adapter.setTiles(game.toArray());
                gridView.invalidateViews();
                timer.setBase(SystemClock.elapsedRealtime());
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        game.setElapsedTime(SystemClock.elapsedRealtime() - timer.getBase());
        outState.putParcelable("gameState",this.game.getGameState());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(RESULT_OK);
    }
}
