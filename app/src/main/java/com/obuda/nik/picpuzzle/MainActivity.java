package com.obuda.nik.picpuzzle;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button highscoreButton = (Button) findViewById(R.id.button_highScores);
        highscoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HighscoreActivity.class);
                startActivity(intent);
            }
        });

        Button newGameButton= (Button) findViewById(R.id.button_newGame);

        newGameButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                final int defaultChoice=0;

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,R.style.CustomAlertDialogColor);

                builder.setTitle("Difficulty:"); //TODO values strings

                builder.setSingleChoiceItems(Difficulty.getNames(), defaultChoice, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        });
                        builder.setPositiveButton("Start", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ListView lw = ((AlertDialog)dialog).getListView();

                                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                                intent.putExtra("difficulty",Difficulty.getNames()[lw.getCheckedItemPosition()]);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {}
                        });
                builder.create().show();


            }
        });

        Button quitButton= (Button) findViewById(R.id.button_quit);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAndRemoveTask();
            }
        });
    }
}
