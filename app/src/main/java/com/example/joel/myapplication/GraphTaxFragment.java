package com.example.joel.myapplication;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener.ChartGesture;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

public class GraphTaxFragment extends SimpleFragment implements OnChartValueSelectedListener, OnChartGestureListener {
    private BarChart mChart;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_graph_tax, container, false);
        this.mChart = (BarChart) v.findViewById(R.id.barChart);
        this.mChart.getDescription().setEnabled(false);
        this.mChart.setOnChartGestureListener(this);
        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
        mv.setChartView(this.mChart);
        this.mChart.setMarker(mv);
        this.mChart.setDrawGridBackground(false);
        this.mChart.setDrawBarShadow(false);
//        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");
        this.mChart.setData(generateBarData(1, 20000.0f, 2));
//        this.mChart.getLegend().setTypeface(tf);
        YAxis leftAxis = this.mChart.getAxisLeft();
//        leftAxis.setTypeface(tf);
        leftAxis.setAxisMinimum(0.0f);
        this.mChart.getAxisRight().setEnabled(false);
        this.mChart.getXAxis().setEnabled(false);
        return v;
    }

    public void onValueSelected(Entry e, Highlight h) {
    }

    public void onNothingSelected() {
    }

    public void onChartGestureStart(MotionEvent me, ChartGesture lastPerformedGesture) {
    }

    public void onChartGestureEnd(MotionEvent me, ChartGesture lastPerformedGesture) {
    }

    public void onChartLongPressed(MotionEvent me) {
    }

    public void onChartDoubleTapped(MotionEvent me) {
    }

    public void onChartSingleTapped(MotionEvent me) {
    }

    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
    }

    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
    }

    public void onChartTranslate(MotionEvent me, float dX, float dY) {
    }
}
