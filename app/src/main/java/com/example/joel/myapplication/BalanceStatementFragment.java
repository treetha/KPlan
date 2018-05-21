package com.example.joel.myapplication;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BalanceStatementFragment extends Fragment {
    private MultipleLayoutAdapter adapter;
    private List<AssetModel> listAsset;
    private List<DebtModel> listDebt;
    private ListView listView;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_balancestatement, container, false);
        this.listView = (ListView) v.findViewById(R.id.listview_balance);
        getAllList();
        if (this.listAsset.size() == 0 && this.listDebt.size() == 0) {
            this.listView.setVisibility(View.GONE);
        } else {
            this.listView.setVisibility(View.VISIBLE);
            this.adapter = new MultipleLayoutAdapter(getActivity().getBaseContext(), this.listAsset, this.listDebt);
            this.listView.setAdapter(this.adapter);
        }
        ((Button) v.findViewById(R.id.button_addasset)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addAssets();
            }
        });
        ((Button) v.findViewById(R.id.button_adddebt)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addDebts();
            }
        });
        return v;
    }

    private void getAllList() {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String userId = appSharedPrefs.getString("currentUserProfile", "treetha");
        Gson gson = new Gson();
        String jsonAsset = appSharedPrefs.getString(Constant.PREF_ASSET + userId, "");
        String jsonDebt = appSharedPrefs.getString(Constant.PREF_DEBT + userId, "");
        Type typeAsset = new TypeToken<List<AssetModel>>() {}.getType();
        Type typeDebt = new TypeToken<List<DebtModel>>() {}.getType();
        this.listAsset = gson.fromJson(jsonAsset, typeAsset);
        this.listDebt = gson.fromJson(jsonDebt, typeDebt);
        if (this.listAsset == null) {
            this.listAsset = new ArrayList<>();
        }
        if (this.listDebt == null) {
            this.listDebt = new ArrayList<>();
        }
    }

    private void addDebts() {
        final String[] DEBTS = new String[]{Constant.DEBT_CREDIT, Constant.DEBT_HOME, Constant.DEBT_CAR, Constant.DEBT_SHORT, Constant.DEBT_LONG};
        Builder builder = new Builder(getActivity());
        builder.setTitle("เลือกหนี้สิน");
        builder.setItems(DEBTS, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String selected = DEBTS[which];
                switch (which) {
                    case 0:
                        BalanceStatementFragment.this.addCredit(Constant.DEBT_CREDIT, selected);
                        return;
                    case 1:
                        BalanceStatementFragment.this.addCredit(Constant.DEBT_HOME, selected);
                        return;
                    case 2:
                        BalanceStatementFragment.this.addCredit(Constant.DEBT_CAR, selected);
                        return;
                    case 3:
                        BalanceStatementFragment.this.addCredit(Constant.DEBT_SHORT, selected);
                        return;
                    case 4:
                        BalanceStatementFragment.this.addCredit(Constant.DEBT_LONG, selected);
                        return;
                    default:
                        return;
                }
            }
        });
        builder.create();
        builder.show();
    }

    private void addCredit(String debtType, String selected) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.adddeposit_dialog, null);
        TextView titleTv = (TextView) view.findViewById(R.id.textview_titlename);
        final EditText amountEt = (EditText) view.findViewById(R.id.edittext_amount);
        amountEt.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        titleTv.setText(selected);
        titleTv.setBackgroundResource(R.color.colorRedButton);
        final String[] arrayForSpinner = new String[]{Constant.BANK_K, Constant.BANK_SCB, Constant.BANK_BKK, Constant.BANK_GSB};
        final Spinner spinner = (Spinner) view.findViewById(R.id.spinner_deposittype);
        spinner.setAdapter(new CustomSpinnerAdapter(getActivity(), R.layout.spinner_row, arrayForSpinner, Constant.SPINNER_BANK_HINT));
        AlertDialog alertDialog = new Builder(getActivity()).setView(view).setPositiveButton("บันทึก", null).setNegativeButton("ยกเลิก", null).create();
        final String str = debtType;
        alertDialog.setOnShowListener(new OnShowListener() {
            public void onShow(final DialogInterface dialog) {
                ((AlertDialog) dialog).getButton(-1).setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (Constant.SPINNER_BANK_HINT.equals((String) spinner.getSelectedItem())) {
                            Toast.makeText(BalanceStatementFragment.this.getActivity(), "Select Bank", Toast.LENGTH_LONG).show();
                        } else if ("".equals(amountEt.getText().toString())) {
                            Toast.makeText(BalanceStatementFragment.this.getActivity(), "Enter Amount", Toast.LENGTH_LONG).show();
                        } else {
                            DebtModel debtModel = new DebtModel(str, new Double(amountEt.getText().toString()).doubleValue(), arrayForSpinner[spinner.getSelectedItemPosition()]);
                            if (BalanceStatementFragment.this.adapter != null) {
                                BalanceStatementFragment.this.adapter.addDebt(debtModel);
                                Log.d("Notify", "position : " + spinner.getSelectedItemPosition());
                            } else {
                                BalanceStatementFragment.this.listDebt.add(debtModel);
                                BalanceStatementFragment.this.listView.setVisibility(View.VISIBLE);
                                BalanceStatementFragment.this.adapter = new MultipleLayoutAdapter(BalanceStatementFragment.this.getActivity().getApplicationContext(), BalanceStatementFragment.this.listAsset, BalanceStatementFragment.this.listDebt);
                                BalanceStatementFragment.this.listView.setAdapter(BalanceStatementFragment.this.adapter);
                                Log.d("onCreate", "position : " + spinner.getSelectedItemPosition());
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

    public void addAssets() {
        final String[] ASSETS = new String[]{Constant.ASSET_CASH, Constant.ASSET_DEPOSIT, Constant.ASSET_FUND, Constant.ASSET_TRADE, Constant.ASSET_OTHER};
        Builder builder = new Builder(getActivity());
        builder.setTitle("เลือกสินทรัพย์");
        builder.setItems(ASSETS, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String selected = ASSETS[which];
                switch (which) {
                    case 0:
                        BalanceStatementFragment.this.addCash(selected);
                        return;
                    case 1:
                        BalanceStatementFragment.this.addDeposit(selected);
                        return;
                    default:
                        return;
                }
            }
        });
        builder.create();
        builder.show();
    }

    private void addDeposit(String selected) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.adddeposit_dialog, null);
        final EditText amountEt = (EditText) view.findViewById(R.id.edittext_amount);
        amountEt.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        ((TextView) view.findViewById(R.id.textview_titlename)).setText(selected);
        final String[] arrayForSpinner = new String[]{Constant.DEPOSIT_SAVING, Constant.DEPOSIT_FIXED, Constant.DEPOSIT_CURRENT};
        final Spinner spinner = (Spinner) view.findViewById(R.id.spinner_deposittype);
        spinner.setAdapter(new CustomSpinnerAdapter(getActivity(), R.layout.spinner_row, arrayForSpinner, Constant.SPINNER_DEPOSIT_HINT));
        AlertDialog alertDialog = new Builder(getActivity()).setView(view).setPositiveButton("บันทึก", null).setNegativeButton("ยกเลิก", null).create();
        alertDialog.setOnShowListener(new OnShowListener() {
            public void onShow(final DialogInterface dialog) {
                ((AlertDialog) dialog).getButton(-1).setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (Constant.SPINNER_DEPOSIT_HINT.equals((String) spinner.getSelectedItem())) {
                            Toast.makeText(BalanceStatementFragment.this.getActivity(), "Select Type", Toast.LENGTH_LONG).show();
                        } else if ("".equals(amountEt.getText().toString())) {
                            Toast.makeText(BalanceStatementFragment.this.getActivity(), "Enter Amount", Toast.LENGTH_LONG).show();
                        } else {
                            AssetModel assetModel = new AssetModel(Constant.ASSET_DEPOSIT, new Double(amountEt.getText().toString()).doubleValue(), arrayForSpinner[spinner.getSelectedItemPosition()]);
                            if (BalanceStatementFragment.this.adapter != null) {
                                BalanceStatementFragment.this.adapter.addAsset(assetModel);
                            } else {
                                BalanceStatementFragment.this.listAsset.add(assetModel);
                                BalanceStatementFragment.this.listView.setVisibility(View.VISIBLE);
                                BalanceStatementFragment.this.adapter = new MultipleLayoutAdapter(BalanceStatementFragment.this.getActivity().getApplicationContext(), BalanceStatementFragment.this.listAsset, BalanceStatementFragment.this.listDebt);
                                BalanceStatementFragment.this.listView.setAdapter(BalanceStatementFragment.this.adapter);
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

    private void addCash(String title) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.addcash_dialog, null);
        final EditText amountEt = (EditText) view.findViewById(R.id.edittext_amount);
        amountEt.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        ((TextView) view.findViewById(R.id.textview_titlename)).setText(title);
        AlertDialog alertDialog = new Builder(getActivity()).setView(view).setPositiveButton("บันทึก", null).setNegativeButton("ยกเลิก", null).create();
        alertDialog.setOnShowListener(new OnShowListener() {
            public void onShow(final DialogInterface dialog) {
                ((AlertDialog) dialog).getButton(-1).setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if ("".equals(amountEt.getText().toString())) {
                            Toast.makeText(BalanceStatementFragment.this.getActivity(), "Enter Amount", Toast.LENGTH_LONG).show();
                            return;
                        }
                        AssetModel assetModel = new AssetModel(Constant.ASSET_CASH, new Double(amountEt.getText().toString()).doubleValue(), null);
                        if (BalanceStatementFragment.this.adapter != null) {
                            BalanceStatementFragment.this.adapter.addAsset(assetModel);
                        } else {
                            BalanceStatementFragment.this.listAsset.add(assetModel);
                            BalanceStatementFragment.this.listView.setVisibility(View.VISIBLE);
                            BalanceStatementFragment.this.adapter = new MultipleLayoutAdapter(BalanceStatementFragment.this.getActivity().getApplicationContext(), BalanceStatementFragment.this.listAsset, BalanceStatementFragment.this.listDebt);
                            BalanceStatementFragment.this.listView.setAdapter(BalanceStatementFragment.this.adapter);
                        }
                        dialog.dismiss();
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

    public void onPause() {
        super.onPause();
        Log.d("onPause()", "YES");
    }

    public void saveData() {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String userId = appSharedPrefs.getString("currentUserProfile", "treetha");
        Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        if(adapter.getListAsset() != null && adapter.getListDebt() != null){
            String jsonAsset = gson.toJson(this.adapter.getListAsset());
            String jsonDebt = gson.toJson(this.adapter.getListDebt());
            prefsEditor.putString(Constant.PREF_ASSET + userId, jsonAsset);
            prefsEditor.putString(Constant.PREF_DEBT + userId, jsonDebt);
            prefsEditor.putString(Constant.PREF_TOTAL_ASSET + userId, this.adapter.getTotalAsset() + "");
            prefsEditor.putString(Constant.PREF_TOTAL_DEBT + userId, this.adapter.getTotalDebt() + "");
            prefsEditor.putString(Constant.PREF_TOTAL_SUM + userId, new DecimalFormat("#,##0.00").format(this.adapter.getTotalSum()));
            prefsEditor.commit();
        }
    }
}
