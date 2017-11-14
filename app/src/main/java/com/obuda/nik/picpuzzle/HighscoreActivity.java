package com.obuda.nik.picpuzzle;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.obuda.nik.picpuzzle.adapters.HighscorePagerAdapter;
import com.obuda.nik.picpuzzle.models.HighscoreItem;
import com.obuda.nik.picpuzzle.models.HighscorePage;

import java.util.ArrayList;
import java.util.List;

public class HighscoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        List<HighscorePage> highscorePages = CreateTestList();

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        HighscorePagerAdapter adapter = new HighscorePagerAdapter(this, highscorePages);
        pager.setAdapter(adapter);
    }

    private List<HighscorePage> CreateTestList(){
        List<HighscorePage> pages = new ArrayList<>();
        List<HighscoreItem> items = new ArrayList<>();
        items.add(new HighscoreItem("Picture1", 1,10));
        items.add(new HighscoreItem("Picture2", 2,20));
        items.add(new HighscoreItem("Picture3", 3,30));

        pages.add(new HighscorePage("Easy", items));
        pages.add(new HighscorePage("Medium", items));
        pages.add(new HighscorePage("Hard", items));

        return pages;
    }
}
