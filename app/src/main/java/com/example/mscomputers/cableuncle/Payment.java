package com.example.mscomputers.cableuncle;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.mscomputers.cableuncle.adp.PaymentAdapter;
import com.example.mscomputers.cableuncle.model.PaymentHistoryModel;
import com.example.mscomputers.cableuncle.model.PaymentModel;
import com.example.mscomputers.cableuncle.parser.JSONParser;
import com.example.mscomputers.cableuncle.util.Constants;
import com.example.mscomputers.cableuncle.util.Util;
import com.example.mscomputers.cableuncle.view.CustomDialog;
import com.example.mscomputers.cableuncle.view.DialogButtonListener;
import com.madept.core.activity.MAdeptActivity;
import com.madept.core.net.MAdeptErrorResponse;
import com.madept.core.net.MAdeptRequest;
import com.madept.core.net.MAdeptResponse;
import com.madept.core.net.MAdeptResponseListener;
import com.madept.core.prefs.MAdeptPrefs;
import com.madept.core.util.MAdeptUtil;

import java.util.ArrayList;

/**
 * Created by MS Computers on 12/14/2017.
 */
public class Payment extends MAdeptActivity {

    EditText mainSearchListEditText;
    ListView paymentListView;
    ArrayList<PaymentModel> contains;
    ArrayList<PaymentModel> containers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);

        mainSearchListEditText = (EditText) findViewById(R.id.mainSearchListEditText);
        paymentListView = (ListView) findViewById(R.id.paymentListView);

      /*  ArrayList<PaymentHistoryModel> models = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            PaymentHistoryModel model = new PaymentHistoryModel();
            model.id = "asfd";
            model.company_name = "12";
            models.add(model);
        }*/


        mainSearchListEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                contains = new ArrayList<PaymentModel>();
                contains.clear();
                for (PaymentModel container : containers) {
                    if ((container.compId != null && container.compId
                            .toLowerCase().indexOf(s.toString().toLowerCase()) > -1)
                            || (container.name != null && container.name
                            .toLowerCase().indexOf(
                                    s.toString().toLowerCase()) > -1)
                            ) {
                        contains.add(container);
                    }
                }

                if (s.length() == 0) {
                /*    searchIconImage.setImageResource(R.drawable.search_icon);
                    searchIconImage.setOnClickListener(null);
                    listFolderLinearLayout.setVisibility(View.VISIBLE);
                    performDetails(null);*/
                    paymentListView.setAdapter(new PaymentAdapter(Payment.this, contains));

                } else {
                    paymentListView.setAdapter(new PaymentAdapter(Payment.this, contains));
                    // isSearched = true;
                  /*  searchIconImage.setImageResource(R.drawable.icon_delete);
                    listFolderLinearLayout.setVisibility(View.GONE);
                    searchIconImage.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            mainSearchListEditText.setText("");
                        }
                    });*/
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });


        getPaymentData();
    }

    public void getPaymentData() {
        MAdeptPrefs.getInstance(Payment.this).loadPrefs();
        MAdeptRequest req = new MAdeptRequest(Constants.PAYMENT_REQUEST, Payment.this, Constants.PAYMENT_URL, MAdeptRequest.METHOD_POST);
        req.setJSONParser(JSONParser.getInstance());
        req.addParam("lco", MAdeptPrefs.getInstance(Payment.this).getPrefs(Constants.LCO));
        req.addParam("area", CableUncleApplication.getInstance().selectedArea);


        req.processForData(new MAdeptResponseListener() {
            @Override
            public void onResponse(final MAdeptResponse resp) {
                Payment.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (resp.isError())// Handle error code
                        {
                            if (resp instanceof MAdeptErrorResponse) {
                                MAdeptErrorResponse error = (MAdeptErrorResponse) resp;
                                MAdeptUtil.showToast(Payment.this, error.getErrorMessage());
                            } else {
                                MAdeptUtil.showToast(Payment.this, resp.dataModel.getErrorMessage());
                            }

                        } else {
                            final PaymentModel data = (PaymentModel) resp.dataModel;
                            if (data != null) {
                                Util.showToast(Payment.this, "Successful");
                                setPaymentData(data);
                                //d finish();
                            }
                        }
                    }
                });

            }

        });


    }

    public void setPaymentData(PaymentModel models) {
        containers = models.modelArrayList;

        PaymentAdapter adapter = new PaymentAdapter(Payment.this, models.modelArrayList);
        paymentListView.setAdapter(adapter);
    }

/*
    public void launchGetPayNowRequest() {
        MAdeptRequest req = new MAdeptRequest(Constants.PAYMENT_REQUEST, Payment.this, Constants.PAYMENT_URL, MAdeptRequest.METHOD_POST);
        req.setJSONParser(JSONParser.getInstance());
        req.addParam("lco", "99903");
        req.addParam("area", "A Blok");


        req.processForData(new MAdeptResponseListener() {
            @Override
            public void onResponse(final MAdeptResponse resp) {
                Payment.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (resp.isError())// Handle error code
                        {
                            if (resp instanceof MAdeptErrorResponse) {
                                MAdeptErrorResponse error = (MAdeptErrorResponse) resp;
                                MAdeptUtil.showToast(Payment.this, error.getErrorMessage());
                            } else {
                                MAdeptUtil.showToast(Payment.this, resp.dataModel.getErrorMessage());
                            }

                        } else {
                            final PaymentModel data = (PaymentModel) resp.dataModel;
                            if (data != null) {
                                Util.showToast(Payment.this, "Successful");
                                setPaymentData(data);
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
/*
        new CustomDialog(Payment.this, "Alert", "Message", new String[]{"OK", "Cancel"}, new DialogButtonListener() {
            @Override
            public void onButtonClicked(String text) {
                Intent i = new Intent(Payment.this, Complaint.class);
                startActivity(i);
                finish();
            }
        }).show();
*/

    }
}
