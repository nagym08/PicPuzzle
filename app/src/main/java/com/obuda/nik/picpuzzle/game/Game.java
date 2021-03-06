package com.obuda.nik.picpuzzle.game;

import android.graphics.Bitmap;

import com.obuda.nik.picpuzzle.exceptions.InvalidTileIDException;
import com.obuda.nik.picpuzzle.exceptions.NoEmptyNeighbourTileException;
import com.obuda.nik.picpuzzle.models.Tile;

import java.util.Random;

/**
 * Created by Dezsi on 2017.11.15..
 */

public class Game {

    private final int EMPTY = 100;

    private Random mixVar;
    private Tile[][] Table;
    private int side;
    private Difficulty difficulty;
    private long elapsedTime;

    public Difficulty getDifficulty(){
        return this.difficulty;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public Game() {
        mixVar = new Random();
    }

    private void Mix() {
        Tile temp;

        //swap empty and last piece
        temp = Table[side][side];
        Table[side][side] = Table[side + 1][side];
        Table[side + 1][side] = temp;

        for (int i = 0; i < 3 * side; i++) {
            randomSwap();
        }

        while (!isSolvable()) {
            randomSwap();
        }
    }

    private void randomSwap() {
        int i1;
        int j1;
        int i2;
        int j2;

        i1 = mixVar.nextInt(side) + 1;
        j1 = mixVar.nextInt(side) + 1;
        i2 = mixVar.nextInt(side) + 1;
        j2 = mixVar.nextInt(side) + 1;

        swap(i1,j1,i2,j2);
    }

    private void swap(int srcX, int srcY, int dstX, int dstY){
        Tile temp=Table[srcX][srcY];
        Table[srcX][srcY] = Table[dstX][dstY];
        Table[dstX][dstY] = temp;
    }

    private int sumInversions() {
        Tile[] array = toArray();
        int inversions = 0;
        for (int i = 0; i < side*side; i++) {
            if (array[i].getID() != EMPTY)
                inversions += countInversions(i, array);
        }
        return inversions;
    }

    private boolean isSolvable() {
        int inversion = sumInversions();

        if (side % 2 == 0) {
            try {
                return (findTile(EMPTY)[0] % 2 != 0) == (inversion % 2 == 0);
            } catch (Exception e) {
            }
        }
        return inversion%2==0;
    }

    private int countInversions(int elementID, Tile[] array) {

        int inversion = 0;

        for (int i = elementID + 1; i < array.length; i++) {
            if (array[i].getID() < array[elementID].getID())
                inversion++;
        }

        return inversion;
    }

    public Tile[] toArray() {
        Tile[] array = new Tile[(side+1) * side];
        int counter = 0;
        for (int i = 1; i <= side+1; i++) {
            for (int j = 1; j <= side; j++) {
                array[counter++] = Table[i][j];
            }
        }
        return array;
    }

    private int[] findTile(int tileID) throws InvalidTileIDException {
        for (int i = 1; i <= side; i++) {
            for (int j = 1; j <= side; j++) {
                if (Table[i][j].getID() == tileID)
                    return new int[]{i, j};
            }
        }

        if (Table[side + 1][side].getID() == tileID)
            return new int[]{side + 1, side};
        throw new InvalidTileIDException();
    }

    private int[] findEmptyNeighbour(int[] baseCoordinates) throws NoEmptyNeighbourTileException {
        int i = baseCoordinates[0];
        int j = baseCoordinates[1];

        if (Table[i - 1][j].getID() == EMPTY)
            return new int[]{i - 1, j};
        if (Table[i][j - 1].getID() == EMPTY)
            return new int[]{i, j - 1};
        if (Table[i][j + 1].getID() == EMPTY)
            return new int[]{i, j + 1};
        if (Table[i + 1][j].getID() == EMPTY)
            return new int[]{i + 1, j};

        throw new NoEmptyNeighbourTileException();
    }

    private  Bitmap[] slicePicture(Bitmap picture, int side){

        final Bitmap[] bitmapsArray = new Bitmap[side*side];
        int widthUnit = picture.getWidth() / side;
        int heightUnit = picture.getHeight() / side;
        int id=0;

        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                bitmapsArray[id++]=Bitmap.createBitmap(picture,j*widthUnit,i*heightUnit,widthUnit,heightUnit);
            }
        }

        return bitmapsArray;
    }

    public void Init(Difficulty difficulty, Bitmap picture) {
        this.side = difficulty.getValue();
        this.difficulty=difficulty;
        this.elapsedTime=0;

        Bitmap[] picturePieces=slicePicture(picture,side);

        Tile border = new Tile(0, null);
        int id = 0;

        Table = new Tile[side + 3][];
        for (int i = 0; i < side + 3; i++) {
            Table[i] = new Tile[side + 2];
        }

        //upper+lower border
        for (int i = 0; i <= side + 1; i++) {
            Table[0][i] = border;
            Table[side + 2][i] = border;
        }

        //left+right border
        for (int i = 1; i <= side + 1; i++) {
            Table[i][0] = border;
            Table[i][side + 1] = border;
        }

        //extra border
        for (int i = 1; i <= side - 1; i++) {
            Table[side + 1][i] = border;
        }

        // Picture Tiles
        for (int i = 1; i <= side; i++) {
            for (int j = 1; j <= side; j++) {
                Table[i][j] = new Tile(id+1, picturePieces[id++]);
            }
        }
        //empty place
        Table[side + 1][side] = new Tile(EMPTY, null);

        Mix();
    }

    public void move(int tileID) {
        try {
            int[] srcCoordinates = findTile(tileID);
            int[] dstCoordinates = findEmptyNeighbour(srcCoordinates);
            Tile temp = Table[srcCoordinates[0]][srcCoordinates[1]];
            Table[srcCoordinates[0]][srcCoordinates[1]] = Table[dstCoordinates[0]][dstCoordinates[1]];
            Table[dstCoordinates[0]][dstCoordinates[1]] = temp;

        } catch (NoEmptyNeighbourTileException e) {}
          catch(InvalidTileIDException e) {}

    }

    public boolean puzzleSolved() {
        int id = 1;
        for (int i = 1; i <= side; i++) {
            for (int j = 1; j <= side; j++) {
                if (Table[i][j].getID() != id++)
                    return false;
            }
        }
        return true;
    }

    public GameState getGameState(){
        return new GameState(this.toArray(),this.difficulty, this.elapsedTime);
    }

    public void loadGameState(GameState state) throws Exception{
        if(state.getDifficulty()!=this.difficulty){
            throw new Exception("Incompatible difficulty levels! "+
                    this.difficulty + " != " +state.getDifficulty() );
        }else{
            int id=0;

            for (int i = 1; i <= side ; i++) {
                for (int j = 1; j <= side ; j++) {
                    if(Table[i][j].getID()!=state.getTilesOrder()[id]){
                        int[] coordinates=findTile(state.getTilesOrder()[id]);
                        swap(i,j,coordinates[0],coordinates[1]);
                    }
                    id++;
                }
            }
            this.elapsedTime = state.getElapsedTime();
        }
    }
}


