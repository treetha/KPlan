package com.example.joel.myapplication;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class TableTaxFragment extends Fragment {
    TableRow row0,row1,row2,row3,row4,row5,row6,row7,row8;
    TextView value1,value2,value3,value4,value5,value6,value7,value8, totalTax;
    private TaxPlanModel taxPlanModel;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_table_tax, container, false);
        row0 = v.findViewById(R.id.table_tax_row0);
        row1 = v.findViewById(R.id.table_tax_row1);
        row2 = v.findViewById(R.id.table_tax_row2);
        row3 = v.findViewById(R.id.table_tax_row3);
        row4 = v.findViewById(R.id.table_tax_row4);
        row5 = v.findViewById(R.id.table_tax_row5);
        row6 = v.findViewById(R.id.table_tax_row6);
        row7 = v.findViewById(R.id.table_tax_row7);
        row8 = v.findViewById(R.id.table_tax_row8);
//        value1 = v.findViewById(R.id.table_tax_value1);
        value2 = v.findViewById(R.id.table_tax_value2);
        value3 = v.findViewById(R.id.table_tax_value3);
        value4 = v.findViewById(R.id.table_tax_value4);
        value5 = v.findViewById(R.id.table_tax_value5);
        value6 = v.findViewById(R.id.table_tax_value6);
        value7 = v.findViewById(R.id.table_tax_value7);
        totalTax = v. findViewById(R.id.textview_total_revenue);
        initData();
        getStringAmount(totalTax,taxPlanModel.getTotalTax());
        return v;
    }

    private void initData() {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String userId = appSharedPrefs.getString("currentUserProfile", "treetha");
        Gson gson = new Gson();
        String jsonTax = appSharedPrefs.getString(Constant.PREF_TAX_PLAN + userId, "");
        Type typetax = new TypeToken<TaxPlanModel>(){}.getType();
        taxPlanModel = (TaxPlanModel) gson.fromJson(jsonTax, typetax);

        if(taxPlanModel!=null){
            switch (taxPlanModel.getTaxRank()){
                case 0: row1.setVisibility(View.VISIBLE);
                        break;
                case 1: getStringAmount(value2,taxPlanModel.getTotalTax());
                        row1.setVisibility(View.VISIBLE);
                        row2.setVisibility(View.VISIBLE);
                    break;
                case 2: getStringAmount(value3, taxPlanModel.getTotalTax() - 7500);
                    row1.setVisibility(View.VISIBLE);
                    row2.setVisibility(View.VISIBLE);
                    row3.setVisibility(View.VISIBLE);
                    break;
                case 3: getStringAmount(value4,taxPlanModel.getTotalTax() - 27500);
                    row1.setVisibility(View.VISIBLE);
                    row2.setVisibility(View.VISIBLE);
                    row3.setVisibility(View.VISIBLE);
                    row4.setVisibility(View.VISIBLE);
                    break;
                case 4: getStringAmount(value5,taxPlanModel.getTotalTax() - 65000 );
                    row1.setVisibility(View.VISIBLE);
                    row2.setVisibility(View.VISIBLE);
                    row3.setVisibility(View.VISIBLE);
                    row4.setVisibility(View.VISIBLE);
                    row5.setVisibility(View.VISIBLE);
                    break;
                case 5: getStringAmount(value6,taxPlanModel.getTotalTax() - 115000);
                    row1.setVisibility(View.VISIBLE);
                    row2.setVisibility(View.VISIBLE);
                    row3.setVisibility(View.VISIBLE);
                    row4.setVisibility(View.VISIBLE);
                    row5.setVisibility(View.VISIBLE);
                    row6.setVisibility(View.VISIBLE);
                    break;
                case 6: getStringAmount(value7, taxPlanModel.getTotalTax() - 365000);
                    row1.setVisibility(View.VISIBLE);
                    row2.setVisibility(View.VISIBLE);
                    row3.setVisibility(View.VISIBLE);
                    row4.setVisibility(View.VISIBLE);
                    row5.setVisibility(View.VISIBLE);
                    row6.setVisibility(View.VISIBLE);
                    row7.setVisibility(View.VISIBLE);
                    break;
                case 7: getStringAmount(value8,taxPlanModel.getTotalTax() - 1265000);
                    row1.setVisibility(View.VISIBLE);
                    row2.setVisibility(View.VISIBLE);
                    row3.setVisibility(View.VISIBLE);
                    row4.setVisibility(View.VISIBLE);
                    row5.setVisibility(View.VISIBLE);
                    row6.setVisibility(View.VISIBLE);
                    row7.setVisibility(View.VISIBLE);
                    row8.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    private void getStringAmount(TextView editText, double totalTax) {
        NumberFormat formatter = new DecimalFormat("#,##0.00");
        editText.setText(formatter.format(totalTax));
    }
}
