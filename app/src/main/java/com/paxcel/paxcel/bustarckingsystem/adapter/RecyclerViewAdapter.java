package com.paxcel.paxcel.bustarckingsystem.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.paxcel.paxcel.bustarckingsystem.R;
import com.paxcel.paxcel.bustarckingsystem.model.SeatsModel;
import com.paxcel.paxcel.bustarckingsystem.utils.ItemClickListener;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    private LayoutInflater mInflater;
    private ItemClickListener listener;
    private ArrayList<SeatsModel> seatsModelArrayList;

    public RecyclerViewAdapter(Context context, ArrayList<SeatsModel> arrayList) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.seatsModelArrayList = arrayList;
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_seats, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {


        Glide.with(context)
                .load(seatsModelArrayList.get(position).getSeatNumber())

                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.imageView);


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onItemClick(R.id.ll, holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return seatsModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        ProgressBar progressBar;
        LinearLayout ll;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_seat);
            progressBar = itemView.findViewById(R.id.progress_bar);
            ll = itemView.findViewById(R.id.ll);
        }
    }
}
