package com.obuda.nik.picpuzzle;

import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.obuda.nik.picpuzzle.adapters.HighscorePagerAdapter;
import com.obuda.nik.picpuzzle.game.Difficulty;
import com.obuda.nik.picpuzzle.handlers.HighscoreHandler;
import com.obuda.nik.picpuzzle.models.HighscoreItem;
import com.obuda.nik.picpuzzle.models.HighscorePage;

import java.util.ArrayList;
import java.util.List;

public class HighscoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        HighscoreHandler easyHandler = new HighscoreHandler(this, Difficulty.EASY);
        HighscoreHandler mediumHandler = new HighscoreHandler(this, Difficulty.MEDIUM);
        HighscoreHandler hardHandler = new HighscoreHandler(this, Difficulty.HARD);

        List<HighscorePage> highscorePages = new ArrayList<>();
        highscorePages.add(new HighscorePage("Easy", easyHandler.getHighscores()));
        highscorePages.add(new HighscorePage("Medium", mediumHandler.getHighscores()));
        highscorePages.add(new HighscorePage("Hard", hardHandler.getHighscores()));

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        HighscorePagerAdapter adapter = new HighscorePagerAdapter(this, highscorePages);
        pager.setAdapter(adapter);
    }

    //ToDo: remove, only here for test purposes
    private void CreateTestList(Difficulty difficulty) {
        HighscoreHandler handler = new HighscoreHandler(this, difficulty);
        //handler.clear();
        HighscoreItem item1 = new HighscoreItem(19000);
        HighscoreItem item2 = new HighscoreItem(18000);
        HighscoreItem item3 = new HighscoreItem(17000);
        HighscoreItem item4 = new HighscoreItem(16000);
        HighscoreItem item5 = new HighscoreItem(15000);
        HighscoreItem item6 = new HighscoreItem(8000);
        HighscoreItem item7 = new HighscoreItem(13000);
        HighscoreItem item8 = new HighscoreItem(12000);
        HighscoreItem item9 = new HighscoreItem(11000);
        HighscoreItem item10 = new HighscoreItem(10000);
        HighscoreItem item11 = new HighscoreItem(9000);

        handler.SaveHighscore(item1.getTimeInMillisec());
        handler.SaveHighscore(item2.getTimeInMillisec());
        handler.SaveHighscore(item3.getTimeInMillisec());
        handler.SaveHighscore(item4.getTimeInMillisec());
        handler.SaveHighscore(item5.getTimeInMillisec());
        handler.SaveHighscore(item6.getTimeInMillisec());
        handler.SaveHighscore(item7.getTimeInMillisec());
        handler.SaveHighscore(item8.getTimeInMillisec());
        handler.SaveHighscore(item9.getTimeInMillisec());
        handler.SaveHighscore(item10.getTimeInMillisec());
        handler.SaveHighscore(item11.getTimeInMillisec());

        handler.SavePreferences();
    }

    //ToDo: remove, only here for test purposes
    private void Clean(){
        SharedPreferences setting = this.getSharedPreferences(HighscoreHandler.EASY_HIGHSCORES_PREF_NAME,0);
        SharedPreferences.Editor editor = setting.edit();
        editor.clear();
        editor.commit();

        setting = this.getSharedPreferences(HighscoreHandler.MEDIUM_HIGHSCORES_PREF_NAME,0);
        editor = setting.edit();
        editor.clear();
        editor.commit();

        setting = this.getSharedPreferences(HighscoreHandler.HARD_HIGHSCORES_PREF_NAME,0);
        editor = setting.edit();
        editor.clear();
        editor.commit();
    }
}
