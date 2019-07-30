package com.mug.pesona_sukabumi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.bumptech.glide.Glide;

import com.mug.pesona_sukabumi.R;


public class RecyclerViewAdapterG extends RecyclerView.Adapter<RecyclerViewAdapterG.myViewHolder> {

    private Context mContext;
    private List<GalleryActivityModel> mDataGallery;

    public RecyclerViewAdapterG(Context mContext, List<GalleryActivityModel> mDataGallery) {
        this.mContext = mContext;
        this.mDataGallery = mDataGallery;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.cardview_item_gallery, viewGroup,false);
        myViewHolder vHolder = new myViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i) {

        final String nama = mDataGallery.get(i).getPenunjang();
        final String gambar = mDataGallery.get(i).getPoto();

        myViewHolder.setNama(nama);
        Glide.with(mContext)
                // LOAD URL DARI INTERNET
                .load(gambar)
                .into(myViewHolder.imageView4);
        //myViewHolder.setImage(image);

        myViewHolder.imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailPenunjang.class);
                intent.putExtra("nama", nama);
                intent.putExtra("gambar", gambar);

                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataGallery.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        private TextView tvPenunjang;
        private ImageView imageView4;
        private View nView;


        public myViewHolder(View itemView){
            super(itemView);
            nView = itemView;
            tvPenunjang = (TextView) itemView.findViewById(R.id.tvPenunjang);
            imageView4 = (ImageView) itemView.findViewById(R.id.img_photo);
        }

        public void setNama(String nama){
            tvPenunjang = nView.findViewById(R.id.tvPenunjang);
            tvPenunjang.setText(nama);

        }
    }
}
