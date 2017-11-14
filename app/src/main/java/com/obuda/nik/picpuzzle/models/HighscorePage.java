package com.obuda.nik.picpuzzle.models;

import java.util.List;

/**
 * Created by nagym on 14/11/2017.
 */

public class HighscorePage {
    private String difficulty;
    private List<HighscoreItem> highscores;

    public HighscorePage(String difficulty, List<HighscoreItem> highscores) {
        this.difficulty = difficulty;
        this.highscores = highscores;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public List<HighscoreItem> getHighscores() {
        return highscores;
    }

    public void setHighscores(List<HighscoreItem> highscores) {
        this.highscores = highscores;
    }
}
