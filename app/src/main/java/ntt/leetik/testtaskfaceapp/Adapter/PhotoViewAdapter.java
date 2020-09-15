package ntt.leetik.testtaskfaceapp.Adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import ntt.leetik.testtaskfaceapp.MainActivity;
import ntt.leetik.testtaskfaceapp.R;
import ntt.leetik.testtaskfaceapp.Utility;
import ntt.leetik.testtaskfaceapp.photo.Photo;
import ntt.leetik.testtaskfaceapp.photo.SquareTransformation;


public class PhotoViewAdapter extends RecyclerView.Adapter<PhotoViewAdapter.ViewHolder> {
    private List<Photo.PhotoAttributes> photoList;
    private Context context;

    public PhotoViewAdapter(Context context) {
        photoList = new ArrayList<>();
        this.context = context;
    }

    @Override
    public PhotoViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_photoview, parent, false);

        PhotoViewAdapter.ViewHolder viewHolder = new PhotoViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Photo.PhotoAttributes photoAttributes = photoList.get(position);
        // отступы
        Log.d("PhotoViewAdapter", "Utility.width " + Utility.width);
        if (Utility.orientation == Configuration.ORIENTATION_PORTRAIT){

            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams((int)(Utility.width*0.35),(int)(Utility.width*0.35));

            if (position%2 == 0) {
                    lp.setMargins((int) (Utility.width * 0.10), 0, (int) (Utility.width * 0.05), 0);
            }
            else
            {
                lp.setMargins((int) (Utility.width * 0.05), 0, (int) (Utility.width * 0.10), 0);
            }
            holder.display_to_image.setLayoutParams(lp);
        }
        else {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams((int)(Utility.width*0.20),(int)(Utility.width*0.20));

            if (position%4 == 0) {
                lp.setMargins((int) (Utility.width * 0.04), 0, (int) (Utility.width * 0.02), 0);
                //holder.relativeLayout.setPadding((int) (Utility.width * 0.04), 0, (int) (Utility.width * 0.02), 0);
            }
            else
            {
                if ((position+1)%4 == 0 || position==3) {
                    lp.setMargins((int) (Utility.width * 0.02), 0, (int) (Utility.width * 0.04), 0);
                    //holder.relativeLayout.setPadding((int) (Utility.width * 0.02), 0, (int) (Utility.width * 0.04), 0);
                }
                else {
                    lp.setMargins((int) (Utility.width * 0.02), 0, (int) (Utility.width * 0.02), 0);
                //    holder.relativeLayout.setPadding((int)(Utility.width*0.02),0, (int)(Utility.width*0.02),0);
                }
            }

            holder.display_to_image.setLayoutParams(lp);
        }



        Picasso.with(this.context)
                .load(Utility.getUrlPhoto(photoAttributes))
                .transform(new SquareTransformation())
                .into(holder.display_to_image);

        holder.display_to_image.setOnClickListener(v ->
        {
            MainActivity.Current.mRelativeLayout.setVisibility(View.VISIBLE);
            MainActivity.Current.mRecyclerView.setVisibility(View.INVISIBLE);
            Picasso.with(this.context)
                    .load(Utility.getUrlPhoto(photoAttributes))
                    .into(MainActivity.Current.mImageView);

        });
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public void SetData(List<Photo.PhotoAttributes> data) {
        this.photoList.addAll(data);
        notifyDataSetChanged();
    }

    public void ClearData() {
        this.photoList.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView display_to_image;
        private RelativeLayout relativeLayout;

        private ViewHolder(View view) {
            super(view);

            display_to_image = view.findViewById(R.id.photoView);
            relativeLayout = view.findViewById(R.id.relativeLayout);

            if (Utility.orientation == Configuration.ORIENTATION_PORTRAIT){
                display_to_image.setLayoutParams(new RelativeLayout.LayoutParams((int)(Utility.width*0.35),(int)(Utility.width*0.35)));
            }
            else {
                display_to_image.setLayoutParams(new RelativeLayout.LayoutParams((int)(Utility.width*0.20),(int)(Utility.width*0.20)));
            }

            Log.d("PhotoViewAdapter", "image " + display_to_image.getWidth());
        }
    }
}