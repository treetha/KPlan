package com.example.joel.myapplication;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DeductTaxFragment extends Fragment {
    private TaxPlanModel taxPlanModel;
    TextView deduct1, deduct2, ltfMax, ltfUse, rmfMax, rmfUse, ins1Use, ins2Use;
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_deduct_tax, container, false);
        deduct1 = v.findViewById(R.id.table_tax_deduct1);
        deduct2 = v.findViewById(R.id.table_tax_deduct2);
        ltfMax = v.findViewById(R.id.table_tax_ltf_max);
        ltfUse = v.findViewById(R.id.table_tax_ltf_use);
        rmfMax = v.findViewById(R.id.table_tax_rmf_max);
        rmfUse = v.findViewById(R.id.table_tax_rmf_use);
        ins1Use = v.findViewById(R.id.table_tax_ins1_use);
        ins2Use = v.findViewById(R.id.table_tax_ins2_use);
        initData();


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
            double calTax = calTax(taxPlanModel.getTotalRevenue());
            double deductValue = calTax - taxPlanModel.getTotalTax();
            getStringAmount(deduct1, deductValue);
        }
        double ltfuse = 0;
        double rmfuse = 0;
        double maxLTF = taxPlanModel.getTotalRevenue() * 15 /100 ;
        if(maxLTF >= 500000){
            getStringAmount(ltfMax, 250000);
            getStringAmount(rmfMax, 250000);
            ltfuse = 250000 - taxPlanModel.getLtfDecrease();
            rmfuse = 250000 - taxPlanModel.getRmfDecrease();

        }else{
            getStringAmount(ltfMax, maxLTF / 2);
            getStringAmount(rmfMax, maxLTF / 2);
            ltfuse = (maxLTF / 2) - taxPlanModel.getLtfDecrease();
            rmfuse = (maxLTF / 2) - taxPlanModel.getRmfDecrease();

        }

        getStringAmount(ltfUse, ltfuse);
        getStringAmount(rmfUse, rmfuse);


        double ins1 = 100000 - taxPlanModel.getInsuranceDecrease();
        double ins2 = 200000 - taxPlanModel.getInsurance2Decrease();
        getStringAmount(ins1Use, ins1);
        getStringAmount(ins2Use, ins2);
        double sumUseDecrease = ins1 + ins2 + ltfuse + rmfuse;
        double taxMaxDecrease = calTax(taxPlanModel.getTotalRevenue()
                - taxPlanModel.getTotalDecrease() - sumUseDecrease);
        double deductMax = taxPlanModel.getTotalTax() - taxMaxDecrease;
        getStringAmount(deduct2, deductMax);
    }

    private double calTax(double sumTax) {
        double calTax = 0;
        if ( sumTax >= 0 && sumTax <= 150000) {
            calTax = 0;
        }else if ( sumTax > 150000 && sumTax <= 300000) {
            calTax = (sumTax - 150000) * 5 / 100;
        }else if ( sumTax > 300001 && sumTax <= 500000) {
            calTax = (((sumTax - 150000) - 150000) * 10 / 100) + 7500;
        }else if ( sumTax > 500001 && sumTax <= 750000) {
            calTax = (((sumTax - 150000) - 150000 - 200000) * 15 / 100) + 27500;
        }else if ( sumTax > 750001 && sumTax <= 1000000) {
            calTax = (((sumTax - 150000) - 150000 - 200000 - 250000) * 20 / 100) + 65000 ;
        }else if ( sumTax > 1000001 && sumTax <= 2000000) {
            calTax = (((sumTax - 150000) - 150000 - 200000 - 250000 - 250000) * 25 / 100) + 115000 ;
        }else if ( sumTax > 2000001 && sumTax <= 5000000) {
            calTax = (((sumTax - 150000) - 150000 - 200000 - 250000 - 250000 - 1000000) * 30 / 100) + 365000 ;
        }else if ( sumTax > 5000001) {
            calTax = (((sumTax - 150000) - 150000 - 200000 - 250000 - 250000 - 1000000 - 3000000) * 35 / 100) + 1265000 ;
        }
        return calTax;
    }

    private void getStringAmount(TextView editText, double totalTax) {
        NumberFormat formatter = new DecimalFormat("#,##0.00");
        editText.setText(formatter.format(totalTax));
    }
}
