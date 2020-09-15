package ntt.leetik.testtaskfaceapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import ntt.leetik.testtaskfaceapp.MainActivity;
import ntt.leetik.testtaskfaceapp.R;
import ntt.leetik.testtaskfaceapp.Utility;
import ntt.leetik.testtaskfaceapp.photo.Photo;


public class PhotoViewAdapter extends RecyclerView.Adapter<PhotoViewAdapter.ViewHolder>
{
    private List<Photo.PhotoAttributes> photoList;
    private Context context;

    public PhotoViewAdapter(Context context)
    {
        photoList = new ArrayList<>();
        this.context = context;
    }

    @Override
    public PhotoViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_photoview, parent, false);

        PhotoViewAdapter.ViewHolder viewHolder = new PhotoViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PhotoViewAdapter.ViewHolder holder, int position)
    {
        final Photo.PhotoAttributes photoAttributes = photoList.get(position);
        Picasso.with(this.context)
                .load(Utility.getUrlPhoto(photoAttributes))
                .into(holder.display_to_image);

        holder.relativeLayout.setOnClickListener(v ->
        {
            Picasso.with(this.context)
                    .load(Utility.getUrlPhoto(photoAttributes))
                    .into(MainActivity.Current.popup_imageview);
        });
    }

    @Override
    public int getItemCount()
    {
        return photoList.size();
    }

    public void SetData(List<Photo.PhotoAttributes> data)
    {
        this.photoList.addAll(data);
        notifyDataSetChanged();
    }

    public void ClearData()
    {
        this.photoList.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView display_to_image;
        private RelativeLayout relativeLayout;

        private ViewHolder(View view)
        {
            super(view);

            display_to_image = view.findViewById(R.id.photoView);
            relativeLayout = view.findViewById(R.id.relativeLayout);
        }
    }
}