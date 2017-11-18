package com.obuda.nik.picpuzzle.game;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;

/**
 * Created by Dezsi on 2017. 11. 18..
 */

public class ImageFactory {

    public  static Bitmap modifyImage(Bitmap bitmap, DisplayMetrics metrics){
        float ratio=(float)2/3; //FIXME ratio should depends on difficulty (difficulty ~ 1 / tile size)
        int screenWidth=metrics.widthPixels;
        int screenHeight=metrics.heightPixels;

        Bitmap picture;

        int dstHeight = (int) (((float) screenWidth / bitmap.getWidth()) * bitmap.getHeight());
        picture = Bitmap.createScaledBitmap(bitmap, screenWidth, dstHeight, true);

        if(dstHeight>(screenHeight*ratio))
            picture=Bitmap.createBitmap(picture,0,0,screenWidth,screenHeight*2/3);

        return picture;
    }
}
