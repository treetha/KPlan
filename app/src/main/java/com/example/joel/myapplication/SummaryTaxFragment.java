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

public class SummaryTaxFragment extends Fragment {
    TextView totalHead,use1,use2,decrease2,decrease1;
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.frag_summary_tax, container, false);
        totalHead = v.findViewById(R.id.tax_total_head);
        use1 = v.findViewById(R.id.tax_total_use1);
        use2 = v.findViewById(R.id.tax_total_use2);
        decrease1 = v.findViewById(R.id.tax_total_decrease1);
        decrease2 = v.findViewById(R.id.tax_total_decrease2);


        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String userId = appSharedPrefs.getString("currentUserProfile", "treetha");
        Gson gson = new Gson();
        String jsonTax = appSharedPrefs.getString(Constant.PREF_TAX_PLAN + userId, "");
        Type typetax = new TypeToken<TaxPlanModel>(){}.getType();
        TaxPlanModel taxPlanModel = (TaxPlanModel) gson.fromJson(jsonTax, typetax);

        double decreasePrivateTax = taxPlanModel.getExpenseRev() + taxPlanModel.getPrivateDecrease() + taxPlanModel.getPairDecrease() + taxPlanModel.getParentDecrease() + taxPlanModel.getSocialDecrease() + taxPlanModel.getChildDecrease();
        double decreaseInvestTax = taxPlanModel.getLtfDecrease() + taxPlanModel.getRmfDecrease() + taxPlanModel.getInsuranceDecrease() + taxPlanModel.getInsurance2Decrease();
        double calTaxWithoutInvest = calTax(taxPlanModel.getTotalRevenue() - decreasePrivateTax);

        getStringAmount(totalHead, calTaxWithoutInvest);
        double deductValue = calTaxWithoutInvest - taxPlanModel.getTotalTax();
        getStringAmount(use1, deductValue);

        getStringAmount(decrease1, taxPlanModel.getTotalTax() );

        double useValue2 = taxPlanModel.getTotalTax() - taxPlanModel.getTotalTestTax();
        getStringAmount(use2, useValue2);

        double deductvalue2 = taxPlanModel.getTotalTax() - useValue2;
        getStringAmount(decrease2, deductvalue2);
        return v;
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
