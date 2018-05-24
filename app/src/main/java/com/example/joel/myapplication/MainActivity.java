package com.example.joel.myapplication;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private LinearLayout linear_footer;
    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent() != null) {
            this.password = getIntent().getStringExtra(Constant.INTENT_PASSWORD);
        }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        this.linear_footer = (LinearLayout) findViewById(R.id.linearlayout_mainmenu_footer);

        openPieChart();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        Fragment f = getFragmentManager().findFragmentById(R.id.layout_fragment_container);
        if (f instanceof PieChartFragment) {
            finish();
        } else if (f instanceof BalanceStatementFragment) {
            ((BalanceStatementFragment) getFragmentManager().findFragmentByTag(Constant.FRAG_BALANCE)).saveData();
            openPieChart();
        } else if (f instanceof RevenueExpenseFragment) {
            ((RevenueExpenseFragment) getFragmentManager().findFragmentByTag(Constant.FRAG_REVENUE)).saveData();
            openPieChart();
        } else if (f instanceof TaxPlanFragment) {
            ((TaxPlanFragment) getFragmentManager().findFragmentByTag(Constant.FRAG_TAXPLAN)).saveData();
            openPieChart();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            // Handle the camera action
            Fragment f = getFragmentManager().findFragmentById(R.id.layout_fragment_container);
            if (f instanceof BalanceStatementFragment) {
                ((BalanceStatementFragment) getFragmentManager().findFragmentByTag(Constant.FRAG_BALANCE)).saveData();
            } else if (f instanceof RevenueExpenseFragment) {
                ((RevenueExpenseFragment) getFragmentManager().findFragmentByTag(Constant.FRAG_REVENUE)).saveData();
            } else if (f instanceof TaxPlanFragment) {
                ((TaxPlanFragment) getFragmentManager().findFragmentByTag(Constant.FRAG_TAXPLAN)).saveData();
            }
            openPieChart();
        } else if (id == R.id.nav_logout) {
            Fragment f = getFragmentManager().findFragmentById(R.id.layout_fragment_container);
            if (f instanceof BalanceStatementFragment) {
                ((BalanceStatementFragment) getFragmentManager().findFragmentByTag(Constant.FRAG_BALANCE)).saveData();
            } else if (f instanceof RevenueExpenseFragment) {
                ((RevenueExpenseFragment) getFragmentManager().findFragmentByTag(Constant.FRAG_REVENUE)).saveData();
            } else if (f instanceof TaxPlanFragment) {
                ((TaxPlanFragment) getFragmentManager().findFragmentByTag(Constant.FRAG_TAXPLAN)).saveData();
            }
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void openBalanceStatement(View view) {
        this.linear_footer.setVisibility(View.VISIBLE);
        BalanceStatementFragment myFragment = (BalanceStatementFragment) getFragmentManager().findFragmentByTag(Constant.FRAG_BALANCE);
        if (myFragment == null || !myFragment.isVisible()) {
            saveDataCurrentFragment();
            BalanceStatementFragment threeFragment = new BalanceStatementFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.layout_fragment_container, threeFragment, Constant.FRAG_BALANCE);
            transaction.commit();
        }
    }

    public void openRevenueExpense(View view) {
        this.linear_footer.setVisibility(View.VISIBLE);
        RevenueExpenseFragment myFragment = (RevenueExpenseFragment) getFragmentManager().findFragmentByTag(Constant.FRAG_REVENUE);
        if (myFragment == null || !myFragment.isVisible()) {
            saveDataCurrentFragment();
            RevenueExpenseFragment threeFragment = new RevenueExpenseFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.layout_fragment_container, threeFragment, Constant.FRAG_REVENUE);
            transaction.commit();
        }
    }

    public void openPieChart() {
        this.linear_footer.setVisibility(View.VISIBLE);
        PieChartFragment myFragment = (PieChartFragment) getFragmentManager().findFragmentByTag(Constant.FRAG_PIECHART);
        if (myFragment == null || !myFragment.isVisible()) {
            PieChartFragment threeFragment = new PieChartFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.layout_fragment_container, threeFragment, Constant.FRAG_PIECHART);
            transaction.commit();
        }
    }

    public void openTaxPlan(View view) {
        this.linear_footer.setVisibility(View.GONE);
        TaxPlanFragment myFragment = (TaxPlanFragment) getFragmentManager().findFragmentByTag(Constant.FRAG_TAXPLAN);
        if (myFragment == null || !myFragment.isVisible()) {
            TaxPlanFragment threeFragment = new TaxPlanFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.layout_fragment_container, threeFragment, Constant.FRAG_TAXPLAN);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    public void OpenTableTax(View view) {
        this.linear_footer.setVisibility(View.GONE);
        TableTaxFragment myFragment = (TableTaxFragment) getFragmentManager().findFragmentByTag(Constant.FRAG_TABLE_TAX);
        if (myFragment == null || !myFragment.isVisible()) {
            TableTaxFragment threeFragment = new TableTaxFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.layout_fragment_container, threeFragment, Constant.FRAG_TABLE_TAX);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    public void OpenDeductTax(View view) {
        this.linear_footer.setVisibility(View.GONE);
        DeductTaxFragment myFragment = (DeductTaxFragment) getFragmentManager().findFragmentByTag(Constant.FRAG_DEDUCT_TAX);
        if (myFragment == null || !myFragment.isVisible()) {
            DeductTaxFragment threeFragment = new DeductTaxFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.layout_fragment_container, threeFragment, Constant.FRAG_DEDUCT_TAX);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    public void OpenDeductTestTax(View view) {
        this.linear_footer.setVisibility(View.GONE);
        DeductTestTaxFragment myFragment = (DeductTestTaxFragment) getFragmentManager().findFragmentByTag(Constant.FRAG_DEDUCT_TEST_TAX);
        if (myFragment == null || !myFragment.isVisible()) {
            DeductTestTaxFragment threeFragment = new DeductTestTaxFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.layout_fragment_container, threeFragment, Constant.FRAG_DEDUCT_TEST_TAX);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    public void OpenSummaryTax(View view) {
        this.linear_footer.setVisibility(View.GONE);
        SummaryTaxFragment myFragment = (SummaryTaxFragment) getFragmentManager().findFragmentByTag(Constant.FRAG_SUMMARY_TAX);
        if (myFragment == null || !myFragment.isVisible()) {
            SummaryTaxFragment threeFragment = new SummaryTaxFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.layout_fragment_container, threeFragment, Constant.FRAG_SUMMARY_TAX);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    public void OpenGraphTax(View view) {
        this.linear_footer.setVisibility(View.GONE);
        GraphTaxFragment myFragment = (GraphTaxFragment) getFragmentManager().findFragmentByTag(Constant.FRAG_GRAPH_TAX);
        if (myFragment == null || !myFragment.isVisible()) {
            GraphTaxFragment threeFragment = new GraphTaxFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.layout_fragment_container, threeFragment, Constant.FRAG_GRAPH_TAX);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    public void OpenMainMenu(View view) {
        openPieChart();
    }

    public void onTaxBackPress(View view) {
        onBackPressed();
    }

    public void saveDataCurrentFragment() {
        Fragment f = getFragmentManager().findFragmentById(R.id.layout_fragment_container);
        if (f instanceof BalanceStatementFragment) {
            ((BalanceStatementFragment) getFragmentManager().findFragmentByTag(Constant.FRAG_BALANCE)).saveData();
        } else if (f instanceof RevenueExpenseFragment) {
            ((RevenueExpenseFragment) getFragmentManager().findFragmentByTag(Constant.FRAG_REVENUE)).saveData();
        } else if (f instanceof TaxPlanFragment) {
            ((TaxPlanFragment) getFragmentManager().findFragmentByTag(Constant.FRAG_TAXPLAN)).saveData();
        }
    }
}
