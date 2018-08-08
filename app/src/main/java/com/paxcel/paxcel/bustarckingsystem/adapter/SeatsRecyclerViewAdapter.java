package com.paxcel.paxcel.bustarckingsystem.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.paxcel.paxcel.bustarckingsystem.utils.ItemClickListener;
import com.paxcel.paxcel.bustarckingsystem.R;
import com.paxcel.paxcel.bustarckingsystem.model.SeatArrangedModel;

import java.util.ArrayList;

public class SeatsRecyclerViewAdapter extends RecyclerView.Adapter<SeatsRecyclerViewAdapter.ViewHolder> {
    private ArrayList<SeatArrangedModel.Lower> arrangedModelsList;
    private LayoutInflater mInflater;
    private ItemClickListener listener;

    public SeatsRecyclerViewAdapter(Context context, ArrayList<SeatArrangedModel.Lower> arrangedModelsList) {
        this.arrangedModelsList = arrangedModelsList;
        this.mInflater = LayoutInflater.from(context);

    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_bus_seats, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.tvSeatNumber.setText(arrangedModelsList.get(position).getLabel());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onItemClick(R.id.rl, holder.getAdapterPosition());
            }
        });

        if (arrangedModelsList.get(position).getStatus() == 0) {
            holder.imageView.setImageResource(R.drawable.seat_unselected);
        } else {
            holder.imageView.setImageResource(R.drawable.seat_selected);

        }

        if (arrangedModelsList.get(position).getLabel().equalsIgnoreCase("#")) {
            holder.imageView.setVisibility(View.GONE);
            holder.tvSeatNumber.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {

        return arrangedModelsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvSeatNumber;
        RelativeLayout rl;

        ViewHolder(View itemView) {
            super(itemView);
            tvSeatNumber = itemView.findViewById(R.id.tv_seatNumber);
            imageView = itemView.findViewById(R.id.iv_seat);
            rl=itemView.findViewById(R.id.rl);
        }
    }
}
