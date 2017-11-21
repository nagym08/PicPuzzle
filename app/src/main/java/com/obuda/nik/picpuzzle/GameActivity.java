package com.obuda.nik.picpuzzle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.obuda.nik.picpuzzle.adapters.ImageAdapter;
import com.obuda.nik.picpuzzle.game.Difficulty;
import com.obuda.nik.picpuzzle.game.Game;
import com.obuda.nik.picpuzzle.game.GameState;
import com.obuda.nik.picpuzzle.game.ImageFactory;

public class GameActivity extends AppCompatActivity {

    GridView gridView;
    Button button;
    Game game;
    Bitmap picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

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
            }catch (Exception e){
                Log.d("Exception",e.getMessage());
            }
        }

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
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("gameState",this.game.getGameState());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(RESULT_OK);
    }
}
