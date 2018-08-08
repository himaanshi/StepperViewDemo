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
import com.paxcel.paxcel.bustarckingsystem.utils.ItemClickListener;

import com.paxcel.paxcel.bustarckingsystem.adapter.RecyclerViewAdapter;
import com.paxcel.paxcel.bustarckingsystem.model.SeatsModel;

import java.util.ArrayList;

public class CalenderFragment extends Fragment {



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.calender_fragment, container, false);

        return v;
    }



}
