package com.example.joel.myapplication;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
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

public class TaxPlanFragment extends Fragment {
    private EditText bonus;
    private EditText child;
    private EditText expense;
    private EditText insurance1;
    private EditText insurance2;
    private EditText ltf;
    private EditText myPrivate;
    private EditText ot;
    private EditText other;
    private EditText pair;
    private EditText parent;
    private EditText rmf;
    private EditText salary;
    private EditText service;
    private EditText social;
    private TextView totalRevenue;
    private TextView totalTax;
    private Button calRev, calPrivate, calDeduct, nextButton;
    private TaxPlanModel taxPlanModel;

    private View.OnClickListener onclickCalculateRev = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            double sumRevenue = (((Double.parseDouble("".equals(bonus.getText().toString()) ? "0" : bonus.getText().toString()) + Double.parseDouble("".equals(salary.getText().toString()) ? "0" : salary.getText().toString())) + Double.parseDouble("".equals(this.ot.getText().toString()) ? "0" : ot.getText().toString())) + Double.parseDouble("".equals(service.getText().toString()) ? "0" : service.getText().toString())) + Double.parseDouble("".equals(other.getText().toString()) ? "0" : other.getText().toString());
            double sumRevenue = sumRevenue();
            NumberFormat formatter = new DecimalFormat("#,##0.00");
            if (sumRevenue / 2.0d >= 100000.0d) {
                expense.setText(formatter.format(100000));
                taxPlanModel.setExpenseRev(100000);
            } else {
                expense.setText(formatter.format(sumRevenue / 2.0d));
                taxPlanModel.setExpenseRev(sumRevenue / 2.0d);
            }
            showTotalrevenue(sumRevenue);
            calculateTax(sumRevenue, sumDecrease());
        }
    };
    private View.OnClickListener onclickCalculatedecrease = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            calculateTotal(sumRevenue(), sumDecrease());
            calculateTax(sumRevenue(), sumDecrease());
        }
    };

    private double sumRevenue() {
        double bonusD = Double.parseDouble("".equals(getStringAmount(bonus)) ? "0" : getStringAmount(bonus));

        double saraD = Double.parseDouble("".equals(getStringAmount(salary)) ? "0" : getStringAmount(salary));

        double otD = Double.parseDouble("".equals(getStringAmount(ot)) ? "0" : getStringAmount(ot));

        double serD = Double.parseDouble("".equals(getStringAmount(service)) ? "0" : getStringAmount(service));
        double otherD = Double.parseDouble("".equals(getStringAmount(other)) ? "0" : getStringAmount(other));

        double sumRevenue = ((bonusD + saraD + otD + serD + otherD));

        taxPlanModel.setBonusRev(bonusD);
        taxPlanModel.setSalaryRev(saraD);
        taxPlanModel.setOtRev(otD);
        taxPlanModel.setOtherRev(otherD);
        taxPlanModel.setServiceRev(serD);
        taxPlanModel.setTotalRevenue(sumRevenue);
        return sumRevenue;
    }


//    private class CustomTextWatcher implements TextWatcher {
//        private EditText mEditText;
//
//        public CustomTextWatcher(EditText e) {
//            this.mEditText = e;
//        }
//
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        }
//
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//        }
//
//        public void afterTextChanged(Editable s) {
//
//        }
//    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_tax_plan, container, false);


        TextInputLayout bonusInput = (TextInputLayout) v.findViewById(R.id.input_bonus);
        TextInputLayout salaryInput = (TextInputLayout) v.findViewById(R.id.input_salary);
        TextInputLayout otInput = (TextInputLayout) v.findViewById(R.id.input_ot);
        TextInputLayout serviceInput = (TextInputLayout) v.findViewById(R.id.input_service);
        TextInputLayout otherInput = (TextInputLayout) v.findViewById(R.id.input_other);
        TextInputLayout expenseInput = (TextInputLayout) v.findViewById(R.id.input_revenue);
        TextInputLayout myPrivateInput = (TextInputLayout) v.findViewById(R.id.input_private_rev);
        TextInputLayout pairInput = (TextInputLayout) v.findViewById(R.id.input_pair_rev);
        TextInputLayout childInput = (TextInputLayout) v.findViewById(R.id.input_child_rev);
        TextInputLayout parentInput = (TextInputLayout) v.findViewById(R.id.input_parent_rev);
        TextInputLayout socialInput = (TextInputLayout) v.findViewById(R.id.input_ss_rev);
        TextInputLayout ltfInput = (TextInputLayout) v.findViewById(R.id.input_ltf_rev);
        TextInputLayout rmfInput = (TextInputLayout) v.findViewById(R.id.input_rmf_rev);
        TextInputLayout insurance1Input = (TextInputLayout) v.findViewById(R.id.input_ins_rev);
        TextInputLayout insurance2Input = (TextInputLayout) v.findViewById(R.id.input_ins_rev2);

        calRev = (Button) v.findViewById(R.id.button_calculate_rev);
        calPrivate = (Button) v.findViewById(R.id.button_calculate_private);
        calDeduct = (Button) v.findViewById(R.id.button_calculate_deduct);

        nextButton  = (Button) v.findViewById(R.id.button_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).OpenTableTax(v);
                saveData();
            }
        });

        this.totalRevenue = (TextView) v.findViewById(R.id.textview_total_revenue);
        this.totalTax = (TextView) v.findViewById(R.id.textview_total_tax);


        this.bonus = bonusInput.getEditText();
        this.bonus.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
