package com.paxcel.paxcel.bustarckingsystem.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.paxcel.paxcel.bustarckingsystem.R;
import com.paxcel.paxcel.bustarckingsystem.adapter.SeatsViewAdapter;
import com.paxcel.paxcel.bustarckingsystem.model.SeatArrangedModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class DestinationFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<SeatArrangedModel> seatArrangedModelArrayList;
    ArrayList<SeatArrangedModel.Lower> arrayList;
    ArrayList<HashMap<String, String>> seatArrayList;
    ArrayList<HashMap<String, String>> lowerArrayList = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> upperArrayList = new ArrayList<HashMap<String, String>>();
    String rows;
    TableLayout table_layout;
    String columns;


    SeatsViewAdapter viewAdapter;

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
        View v = inflater.inflate(R.layout.destination_fragment, container, false);
        table_layout = v.findViewById(R.id.table_layout);

        seatArrangedModelArrayList = new ArrayList<>();
        arrayList = new ArrayList<>();
        seatArrayList = new ArrayList<HashMap<String, String>>();


        viewAdapter = new SeatsViewAdapter(getActivity(), seatArrayList);
        new DestinationFragment.NewTask(getActivity()).execute();


        return v;
    }

    private void buildTable(int rows, int cols, Context context, ArrayList<HashMap<String, String>> seatArrayList) {

        // outer for loop
        int index = 0;

        for (int i = 0; i < 40; i++) {

            TableRow row = new TableRow(context);
            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            row.setGravity(Gravity.CENTER);

            // inner for loop
            for (int j = 0; j < 30; j++) {

                TextView tv = new TextView(context);
                tv.setLayoutParams(new TableRow.LayoutParams(100,100));
                tv.setPadding(5, 5, 5, 5);
                tv.setBackgroundResource(R.drawable.seat_unselected);
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(10);
                   tv.setText(i+" "+j);

//                setText(seatArrayList.get(index).get("label"), tv);
//                setStatus(seatArrayList.get(index).get("status"), tv);
//                setLabel(seatArrayList.get(index).get("label"), tv);

                index++;
                row.addView(tv);

            }

            table_layout.addView(row);


        }
    }

    void setLabel(String label, TextView tv) {

        if (label.equalsIgnoreCase("#")) {
            setHidden(false, tv);

        } else {
            setHidden(true, tv);
        }

    }

    void setStatus(String status, TextView tv) {

        if (status.equalsIgnoreCase("Y")) {
            tv.setBackgroundResource(R.drawable.seat_unselected);
        } else {
            tv.setBackgroundResource(R.drawable.seat_selected);

        }

    }

    void setText(String label, TextView tv) {
        tv.setText(label);
    }

    void setHidden(boolean hidden, TextView tv) {
        int visibility = hidden ? View.VISIBLE : View.INVISIBLE;
        tv.setVisibility(visibility);
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

        @Override
        protected void onPostExecute(Boolean bool) {

            Log.d("json upper2-->", "here");

            seatArrayList.addAll(lowerArrayList);
            buildTable(Integer.parseInt(rows), Integer.parseInt(columns), context, seatArrayList);

        }

    }

}
