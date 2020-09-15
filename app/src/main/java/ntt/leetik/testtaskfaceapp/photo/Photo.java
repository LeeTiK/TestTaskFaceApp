package ntt.leetik.testtaskfaceapp.photo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Photo {
    @SerializedName("photos")
    public Photos photos;

    public class PhotoAttributes
    {
        @SerializedName("farm")
        public int farm;
        @SerializedName("server")
        public String server;
        @SerializedName("id")
        public String id;
        @SerializedName("secret")
        public String secret;
    }

    public class Photos
    {
        @SerializedName("photo")
        public List<PhotoAttributes> photo = null;
    }
}
