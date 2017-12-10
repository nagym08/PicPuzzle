package com.obuda.nik.picpuzzle.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.obuda.nik.picpuzzle.R;
import com.obuda.nik.picpuzzle.models.HighscoreItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by nagym on 14/11/2017.
 */

public class HighscoreItemListAdapter extends BaseAdapter {

    private Stack<HighscoreItem> highscores;

    public HighscoreItemListAdapter() {
        this.highscores = new Stack<HighscoreItem>();
    }

    public HighscoreItemListAdapter(Stack<HighscoreItem> highscores) {
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

        TextView bestTimeText = (TextView) myView.findViewById(R.id.highscoreItemBestTime);

        String bestTime = highscores.get(position).getBestTime();

        int sec=Integer.parseInt(bestTime.substring(bestTime.indexOf(":")+1));
        bestTime=bestTime.substring(0,bestTime.indexOf(':')+1).concat(String.format("%02d",sec));

        bestTimeText.setText(bestTime);

        return myView;
    }
}
