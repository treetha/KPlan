package com.example.joel.myapplication;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DeductTestTaxFragment extends Fragment {
    EditText ltfE,rmfE,ins1E,ins2E;
    TextView ltfT, rmfT,ins1T,ins2T, total;
    Button button, next;
    private TaxPlanModel taxPlanModel;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_deduct_test_tax, container, false);

        ltfE = v.findViewById(R.id.edit_ltf);
        rmfE = v.findViewById(R.id.edit_rmf);
        ins1E = v.findViewById(R.id.edit_ins1);
        ins2E = v.findViewById(R.id.edit_ins2);
        ltfT = v.findViewById(R.id.text_ltf);
        rmfT = v.findViewById(R.id.text_rmf);
        ins1T = v.findViewById(R.id.text_ins1);
        ins2T = v.findViewById(R.id.text_ins2);
        total = v.findViewById(R.id.textview_total_revenue);
        button = v.findViewById(R.id.button_calculate_tax);
        next = v.findViewById(R.id.button_next);

        ltfE.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        rmfE.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        ins1E.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        ins2E.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});

        initText();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calTaxTotal();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                ((MainActivity)getActivity()).OpenSummaryTax(v);
            }
        });
        return v;
    }

    private void calTaxTotal() {
        double ltfadd = Double.parseDouble("".equals(ltfE.getText().toString().replace(",","")) ? "0" : ltfE.getText().toString().replace(",",""));
        double rmfadd = Double.parseDouble("".equals(rmfE.getText().toString().replace(",","")) ? "0" : rmfE.getText().toString().replace(",",""));
        double ins1add = Double.parseDouble("".equals(ins1E.getText().toString().replace(",","")) ? "0" : ins1E.getText().toString().replace(",",""));
        double ins2add = Double.parseDouble("".equals(ins2E.getText().toString().replace(",","")) ? "0" : ins2E.getText().toString().replace(",",""));
        double sumUseDecrease = ltfadd + rmfadd + ins1add + ins2add;
        double newTaxTotal = calTax(taxPlanModel.getTotalRevenue() - taxPlanModel.getTotalDecrease() - sumUseDecrease);
        getStringAmount(total, taxPlanModel.getTotalTax() - newTaxTotal);

        taxPlanModel.setTotalTestTax(newTaxTotal);
        taxPlanModel.setInsuranceTest(ins1add);
        taxPlanModel.setInsurance2Test(ins2add);
        taxPlanModel.setLtfTest(ltfadd);
        taxPlanModel.setRmfTest(rmfadd);
    }

    private void saveModel() {



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

    private void initText() {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String userId = appSharedPrefs.getString("currentUserProfile", "treetha");
        Gson gson = new Gson();
        String jsonTax = appSharedPrefs.getString(Constant.PREF_TAX_PLAN + userId, "");
        Type typetax = new TypeToken<TaxPlanModel>(){}.getType();
        taxPlanModel = (TaxPlanModel) gson.fromJson(jsonTax, typetax);

        double ltfuse = 0;
        double rmfuse = 0;
        double maxLTF = taxPlanModel.getTotalRevenue() * 15 /100 ;
        if(maxLTF >= 500000){
            ltfuse = 500000 - taxPlanModel.getLtfDecrease();
            rmfuse = 500000 - taxPlanModel.getRmfDecrease();
        }else{
            ltfuse = (maxLTF) - taxPlanModel.getLtfDecrease();
            rmfuse = (maxLTF) - taxPlanModel.getRmfDecrease();
        }

        getStringAmount(ltfT, ltfuse);
        getStringAmount(rmfT, rmfuse);


        double ins1 = 100000 - taxPlanModel.getInsuranceDecrease();
        double ins2 = 200000 - taxPlanModel.getInsurance2Decrease();
        getStringAmount(ins1T, ins1);
        getStringAmount(ins2T, ins2);


        if(taxPlanModel.getInsuranceTest() != 0){
            getStringAmount(ins1E, taxPlanModel.getInsuranceTest());
        }
        if(taxPlanModel.getInsurance2Test() != 0){
            getStringAmount(ins2E, taxPlanModel.getInsurance2Test());
        }
        if(taxPlanModel.getLtfTest() != 0){
            getStringAmount(ltfE, taxPlanModel.getLtfTest());
        }
        if(taxPlanModel.getRmfTest() != 0){
            getStringAmount(rmfE, taxPlanModel.getRmfTest());
        }
        if(taxPlanModel.getTotalTestTax() != 0){
            getStringAmount(total, taxPlanModel.getTotalTax() - taxPlanModel.getTotalTestTax());
        }

    }

    private void getStringAmount(TextView editText, double totalTax) {
        NumberFormat formatter = new DecimalFormat("#,##0.00");
        editText.setText(formatter.format(totalTax));
    }
    private void getStringAmount(EditText editText, double totalTax) {
        NumberFormat formatter = new DecimalFormat("#,##0.00");
        editText.setText(formatter.format(totalTax));
    }

    public void saveData() {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String userId = appSharedPrefs.getString("currentUserProfile", "treetha");
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        if(taxPlanModel != null){
            String jsonTax = gson.toJson(taxPlanModel);
            prefsEditor.putString(Constant.PREF_TAX_PLAN + userId, jsonTax);
            prefsEditor.commit();
        }
    }
}
