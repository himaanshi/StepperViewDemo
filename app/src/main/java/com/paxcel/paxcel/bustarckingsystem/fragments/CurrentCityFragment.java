package com.paxcel.paxcel.bustarckingsystem.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.paxcel.paxcel.bustarckingsystem.utils.ItemClickListener;
import com.paxcel.paxcel.bustarckingsystem.R;
import com.paxcel.paxcel.bustarckingsystem.activities.TabActivity;
import com.paxcel.paxcel.bustarckingsystem.model.SeatArrangedModel;
import com.paxcel.paxcel.bustarckingsystem.adapter.SeatsViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class CurrentCityFragment extends Fragment implements ItemClickListener {

    RecyclerView recyclerView;
    ArrayList<SeatArrangedModel> seatArrangedModelArrayList;
    ArrayList<SeatArrangedModel.Lower> arrayList;
    ArrayList<HashMap<String, String>> seatArrayList;
    ArrayList<HashMap<String, String>> lowerArrayList = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> upperArrayList = new ArrayList<HashMap<String, String>>();


    SeatsViewAdapter viewAdapter;
    String rows,columns;

    TabActivity activity;


    public static String loadJSONFromAssets(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.city_fragment, container, false);
        recyclerView = v.findViewById(R.id.rv);
        seatArrangedModelArrayList = new ArrayList<>();
        arrayList = new ArrayList<>();
        seatArrayList = new ArrayList<HashMap<String, String>>();


        activity = ((TabActivity) getActivity());



        v.findViewById(R.id.tvUpper).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seatArrayList.clear();
                seatArrayList.addAll(upperArrayList);
                viewAdapter.notifyDataSetChanged();


            }
        });

        v.findViewById(R.id.tvFragFirst).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seatArrayList.clear();
                seatArrayList.addAll(lowerArrayList);
                viewAdapter.notifyDataSetChanged();


            }
        });

        viewAdapter = new SeatsViewAdapter(getActivity(), seatArrayList);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 10));

        viewAdapter.setOnItemClickListener(CurrentCityFragment.this);


        viewAdapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        new NewTask(getActivity()).execute();

        return v;
    }

    @Override
    public void onItemClick(int id, int position) {
        switch (id) {
            case R.id.rl:
                Toast.makeText(getActivity(), "" + lowerArrayList.get(position).get("label") + " Clicked ", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private class InsertTask extends AsyncTask<Void, Void, Boolean> {
        @SuppressLint("StaticFieldLeak")
        Context context;

        InsertTask(Context context) {
            this.context = context;

        }

        @Override
        protected Boolean doInBackground(Void... objs) {
            try {
                JSONObject obj = new JSONObject(loadJSONFromAssets(context, "seats2.json"));
                Log.e("json", " " + obj);

                JSONObject jsonObject = obj.getJSONObject("data");
                JSONArray jsonArray = jsonObject.getJSONArray("lower");

                lowerArrayList.clear();

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jo_inside = jsonArray.getJSONObject(i);
                    HashMap<String, String> hashMap;


                    Log.d("json lower-->", jo_inside.getString("rowIndex"));

                    String rowIndex = jo_inside.getString("rowIndex");
                    String colIndex = jo_inside.getString("colIndex");
                    String label = jo_inside.getString("label");
                    String status = jo_inside.getString("status");

                    hashMap = new HashMap<String, String>();
                    hashMap.put("rowIndex", rowIndex);
                    hashMap.put("colIndex", colIndex);
                    hashMap.put("label", label);
                    hashMap.put("status", status);

                    lowerArrayList.add(hashMap);

                }

                JSONArray jsonArrayUpper = jsonObject.getJSONArray("upper");
                upperArrayList.clear();


                for (int i = 0; i < jsonArrayUpper.length(); i++) {

                    JSONObject jo_inside = jsonArrayUpper.getJSONObject(i);
                    HashMap<String, String> hashMap;

                    Log.d("json upper-->", jo_inside.getString("rowIndex"));

                    String rowIndex = jo_inside.getString("rowIndex");
                    String colIndex = jo_inside.getString("colIndex");
                    String label = jo_inside.getString("label");
                    String status = jo_inside.getString("status");

                    hashMap = new HashMap<String, String>();
                    hashMap.put("rowIndex", rowIndex);
                    hashMap.put("colIndex", colIndex);
                    hashMap.put("label", label);
                    hashMap.put("status", status);

                    upperArrayList.add(hashMap);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {

            Log.d("json upper-->", "here");

            seatArrayList.addAll(lowerArrayList);
            viewAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(viewAdapter);

        }
    }

    private class NewTask extends AsyncTask<Void, Void, Boolean> {
        Context context;

        NewTask(Context context) {
            this.context = context;

        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            try {
                JSONObject obj = new JSONObject(loadJSONFromAssets(context, "seats.json"));
                Log.e("json", " " + obj);

                JSONObject jsonObject = obj.getJSONObject("data");

                JSONObject dimensionObj = jsonObject.getJSONObject("dimensions");
                rows = dimensionObj.getString("rows");
                columns = dimensionObj.getString("cols");

                JSONArray jsonArray = jsonObject.getJSONArray("lowerDecker");
                lowerArrayList.clear();

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jo_inside = jsonArray.getJSONObject(i);
                    HashMap<String, String> hashMap;


                    Log.d("json lower2-->", jo_inside.getString("rowIndex"));

                    String rowIndex = jo_inside.getString("rowIndex");
                    String colIndex = jo_inside.getString("colIndex");
                    String label = jo_inside.getString("seatLabel");
                    String status = "";

                    if (jo_inside.has("status")) {
                        status = jo_inside.getString("status");

                    }

                    hashMap = new HashMap<String, String>();
                    hashMap.put("rowIndex", rowIndex);
                    hashMap.put("colIndex", colIndex);
                    hashMap.put("label", label);
                    hashMap.put("status", status);

                    lowerArrayList.add(hashMap);

                }

                JSONArray jsonArrayUpper = jsonObject.getJSONArray("upperDecker");
                upperArrayList.clear();


                for (int i = 0; i < jsonArrayUpper.length(); i++) {

                    JSONObject jo_inside = jsonArrayUpper.getJSONObject(i);
                    HashMap<String, String> hashMap;

                    Log.d("json upper-->", jo_inside.getString("rowIndex"));

                    String rowIndex = jo_inside.getString("rowIndex");
                    String colIndex = jo_inside.getString("colIndex");
                    String label = jo_inside.getString("seatLabel");
                    String status = "";
                    if (jo_inside.has("status")) {
                        status = jo_inside.getString("status");

                    }

                    hashMap = new HashMap<String, String>();
                    hashMap.put("rowIndex", rowIndex);
                    hashMap.put("colIndex", colIndex);
                    hashMap.put("label", label);
                    hashMap.put("status", status);

                    upperArrayList.add(hashMap);

                }


                return null;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return true;
        }

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {

            Log.d("json upper2-->", "here");

            seatArrayList.addAll(lowerArrayList);
            viewAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(viewAdapter);

        }

    }

}
