package com.example.joel.myapplication;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;

public class PieChartFragment extends SimpleFragment {
    private PieChart mChart;

    public static Fragment newInstance() {
        return new PieChartFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Pie", "create view");
        View v = inflater.inflate(R.layout.frag_simple_pie, container, false);
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        ((TextView) v.findViewById(R.id.simplepie_descripTv3)).setText(appSharedPrefs.getString(Constant.PREF_TOTAL_SUM + appSharedPrefs.getString("currentUserProfile", "treetha"), "0") + " บาท");
        this.mChart = (PieChart) v.findViewById(R.id.pieChart1);
        this.mChart.getDescription().setEnabled(false);
        this.mChart.setDrawCenterText(false);
        this.mChart.setDrawHoleEnabled(false);
        this.mChart.setData(generatePieData());
        return v;
    }

    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString("Revenues\nQuarters 2015");
        s.setSpan(new RelativeSizeSpan(2.0f), 0, 8, 0);
        s.setSpan(new ForegroundColorSpan(-7829368), 8, s.length(), 0);
        return s;
    }
}
