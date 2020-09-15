package ntt.leetik.testtaskfaceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ntt.leetik.testtaskfaceapp.Adapter.PhotoViewAdapter;
import ntt.leetik.testtaskfaceapp.net.RestService;
import ntt.leetik.testtaskfaceapp.photo.PhotoLoader;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static ntt.leetik.testtaskfaceapp.net.RestService.BASE_URL;
import static ntt.leetik.testtaskfaceapp.net.RestService.MAIN_URL;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    Retrofit retrofit;
    PhotoViewAdapter recyclerViewAdapter;

    public RelativeLayout mRelativeLayout;
    public ImageView popupImageView;

    int pageCounter = 1;
    public static MainActivity Current;

    PhotoLoader mPhotoLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Current = this;
        popupImageView = findViewById(R.id.popup_imageview);
        mRelativeLayout =  findViewById(R.id.relativeLayout);
        mRecyclerView = findViewById(R.id.show_images_recyclerView);
        recyclerViewAdapter = new PhotoViewAdapter(getApplicationContext());
        mRecyclerView.setAdapter(recyclerViewAdapter);

        GridLayoutManager manager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(manager);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        RestService restService = retrofit.create(RestService.class);
        mPhotoLoader = new PhotoLoader(MainActivity.this,retrofit,recyclerViewAdapter);
        mPhotoLoader.GetPagesCount(MAIN_URL);
        mPhotoLoader.GetPhotosData(String.valueOf(pageCounter));

    /*    Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {


                Log.d("Main", "sleep");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("Main", "photoPresenter.getMarketList(): " + mPhotoLoader.getMarketList());
                if (mPhotoLoader.getMarketList()!=null)
                {
                    Log.d("Main", "Url: " + Utility.getUrlPhoto(mPhotoLoader.getMarketList().get(0)));
                    //ImageView imageView = findViewById(R.id.imageView);

                    Handler uiHandler = new Handler(Looper.getMainLooper());
                    uiHandler.post(new Runnable(){
                        @Override
                        public void run() {
                            Picasso.with(Current)
                                    .load(Utility.getUrlPhoto(mPhotoLoader.getMarketList().get(0)));
                                   //.into(imageView);
                        }
                    });
                }

            }
        });
        thread.start();;*/

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE)
                {
                    if(pageCounter <= PhotoLoader.pageCounterServer)
                    {
                        pageCounter++;

                        mPhotoLoader.GetPhotosData(String.valueOf(pageCounter));
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"нет фото",Toast.LENGTH_LONG).show();
                    }
                }
                else if(!recyclerView.canScrollVertically(-1) && newState==RecyclerView.SCROLL_STATE_IDLE)
                {

                    pageCounter = 1;
                    mPhotoLoader.GetPhotosData(String.valueOf(pageCounter));
                }
            }
        });

    }
}