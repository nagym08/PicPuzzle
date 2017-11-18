package com.obuda.nik.picpuzzle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.obuda.nik.picpuzzle.adapters.ImageAdapter;
import com.obuda.nik.picpuzzle.game.Difficulty;
import com.obuda.nik.picpuzzle.game.Game;
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

        gridView= (GridView) findViewById(R.id.gridView);
        button= (Button) findViewById(R.id.newGame_btn);
        final ImageAdapter adapter;
        game=new Game();
        Difficulty difficulty=Difficulty.valueOf(getIntent().getStringExtra("difficulty").toUpperCase());
        Bitmap pic=BitmapFactory.decodeResource(getResources(),R.drawable.logo);

        picture = ImageFactory.modifyImage(pic,this.getResources().getDisplayMetrics());

        game.Init(difficulty, picture);
        adapter=new ImageAdapter(this,game.toArray());


        gridView.setAdapter(adapter);
        gridView.setNumColumns(difficulty.getValue());

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                game.move(adapter.getTiles()[i].getID());
                adapter.setTiles(game.toArray());
                gridView.invalidateViews();
                if(game.puzzleSolved())
                    Toast.makeText(getBaseContext(),"Nyert",Toast.LENGTH_LONG).show();
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
}
