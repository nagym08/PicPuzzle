package com.obuda.nik.picpuzzle.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.obuda.nik.picpuzzle.R;
import com.obuda.nik.picpuzzle.models.HighscorePage;

import java.util.List;

/**
 * Created by nagym on 14/11/2017.
 */

public class HighscorePagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<HighscorePage> pages;
    LayoutInflater mLayoutInflater;

    public HighscorePagerAdapter(Context mContext, List<HighscorePage> pages) {
        this.mContext = mContext;
        this.pages = pages;
        this.mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return pages == null ? 0 : pages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //View myView = View.inflate(mContext, R.layout.highscore_page, null);/

        View myView = mLayoutInflater.inflate(R.layout.highscore_page, container, false);

        TextView difficultyText = (TextView) myView.findViewById(R.id.highscoreDifficulty);
        ListView highscoreList = (ListView) myView.findViewById(R.id.highscoreList);

        HighscorePage currentPage = pages.get(position);

        String difficulty = difficultyText.getText() + currentPage.getDifficulty();
        difficultyText.setText(difficulty);

        HighscoreItemListAdapter adapter = new HighscoreItemListAdapter(currentPage.getHighscores());
        highscoreList.setAdapter(adapter);

        container.addView(myView);
        return myView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
