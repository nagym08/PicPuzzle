package com.obuda.nik.picpuzzle;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.obuda.nik.picpuzzle.adapters.ImageAdapter;

import static com.obuda.nik.picpuzzle.R.id.gridView;

public class GameActivity extends AppCompatActivity {

    GridView gridView;
    Button button;
    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gridView= (GridView) findViewById(R.id.gridView);
        button= (Button) findViewById(R.id.newGame_btn);
        final ImageAdapter adapter;
        game=new Game();
        Difficulty difficulty=Difficulty.valueOf(getIntent().getStringExtra("difficulty").toUpperCase());

        game.Init(difficulty, BitmapFactory.decodeResource(getResources(), R.drawable.logo));
        adapter=new ImageAdapter(this,game.ToArray());


        gridView.setAdapter(adapter);
        gridView.setNumColumns(difficulty.getValue());

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                game.move(adapter.getTiles()[i].getID());
                adapter.setTiles(game.ToArray());
                gridView.invalidateViews();
                if(game.puzzleSolved())
                    Toast.makeText(getBaseContext(),"Nyert",Toast.LENGTH_LONG).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                game.Init(Difficulty.EASY,BitmapFactory.decodeResource(getResources(),R.drawable.logo));
                adapter.setTiles(game.ToArray());
                gridView.invalidateViews();
            }
        });

    }
}
