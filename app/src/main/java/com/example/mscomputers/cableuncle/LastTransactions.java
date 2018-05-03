package com.example.mscomputers.cableuncle;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mscomputers.cableuncle.Printing.Maestro;
import com.example.mscomputers.cableuncle.adp.PaymentAdapter;
import com.example.mscomputers.cableuncle.adp.UserAdapter;
import com.example.mscomputers.cableuncle.model.LastTransactionModel;
import com.example.mscomputers.cableuncle.model.LoginModel;
import com.example.mscomputers.cableuncle.model.PaymentHistoryModel;
import com.example.mscomputers.cableuncle.model.UserModel;
import com.example.mscomputers.cableuncle.parser.JSONParser;
import com.example.mscomputers.cableuncle.util.Constants;
import com.example.mscomputers.cableuncle.util.Util;
import com.madept.core.activity.MAdeptActivity;
import com.madept.core.net.MAdeptErrorResponse;
import com.madept.core.net.MAdeptRequest;
import com.madept.core.net.MAdeptResponse;
import com.madept.core.net.MAdeptResponseListener;
import com.madept.core.prefs.MAdeptPrefs;
import com.madept.core.util.MAdeptUtil;

import java.util.ArrayList;

import mmsl.DeviceUtility.DeviceBluetoothCommunication;

/**
 * Created by MS Computers on 12/16/2017.
 */
public class LastTransactions extends MAdeptActivity implements ActionBar.TabListener {

    ViewPager mViewPager;
    AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    ListView listView;
    ArrayList<LastTransactionModel> transactionModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.last_transactions);
        listView = (ListView) findViewById(R.id.listView);

      //  setUpActionBar();

        getData();

    }

    public void getData(){
            MAdeptPrefs.getInstance(LastTransactions.this).loadPrefs();

            MAdeptRequest req = new MAdeptRequest(Constants.LAST_TRANSACTION_REQUEST, this, Constants.GET_LAST_TRANSACTION_URL, MAdeptRequest.METHOD_POST);
            req.setJSONParser(JSONParser.getInstance());
            req.addParam("getid", MAdeptPrefs.getInstance(LastTransactions.this).getPrefs(Constants.UNIQUE_ID));
            req.addParam("lco", MAdeptPrefs.getInstance(LastTransactions.this).getPrefs(Constants.LCO));

            req.processForData(new MAdeptResponseListener() {
                @Override
                public void onResponse(final MAdeptResponse resp) {
                    LastTransactions.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (resp.isError())// Handle error code
                            {
                                if (resp instanceof MAdeptErrorResponse) {
                                    MAdeptErrorResponse error = (MAdeptErrorResponse) resp;
                                    MAdeptUtil.showToast(LastTransactions.this, error.getErrorMessage());
                                } else {
                                    MAdeptUtil.showToast(LastTransactions.this, resp.dataModel.getErrorMessage());
                                }

                            } else {
                                final LastTransactionModel data = (LastTransactionModel) resp.dataModel;
                                if (data != null) {
                                    transactionModels=data.lastTransactionModelArrayList;
                                    setData(data.lastTransactionModelArrayList);
                                   // setSpinner();

                                }
                            }
                        }
                    });

                }

            });

    }

    public void setData(ArrayList<LastTransactionModel> lastTransactionModelArrayList){
        UserAdapter adapter=new UserAdapter(LastTransactions.this,lastTransactionModelArrayList);

        listView.setAdapter(adapter);
    }

    public void printBill(int position){
        DeviceBluetoothCommunication communication=CableUncleApplication.getInstance().bluetoothCommunication;
        BluetoothDevice device=CableUncleApplication.getInstance().device;

        if(communication==null){
            Intent intent = new Intent(LastTransactions.this, Maestro.class);
            intent.putExtra("from", "lastTransaction");
            intent.putExtra("transactionModels", transactionModels.get(position));
            startActivity(intent);
        }else{
            Util.printBillForLastTransaction(LastTransactions.this,communication,transactionModels.get(position));
        }
        finish();
    }


/*
    public void setUpActionBar() {
        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();

        // Specify that the Home/Up button should not be enabled, since there is no hierarchical
        // parent.
        actionBar.setHomeButtonEnabled(false);

        // Specify that we will be displaying tabs in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }
*/

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    // The first section of the app is the most interesting -- it offers
                    // a launchpad into the other demonstrations in this example application.
                 //   return new UserFragment();

                case 1:
                    return new SubscriberFragment();

                default:
                    // The other sections of the app are dummy placeholders.
                    Fragment fragment = new DummySectionFragment();
                    Bundle args = new Bundle();
                    args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
                    fragment.setArguments(args);
                    return fragment;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int i) {
            switch (i) {
                case 0:
                    // The first section of the app is the most interesting -- it offers
                    // a launchpad into the other demonstrations in this example application.
                    return "User";

                case 1:
                    return "Subscriber";

                default:
                    return "";
            }
        }
    }

/*
    public static class UserFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.user_fragment, container, false);

            ListView listView = (ListView) rootView.findViewById(R.id.listView);

            ArrayList<UserModel> models=new ArrayList<>();

            for(int i=0;i<50;i++){
                UserModel model=new UserModel();
              //  model.subscriberName="asfd";
                model.id="12";
                models.add(model);
            }

            UserAdapter adapter=new UserAdapter(getActivity(),models);
            listView.setAdapter(adapter);

            return rootView;
        }
    }
*/

    public static class SubscriberFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.subscriber_fragment, container, false);

            ListView paymentListView = (ListView) rootView.findViewById(R.id.paymentListView);

            ArrayList<PaymentHistoryModel> models=new ArrayList<>();

            for(int i=0;i<50;i++){
                PaymentHistoryModel model=new PaymentHistoryModel();
                //  model.subscriberName="asfd";
                model.id="12";
                models.add(model);
            }

          /*  PaymentAdapter adapter=new PaymentAdapter(getActivity(),models);
            paymentListView.setAdapter(adapter);*/


            return rootView;
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public static class DummySectionFragment extends Fragment {

        public static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_section_dummy, container, false);
            Bundle args = getArguments();
            ((TextView) rootView.findViewById(android.R.id.text1)).setText(
                    getString(R.string.dummy_section_text, args.getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /*
    * http://cableuncle.in/cableuncle/android/last_6_transation.php

        getid = 9990502
        lco= 99905
        */
}
