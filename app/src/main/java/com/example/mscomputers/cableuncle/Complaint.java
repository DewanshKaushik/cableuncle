package com.example.mscomputers.cableuncle;

import android.app.ActionBar;
import android.app.FragmentTransaction;
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

import com.example.mscomputers.cableuncle.adp.ResolvedComplaintAdapter;
import com.example.mscomputers.cableuncle.model.LoginModel;
import com.example.mscomputers.cableuncle.model.ResolvedModel;
import com.example.mscomputers.cableuncle.parser.JSONParser;
import com.example.mscomputers.cableuncle.util.Constants;
import com.example.mscomputers.cableuncle.util.Util;
import com.madept.core.activity.MAdeptActivity;
import com.madept.core.net.MAdeptErrorResponse;
import com.madept.core.net.MAdeptRequest;
import com.madept.core.net.MAdeptResponse;
import com.madept.core.net.MAdeptResponseListener;
import com.madept.core.util.MAdeptUtil;

import java.util.ArrayList;

/**
 * Created by MS Computers on 12/16/2017.
 */
public class Complaint extends FragmentActivity implements ActionBar.TabListener {

    ViewPager mViewPager;
    SectionsPagerAdapter mAppSectionsPagerAdapter;

    //new, view, resolved

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);

        setContentView(R.layout.complaint);
        setActionBar();
      //  getPaymentData();

    }

    public void setActionBar(){
        mAppSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        final ActionBar actionBar = getActionBar();

        actionBar.setHomeButtonEnabled(false);

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return new NewFragment();

                case 1:
                    return new ViewFragment();

                case 2:
                    return new ResolvedFragment();

                default:
                    Fragment fragment = new DummySectionFragment();
                    Bundle args = new Bundle();
                    args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
                    fragment.setArguments(args);
                    return fragment;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int i) {
            switch (i) {
                case 0:
                    return "New";

                case 1:
                    return "View";

                case 2:
                    return "Resolved";

                default:
                    return "";
            }
        }
    }

    public static class NewFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.newcomplaint, container, false);

            ListView listView = (ListView) rootView.findViewById(R.id.listView);

            return rootView;
        }
    }

    public static class ViewFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.subscriber_fragment, container, false);

            ListView paymentListView = (ListView) rootView.findViewById(R.id.paymentListView);


            return rootView;
        }
    }

    public static class ResolvedFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.resolved_fragment, container, false);

            ListView listView = (ListView) rootView.findViewById(R.id.listView);

            ArrayList<ResolvedModel> list = new ArrayList<>();

            for (int i=0;i<5;i++) {
                ResolvedModel mod=new ResolvedModel();
                mod.complaintNumber="my name is dewansh";
                list.add(mod);
            }

            ResolvedComplaintAdapter adapter = new ResolvedComplaintAdapter(getActivity(), list);
            listView.setAdapter(adapter);


            return rootView;
        }
    }


    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
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
    public void getPaymentData(){
        MAdeptActivity activity=new MAdeptActivity();

        MAdeptRequest req = new MAdeptRequest(Constants.COMPLAINT_VIEW_REQUEST , activity, Constants.PAYMENT_HISTORY_URL, MAdeptRequest.METHOD_POST);
        req.setJSONParser(JSONParser.getInstance());
        req.addParam("user_name", "shweta.gupta249@gmail.com");
        req.addParam("user_pass", "shweta@123");
        req.addParam("auth_key", "SAHYOG-9935144600");

        req.processForData(new MAdeptResponseListener() {
            @Override
            public void onResponse(final MAdeptResponse resp) {
                Complaint.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (resp.isError())// Handle error code
                        {
                            if (resp instanceof MAdeptErrorResponse) {
                                MAdeptErrorResponse error = (MAdeptErrorResponse) resp;
                                MAdeptUtil.showToast(Complaint.this, error.getErrorMessage());
                            } else {
                                MAdeptUtil.showToast(Complaint.this, resp.dataModel.getErrorMessage());
                            }

                        } else {
                            final LoginModel data = (LoginModel) resp.dataModel;
                            if (data != null) {
                                Util.showToast(Complaint.this, "Successful");

                                //d finish();
                            }
                        }
                    }
                });

            }

        });


    }
*/


    public void goToNext(View v) {
      /*  Intent i = new Intent(this, TotalCollectionReport.class);
        startActivity(i);*/
    }
}
