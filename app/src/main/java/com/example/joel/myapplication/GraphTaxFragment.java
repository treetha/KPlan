package com.example.joel.myapplication;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class GraphTaxFragment extends SimpleFragment implements OnChartValueSelectedListener, OnChartGestureListener {
    private BarChart mChart;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_graph_tax, container, false);

        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String userId = appSharedPrefs.getString("currentUserProfile", "treetha");
        Gson gson = new Gson();
        String jsonTax = appSharedPrefs.getString(Constant.PREF_TAX_PLAN + userId, "");
        Type typetax = new TypeToken<TaxPlanModel>(){}.getType();
        TaxPlanModel taxPlanModel = (TaxPlanModel) gson.fromJson(jsonTax, typetax);

        double useValue2 = taxPlanModel.getTotalTax() - taxPlanModel.getTotalTestTax();
        double deductvalue2 = taxPlanModel.getTotalTax() - useValue2;
        float data1 = (float) taxPlanModel.getTotalTax();
        float data2 = (float) deductvalue2;

        this.mChart = (BarChart) v.findViewById(R.id.barChart);
        this.mChart.getDescription().setEnabled(false);
        this.mChart.setOnChartGestureListener(this);
//        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
//        mv.setChartView(this.mChart);
//        this.mChart.setMarker(mv);
        this.mChart.setDrawGridBackground(false);
        this.mChart.setDrawBarShadow(false);
//        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");
//        if(data1 > data2){
        this.mChart.setData(generateBarData(data1,data2, data1, 2));
//        }else{
//            this.mChart.setData(generateBarData(data1,data2, data2, 2));
//        }
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
