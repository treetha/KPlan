package com.example.joel.myapplication;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MultipleLayoutAdapter extends BaseAdapter {
    private static final int TYPE_CALCULATE = 3;
    private static final int TYPE_DATA = 1;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_SUMMARY = 2;
    private int countSize = 0;
    private List<AssetModel> listAsset = new ArrayList();
    private List<DebtModel> listDebt = new ArrayList();
    private Context mContext;
    private int positionAssetHead = -1;
    private int positionAssetTotal = -1;
    private int positionDebtHead = -1;
    private int positionDebtTotal = -1;

    static class DataHolder {
        public ImageButton imageButton;
        public TextView textViewData;
        public TextView textViewDataAmount;
        public TextView textViewDate;

        public DataHolder(View convertView) {
            this.textViewData = (TextView) convertView.findViewById(R.id.textview_data_list_row);
            this.textViewDate = (TextView) convertView.findViewById(R.id.textview_date_list_row);
            this.textViewDataAmount = (TextView) convertView.findViewById(R.id.textview_data_amount_list_row);
            this.imageButton = (ImageButton) convertView.findViewById(R.id.imagebutton_data_delete_row);
        }
    }

    static class SummaryHolder {
        private TextView textViewAmount;
        private TextView textViewAmountBaht;
        private TextView textViewTotal;

        public SummaryHolder(View convertView) {
            this.textViewTotal = (TextView) convertView.findViewById(R.id.textview_total_list_row);
            this.textViewAmount = (TextView) convertView.findViewById(R.id.textview_amount_list_row);
            this.textViewAmountBaht = (TextView) convertView.findViewById(R.id.textview_amount_baht_row);
        }
    }

    static class TextViewHolder {
        public TextView textView;

        public TextViewHolder(View convertView) {
            this.textView = (TextView) convertView.findViewById(R.id.textview_list_row);
        }
    }

    public List<AssetModel> getListAsset() {
        return this.listAsset;
    }

    public void setListAsset(List<AssetModel> listAsset) {
        this.listAsset = listAsset;
    }

    public List<DebtModel> getListDebt() {
        return this.listDebt;
    }

    public void setListDebt(List<DebtModel> listDebt) {
        this.listDebt = listDebt;
    }

    public MultipleLayoutAdapter(Context context, List<AssetModel> assets, List<DebtModel> debts) {
        this.mContext = context;
        this.listAsset = assets;
        this.listDebt = debts;
        if (assets.size() != 0) {
            this.positionAssetHead = 0;
            this.positionAssetTotal = this.listAsset.size() + 1;
            this.countSize = this.listAsset.size() + 2;
            if (debts.size() != 0) {
                this.positionDebtHead = this.countSize;
                this.positionDebtTotal = (this.positionDebtHead + this.listDebt.size()) + 1;
                this.countSize = (this.countSize + this.listDebt.size()) + 2;
            }
            this.countSize++;
        } else if (debts.size() != 0) {
            this.positionDebtHead = 0;
            this.positionDebtTotal = (this.positionDebtHead + this.listDebt.size()) + 1;
            this.countSize = ((this.countSize + this.listDebt.size()) + 2) + 1;
        }
    }

    public int getCount() {
        return this.countSize;
    }

    public int getViewTypeCount() {
        return 4;
    }

    public int getItemViewType(int position) {
        int i = 0;
        if (((position == this.positionAssetHead ? 1 : 0) | (position == this.positionDebtHead ? 1 : 0)) != 0) {
            return 0;
        }
        int i2;
        if (position == this.positionAssetTotal) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        if (position == this.positionDebtTotal) {
            i = 1;
        }
        if ((i2 | i) != 0) {
            return 2;
        }
        return position == this.countSize + -1 ? 3 : 1;
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        Log.d("getView", "TYPE: " + viewType);
        switch (viewType) {
            case 0:
                return inflateHeader(convertView, parent, position);
            case 1:
                return inflateData(convertView, parent, position);
            case 2:
                return inflateSummary(convertView, parent, position);
            case 3:
                return inflateCalculate(convertView, parent, position);
            default:
                return convertView;
        }
    }

    private View inflateSummary(View convertView, ViewGroup parent, int position) {
        SummaryHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.total_list_row, parent, false);
            viewHolder = new SummaryHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SummaryHolder) convertView.getTag();
        }
        viewHolder.textViewTotal.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        viewHolder.textViewAmount.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        viewHolder.textViewAmountBaht.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        if (position == this.positionAssetTotal) {
            viewHolder.textViewTotal.setText("รวมสินทรัพย์");
            viewHolder.textViewAmount.setText(sumAsset());
        } else if (position == this.positionDebtTotal) {
            viewHolder.textViewTotal.setText("รวมหนี้สิน");
            viewHolder.textViewAmount.setText(sumDebt());
        }
        return convertView;
    }

    private String sumDebt() {
        return new DecimalFormat("#,##0.00").format(getTotalDebt());
    }

    public double getTotalDebt() {
        double total = 0.0d;
        for (int i = 0; i < this.listDebt.size(); i++) {
            total += ((DebtModel) this.listDebt.get(i)).getAmount();
        }
        return total;
    }

    private String sumAsset() {
        return new DecimalFormat("#,##0.00").format(getTotalAsset());
    }

    private View inflateData(View convertView, ViewGroup parent, final int position) {
        DataHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.data_list_row, parent, false);
            viewHolder = new DataHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (DataHolder) convertView.getTag();
        }
        viewHolder.textViewData.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        viewHolder.textViewDataAmount.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        viewHolder.textViewDate.setVisibility(View.INVISIBLE);
        if (this.listAsset.size() == 0) {
            viewHolder.textViewData.setText(((DebtModel) this.listDebt.get(position - 1)).getType());
            viewHolder.textViewDataAmount.setText(((DebtModel) this.listDebt.get(position - 1)).getAmount(true));
            viewHolder.imageButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    MultipleLayoutAdapter.this.removeDebt(position - 1);
                }
            });
        } else if (position <= this.listAsset.size()) {
            viewHolder.textViewData.setText(((AssetModel) this.listAsset.get(position - 1)).getType());
            viewHolder.textViewDataAmount.setText(((AssetModel) this.listAsset.get(position - 1)).getAmount(true));
            viewHolder.imageButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    MultipleLayoutAdapter.this.removeAsset(position - 1);
                }
            });
        } else {
            viewHolder.textViewData.setText(((DebtModel) this.listDebt.get(position - (this.listAsset.size() + 3))).getType());
            viewHolder.textViewDataAmount.setText(((DebtModel) this.listDebt.get(position - (this.listAsset.size() + 3))).getAmount(true));
            viewHolder.imageButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    MultipleLayoutAdapter.this.removeDebt(position - (MultipleLayoutAdapter.this.listAsset.size() + 3));
                }
            });
        }
        return convertView;
    }

    private View inflateHeader(View convertView, ViewGroup parent, int position) {
        TextViewHolder viewHolder;
        Log.d("THIS", "1");
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.title_list_row, parent, false);
            viewHolder = new TextViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TextViewHolder) convertView.getTag();
        }
        if (position == this.positionDebtHead) {
            viewHolder.textView.setText("รายการหนี้สิน");
            viewHolder.textView.setBackgroundResource(R.color.colorRedButton);
        } else if (position == this.positionAssetHead) {
            viewHolder.textView.setText("รายการสินทรัพย์");
            viewHolder.textView.setBackgroundResource(R.color.colorGreenButton);
        }
        return convertView;
    }

    private View inflateCalculate(View convertView, ViewGroup parent, int position) {
        SummaryHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.layout_item_calculate, parent, false);
            viewHolder = new SummaryHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SummaryHolder) convertView.getTag();
        }
        double sum = getTotalSum();
        String formattedNumber = new DecimalFormat("#,##0.00").format(sum);
        if (sum > 0.0d) {
            viewHolder.textViewAmount.setTextColor(this.mContext.getResources().getColor(R.color.colorGreenButton));
            viewHolder.textViewAmountBaht.setTextColor(this.mContext.getResources().getColor(R.color.colorGreenButton));
        } else if (sum < 0.0d) {
            viewHolder.textViewAmount.setTextColor(this.mContext.getResources().getColor(R.color.colorRedButton));
            viewHolder.textViewAmountBaht.setTextColor(this.mContext.getResources().getColor(R.color.colorRedButton));
        } else {
            viewHolder.textViewAmount.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            viewHolder.textViewAmountBaht.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        }
        viewHolder.textViewAmount.setText(formattedNumber);
        return convertView;
    }

    public double getTotalSum() {
        return getTotalAsset() - getTotalDebt();
    }

    public void addDebt(DebtModel debtModel) {
        if (this.listDebt.size() == 0) {
            this.listDebt.add(debtModel);
            if (this.listAsset.size() == 0) {
                this.positionDebtHead = this.countSize;
                this.positionDebtTotal = (this.positionDebtHead + 1) + 1;
                this.countSize += 4;
            } else {
                this.positionDebtHead = this.countSize - 1;
                this.positionDebtTotal = (this.positionDebtHead + 1) + 1;
                this.countSize += 3;
            }
        } else {
            this.listDebt.add(debtModel);
            this.positionDebtTotal++;
            this.countSize++;
        }
        notifyDataSetChanged();
    }

    public void removeDebt(int position) {
        if (this.listDebt.size() == 1) {
            this.listDebt.clear();
            this.positionDebtHead = -1;
            this.positionDebtTotal = -1;
            this.countSize -= 3;
        } else if (this.listDebt.size() > 1) {
            this.listDebt.remove(position);
            this.positionDebtTotal--;
            this.countSize--;
        }
        if (this.listAsset.size() == 0 && this.listDebt.size() == 0) {
            this.countSize = 0;
        }
        notifyDataSetChanged();
    }

    public void addAsset(AssetModel assetModel) {
        if (this.listAsset.size() == 0) {
            this.listAsset.add(assetModel);
            this.positionAssetHead = 0;
            this.positionAssetTotal = 2;
            if (this.listDebt.size() != 0) {
                this.positionDebtHead += 3;
                this.positionDebtTotal += 3;
                this.countSize += 3;
            } else {
                this.countSize += 4;
            }
        } else {
            this.listAsset.add(assetModel);
            this.positionAssetTotal++;
            this.countSize++;
            if (this.listDebt.size() != 0) {
                this.positionDebtHead++;
                this.positionDebtTotal++;
            }
        }
        notifyDataSetChanged();
    }

    public void removeAsset(int position) {
        if (this.listAsset.size() == 1) {
            this.listAsset.clear();
            this.positionAssetHead = -1;
            this.positionAssetTotal = -1;
            if (this.listDebt.size() != 0) {
                this.positionDebtHead = 0;
                this.positionDebtTotal -= 3;
                this.countSize -= 3;
            } else {
                this.countSize = 0;
            }
        } else if (this.listAsset.size() > 1) {
            this.listAsset.remove(position);
            this.positionAssetTotal--;
            if (this.listDebt.size() != 0) {
                this.positionDebtHead--;
                this.positionDebtTotal--;
            }
            this.countSize--;
        }
        if (this.listAsset.size() == 0 && this.listDebt.size() == 0) {
            this.countSize = 0;
        }
        notifyDataSetChanged();
    }

    public double getTotalAsset() {
        double total = 0.0d;
        for (int i = 0; i < this.listAsset.size(); i++) {
            total += ((AssetModel) this.listAsset.get(i)).getAmount();
        }
        return total;
    }
}
