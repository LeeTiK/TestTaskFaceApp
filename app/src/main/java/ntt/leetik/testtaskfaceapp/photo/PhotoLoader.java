package ntt.leetik.testtaskfaceapp.photo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ntt.leetik.testtaskfaceapp.Adapter.PhotoViewAdapter;
import ntt.leetik.testtaskfaceapp.net.RestService;
import retrofit2.Retrofit;

public class PhotoLoader {
    private Retrofit retrofit;
    private PhotoViewAdapter photoViewAdapter;
    private Context context;
    public static int pageCounterServer;

    List<Photo.PhotoAttributes> marketList;

    public PhotoLoader(Context context, Retrofit retrofit, PhotoViewAdapter photoViewAdapter) {
        this.context = context;
        this.retrofit = retrofit;
        this.photoViewAdapter = photoViewAdapter;
        marketList = null;
    }

    public void GetPagesCount(String urlString) {
        new Thread() {
            @Override
            public void run() {
                try {
                    HttpURLConnection urlConnection = null;
                    URL url = new URL(urlString);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setDoOutput(true);
                    urlConnection.connect();

                    BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                    StringBuilder sb = new StringBuilder();

                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();

                    Log.v("PhotoPresent", "str: " + sb.toString());

                    pageCounterServer = Integer.valueOf(new JSONObject(new JSONObject(sb.toString()).get("photos").toString()).get("pages").toString());
                } catch (Exception e) {
                    System.out.println("JSON Exception: " + e);
                }
            }
        }.start();
    }

    @SuppressLint("CheckResult")
    public void GetPhotosData(String page) {
        RestService restService = retrofit.create(RestService.class);

        restService
                .getRecentImageList(page, "20")
                .map(result -> Observable.fromIterable(result.photos.photo))
                .flatMap(x -> x)
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .toObservable()
                .subscribe(this::AddResults, this::CatchError);
    }

    private void AddResults(List<Photo.PhotoAttributes> marketList) {
        Log.v("PhotoPresent", "marketList: " + marketList);

        if (marketList != null && marketList.size() != 0) {
            this.marketList = marketList;
            photoViewAdapter.SetData(marketList);
        } else {
            Toast.makeText(context, "нет результата", Toast.LENGTH_LONG).show();
        }
    }

    private void CatchError(Throwable t) {
        Toast.makeText(context, "ERROR" + " : " + t, Toast.LENGTH_LONG).show();
    }

    public List<Photo.PhotoAttributes> getMarketList() {
        return marketList;
    }
}