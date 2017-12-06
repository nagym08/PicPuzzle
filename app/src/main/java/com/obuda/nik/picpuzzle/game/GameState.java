package com.obuda.nik.picpuzzle.game;

import android.os.Parcel;
import android.os.Parcelable;

import com.obuda.nik.picpuzzle.models.Tile;

/**
 * Created by Dezsi on 2017. 11. 19..
 */

public class GameState implements Parcelable {
    private Difficulty difficulty;
    private int[] TilesOrder;
    private long elapsedTime;

    GameState(Tile[] Tiles, Difficulty difficulty, long elapsedTime){
        this.TilesOrder=new int[difficulty.getValue()*difficulty.getValue()];
        this.difficulty=difficulty;
        this.elapsedTime = elapsedTime;

        for (int i = 0; i < TilesOrder.length; i++) {
            this.TilesOrder[i]=Tiles[i].getID();
        }
    }

    Difficulty getDifficulty(){
        return this.difficulty;
    }
    int[] getTilesOrder(){
        return  this.TilesOrder;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    private GameState(Parcel in) {
        this.TilesOrder = in.createIntArray();
        difficulty=Difficulty.typeOf(in.readInt());
    }
    public static final Creator<GameState> CREATOR = new Creator<GameState>() {
        @Override
        public GameState createFromParcel(Parcel in) {
            return new GameState(in);
        }

        @Override
        public GameState[] newArray(int size) {
            return new GameState[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(TilesOrder);
        dest.writeInt(difficulty.getValue());
    }
}
