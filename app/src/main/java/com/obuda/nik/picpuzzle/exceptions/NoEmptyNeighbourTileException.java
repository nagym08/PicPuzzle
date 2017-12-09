package com.obuda.nik.picpuzzle.exceptions;

/**
 * Created by nagym on 09/12/2017.
 */

public class NoEmptyNeighbourTileException extends Exception {
    public NoEmptyNeighbourTileException() {
    }

    public NoEmptyNeighbourTileException(String message) {
        super(message);
    }
}
