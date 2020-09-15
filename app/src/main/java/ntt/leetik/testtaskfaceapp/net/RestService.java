package ntt.leetik.testtaskfaceapp.net;

import android.provider.ContactsContract;

import java.util.Map;
import io.reactivex.Observable;
import ntt.leetik.testtaskfaceapp.photo.Photo;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface RestService {

    String BASE_URL = "https://www.flickr.com/services/rest/";
    String MAIN_URL = "https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=da9d38d3dee82ec8dda8bb0763bf5d9c&format=json&nojsoncallback=1";

    @GET("%3Fmethod=flickr.photos.getRecent&api_key=da9d38d3dee82ec8dda8bb0763bf5d9c&page={pageNumber}&per_page={perPage}&format=json&nojsoncallback=1")
    Observable<Photo> getRecentImageList(@Path("pageNumber") String pageNumber, @Path("perPage") String perPage);

}