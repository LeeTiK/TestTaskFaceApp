package ntt.leetik.testtaskfaceapp.photo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Photo {
    @SerializedName("photos")
    public Photos photos;

    public class PhotoAttributes {
        @SerializedName("id")
        public String id;
        @SerializedName("owner")
        public String owner;
        @SerializedName("secret")
        public String secret;
        @SerializedName("server")
        public String server;
        @SerializedName("farm")
        public int farm;
        @SerializedName("title")
        public String title;

    }

    public class Photos {
        @SerializedName("photo")
        public List<PhotoAttributes> photo = null;
    }
}
