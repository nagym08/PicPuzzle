package com.obuda.nik.picpuzzle.handlers;

import android.content.Context;
import android.content.SharedPreferences;

import com.obuda.nik.picpuzzle.game.Difficulty;
import com.obuda.nik.picpuzzle.models.HighscoreItem;

import java.util.Stack;

/**
 * Created by nagym on 08/12/2017.
 */

public class HighscoreHandler {

    public static final String EASY_HIGHSCORES_PREF_NAME = "EasyHighscores";
    public static final String MEDIUM_HIGHSCORES_PREF_NAME = "MediumHighscores";
    public static final String HARD_HIGHSCORES_PREF_NAME = "HardHighscores";

    public static final int MAX_HIGHSCORE_COUNT = 10;

    Stack<HighscoreItem> highscores;
    String actPreferenceName;
    Context context;

    public Stack<HighscoreItem> getHighscores() {
        return highscores;
    }

    public HighscoreHandler(Context context, Difficulty difficulty) {

        this.context = context;
        this.highscores = new Stack<>();

        if(difficulty == Difficulty.EASY){
            actPreferenceName = EASY_HIGHSCORES_PREF_NAME;
        }

        if(difficulty == Difficulty.MEDIUM){
            actPreferenceName = MEDIUM_HIGHSCORES_PREF_NAME;
        }

        if(difficulty == Difficulty.HARD){
            actPreferenceName = HARD_HIGHSCORES_PREF_NAME;
        }

        LoadHighscores();
    }

    private void LoadHighscores(){
        if(!actPreferenceName.isEmpty()){
            SharedPreferences settings = context.getSharedPreferences(actPreferenceName, 0);
            for (int i = 0; i < settings.getAll().size(); i++) {
                long value = settings.getLong(Integer.toString(i), 0);
                highscores.push(new HighscoreItem(value));
            }
        }
    }

    public void SaveHighscore(long time){
        if(!highscores.isEmpty()){
            Stack<HighscoreItem> tempStack = new Stack<>();

            HighscoreItem actHighscore = highscores.pop();
            tempStack.push(actHighscore);

            while (!highscores.isEmpty() && time < actHighscore.getTimeInMillisec()){
                actHighscore = highscores.pop();
                tempStack.push(actHighscore);
            }

            actHighscore = tempStack.pop();
            HighscoreItem highscoreToAdd = new HighscoreItem(time);

            if(highscoreToAdd.getTimeInMillisec() < actHighscore.getTimeInMillisec()) {
                highscores.push(highscoreToAdd);
                highscores.push(actHighscore);
            }else {
                highscores.push(actHighscore);
                highscores.push(highscoreToAdd);
            }

            while (highscores.size() != 10 && !tempStack.empty()){
                actHighscore = tempStack.pop();
                highscores.push(actHighscore);
            }
        }else {
            highscores.push(new HighscoreItem(time));
        }

    }

    public boolean isTimeHighscore(long time){
        if(highscores.size() == MAX_HIGHSCORE_COUNT){
            if(highscores.get(MAX_HIGHSCORE_COUNT-1).getTimeInMillisec() < time){
                return false;
            }
        }
        return true;
    }

    public void SavePreferences(){
        SharedPreferences settings = context.getSharedPreferences(actPreferenceName, 0);
        SharedPreferences.Editor editor = settings.edit();
        int originalStackSize = highscores.size();
        for (int i = (originalStackSize - 1); i >= 0 ; i--) {
            HighscoreItem actItem = highscores.pop();
            editor.putLong(Integer.toString(i), actItem.getTimeInMillisec());
        }
        editor.commit();
    }

    public void clear(){
        SharedPreferences settings = context.getSharedPreferences(actPreferenceName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }
}
