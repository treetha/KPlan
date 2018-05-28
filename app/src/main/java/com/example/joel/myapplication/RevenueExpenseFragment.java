package com.example.joel.myapplication;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RevenueExpenseFragment extends Fragment {
    private RevenueExpenseAdapter adapter;
    private List<RevenueModel> listExp = new ArrayList();
    private List<RevenueModel> listPlan = new ArrayList();
    private List<RevenueModel> listRev = new ArrayList();
    private ListView listView;
    private OnFocusChangeListener onFocusChangeListener = new C03362();
    private OnClickListener onShowDateCalendar = new C03351();

    class C03351 implements OnClickListener {
        C03351() {
        }

        public void onClick(View v) {
            RevenueExpenseFragment.this.showToDatePickerDialog(v);
        }
    }

    class C03362 implements OnFocusChangeListener {
        C03362() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                RevenueExpenseFragment.this.showToDatePickerDialog(v);
            }
        }
    }

    class C03373 implements OnClickListener {
        C03373() {
        }

        public void onClick(View view) {
            RevenueExpenseFragment.this.addRev();
        }
    }

    class C03384 implements OnClickListener {
        C03384() {
        }

        public void onClick(View view) {
            RevenueExpenseFragment.this.addExp();
        }
    }

    class C03395 implements OnClickListener {
        C03395() {
        }

        public void onClick(View view) {
            RevenueExpenseFragment.this.addPlan();
        }
    }

    public static class DatePickerFragment extends DialogFragment implements OnDateSetListener {
        EditText editText;

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar c = Calendar.getInstance();
            return new DatePickerDialog(getActivity(), this, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            this.editText.setText(day + "/" + month + "/" + year);
        }

        public EditText getEditText() {
            return this.editText;
        }

        public void setEditText(EditText editText) {
            this.editText = editText;
        }
    }

    class C04559 extends TypeToken<List<RevenueModel>> {
        C04559() {
        }
    }

    private void showToDatePickerDialog(View v) {
        EditText editText = (EditText) v.findViewById(R.id.edittext_date);
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setEditText(editText);
        newFragment.show(getFragmentManager(), "datePicker");
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_revenue_expense, container, false);
        this.listView = (ListView) v.findViewById(R.id.listview_revenue);
        getAllList();
        if (this.listRev.size() == 0 && this.listExp.size() == 0 && this.listPlan.size() == 0) {
            this.listView.setVisibility(View.GONE);
        } else {
            this.listView.setVisibility(View.VISIBLE);
            this.adapter = new RevenueExpenseAdapter(getActivity().getBaseContext(), this.listRev, this.listExp, this.listPlan);
            this.listView.setAdapter(this.adapter);
        }
        ((Button) v.findViewById(R.id.button_addRev)).setOnClickListener(new C03373());
        ((Button) v.findViewById(R.id.button_addExp)).setOnClickListener(new C03384());
        ((Button) v.findViewById(R.id.button_addPlan)).setOnClickListener(new C03395());
        return v;
    }

    private void addPlan() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.add_revenue_dialog, null);
        final EditText amountEt = (EditText) view.findViewById(R.id.edittext_amount);
        amountEt.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        final EditText addNewExp = (EditText) view.findViewById(R.id.edittext_add_new_task);
        addNewExp.setHint("กรอกแผนอื่นๆ");
        final EditText dateEt = (EditText) view.findViewById(R.id.edittext_date);
        dateEt.setHint("วันที่จ่าย");
        dateEt.setOnClickListener(this.onShowDateCalendar);
        dateEt.setOnFocusChangeListener(this.onFocusChangeListener);
        TextView titleTv = (TextView) view.findViewById(R.id.textview_titlename);
        titleTv.setText("แผนค่าใช้จ่าย");
        titleTv.setBackgroundResource(R.color.colorOrangeButton);
        final String[] arrayForSpinner = new String[]{Constant.PLAN_HOME, Constant.PLAN_CAR, Constant.EXP_OTHER};
        final Spinner spinner = (Spinner) view.findViewById(R.id.spinner_deposittype);
        spinner.setAdapter(new CustomSpinnerAdapter(getActivity(), R.layout.spinner_row, arrayForSpinner, Constant.SPINNER_PLAN_HINT));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == parent.getCount() - 1){
                    addNewExp.setVisibility(View.VISIBLE);
                    addNewExp.requestFocus();
                }else{
                    addNewExp.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        AlertDialog alertDialog = new Builder(getActivity()).setView(view).setPositiveButton("บันทึก", null).setNegativeButton("ยกเลิก", null).create();
        alertDialog.setOnShowListener(new OnShowListener() {
            public void onShow(final DialogInterface dialog) {
                ((AlertDialog) dialog).getButton(-1).setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (Constant.SPINNER_PLAN_HINT.equals((String) spinner.getSelectedItem())) {
                            Toast.makeText(RevenueExpenseFragment.this.getActivity(), "Select Type", Toast.LENGTH_SHORT).show();
                        } else if ("".equals(amountEt.getText().toString())) {
                            Toast.makeText(RevenueExpenseFragment.this.getActivity(), "Enter Amount", Toast.LENGTH_SHORT).show();
                        } else if ("".equals(dateEt.getText().toString())) {
                            Toast.makeText(RevenueExpenseFragment.this.getActivity(), "Enter Date", Toast.LENGTH_SHORT).show();
                        } else if(Constant.EXP_OTHER.equals((String) spinner.getSelectedItem())){
                            if ("".equals(addNewExp.getText().toString())) {
                                Toast.makeText(RevenueExpenseFragment.this.getActivity(), "Enter Expense", Toast.LENGTH_SHORT).show();
                            }else{
                                RevenueModel planModel = new RevenueModel(addNewExp.getText().toString(), new Double(amountEt.getText().toString()).doubleValue(), dateEt.getText().toString());
                                if (RevenueExpenseFragment.this.adapter != null) {
                                    RevenueExpenseFragment.this.adapter.addPlan(planModel);
                                } else {
                                    RevenueExpenseFragment.this.listPlan.add(planModel);
                                    RevenueExpenseFragment.this.listView.setVisibility(View.VISIBLE);
                                    RevenueExpenseFragment.this.adapter = new RevenueExpenseAdapter(RevenueExpenseFragment.this.getActivity().getApplicationContext(), RevenueExpenseFragment.this.listRev, RevenueExpenseFragment.this.listExp, RevenueExpenseFragment.this.listPlan);
                                    RevenueExpenseFragment.this.listView.setAdapter(RevenueExpenseFragment.this.adapter);
                                }
                                dialog.dismiss();
                            }
                        } else {
                            RevenueModel planModel = new RevenueModel(arrayForSpinner[spinner.getSelectedItemPosition()], new Double(amountEt.getText().toString()).doubleValue(), dateEt.getText().toString());
                            if (RevenueExpenseFragment.this.adapter != null) {
                                RevenueExpenseFragment.this.adapter.addPlan(planModel);
                            } else {
                                RevenueExpenseFragment.this.listPlan.add(planModel);
                                RevenueExpenseFragment.this.listView.setVisibility(View.VISIBLE);
                                RevenueExpenseFragment.this.adapter = new RevenueExpenseAdapter(RevenueExpenseFragment.this.getActivity().getApplicationContext(), RevenueExpenseFragment.this.listRev, RevenueExpenseFragment.this.listExp, RevenueExpenseFragment.this.listPlan);
                                RevenueExpenseFragment.this.listView.setAdapter(RevenueExpenseFragment.this.adapter);
                            }
                            dialog.dismiss();
                        }
                    }
                });
                ((AlertDialog) dialog).getButton(-2).setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        alertDialog.show();
    }

    private void addExp() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.add_revenue_dialog, null);
        final EditText amountEt = (EditText) view.findViewById(R.id.edittext_amount);
        amountEt.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        final EditText dateEt = (EditText) view.findViewById(R.id.edittext_date);
        dateEt.setHint("วันที่จ่าย");
        dateEt.setOnClickListener(this.onShowDateCalendar);
        dateEt.setOnFocusChangeListener(this.onFocusChangeListener);
        final EditText addNewExp = (EditText) view.findViewById(R.id.edittext_add_new_task);
        addNewExp.setHint("กรอกรายจ่ายอื่นๆ");
        TextView titleTv = (TextView) view.findViewById(R.id.textview_titlename);
        titleTv.setText("รายจ่าย");
        titleTv.setBackgroundResource(R.color.colorRedButton);
        final String[] arrayForSpinner = new String[]{Constant.EXP_CAR, Constant.EXP_HOME, Constant.EXP_MEA, Constant.EXP_ELEC, Constant.EXP_FOOD, Constant.EXP_OTHER};
        final Spinner spinner = (Spinner) view.findViewById(R.id.spinner_deposittype);
        spinner.setAdapter(new CustomSpinnerAdapter(getActivity(), R.layout.spinner_row, arrayForSpinner, Constant.SPINNER_EXPENSE_HINT));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == parent.getCount() - 1){
                    addNewExp.setVisibility(View.VISIBLE);
                    addNewExp.requestFocus();
                }else{
                    addNewExp.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        AlertDialog alertDialog = new Builder(getActivity()).setView(view).setPositiveButton("บันทึก", null).setNegativeButton("ยกเลิก", null).create();
        alertDialog.setOnShowListener(new OnShowListener() {
            public void onShow(final DialogInterface dialog) {
                ((AlertDialog) dialog).getButton(-1).setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (Constant.SPINNER_EXPENSE_HINT.equals((String) spinner.getSelectedItem())) {
                            Toast.makeText(RevenueExpenseFragment.this.getActivity(), "Select Type", Toast.LENGTH_SHORT).show();
                        } else if ("".equals(amountEt.getText().toString())) {
                            Toast.makeText(RevenueExpenseFragment.this.getActivity(), "Enter Amount", Toast.LENGTH_SHORT).show();
                        } else if ("".equals(dateEt.getText().toString())) {
                            Toast.makeText(RevenueExpenseFragment.this.getActivity(), "Enter Date", Toast.LENGTH_SHORT).show();
                        } else if(Constant.EXP_OTHER.equals((String) spinner.getSelectedItem())){
                            if ("".equals(addNewExp.getText().toString())) {
                                Toast.makeText(RevenueExpenseFragment.this.getActivity(), "Enter Expense", Toast.LENGTH_SHORT).show();
                            }else{
                                RevenueModel expenseModel = new RevenueModel(addNewExp.getText().toString(), new Double(amountEt.getText().toString()).doubleValue(), dateEt.getText().toString());
                                if (RevenueExpenseFragment.this.adapter != null) {
                                    RevenueExpenseFragment.this.adapter.addExp(expenseModel);
                                } else {
                                    RevenueExpenseFragment.this.listExp.add(expenseModel);
                                    RevenueExpenseFragment.this.listView.setVisibility(View.VISIBLE);
                                    RevenueExpenseFragment.this.adapter = new RevenueExpenseAdapter(RevenueExpenseFragment.this.getActivity().getApplicationContext(), RevenueExpenseFragment.this.listRev, RevenueExpenseFragment.this.listExp, RevenueExpenseFragment.this.listPlan);
                                    RevenueExpenseFragment.this.listView.setAdapter(RevenueExpenseFragment.this.adapter);
                                }
                                dialog.dismiss();
                            }
                        } else {
                            RevenueModel expenseModel = new RevenueModel(arrayForSpinner[spinner.getSelectedItemPosition()], new Double(amountEt.getText().toString()).doubleValue(), dateEt.getText().toString());
                            if (RevenueExpenseFragment.this.adapter != null) {
                                RevenueExpenseFragment.this.adapter.addExp(expenseModel);
                            } else {
                                RevenueExpenseFragment.this.listExp.add(expenseModel);
                                RevenueExpenseFragment.this.listView.setVisibility(View.VISIBLE);
                                RevenueExpenseFragment.this.adapter = new RevenueExpenseAdapter(RevenueExpenseFragment.this.getActivity().getApplicationContext(), RevenueExpenseFragment.this.listRev, RevenueExpenseFragment.this.listExp, RevenueExpenseFragment.this.listPlan);
                                RevenueExpenseFragment.this.listView.setAdapter(RevenueExpenseFragment.this.adapter);
                            }
                            dialog.dismiss();
                        }
                    }
                });
                ((AlertDialog) dialog).getButton(-2).setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        alertDialog.show();
    }

    private void addRev() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.add_revenue_dialog, null);
        final EditText amountEt = (EditText) view.findViewById(R.id.edittext_amount);
        amountEt.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        final EditText dateEt = (EditText) view.findViewById(R.id.edittext_date);
        dateEt.setOnClickListener(this.onShowDateCalendar);
        dateEt.setOnFocusChangeListener(this.onFocusChangeListener);
        final EditText addNewExp = (EditText) view.findViewById(R.id.edittext_add_new_task);
        addNewExp.setHint("กรอกรายรับอื่นๆ");
        ((TextView) view.findViewById(R.id.textview_titlename)).setText("รายรับ");
        final String[] arrayForSpinner = new String[]{Constant.REV_SARALY, Constant.REV_OT, Constant.REV_BONUS, Constant.REV_COMMISSION, Constant.EXP_OTHER};
        final Spinner spinner = (Spinner) view.findViewById(R.id.spinner_deposittype);
        spinner.setAdapter(new CustomSpinnerAdapter(getActivity(), R.layout.spinner_row, arrayForSpinner, Constant.SPINNER_REVENUE_HINT));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == parent.getCount() - 1){
                    addNewExp.setVisibility(View.VISIBLE);
                    addNewExp.requestFocus();
                }else{
                    addNewExp.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        AlertDialog alertDialog = new Builder(getActivity()).setView(view).setPositiveButton("บันทึก", null).setNegativeButton("ยกเลิก", null).create();
        alertDialog.setOnShowListener(new OnShowListener() {
            public void onShow(final DialogInterface dialog) {
                ((AlertDialog) dialog).getButton(-1).setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (Constant.SPINNER_REVENUE_HINT.equals((String) spinner.getSelectedItem())) {
                            Toast.makeText(RevenueExpenseFragment.this.getActivity(), "Select Type", Toast.LENGTH_SHORT).show();
                        } else if ("".equals(amountEt.getText().toString())) {
                            Toast.makeText(RevenueExpenseFragment.this.getActivity(), "Enter Amount", Toast.LENGTH_SHORT).show();
                        } else if ("".equals(dateEt.getText().toString())) {
                            Toast.makeText(RevenueExpenseFragment.this.getActivity(), "Enter Date", Toast.LENGTH_SHORT).show();
                        } else if(Constant.EXP_OTHER.equals((String) spinner.getSelectedItem())){
                            if ("".equals(addNewExp.getText().toString())) {
                                Toast.makeText(RevenueExpenseFragment.this.getActivity(), "Enter Expense", Toast.LENGTH_SHORT).show();
                            }else{
                                RevenueModel revenueModel = new RevenueModel(addNewExp.getText().toString(), new Double(amountEt.getText().toString()).doubleValue(), dateEt.getText().toString());
                                if (RevenueExpenseFragment.this.adapter != null) {
                                    RevenueExpenseFragment.this.adapter.addRevenue(revenueModel);
                                } else {
                                    RevenueExpenseFragment.this.listRev.add(revenueModel);
                                    RevenueExpenseFragment.this.listView.setVisibility(View.VISIBLE);
                                    RevenueExpenseFragment.this.adapter = new RevenueExpenseAdapter(RevenueExpenseFragment.this.getActivity().getApplicationContext(), RevenueExpenseFragment.this.listRev, RevenueExpenseFragment.this.listExp, RevenueExpenseFragment.this.listPlan);
                                    RevenueExpenseFragment.this.listView.setAdapter(RevenueExpenseFragment.this.adapter);
                                }
                                dialog.dismiss();
                            }
                        } else {
                            RevenueModel revenueModel = new RevenueModel(arrayForSpinner[spinner.getSelectedItemPosition()], new Double(amountEt.getText().toString()).doubleValue(), dateEt.getText().toString());
                            if (RevenueExpenseFragment.this.adapter != null) {
                                RevenueExpenseFragment.this.adapter.addRevenue(revenueModel);
                            } else {
                                RevenueExpenseFragment.this.listRev.add(revenueModel);
                                RevenueExpenseFragment.this.listView.setVisibility(View.VISIBLE);
                                RevenueExpenseFragment.this.adapter = new RevenueExpenseAdapter(RevenueExpenseFragment.this.getActivity().getApplicationContext(), RevenueExpenseFragment.this.listRev, RevenueExpenseFragment.this.listExp, RevenueExpenseFragment.this.listPlan);
                                RevenueExpenseFragment.this.listView.setAdapter(RevenueExpenseFragment.this.adapter);
                            }
                            dialog.dismiss();
                        }
                    }
                });
                ((AlertDialog) dialog).getButton(-2).setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        alertDialog.show();
    }

    private void getAllList() {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String userId = appSharedPrefs.getString("currentUserProfile", "treetha");
        Gson gson = new Gson();
        String jsonRevenue = appSharedPrefs.getString(Constant.PREF_REVENUE + userId, "");
        String jsonExpense = appSharedPrefs.getString(Constant.PREF_EXPENSE + userId, "");
        String jsonPlan = appSharedPrefs.getString(Constant.PREF_PLAN + userId, "");
        Type typeRevenue = new C04559().getType();
        this.listExp = (List) gson.fromJson(jsonExpense, typeRevenue);
        this.listRev = (List) gson.fromJson(jsonRevenue, typeRevenue);
        this.listPlan = (List) gson.fromJson(jsonPlan, typeRevenue);
        if (this.listExp == null) {
            this.listExp = new ArrayList();
        }
        if (this.listRev == null) {
            this.listRev = new ArrayList();
        }
        if (this.listPlan == null) {
            this.listPlan = new ArrayList();
        }
    }

    public void saveData() {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String userId = appSharedPrefs.getString("currentUserProfile", "treetha");
        Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        if(adapter.getListExp() != null && adapter.getListPlan() != null && adapter.getListRev() != null){
            String jsonExpense = gson.toJson(this.adapter.getListExp());
            String jsonPlan = gson.toJson(this.adapter.getListPlan());
            String jsonRevenue = gson.toJson(this.adapter.getListRev());
            prefsEditor.putString(Constant.PREF_EXPENSE + userId, jsonExpense);
            prefsEditor.putString(Constant.PREF_PLAN + userId, jsonPlan);
            prefsEditor.putString(Constant.PREF_REVENUE + userId, jsonRevenue);
            prefsEditor.commit();
        }

    }
}
