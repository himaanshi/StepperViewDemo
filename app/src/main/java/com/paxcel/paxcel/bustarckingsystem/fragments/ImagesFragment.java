package com.paxcel.paxcel.bustarckingsystem.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.paxcel.paxcel.bustarckingsystem.R;
import com.paxcel.paxcel.bustarckingsystem.adapter.RecyclerViewAdapter;
import com.paxcel.paxcel.bustarckingsystem.model.SeatsModel;
import com.paxcel.paxcel.bustarckingsystem.utils.ItemClickListener;

import java.util.ArrayList;

public class ImagesFragment extends Fragment implements ItemClickListener {

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    int[] imagesArray = new int[]{R.drawable.jeep, R.drawable.jeep, R.drawable.jeep, R.drawable.jeep};


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_images, container, false);
        recyclerView = v.findViewById(R.id.rv_seats);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        SeatsModel seatsModel = new SeatsModel();
        seatsModel.setValue("booked");
        seatsModel.setSeatNumber(imagesArray[0]);

        SeatsModel seatsModel2 = new SeatsModel();
        seatsModel2.setValue("booked");
        seatsModel2.setSeatNumber(imagesArray[1]);


        SeatsModel seatsModel3 = new SeatsModel();
        seatsModel3.setValue("vacant");
        seatsModel3.setSeatNumber(imagesArray[2]);
        SeatsModel seatsModel4 = new SeatsModel();
        seatsModel4.setValue("vacant");
        seatsModel4.setSeatNumber(imagesArray[3]);

        ArrayList<SeatsModel> seatsModelArrayList = new ArrayList<>();
        seatsModelArrayList.add(seatsModel);
        seatsModelArrayList.add(seatsModel2);

        seatsModelArrayList.add(seatsModel3);
        seatsModelArrayList.add(seatsModel4);

        adapter = new RecyclerViewAdapter(getActivity(), seatsModelArrayList);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        return v;
    }


    @Override
    public void onItemClick(int id, int position) {
        switch (id) {
            case R.id.ll:
                Toast.makeText(getActivity(), "" + position + " Clicked ", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
