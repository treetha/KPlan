package com.example.joel.myapplication;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RevenueExpenseAdapter extends BaseAdapter {
    private static final int TYPE_DATA = 1;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_PIECHART = 2;
    private int countSize = 0;
    private List<RevenueModel> listExp = new ArrayList();
    private List<RevenueModel> listPlan = new ArrayList();
    private List<RevenueModel> listRev = new ArrayList();
    private Context mContext;
    private int posExpHead = -1;
    private int posPieChard = -1;
    private int posPlanHead = -1;
    private int posRevHead = -1;

    static class PieChartHolder {
        private TextView amountTv;
        private PieChart piechart;

        public PieChartHolder(View convertView) {
            this.amountTv = (TextView) convertView.findViewById(R.id.simplepie_amountTv);
            this.piechart = (PieChart) convertView.findViewById(R.id.rev_pieChart);
        }
    }

    public List<RevenueModel> getListRev() {
        return this.listRev;
    }

    public List<RevenueModel> getListExp() {
        return this.listExp;
    }

    public List<RevenueModel> getListPlan() {
        return this.listPlan;
    }

    public RevenueExpenseAdapter(Context context, List<RevenueModel> listRev, List<RevenueModel> listExp, List<RevenueModel> listPlan) {
        this.mContext = context;
        this.listRev = listRev;
        this.listExp = listExp;
        this.listPlan = listPlan;
        if (listRev.size() != 0) {
            this.posPieChard = 0;
            this.countSize++;
            this.posRevHead = 1;
            this.countSize++;
            this.countSize += listRev.size();
            if (listExp.size() != 0) {
                this.posExpHead = this.countSize;
                this.countSize++;
                this.countSize += listExp.size();
            }
            if (listPlan.size() != 0) {
                this.posPlanHead = this.countSize;
                this.countSize++;
                this.countSize += listPlan.size();
            }
        } else if (listExp.size() != 0) {
            this.posPieChard = 0;
            this.countSize++;
            this.posExpHead = 1;
            this.countSize++;
            this.countSize += listExp.size();
            if (listPlan.size() != 0) {
                this.posPlanHead = this.countSize;
                this.countSize++;
                this.countSize += listPlan.size();
            }
        } else if (listPlan.size() != 0) {
            this.posPieChard = 0;
            this.countSize++;
            this.posPlanHead = 1;
            this.countSize++;
            this.countSize += listPlan.size();
        }
    }

    public int getViewTypeCount() {
        return 3;
    }

    public int getCount() {
        return this.countSize;
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public int getItemViewType(int position) {
        if (((position == this.posPlanHead ? 1 : 0) | ((position == this.posExpHead ? 1 : 0) | (position == this.posRevHead ? 1 : 0))) != 0) {
            return 0;
        }
        return position == this.posPieChard ? 2 : 1;
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
                return inflatePieChart(convertView, parent, position);
            default:
                return convertView;
        }
    }

    private View inflatePieChart(View convertView, ViewGroup parent, int position) {
        PieChartHolder viewHolder;
        Log.d("pie", "inflate()");
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.rev_piechart_item, parent, false);
            viewHolder = new PieChartHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (PieChartHolder) convertView.getTag();
        }
        double sum = getSumRevenue() - (getSumExpense() + getSumPlan());
        String formattedNumber = new DecimalFormat("#,##0.00").format(sum);
        if (sum > 0.0d) {
            viewHolder.amountTv.setTextColor(this.mContext.getResources().getColor(R.color.colorGreenButton));
        } else if (sum < 0.0d) {
            viewHolder.amountTv.setTextColor(this.mContext.getResources().getColor(R.color.colorRedButton));
        } else {
            viewHolder.amountTv.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        }
        viewHolder.amountTv.setText(formattedNumber);
        viewHolder.piechart.getDescription().setEnabled(false);
        viewHolder.piechart.setDrawCenterText(false);
        viewHolder.piechart.setDrawHoleEnabled(false);
        viewHolder.piechart.setData(generatePieData());
        viewHolder.piechart.invalidate();
        return convertView;
    }

    private View inflateHeader(View convertView, ViewGroup parent, int position) {
        MultipleLayoutAdapter.TextViewHolder viewHolder;
        Log.d("THIS", "1");
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.title_list_row, parent, false);
            viewHolder = new MultipleLayoutAdapter.TextViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MultipleLayoutAdapter.TextViewHolder) convertView.getTag();
        }
        if (position == this.posExpHead) {
            viewHolder.textView.setText("รายจ่าย");
            viewHolder.textView.setBackgroundResource(R.color.colorRedButton);
        } else if (position == this.posRevHead) {
            viewHolder.textView.setText("รายรับ");
            viewHolder.textView.setBackgroundResource(R.color.colorGreenButton);
        } else if (position == this.posPlanHead) {
            viewHolder.textView.setText("แผนค่าใช้จ่าย");
            viewHolder.textView.setBackgroundResource(R.color.colorOrangeButton);
        }
        return convertView;
    }

    private View inflateData(View convertView, ViewGroup parent, final int position) {
        MultipleLayoutAdapter.DataHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.data_list_row, parent, false);
            viewHolder = new MultipleLayoutAdapter.DataHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MultipleLayoutAdapter.DataHolder) convertView.getTag();
        }
        viewHolder.textViewData.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        viewHolder.textViewDataAmount.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        viewHolder.textViewDate.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        if (this.listRev.size() != 0) {
            if (position <= this.listRev.size() + this.posRevHead) {
                viewHolder.textViewData.setText(((RevenueModel) this.listRev.get(position - 2)).getTitle());
                viewHolder.textViewDate.setText(((RevenueModel) this.listRev.get(position - 2)).getDate());
                viewHolder.textViewDataAmount.setText(((RevenueModel) this.listRev.get(position - 2)).getAmount(true));
                viewHolder.imageButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        RevenueExpenseAdapter.this.removeRev(position - 2);
                    }
                });
            } else if (position <= this.listExp.size() + this.posExpHead) {
                viewHolder.textViewData.setText(((RevenueModel) this.listExp.get(position - (this.posExpHead + 1))).getTitle());
                viewHolder.textViewDate.setText(((RevenueModel) this.listExp.get(position - (this.posExpHead + 1))).getDate());
                viewHolder.textViewDataAmount.setText(((RevenueModel) this.listExp.get(position - (this.posExpHead + 1))).getAmount(true));
                viewHolder.imageButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        RevenueExpenseAdapter.this.removeExp(position - (RevenueExpenseAdapter.this.posExpHead + 1));
                    }
                });
            } else if (position <= this.listPlan.size() + this.posPlanHead) {
                viewHolder.textViewData.setText(((RevenueModel) this.listPlan.get(position - (this.posPlanHead + 1))).getTitle());
                viewHolder.textViewDate.setText(((RevenueModel) this.listPlan.get(position - (this.posPlanHead + 1))).getDate());
                viewHolder.textViewDataAmount.setText(((RevenueModel) this.listPlan.get(position - (this.posPlanHead + 1))).getAmount(true));
                viewHolder.imageButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        RevenueExpenseAdapter.this.removePlan(position - (RevenueExpenseAdapter.this.posPlanHead + 1));
                    }
                });
            }
        } else if (this.listExp.size() != 0) {
            if (position <= this.listExp.size() + this.posExpHead) {
                viewHolder.textViewData.setText(((RevenueModel) this.listExp.get(position - (this.posExpHead + 1))).getTitle());
                viewHolder.textViewDate.setText(((RevenueModel) this.listExp.get(position - (this.posExpHead + 1))).getDate());
                viewHolder.textViewDataAmount.setText(((RevenueModel) this.listExp.get(position - (this.posExpHead + 1))).getAmount(true));
                viewHolder.imageButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        RevenueExpenseAdapter.this.removeExp(position - (RevenueExpenseAdapter.this.posExpHead + 1));
                    }
                });
            } else if (position <= this.listPlan.size() + this.posPlanHead) {
                viewHolder.textViewData.setText(((RevenueModel) this.listPlan.get(position - (this.posPlanHead + 1))).getTitle());
                viewHolder.textViewDate.setText(((RevenueModel) this.listPlan.get(position - (this.posPlanHead + 1))).getDate());
                viewHolder.textViewDataAmount.setText(((RevenueModel) this.listPlan.get(position - (this.posPlanHead + 1))).getAmount(true));
                viewHolder.imageButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        RevenueExpenseAdapter.this.removePlan(position - (RevenueExpenseAdapter.this.posPlanHead + 1));
                    }
                });
            }
        } else if (this.listPlan.size() != 0) {
            viewHolder.textViewData.setText(((RevenueModel) this.listPlan.get(position - (this.posPlanHead + 1))).getTitle());
            viewHolder.textViewDate.setText(((RevenueModel) this.listPlan.get(position - (this.posPlanHead + 1))).getDate());
            viewHolder.textViewDataAmount.setText(((RevenueModel) this.listPlan.get(position - (this.posPlanHead + 1))).getAmount(true));
            viewHolder.imageButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    RevenueExpenseAdapter.this.removePlan(position - (RevenueExpenseAdapter.this.posPlanHead + 1));
                }
            });
        }
        return convertView;
    }

    private void removePlan(int position) {
        if (this.listPlan.size() == 1) {
            this.listPlan.clear();
            this.posPlanHead = -1;
            this.countSize -= 2;
        } else if (this.listPlan.size() > 1) {
            this.listPlan.remove(position);
            this.countSize--;
        }
        if (this.listRev.size() == 0 && this.listExp.size() == 0 && this.listPlan.size() == 0) {
            this.countSize = 0;
        }
        notifyDataSetChanged();
    }

    private void removeExp(int position) {
        if (this.listExp.size() == 1) {
            this.listExp.clear();
            this.posExpHead = -1;
            if (this.listPlan.size() != 0) {
                this.posPlanHead -= 2;
            }
            this.countSize -= 2;
        } else if (this.listExp.size() > 1) {
            this.listExp.remove(position);
            if (this.listPlan.size() != 0) {
                this.posPlanHead--;
            }
            this.countSize--;
        }
        if (this.listRev.size() == 0 && this.listExp.size() == 0 && this.listPlan.size() == 0) {
            this.countSize = 0;
        }
        notifyDataSetChanged();
    }

    private void removeRev(int position) {
        if (this.listRev.size() == 1) {
            this.listRev.clear();
            this.posRevHead = -1;
            if (this.listExp.size() != 0) {
                this.posExpHead -= 2;
                if (this.listPlan.size() != 0) {
                    this.posPlanHead -= 2;
                }
                this.countSize -= 2;
            } else if (this.listPlan.size() != 0) {
                this.posPlanHead -= 2;
                this.countSize -= 2;
            } else {
                this.posPlanHead = -1;
                this.posExpHead = -1;
                this.countSize = 0;
            }
        } else if (this.listRev.size() > 1) {
            this.listRev.remove(position);
            if (this.listExp.size() != 0) {
                this.posExpHead--;
                if (this.listPlan.size() != 0) {
                    this.posPlanHead--;
                }
            } else if (this.listPlan.size() != 0) {
                this.posPlanHead--;
            }
            this.countSize--;
        }
        if (this.listRev.size() == 0 && this.listExp.size() == 0 && this.listPlan.size() == 0) {
            this.countSize = 0;
        }
        notifyDataSetChanged();
    }

    public void addRevenue(RevenueModel revenueModel) {
        if (this.listRev.size() == 0) {
            this.listRev.add(revenueModel);
            this.posRevHead = 1;
            this.countSize++;
            this.countSize++;
            if (this.listExp.size() != 0) {
                this.posExpHead += 2;
                if (this.listPlan.size() != 0) {
                    this.posPlanHead += 2;
                }
            } else if (this.listPlan.size() != 0) {
                this.posExpHead += 2;
            } else if (this.listExp.size() == 0 && this.listPlan.size() == 0) {
                this.posPieChard = 0;
                this.countSize++;
            }
        } else {
            this.listRev.add(revenueModel);
            this.countSize++;
            if (this.listExp.size() != 0) {
                this.posExpHead++;
                if (this.listPlan.size() != 0) {
                    this.posPlanHead++;
                }
            } else if (this.listPlan.size() != 0) {
                this.posPlanHead++;
            }
        }
        notifyDataSetChanged();
    }

    public void addExp(RevenueModel expenseModel) {
        if (this.listExp.size() == 0) {
            this.listExp.add(expenseModel);
            if (this.listRev.size() == 0) {
                this.posExpHead = 1;
                if (this.listPlan.size() == 0) {
                    this.posPieChard = 0;
                    this.countSize++;
                } else {
                    this.posPlanHead += 2;
                }
                this.countSize += 2;
            } else {
                this.posExpHead = this.listRev.size() + 2;
                if (this.listPlan.size() != 0) {
                    this.posPlanHead += 2;
                }
                this.countSize += 2;
            }
        } else {
            this.listExp.add(expenseModel);
            this.countSize++;
            if (this.listPlan.size() != 0) {
                this.posPlanHead++;
            }
        }
        notifyDataSetChanged();
    }

    public void addPlan(RevenueModel planModel) {
        if (this.listPlan.size() == 0) {
            this.listPlan.add(planModel);
            if (this.listRev.size() == 0 && this.listExp.size() == 0) {
                this.posPieChard = 0;
                this.posPlanHead = 1;
                this.countSize = 3;
            } else if (this.listRev.size() != 0 && this.listExp.size() == 0) {
                this.posPlanHead = this.listRev.size() + 2;
                this.countSize += 2;
            } else if (this.listRev.size() == 0 && this.listExp.size() != 0) {
                this.posPlanHead = this.listExp.size() + 2;
                this.countSize += 2;
            } else if (!(this.listRev.size() == 0 || this.listExp.size() == 0)) {
                this.posPlanHead = (this.posExpHead + this.listExp.size()) + 1;
                this.countSize += 2;
            }
        } else {
            this.listPlan.add(planModel);
            this.countSize++;
        }
        notifyDataSetChanged();
    }

    private PieData generatePieData() {
        double sumRev = 0.0d;
        double sumExp = 0.0d;
        double sumPlan = 0.0d;
        for (int i = 0; i < this.listRev.size(); i++) {
            sumRev += ((RevenueModel) this.listRev.get(i)).getAmount();
        }
        for (int j = 0; j < this.listExp.size(); j++) {
            sumExp += ((RevenueModel) this.listExp.get(j)).getAmount();
        }
        for (int k = 0; k < this.listPlan.size(); k++) {
            sumPlan += ((RevenueModel) this.listPlan.get(k)).getAmount();
        }
        ArrayList<PieEntry> entries1 = new ArrayList();
        PieDataSet ds1;
        if (this.listRev.size() != 0 && this.listExp.size() != 0 && this.listPlan.size() != 0) {
            entries1.add(new PieEntry(Float.parseFloat(sumRev + ""), "รายรับ"));
            entries1.add(new PieEntry(Float.parseFloat(sumExp + ""), "รายจ่าย"));
            entries1.add(new PieEntry(Float.parseFloat(sumPlan + ""), "แผนค่าใช้จ่าย"));
            ds1 = new PieDataSet(entries1, "");
            ds1.setColors(ColorTemplate.rgb("#53DC62"), ColorTemplate.rgb("#F8695E"), ColorTemplate.rgb("#F79940"));
            ds1.setSliceSpace(2.0f);
            ds1.setValueTextColor(-1);
            ds1.setValueTextSize(12.0f);
            return new PieData(ds1);
        } else if (this.listRev.size() == 0 && this.listExp.size() != 0 && this.listPlan.size() != 0) {
            entries1.add(new PieEntry(Float.parseFloat(sumExp + ""), "รายจ่าย"));
            entries1.add(new PieEntry(Float.parseFloat(sumPlan + ""), "แผนค่าใช้จ่าย"));
            ds1 = new PieDataSet(entries1, "");
            ds1.setColors(ColorTemplate.rgb("#F8695E"), ColorTemplate.rgb("#F79940"));
            ds1.setSliceSpace(2.0f);
            ds1.setValueTextColor(-1);
            ds1.setValueTextSize(12.0f);
            return new PieData(ds1);
        } else if (this.listRev.size() != 0 && this.listExp.size() == 0 && this.listPlan.size() != 0) {
            entries1.add(new PieEntry(Float.parseFloat(sumRev + ""), "รายรับ"));
            entries1.add(new PieEntry(Float.parseFloat(sumPlan + ""), "แผนค่าใช้จ่าย"));
            ds1 = new PieDataSet(entries1, "");
            ds1.setColors(ColorTemplate.rgb("#53DC62"), ColorTemplate.rgb("#F79940"));
            ds1.setSliceSpace(2.0f);
            ds1.setValueTextColor(-1);
            ds1.setValueTextSize(12.0f);
            return new PieData(ds1);
        } else if (this.listRev.size() != 0 && this.listExp.size() != 0 && this.listPlan.size() == 0) {
            entries1.add(new PieEntry(Float.parseFloat(sumRev + ""), "รายรับ"));
            entries1.add(new PieEntry(Float.parseFloat(sumExp + ""), "รายจ่าย"));
            ds1 = new PieDataSet(entries1, "");
            ds1.setColors(ColorTemplate.rgb("#53DC62"), ColorTemplate.rgb("#F8695E"));
            ds1.setSliceSpace(2.0f);
            ds1.setValueTextColor(-1);
            ds1.setValueTextSize(12.0f);
            return new PieData(ds1);
        } else if (this.listRev.size() != 0 && this.listExp.size() == 0 && this.listPlan.size() == 0) {
            entries1.add(new PieEntry(Float.parseFloat(sumRev + ""), "รายรับ"));
            ds1 = new PieDataSet(entries1, "");
            ds1.setColors(ColorTemplate.rgb("#53DC62"));
            ds1.setSliceSpace(2.0f);
            ds1.setValueTextColor(-1);
            ds1.setValueTextSize(12.0f);
            return new PieData(ds1);
        } else if (this.listRev.size() == 0 && this.listExp.size() != 0 && this.listPlan.size() == 0) {
            entries1.add(new PieEntry(Float.parseFloat(sumExp + ""), "รายจ่าย"));
            ds1 = new PieDataSet(entries1, "");
            ds1.setColors(ColorTemplate.rgb("#F8695E"));
            ds1.setSliceSpace(2.0f);
            ds1.setValueTextColor(-1);
            ds1.setValueTextSize(12.0f);
            return new PieData(ds1);
        } else if (this.listRev.size() == 0 && this.listExp.size() == 0 && this.listPlan.size() != 0) {
            entries1.add(new PieEntry(Float.parseFloat(sumPlan + ""), "แผนค่าใช้จ่าย"));
            ds1 = new PieDataSet(entries1, "");
            ds1.setColors(ColorTemplate.rgb("#F79940"));
            ds1.setSliceSpace(2.0f);
            ds1.setValueTextColor(-1);
            ds1.setValueTextSize(12.0f);
            return new PieData(ds1);
        } else {
            entries1.add(new PieEntry(Float.parseFloat(sumRev + ""), "รายรับ"));
            entries1.add(new PieEntry(Float.parseFloat(sumExp + ""), "รายจ่าย"));
            entries1.add(new PieEntry(Float.parseFloat(sumPlan + ""), "แผนค่าใช้จ่าย"));
            ds1 = new PieDataSet(entries1, "");
            ds1.setColors(ColorTemplate.rgb("#53DC62"), ColorTemplate.rgb("#F8695E"), ColorTemplate.rgb("#F79940"));
            ds1.setSliceSpace(2.0f);
            ds1.setValueTextColor(-1);
            ds1.setValueTextSize(12.0f);
            return new PieData(ds1);
        }
    }

    public double getSumRevenue() {
        double sumRev = 0.0d;
        for (int i = 0; i < this.listRev.size(); i++) {
            sumRev += ((RevenueModel) this.listRev.get(i)).getAmount();
        }
        return sumRev;
    }

    public double getSumExpense() {
        double sumExp = 0.0d;
        for (int j = 0; j < this.listExp.size(); j++) {
            sumExp += ((RevenueModel) this.listExp.get(j)).getAmount();
        }
        return sumExp;
    }

    public double getSumPlan() {
        double sumPlan = 0.0d;
        for (int k = 0; k < this.listPlan.size(); k++) {
            sumPlan += ((RevenueModel) this.listPlan.get(k)).getAmount();
        }
        return sumPlan;
    }
}
