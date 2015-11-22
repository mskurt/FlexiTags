/**
 * @author  Sedat Kurt
 * @since   2015-11-21
 * @company 4pps Mobile Software
 */

package net.mskurt.flexitagslibrary;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;


public class Util {

    public static int DEVICE_HEIGHT;
    public static int DEVICE_WIDTH;
    public static float DENSITY;
    public static float SCALED_DENSITY;
    public static int SDK_VERSION;


    public static void setUtilStuff(Context context) {
        SDK_VERSION= Build.VERSION.SDK_INT;
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        DEVICE_HEIGHT=metrics.heightPixels;
        DEVICE_WIDTH=metrics.widthPixels;
        DENSITY=metrics.density;
        SCALED_DENSITY=metrics.scaledDensity;
    }

    public static Drawable getDrawableResource(Context context,int drawableId){
        if(SDK_VERSION >=22){
            return context.getResources().getDrawable(drawableId, null);
        }
        else if(SDK_VERSION >= 16){
            return context.getResources().getDrawable(drawableId);
        }else{
            return context.getResources().getDrawable(drawableId);
        }
    }

    public static void setViewBackground(View view,Drawable drawable){
        if(SDK_VERSION >= 16){
            view.setBackground(drawable);
        }else{
            view.setBackgroundDrawable(drawable);
        }
    }

}
