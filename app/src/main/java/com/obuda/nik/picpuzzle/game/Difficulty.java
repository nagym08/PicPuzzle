package com.obuda.nik.picpuzzle.game;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dezsi on 2017. 11. 17..
 */

public enum Difficulty {
    EASY(3), MEDIUM(4), HARD(5);

    private final int value;
    private static Map<Integer,Difficulty> map = new HashMap<>();

    Difficulty(int i) {
        this.value=i;
    }

    static {
        for (Difficulty difficulty : Difficulty.values()) {
            map.put(difficulty.value,difficulty);
        }
    }

    public static Difficulty typeOf(int value) {
        return map.get(value);
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
