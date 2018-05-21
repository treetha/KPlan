package com.example.joel.myapplication;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.ScatterChart.ScatterShape;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class SimpleFragment extends Fragment {
    private String[] mLabels = new String[]{"เมื่อใช้สิทธิแล้ว", "เมื่อใช้สิทธิเพิ่มเติม", "Company C", "Company D", "Company E", "Company F"};
    private Typeface tf;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected BarData generateBarData(int dataSets, float range, int count) {
        List sets = new ArrayList();
        for (int i = 0; i < dataSets; i++) {
            ArrayList<BarEntry> entries = new ArrayList();
            entries.add(new BarEntry(0.0f, 14865.0f));
            entries.add(new BarEntry(1.0f, 4589.0f));
            BarDataSet ds = new BarDataSet(entries, getLabel(i));
            ds.setColors(ColorTemplate.VORDIPLOM_COLORS);
            sets.add(ds);
        }
        BarData d = new BarData(sets);
        d.setValueTypeface(this.tf);
        return d;
    }

    protected ScatterData generateScatterData(int dataSets, float range, int count) {
        List sets = new ArrayList();
        ScatterShape[] shapes = ScatterShape.getAllDefaultShapes();
        for (int i = 0; i < dataSets; i++) {
            ArrayList<Entry> entries = new ArrayList();
            for (int j = 0; j < count; j++) {
                entries.add(new Entry((float) j, ((float) (Math.random() * ((double) range))) + (range / 4.0f)));
            }
            ScatterDataSet ds = new ScatterDataSet(entries, getLabel(i));
            ds.setScatterShapeSize(12.0f);
            ds.setScatterShape(shapes[i % shapes.length]);
            ds.setColors(ColorTemplate.COLORFUL_COLORS);
            ds.setScatterShapeSize(9.0f);
            sets.add(ds);
        }
        ScatterData d = new ScatterData(sets);
        d.setValueTypeface(this.tf);
        return d;
    }

    protected PieData generatePieData() {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String userId = appSharedPrefs.getString("currentUserProfile", "treetha");
        String totalAsset = appSharedPrefs.getString(Constant.PREF_TOTAL_ASSET + userId, "0");
        String totalDebt = appSharedPrefs.getString(Constant.PREF_TOTAL_DEBT + userId, "0");
        ArrayList<PieEntry> entries1 = new ArrayList();
        entries1.add(new PieEntry(Float.parseFloat(totalAsset), "สินทรัพย์"));
        entries1.add(new PieEntry(Float.parseFloat(totalDebt), "หนี้สิน"));
        PieDataSet ds1 = new PieDataSet(entries1, "");
        ds1.setColors(ColorTemplate.rgb("#53DC62"), ColorTemplate.rgb("#F8695E"));
        ds1.setSliceSpace(2.0f);
        ds1.setValueTextColor(-1);
        ds1.setValueTextSize(12.0f);
        PieData d = new PieData(ds1);
        d.setValueTypeface(this.tf);
        return d;
    }

    protected LineData generateLineData() {
        List sets = new ArrayList();
        LineDataSet ds1 = new LineDataSet(FileUtils.loadEntriesFromAssets(getActivity().getAssets(), "sine.txt"), "Sine function");
        LineDataSet ds2 = new LineDataSet(FileUtils.loadEntriesFromAssets(getActivity().getAssets(), "cosine.txt"), "Cosine function");
        ds1.setLineWidth(2.0f);
        ds2.setLineWidth(2.0f);
        ds1.setDrawCircles(false);
        ds2.setDrawCircles(false);
        ds1.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        ds2.setColor(ColorTemplate.VORDIPLOM_COLORS[1]);
        sets.add(ds1);
        sets.add(ds2);
        LineData d = new LineData(sets);
        d.setValueTypeface(this.tf);
        return d;
    }

    protected LineData getComplexity() {
        List sets = new ArrayList();
        LineDataSet ds1 = new LineDataSet(FileUtils.loadEntriesFromAssets(getActivity().getAssets(), "n.txt"), "O(n)");
        LineDataSet ds2 = new LineDataSet(FileUtils.loadEntriesFromAssets(getActivity().getAssets(), "nlogn.txt"), "O(nlogn)");
        LineDataSet ds3 = new LineDataSet(FileUtils.loadEntriesFromAssets(getActivity().getAssets(), "square.txt"), "O(n²)");
        LineDataSet ds4 = new LineDataSet(FileUtils.loadEntriesFromAssets(getActivity().getAssets(), "three.txt"), "O(n³)");
        ds1.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        ds2.setColor(ColorTemplate.VORDIPLOM_COLORS[1]);
        ds3.setColor(ColorTemplate.VORDIPLOM_COLORS[2]);
        ds4.setColor(ColorTemplate.VORDIPLOM_COLORS[3]);
        ds1.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        ds2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[1]);
        ds3.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[2]);
        ds4.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[3]);
        ds1.setLineWidth(2.5f);
        ds1.setCircleRadius(3.0f);
        ds2.setLineWidth(2.5f);
        ds2.setCircleRadius(3.0f);
        ds3.setLineWidth(2.5f);
        ds3.setCircleRadius(3.0f);
        ds4.setLineWidth(2.5f);
        ds4.setCircleRadius(3.0f);
        sets.add(ds1);
        sets.add(ds2);
        sets.add(ds3);
        sets.add(ds4);
        LineData d = new LineData(sets);
        d.setValueTypeface(this.tf);
        return d;
    }

    private String getLabel(int i) {
        return this.mLabels[i];
    }
}
