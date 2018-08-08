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

import java.util.ArrayList;
import java.util.HashMap;

public class SeatsViewAdapter extends RecyclerView.Adapter<SeatsViewAdapter.ViewHolder> {
    private ArrayList<HashMap<String, String>> arrangedModelsList;
    private LayoutInflater mInflater;
    private ItemClickListener listener;
    private View.OnClickListener mClickListener;

    public SeatsViewAdapter(Context context, ArrayList<HashMap<String, String>> arrangedModelsList) {
        this.arrangedModelsList = arrangedModelsList;
        this.mInflater = LayoutInflater.from(context);

    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public void setClickListener(View.OnClickListener callback) {
        mClickListener = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_bus_seats, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onItemClick(R.id.rl, holder.getAdapterPosition());
            }
        });

        holder.setText(arrangedModelsList.get(position).get("label"));
        holder.setStatus(arrangedModelsList.get(position).get("status"));
        holder.setLabel(arrangedModelsList.get(position).get("label"));

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

        void setHidden(boolean hidden) {
            int visibility = hidden ? View.VISIBLE : View.GONE;
            imageView.setVisibility(visibility);
            tvSeatNumber.setVisibility(visibility);
        }

        void setLabel(String label) {

            if (label.equalsIgnoreCase("#")) {
                setHidden(false);

            } else {
                setHidden(true);
            }

        }

        void setStatus(String status) {

            if (status.equalsIgnoreCase("Y")) {
                imageView.setImageResource(R.drawable.seat_unselected);
            } else {
                imageView.setImageResource(R.drawable.seat_selected);

            }

        }

        void setText(String label) {
            tvSeatNumber.setText(label);
        }

    }
}
