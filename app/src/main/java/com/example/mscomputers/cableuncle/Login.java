package com.example.mscomputers.cableuncle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.mscomputers.cableuncle.Prefs.Prefs;
import com.example.mscomputers.cableuncle.model.LoginModel;
import com.example.mscomputers.cableuncle.model.PayNowModel;
import com.example.mscomputers.cableuncle.model.PaymentModel;
import com.example.mscomputers.cableuncle.parser.JSONParser;
import com.example.mscomputers.cableuncle.util.Constants;
import com.example.mscomputers.cableuncle.util.Util;
import com.example.mscomputers.cableuncle.view.DialogButtonListener;
import com.example.mscomputers.cableuncle.view.EditTextDialog;
import com.madept.core.activity.MAdeptActivity;
import com.madept.core.net.MAdeptErrorResponse;
import com.madept.core.net.MAdeptRequest;
import com.madept.core.net.MAdeptResponse;
import com.madept.core.net.MAdeptResponseListener;
import com.madept.core.prefs.MAdeptPrefs;
import com.madept.core.util.MAdeptUtil;

/**
 * Created by MS Computers on 12/12/2017.
 */
public class Login extends MAdeptActivity {
    EditText loginEmail, loginPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getAllIds();
        Prefs.getInstance().loadPrefs(Login.this);

    }

    private void getAllIds() {
        loginEmail = (EditText) findViewById(R.id.loginEmail);
        loginPassword = (EditText) findViewById(R.id.loginPassword);
    }

    public void goToAdminHome(View v) {
        String email = loginEmail.getEditableText().toString();
        String password = loginPassword.getEditableText().toString();
        if (!Util.isValidString(email)) {
            MAdeptUtil.showToast(this, "Enter UserID");
            return;
        }
        if (!Util.isValidEmail(email)) {
            MAdeptUtil.showToast(this, "Enter valid email");
            return;
        }
        if (!Util.isValidString(password)) {
            MAdeptUtil.showToast(this, "Enter password");
            return;
        }
        launchLoginRequest(email, password);

      /*  Intent i = new Intent(Login.this, Dashboard.class);
        startActivity(i);
        finish();*/
    }

    public void launchLoginRequest(String email, String password) {
        MAdeptPrefs.getInstance(Login.this).loadPrefs();

        MAdeptRequest req = new MAdeptRequest(Constants.LOGIN_REQUEST, this, Constants.LOGINURL, MAdeptRequest.METHOD_POST);
        req.setJSONParser(JSONParser.getInstance());
        req.addParam("email", email);
        req.addParam("password", password);


        req.processForData(new MAdeptResponseListener() {
            @Override
            public void onResponse(final MAdeptResponse resp) {
                Login.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (resp.isError())// Handle error code
                        {
                            if (resp instanceof MAdeptErrorResponse) {
                                MAdeptErrorResponse error = (MAdeptErrorResponse) resp;
                                MAdeptUtil.showToast(Login.this, error.getErrorMessage());
                            } else {
                                MAdeptUtil.showToast(Login.this, resp.dataModel.getErrorMessage());
                            }

                        } else {
                            final LoginModel data = (LoginModel) resp.dataModel;
                            if (data != null) {
                                MAdeptPrefs.getInstance(Login.this).loadPrefs();
                                MAdeptPrefs.getInstance(Login.this).addPrefs(Constants.LCO, data.lco);
                                MAdeptPrefs.getInstance(Login.this).addPrefs(Constants.UNIQUE_ID, data.uniqueId);
                                MAdeptPrefs.getInstance(Login.this).addPrefs(Constants.EMAIL, data.email);
                                MAdeptPrefs.getInstance(Login.this).addPrefs(Constants.PREFS_IS_LOGIN, "true");

                                CableUncleApplication.getInstance().areaList = data.modelarrlist;
                                Intent i = new Intent(Login.this, Dashboard.class);
                                startActivity(i);
                                finish();
                            }
                        }
                    }
                });

            }

        });


    }

    public void forgotPassword(View v) {

        new EditTextDialog(Login.this, "Enter Email", new String[]{"OK"}, new DialogButtonListener() {
            @Override
            public void onButtonClicked(String text) {
                launchpasswordchange(text);
            }
        }).show();

    }


    public void launchpasswordchange(String text) {
        MAdeptRequest req = new MAdeptRequest(Constants.FORGET_PASS_REQUEST, Login.this, Constants.FORGET_PASS_URL, MAdeptRequest.METHOD_POST);
        req.setJSONParser(JSONParser.getInstance());
        req.addParam("email", text);

        req.processForData(new MAdeptResponseListener() {
            @Override
            public void onResponse(final MAdeptResponse resp) {
                Login.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (resp.isError())// Handle error code
                        {
                            if (resp instanceof MAdeptErrorResponse) {
                                MAdeptErrorResponse error = (MAdeptErrorResponse) resp;
                                MAdeptUtil.showToast(Login.this, error.getErrorMessage());
                            } else {
                                MAdeptUtil.showToast(Login.this, resp.dataModel.getErrorMessage());
                            }

                        } else {
                            final LoginModel data = (LoginModel) resp.dataModel;
                            if (data != null) {
                                Util.showToast(Login.this, "Login Successful");
                                Intent i = new Intent(Login.this, Dashboard.class);
                                startActivity(i);

                            }
                        }
                    }
                });

            }

        });
    }


    public String cutStringsecond(String string) {
        String finalString = "";
        if (string.length() > 30 && string.length() < 60) {
            finalString = string.substring(0, 30) + "\n" + string.substring(30, string.length());
        } else if (string.length() > 60 && string.length() < 90) {
            finalString = string.substring(0, 30) + "\n" + string.substring(30, 60) + "\n" + string.substring(60, string.length());
        } else if (string.length() > 90 && string.length() < 120) {
            finalString = string.substring(0, 30) + "\n" + string.substring(30, 60) + "\n" + string.substring(60, 90) + "\n" + string.substring(90, string.length());
        } else {
            return string;
        }
        return finalString;
    }


    public void printBill(View v) {
        PayNowModel model = new PayNowModel();
        model.customerName = "";
        model.subscriberName = "Dewansh kaushik";
        model.noOfTv = "6";
        String lcoName = "Lco Name: " + "my name is dewansh kaushik and again i am saying my name is dewansh kaushik";
        String finalLcoName = cutStringsecond(lcoName);
        Log.e("finalLcoName", finalLcoName);
        String customerName = "Customer Name: " + model.customerName;
        String finalCustomerName = cutStringsecond(customerName);
        Log.e("finalCustomerName ", finalCustomerName);
        String subscriberName = "Subscriber Id: " + model.subscriberName;
        String finalSubscriberName = cutStringsecond(subscriberName);
        Log.e("finalLcoName", finalSubscriberName);
        String noOfTv = "No of Tv: " + model.noOfTv;
        String finalNoOfTv = cutStringsecond(noOfTv);
        Log.e("finalLcoName", finalNoOfTv);
        String mobileNumber = "Mobile Number: " + model.phone;
        String finalMobileNumber = cutStringsecond(mobileNumber);
        Log.e("finalLcoName", finalMobileNumber);
        String balance = "Balance: " + model.balance;
        String finalBalance = cutStringsecond(balance);
        Log.e("finalLcoName", finalBalance);
        String basics = "Basics: " + model.basic;
        String finalBasics = cutStringsecond(basics);
        Log.e("finalLcoName", finalBasics);
    }
}
