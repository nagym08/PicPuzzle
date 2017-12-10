package com.obuda.nik.picpuzzle.game;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;

/**
 * Created by Dezsi on 2017. 11. 18..
 */

public class ImageFactory {

    public  static Bitmap modifyImage(Bitmap bitmap, DisplayMetrics metrics, int orientation){
        float ratio=(float)2/3;
        int screenWidth=metrics.widthPixels;
        int screenHeight=metrics.heightPixels;

        Bitmap picture;

        if(orientation== Configuration.ORIENTATION_PORTRAIT) {
            int dstHeight = (int) (((float) screenWidth / bitmap.getWidth()) * bitmap.getHeight());
            picture = Bitmap.createScaledBitmap(bitmap, screenWidth, dstHeight, true);

            if (dstHeight > (screenHeight * ratio))
                picture = Bitmap.createBitmap(picture, 0, 0, screenWidth, (int)(screenHeight * ratio));
        }
        else{
            int dstHeight=(int)(screenHeight*ratio);
            int dstWidth=(int)((float)bitmap.getWidth()/bitmap.getHeight()*dstHeight);

            picture=Bitmap.createScaledBitmap(bitmap,dstWidth,dstHeight,true);
        }
        return picture;
    }
}