//        this.bonus.addTextChangedListener(new CustomTextWatcher(this.bonus));
        this.salary = salaryInput.getEditText();
        this.salary.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
//        this.salary.addTextChangedListener(new CustomTextWatcher(this.salary));
        this.ot = otInput.getEditText();
        this.ot.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
//        this.ot.addTextChangedListener(new CustomTextWatcher(this.ot));
        this.service = serviceInput.getEditText();
        this.service.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
//        this.service.addTextChangedListener(new CustomTextWatcher(this.service));
        this.other = otherInput.getEditText();
        this.other.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
//        this.other.addTextChangedListener(new CustomTextWatcher(this.other));
        this.expense = expenseInput.getEditText();
        this.expense.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
//        this.expense.addTextChangedListener(new CustomTextWatcher(this.expense));
        this.myPrivate = myPrivateInput.getEditText();
        this.myPrivate.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
//        this.myPrivate.addTextChangedListener(new CustomTextWatcher(this.myPrivate));
        this.pair = pairInput.getEditText();
        this.pair.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
//        this.pair.addTextChangedListener(new CustomTextWatcher(this.pair));
        this.child = childInput.getEditText();
        this.child.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
//        this.child.addTextChangedListener(new CustomTextWatcher(this.child));
        this.parent = parentInput.getEditText();
        this.parent.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
//        this.parent.addTextChangedListener(new CustomTextWatcher(this.parent));
        this.social = socialInput.getEditText();
        this.social.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
//        this.social.addTextChangedListener(new CustomTextWatcher(this.social));
        this.ltf = ltfInput.getEditText();
        this.ltf.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
//        this.ltf.addTextChangedListener(new CustomTextWatcher(this.ltf));
        this.rmf = rmfInput.getEditText();
        this.rmf.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
//        this.rmf.addTextChangedListener(new CustomTextWatcher(this.rmf));
        this.insurance1 = insurance1Input.getEditText();
        this.insurance1.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
//        this.insurance1.addTextChangedListener(new CustomTextWatcher(this.insurance1));
        this.insurance2 = insurance2Input.getEditText();
        this.insurance2.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
