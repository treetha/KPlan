package com.example.joel.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {
    Context context;
    String firstElement;
    boolean isFirstTime = true;
    String[] objects;

    public CustomSpinnerAdapter(Context context, int textViewResourceId, String[] objects, String defaultText) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.objects = objects;
        setDefaultText(defaultText);
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (this.isFirstTime) {
            this.objects[0] = this.firstElement;
            this.isFirstTime = false;
        }
        return getCustomView(position, convertView, parent);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        notifyDataSetChanged();
        return getCustomView(position, convertView, parent);
    }

    public void setDefaultText(String defaultText) {
        this.firstElement = this.objects[0];
        this.objects[0] = defaultText;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = ((LayoutInflater) this.context.getSystemService((Context.LAYOUT_INFLATER_SERVICE))).inflate(R.layout.spinner_row, parent, false);
        ((TextView) row.findViewById(R.id.spinner_text)).setText(this.objects[position]);
        return row;
    }
}
