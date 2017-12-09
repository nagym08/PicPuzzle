package com.obuda.nik.picpuzzle.exceptions;

/**
 * Created by nagym on 09/12/2017.
 */

public class InvalidTileIDException extends Exception {
    public InvalidTileIDException() {
    }

    public InvalidTileIDException(String message) {
        super(message);
    }
}
