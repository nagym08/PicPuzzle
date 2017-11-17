package com.obuda.nik.picpuzzle;

import android.graphics.Bitmap;

import com.obuda.nik.picpuzzle.models.Tile;

import java.util.Random;

/**
 * Created by Dezsi on 2017.11.15..
 */

public class Game {

    //FIXME
    protected enum DIFFICULTY{toRemove0, toRemove1, toRemove2 }

    private final int EMPTY = 100;

    private Random mixVar;
    private Tile[][] Table;
    private int side;


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
        Tile temp;
        int i1;
        int j1;
        int i2;
        int j2;

        i1 = mixVar.nextInt(side) + 1;
        j1 = mixVar.nextInt(side) + 1;
        i2 = mixVar.nextInt(side) + 1;
        j2 = mixVar.nextInt(side) + 1;

        temp = Table[i1][j1];
        Table[i1][j1] = Table[i2][j2];
        Table[i2][j2] = temp;
    }

    private int sumInversions() {
        Tile[] array = ToArray();
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
                return (findTile(EMPTY)[0] % 2 == 0) == (inversion % 2 == 0);
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

    Tile[] ToArray() {
        Tile[] array = new Tile[(side+1) * side];
        int counter = 0;
        for (int i = 1; i <= side+1; i++) {
            for (int j = 1; j <= side; j++) {
                array[counter++] = Table[i][j];
            }
        }
        return array;
    }

    private int[] findTile(int tileID) throws Exception {
        for (int i = 1; i <= side; i++) {
            for (int j = 1; j <= side; j++) {
                if (Table[i][j].getID() == tileID)
                    return new int[]{i, j};
            }
        }

        if (Table[side + 1][side].getID() == tileID)
            return new int[]{side + 1, side};
        throw new Exception(); //TODO make custom InvalidTileID exception?
    }

    private int[] findEmptyNeighbour(int[] baseCoordinates) throws Exception {
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

        throw new Exception(); //TODO make custom, NoEmptyNeighbourTile exception?
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

    public void Init(int side, Bitmap picture) {
        this.side = side;

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

        } catch (Exception e) {

        }

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
}

