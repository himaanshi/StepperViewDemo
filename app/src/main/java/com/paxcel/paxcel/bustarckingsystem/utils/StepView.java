package com.paxcel.paxcel.bustarckingsystem.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paxcel.paxcel.bustarckingsystem.R;

public class StepView extends LinearLayout {

    private TextView textView;
    private int separatorColor, separatorWidth, separatorHeight, textSize, noOfSteps, unSelectedTextColor, selectedTextColor;
    private Drawable unSelectedBackground, selectedBackground;


    public StepView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, context);
    }

    public StepView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, context);

    }

    public StepView(Context context) {
        super(context);
        init(null, context);
    }

    private void init(AttributeSet attrs, Context context) {
        if (attrs != null) {

            super.setGravity(Gravity.CENTER);
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.stepView);

            noOfSteps = a.getInteger(R.styleable.stepView_noOfSteps, 2);
            textSize = a.getInteger(R.styleable.stepView_stepTextSize, 12);
            unSelectedBackground = a.getDrawable(R.styleable.stepView_unSelectedBackground);
            selectedBackground = a.getDrawable(R.styleable.stepView_selectedBackground);
            unSelectedTextColor = a.getColor(R.styleable.stepView_unSelectedTextColor, ContextCompat.getColor(context,
                    R.color.colorPrimary));
            selectedTextColor = a.getColor(R.styleable.stepView_selectedTextColor, ContextCompat.getColor(context,
                    R.color.colorPrimary));

            separatorColor = a.getColor(R.styleable.stepView_midLineColor,
                    ContextCompat.getColor(context, R.color.colorPrimary));
            separatorHeight = a.getInteger(R.styleable.stepView_midLineHeight, 60);
            separatorWidth = a.getInteger(R.styleable.stepView_midLineWidth, 3);

            addSteps(context, noOfSteps);
            a.recycle();
        }
    }


    private void addSteps(Context context, int count) {

        for (int i = 0; i < count; i++) {

            textView = new TextView(context);
            View view = new View(context);

            textView.setId(i);
            String index = String.valueOf(i + 1);
            textView.setText(index);
            textView.setTextSize(textSize);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(unSelectedTextColor);
            textView.setBackgroundDrawable(unSelectedBackground);

            view.setBackgroundColor(separatorColor);

            super.addView(textView, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            if (i < count - 1) {
                super.addView(view, new LayoutParams(separatorWidth, separatorHeight));

            }
        }
    }


    public void setSelected(int position) {

        for (int i = 0; i < noOfSteps; i++) {

            if (i <= position) {
                textView = findViewById(i);
                textView.setTextColor(selectedTextColor);
                textView.setBackgroundDrawable(selectedBackground);
            } else {
                textView = findViewById(i);
                textView.setTextColor(unSelectedTextColor);
                textView.setBackgroundDrawable(unSelectedBackground);

            }


        }
    }
}



