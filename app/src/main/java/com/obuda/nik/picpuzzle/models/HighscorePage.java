package com.obuda.nik.picpuzzle.models;

import java.util.List;
import java.util.Stack;

/**
 * Created by nagym on 14/11/2017.
 */

public class HighscorePage {
    private String difficulty;
    private Stack<HighscoreItem> highscores;

    public HighscorePage(String difficulty, Stack<HighscoreItem> highscores) {
        this.difficulty = difficulty;
        this.highscores = highscores;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Stack<HighscoreItem> getHighscores() {
        return highscores;
    }

    public void setHighscores(Stack<HighscoreItem> highscores) {
        this.highscores = highscores;
    }
}