//        this.insurance2.addTextChangedListener(new CustomTextWatcher(this.insurance2));

        calRev.setOnClickListener(onclickCalculateRev);
        calPrivate.setOnClickListener(onclickCalculatedecrease);
        calDeduct.setOnClickListener(onclickCalculatedecrease);

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
            setDefaultData(bonus, taxPlanModel.getBonusRev());
            setDefaultData(child, taxPlanModel.getChildDecrease());
            setDefaultData(expense, taxPlanModel.getExpenseRev());
            setDefaultData(insurance1, taxPlanModel.getInsuranceDecrease());
            setDefaultData(insurance2, taxPlanModel.getInsurance2Decrease());
            setDefaultData(ltf, taxPlanModel.getLtfDecrease());
            setDefaultData(myPrivate, taxPlanModel.getPrivateDecrease());
            setDefaultData(ot, taxPlanModel.getOtRev());
            setDefaultData(other, taxPlanModel.getOtherRev());
            setDefaultData(pair, taxPlanModel.getPairDecrease());
            setDefaultData(parent, taxPlanModel.getBonusRev());
            setDefaultData(rmf, taxPlanModel.getParentDecrease());
            setDefaultData(salary, taxPlanModel.getSalaryRev());
            setDefaultData(service, taxPlanModel.getServiceRev());
            setDefaultData(social, taxPlanModel.getSocialDecrease());

            showTotalrevenue(sumRevenue());
            calculateTax(sumRevenue(),sumDecrease());

        }else{
            taxPlanModel = new TaxPlanModel();
        }

        taxPlanModel.setPrivateDecrease(60000);

    }

    private void setDefaultData(EditText edit, double bonusRev) {
        if(bonusRev != 0){
            NumberFormat formatter = new DecimalFormat("#,##0.00");
            edit.setText(formatter.format(bonusRev));
        }
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

    public void calculateTax(double sumRevenue, double sumDecrease) {
//        double sumRevenue = (((Double.parseDouble("".equals(this.bonus.getText().toString()) ? "0" : this.bonus.getText().toString()) + Double.parseDouble("".equals(this.salary.getText().toString()) ? "0" : this.salary.getText().toString())) + Double.parseDouble("".equals(this.ot.getText().toString()) ? "0" : this.ot.getText().toString())) + Double.parseDouble("".equals(this.service.getText().toString()) ? "0" : this.service.getText().toString())) + Double.parseDouble("".equals(this.other.getText().toString()) ? "0" : this.other.getText().toString());
        NumberFormat formatter = new DecimalFormat("#,##0.00");
        double sumTax = sumRevenue - sumDecrease;
        double calTax = 0;
        if ( sumTax >= 0 && sumTax <= 150000) {
            calTax = 0;
            taxPlanModel.setTaxRank(0);
        }else if ( sumTax > 150000 && sumTax <= 300000) {
            calTax = (sumTax - 150000) * 5 / 100;
            taxPlanModel.setTaxRank(1);
        }else if ( sumTax > 300001 && sumTax <= 500000) {
            calTax = (((sumTax - 150000) - 150000) * 10 / 100) + 7500;
            taxPlanModel.setTaxRank(2);
        }else if ( sumTax > 500001 && sumTax <= 750000) {
            calTax = (((sumTax - 150000) - 150000 - 200000) * 15 / 100) + 27500;
            taxPlanModel.setTaxRank(3);
        }else if ( sumTax > 750001 && sumTax <= 1000000) {
            calTax = (((sumTax - 150000) - 150000 - 200000 - 250000) * 20 / 100) + 65000 ;
            taxPlanModel.setTaxRank(4);
        }else if ( sumTax > 1000001 && sumTax <= 2000000) {
            calTax = (((sumTax - 150000) - 150000 - 200000 - 250000 - 250000) * 25 / 100) + 115000 ;
            taxPlanModel.setTaxRank(5);
        }else if ( sumTax > 2000001 && sumTax <= 5000000) {
            calTax = (((sumTax - 150000) - 150000 - 200000 - 250000 - 250000 - 1000000) * 30 / 100) + 365000 ;
            taxPlanModel.setTaxRank(6);
        }else if ( sumTax > 5000001) {
            calTax = (((sumTax - 150000) - 150000 - 200000 - 250000 - 250000 - 1000000 - 3000000) * 35 / 100) + 1265000 ;
            taxPlanModel.setTaxRank(7);
        }
        taxPlanModel.setTotalTax(calTax);
        this.totalTax.setText(formatter.format(calTax));
    }

    public void showTotalrevenue(double sumRevenue) {
        NumberFormat formatter = new DecimalFormat("#,##0.00");
        this.totalRevenue.setText(formatter.format(sumRevenue));
        taxPlanModel.setTotalRevenue(sumRevenue);

    }


    private double sumDecrease() {
        double expenseD = Double.parseDouble("".equals(getStringAmount(expense)) ? "0" : getStringAmount(expense));
        double myPrivateD = Double.parseDouble("".equals(getStringAmount(myPrivate)) ? "0" : getStringAmount(myPrivate));
        double pairD = Double.parseDouble("".equals(getStringAmount(pair)) ? "0" : getStringAmount(pair));
        double childD = Double.parseDouble("".equals(getStringAmount(child)) ? "0" : getStringAmount(child));
        double parentD = Double.parseDouble("".equals(getStringAmount(parent)) ? "0" : getStringAmount(parent));
        double socialD = Double.parseDouble("".equals(getStringAmount(social)) ? "0" : getStringAmount(social));
        double ltfD = Double.parseDouble("".equals(getStringAmount(ltf)) ? "0" : getStringAmount(ltf));
        double rmfD = Double.parseDouble("".equals(getStringAmount(rmf)) ? "0" : getStringAmount(rmf));
        double ins1D = Double.parseDouble("".equals(getStringAmount(insurance1)) ? "0" : getStringAmount(insurance1));
        double ins2D = Double.parseDouble("".equals(getStringAmount(insurance2)) ? "0" : getStringAmount(insurance2));

        double sumDecrease = ((((((((expenseD + myPrivateD) + pairD) + childD) + parentD) + socialD) + ltfD) + rmfD + ins1D + ins2D));
        taxPlanModel.setExpenseRev(expenseD);
        taxPlanModel.setPrivateDecrease(myPrivateD);
        taxPlanModel.setPairDecrease(pairD);
        taxPlanModel.setChildDecrease(childD);
        taxPlanModel.setParentDecrease(parentD);
        taxPlanModel.setSocialDecrease(socialD);
        taxPlanModel.setLtfDecrease(ltfD);
        taxPlanModel.setRmfDecrease(rmfD);
        taxPlanModel.setInsuranceDecrease(ins1D);
        taxPlanModel.setInsurance2Decrease(ins2D);
        taxPlanModel.setTotalDecrease(sumDecrease);
        return sumDecrease;
    }

    private String getStringAmount(EditText editText) {
        return editText.getText().toString().replace(",","");
    }
}
