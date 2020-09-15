package ntt.leetik.testtaskfaceapp;

import android.content.Context;

import ntt.leetik.testtaskfaceapp.photo.Photo;

public final class Utility {

    static public int width;
    static public int height;

    static public int orientation;

    public static String getUrlPhoto(Photo.PhotoAttributes attributes) {
        return "https://farm" + attributes.farm + ".staticflickr.com/" + attributes.server + "/" + attributes.id + "_" + attributes.secret + ".jpg";
    }

    // Java
    static public float  convertDpToPixels(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    static public float convertPixelsToDp(Context context, float pixels) {
        return pixels / context.getResources().getDisplayMetrics().density;
    }
}
