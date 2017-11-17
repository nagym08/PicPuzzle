package com.obuda.nik.picpuzzle;

/**
 * Created by Admin on 2017. 11. 17..
 */

public enum Difficulty {
    EASY(3), MEDIUM(4), HARD(5);

    private final int value;

    private Difficulty(int i) {
        this.value=i;
    }

    public int getValue(){
        return this.value;
    }
    public static CharSequence[] getNames() {
        CharSequence[] Names = new CharSequence[Difficulty.values().length];

        for (int i = 0; i < Names.length; i++) {
            String s = Difficulty.values()[i].toString().toLowerCase();
            Names[i]=s.substring(0,1).toUpperCase() + s.substring(1);
        }

        return Names;
    }
}
