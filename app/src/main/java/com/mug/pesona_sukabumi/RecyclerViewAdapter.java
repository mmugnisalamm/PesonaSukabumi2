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

import com.mug.pesona_sukabumi.R;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.myViewHolder> {

    Context mContext;
    List<TempatPariwisataModel> mDataDaily;
    //private ImageView imageView;
    //HorizontalScrollView<GalleryActivityModel> mDataGallery;

    public RecyclerViewAdapter(Context mContext, List<TempatPariwisataModel> mDataDaily) {
        this.mContext = mContext;
        this.mDataDaily = mDataDaily;
        //this.mDataGallery = mDataGallery;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_tempat_pariwisata, viewGroup,false);
        myViewHolder vHolder = new myViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i) {

        final String nama = mDataDaily.get(i).getNama();
        final String gambar = mDataDaily.get(i).getGambar();
        final String deskripsi = mDataDaily.get(i).getDeskripsi();
        final String latitude = mDataDaily.get(i).getLatitude();
        final String longitude = mDataDaily.get(i).getLongitude();
        //myViewHolder.tv_kegiatan.setText(mDataDaily.get(i).getKegiatan());
        //myViewHolder.imageView.setImageResource(mDataDaily.get(i).getImage());
        //Glide.with(mContext).load(mDataDaily.get(i).getImage()).into(myViewHolder.imageView);
        myViewHolder.setNama(nama);
        Glide.with(mContext)
                // LOAD URL DARI INTERNET
                .load(gambar)
                .into(myViewHolder.imageView);
        //myViewHolder.setImage(image);

        myViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailTempat.class);
                intent.putExtra("deskripsi", deskripsi);
                intent.putExtra("gambar", gambar);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("nama", nama);

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataDaily.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_kegiatan;
        private ImageView imageView;
        private View nView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            nView = itemView;
            tv_kegiatan = (TextView) itemView.findViewById(R.id.tv_kegiatan);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }

        public void setNama(String nama){
            tv_kegiatan = nView.findViewById(R.id.tv_kegiatan);
            tv_kegiatan.setText(nama);

        }
    }


}
