package ntt.leetik.testtaskfaceapp;

import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ntt.leetik.testtaskfaceapp.Adapter.PhotoViewAdapter;
import ntt.leetik.testtaskfaceapp.net.RestService;
import ntt.leetik.testtaskfaceapp.photo.PhotoLoader;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static ntt.leetik.testtaskfaceapp.net.RestService.BASE_URL;
import static ntt.leetik.testtaskfaceapp.net.RestService.MAIN_URL;

public class MainActivity extends AppCompatActivity {

    public RecyclerView mRecyclerView;
    Retrofit retrofit;
    PhotoViewAdapter recyclerViewAdapter;

    public RelativeLayout mRelativeLayout;
    public ImageView mImageView;
    public ImageButton mButtonClose;

    int pageCounter = 1;
    public static MainActivity Current;

    PhotoLoader mPhotoLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSizeDisplay();

        Current = this;
        mImageView = findViewById(R.id.popup_imageview);
        mRelativeLayout = findViewById(R.id.popup_relativeLayout);
        mRecyclerView = findViewById(R.id.show_images_recyclerView);
        mButtonClose = findViewById(R.id.close_imagebutton);
        recyclerViewAdapter = new PhotoViewAdapter(getApplicationContext());
        mRecyclerView.setAdapter(recyclerViewAdapter);

        Utility.orientation = getResources().getConfiguration().orientation;


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            GridLayoutManager manager = new GridLayoutManager(this, 2);
            mRecyclerView.setLayoutManager(manager);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            GridLayoutManager manager = new GridLayoutManager(this, 4);
            mRecyclerView.setLayoutManager(manager);
        }

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
        mPhotoLoader = new PhotoLoader(MainActivity.this, retrofit, recyclerViewAdapter);
        mPhotoLoader.GetPagesCount(MAIN_URL);
        mPhotoLoader.GetPhotosData(String.valueOf(pageCounter));

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (pageCounter <= PhotoLoader.pageCounterServer) {
                        pageCounter++;

                        mPhotoLoader.GetPhotosData(String.valueOf(pageCounter));
                    } else {
                        Toast.makeText(MainActivity.this, "нет фото", Toast.LENGTH_LONG).show();
                    }
                } else if (!recyclerView.canScrollVertically(-1) && newState == RecyclerView.SCROLL_STATE_IDLE) {

                    pageCounter = 1;
                    mPhotoLoader.GetPhotosData(String.valueOf(pageCounter));
                }
            }
        });

        mButtonClose.setOnClickListener(v ->
        {
            //popup_relativeLayout.setAnimation(SetAnimation(MainActivity.Current,R.anim.origin_to_bottom));
            mRelativeLayout.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        });

    }

    void initSizeDisplay(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Utility.width = size.x;
        Utility.height = size.y;
    }
}