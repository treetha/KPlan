package com.example.joel.myapplication;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

public class MyMarkerView extends MarkerView {
    private TextView tvContent = ((TextView) findViewById(R.id.tvContent));

    public MyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
    }

    public void refreshContent(Entry e, Highlight highlight) {
        if (e instanceof CandleEntry) {
            this.tvContent.setText("" + Utils.formatNumber(((CandleEntry) e).getHigh(), 0, true));
        } else {
            this.tvContent.setText("" + Utils.formatNumber(e.getY(), 0, true));
        }
        super.refreshContent(e, highlight);
    }

    public MPPointF getOffset() {
        return new MPPointF((float) (-(getWidth() / 2)), (float) (-getHeight()));
    }
}
