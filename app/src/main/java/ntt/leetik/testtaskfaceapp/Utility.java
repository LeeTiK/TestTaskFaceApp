package ntt.leetik.testtaskfaceapp;

import ntt.leetik.testtaskfaceapp.photo.Photo;

public final class Utility {

    public static String getUrlPhoto(Photo.PhotoAttributes attributes) {
        return "https://farm" + attributes.farm + ".staticflickr.com/" + attributes.server + "/" + attributes.id + "_" + attributes.secret + ".jpg";
    }
}
