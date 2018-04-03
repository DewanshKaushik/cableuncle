package com.example.mscomputers.cableuncle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mscomputers.cableuncle.model.DashboardModel;
import com.example.mscomputers.cableuncle.model.LoginModel;
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

/**
 * Created by MS Computers on 12/16/2017.
 */
public class Dashboard extends MAdeptActivity implements DrawerLayout.DrawerListener {
    LinearLayout menuList;
    DrawerLayout drawer;
    DrawerLayout dl;
    ImageView menuButton;
    TextView totalPendingCustomers, totalCollectionCustomers, areaName;
    LinearLayout linearLayout;
    Spinner spinner;
    String selectedArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dashboard);
        menuList = (LinearLayout) findViewById(R.id.menuList);
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        menuButton = (ImageView) findViewById(R.id.menuButton);
        totalPendingCustomers = (TextView) findViewById(R.id.totalPendingCustomers);
        totalCollectionCustomers = (TextView) findViewById(R.id.totalCollectionCustomers);
        areaName = (TextView) findViewById(R.id.areaName);
        spinner = (Spinner) findViewById(R.id.spinner);


        MAdeptPrefs.getInstance(Dashboard.this).loadPrefs();


        setSliderMenu();

        getAreas();

    }

    public void getAreas(){
            MAdeptPrefs.getInstance(Dashboard.this).loadPrefs();

            MAdeptRequest req = new MAdeptRequest(Constants.GET_AREA, this, Constants.GET_AREA_URL, MAdeptRequest.METHOD_POST);
            req.setJSONParser(JSONParser.getInstance());
            req.addParam("lco", MAdeptPrefs.getInstance(Dashboard.this).getPrefs(Constants.LCO));
            req.addParam("field", MAdeptPrefs.getInstance(Dashboard.this).getPrefs(Constants.UNIQUE_ID));


            req.processForData(new MAdeptResponseListener() {
                @Override
                public void onResponse(final MAdeptResponse resp) {
                    Dashboard.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (resp.isError())// Handle error code
                            {
                                if (resp instanceof MAdeptErrorResponse) {
                                    MAdeptErrorResponse error = (MAdeptErrorResponse) resp;
                                    MAdeptUtil.showToast(Dashboard.this, error.getErrorMessage());
                                } else {
                                    MAdeptUtil.showToast(Dashboard.this, resp.dataModel.getErrorMessage());
                                }

                            } else {
                                final LoginModel data = (LoginModel) resp.dataModel;
                                if (data != null) {
                                    CableUncleApplication.getInstance().areaList.clear();

                                    CableUncleApplication.getInstance().areaList = data.modelarrlist;

                                    setSpinner();

                                }
                            }
                        }
                    });

                }

            });



    }


    public void setSpinner() {
        final ArrayList<String> areaList = CableUncleApplication.getInstance().areaList;
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        areaList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedArea = areaList.get(position);
                CableUncleApplication.getInstance().selectedArea = areaList.get(position);
                getData();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getData() {


        MAdeptRequest req = new MAdeptRequest(Constants.DASHBOARD_REQUEST, Dashboard.this, Constants.DASHBOARD_URL, MAdeptRequest.METHOD_POST);
        req.setJSONParser(JSONParser.getInstance());
        req.addParam("lco", MAdeptPrefs.getInstance(Dashboard.this).getPrefs(Constants.LCO));
        if (selectedArea == null) {
            return;
        }
        req.addParam("area", CableUncleApplication.getInstance().selectedArea);


        req.processForData(new MAdeptResponseListener() {
            @Override
            public void onResponse(final MAdeptResponse resp) {
                Dashboard.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (resp.isError())// Handle error code
                        {
                            if (resp instanceof MAdeptErrorResponse) {
                                MAdeptErrorResponse error = (MAdeptErrorResponse) resp;
                                MAdeptUtil.showToast(Dashboard.this, error.getErrorMessage());
                            } else {
                                MAdeptUtil.showToast(Dashboard.this, resp.dataModel.getErrorMessage());
                            }

                        } else {
                            final DashboardModel data = (DashboardModel) resp.dataModel;
                            if (data != null) {
                                Util.showToast(Dashboard.this, "Successful");
                                setData(data);
                                //d finish();
                            }
                        }
                    }
                });

            }

        });
    }

    public void setData(DashboardModel data) {
        totalPendingCustomers.setText(data.pandingCustomer + "");
        totalCollectionCustomers.setText(data.paidCustomer + "");
        areaName.setText(data.totalCustomer + "");

    }

    public void getAllIds() {


    }

    private void setSliderMenu() {
        initActionBar(drawer, menuList);
        drawer.setDrawerListener(this);
    }

    public void initActionBar(final DrawerLayout drawerLayout,
                              final LinearLayout list) {
        this.dl = drawerLayout;
        this.linearLayout = list;

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dl.isDrawerOpen(linearLayout))
                    dl.closeDrawer(linearLayout);
                else {
                    dl.openDrawer(linearLayout);
                }
            }
        });

        setMenuList();
    }

    protected void setMenuList() {

        //menuScreenLayout.setAdapter(adapter);
        LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.drawer_layout, null);
        TextView logout = (TextView) v.findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                closeDrawerLayout(dl, linearLayout);
                launchLogoutRequest();
            }
        })

        //faultsLayout = (LinearLayout) v.findViewById(R.id.faultsLayout);
        //d ImageView closeButton = (ImageView) v.findViewById(R.id.closeButton);
        //d radioGroup = (RadioGroup) v.findViewById(R.id.radioGroup);

        //d    radioGroup = (RadioGroup) v.findViewById(R.id.radioGroup);

        //d   int selectedId = radioGroup.getCheckedRadioButtonId();

        /* closeButton.setOnClickListener(new View.OnClickListener(
            closeDrawerLayout(drawer,linearLayout);
        ));*/

      /*  closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                closeDrawerLayout(dl, linearLayout);
            }
        })*/
        ;


        LinearLayout listViewLinearLayout = (LinearLayout) v;
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);

        listViewLinearLayout.setLayoutParams(param);
        linearLayout.addView(v);

        // editProfile= (ImageView) v.findViewById(R.id.editProfile);

    }

    public void launchLogoutRequest() {
        MAdeptRequest req = new MAdeptRequest(Constants.LOGOUT, Dashboard.this, Constants.LOGOUT_URL, MAdeptRequest.METHOD_POST);
        req.setJSONParser(JSONParser.getInstance());
        req.addParam("lco", MAdeptPrefs.getInstance(Dashboard.this).getPrefs(Constants.LCO));
        req.addParam("field", MAdeptPrefs.getInstance(Dashboard.this).getPrefs(Constants.UNIQUE_ID));


        req.processForData(new MAdeptResponseListener() {
            @Override
            public void onResponse(final MAdeptResponse resp) {
                Dashboard.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (resp.isError())// Handle error code
                        {
                            if (resp instanceof MAdeptErrorResponse) {
                                MAdeptErrorResponse error = (MAdeptErrorResponse) resp;
                                MAdeptUtil.showToast(Dashboard.this, error.getErrorMessage());
                            } else {
                                MAdeptUtil.showToast(Dashboard.this, resp.dataModel.getErrorMessage());
                            }

                        } else {
                            final LoginModel data = (LoginModel) resp.dataModel;
                            if (data != null) {
                                Util.showToast(Dashboard.this, "Successful");
                                MAdeptPrefs.getInstance(Dashboard.this).addPrefs(Constants.PREFS_IS_LOGIN, "false");

                                Intent i = new Intent(Dashboard.this, Login.class);
                                startActivity(i);
                                finish();
                            }
                        }
                    }
                });

            }

        });

    }


    public void closeDrawerLayout(final DrawerLayout drawerLayout,
                                  final LinearLayout list) {
        this.dl = drawerLayout;
        this.linearLayout = list;

        if (dl.isDrawerOpen(linearLayout)) {
            dl.closeDrawer(linearLayout);
        } else {
            dl.openDrawer(linearLayout);
        }
    }

    public void goToNext(View v) {
        Intent i = new Intent(this, Payment.class);
        startActivity(i);
        //d  finish();
    }


    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

}
