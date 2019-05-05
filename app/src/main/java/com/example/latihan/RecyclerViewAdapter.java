package com.example.latihan;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.example.latihan.DailyActivityModel;
import com.example.latihan.R;


import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.myViewHolder> {

    Context mContext;
    List<DailyActivityModel> mDataDaily;
    //HorizontalScrollView<GalleryActivityModel> mDataGallery;

    public RecyclerViewAdapter(Context mContext, List<DailyActivityModel> mDataDaily) {
        this.mContext = mContext;
        this.mDataDaily = mDataDaily;
        //this.mDataGallery = mDataGallery;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_daily, viewGroup,false);
        myViewHolder vHolder = new myViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i) {

        myViewHolder.tv_kegiatan.setText(mDataDaily.get(i).getKegiatan());

    }

    @Override
    public int getItemCount() {
        return mDataDaily.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_kegiatan;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_kegiatan = (TextView) itemView.findViewById(R.id.tv_kegiatan);
        }
    }


}
