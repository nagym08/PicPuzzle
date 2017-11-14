package com.obuda.nik.picpuzzle.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.obuda.nik.picpuzzle.R;
import com.obuda.nik.picpuzzle.models.HighscoreItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nagym on 14/11/2017.
 */

public class HighscoreItemListAdapter extends BaseAdapter {

    private List<HighscoreItem> highscores;

    public HighscoreItemListAdapter() {
        this.highscores = new ArrayList<HighscoreItem>();
    }

    public HighscoreItemListAdapter(List<HighscoreItem> highscores) {
        this.highscores = highscores;
    }

    @Override
    public int getCount() {
        return highscores.size();
    }

    @Override
    public Object getItem(int position) {
        return highscores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View myView = convertView;

        if(myView==null){
            myView = View.inflate(parent.getContext(), R.layout.highscore_item, null);
        }

        TextView pictureNameText = (TextView) myView.findViewById(R.id.highscoreItemPictureName);
        TextView bestTimeText = (TextView) myView.findViewById(R.id.highscoreItemBestTime);

        String pictureName = highscores.get(position).getPictureName();
        String bestTime = highscores.get(position).getBestTime();

        pictureNameText.setText(pictureName);
        bestTimeText.setText(bestTime);

        return myView;
    }
}
