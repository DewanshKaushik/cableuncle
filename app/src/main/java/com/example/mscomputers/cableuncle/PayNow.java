package com.example.mscomputers.cableuncle;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mscomputers.cableuncle.Prefs.Prefs;
import com.example.mscomputers.cableuncle.Printing.Maestro;
import com.example.mscomputers.cableuncle.aem.PrintActivity;
import com.example.mscomputers.cableuncle.model.LoginModel;
import com.example.mscomputers.cableuncle.model.PayNowModel;
import com.example.mscomputers.cableuncle.parser.JSONParser;
import com.example.mscomputers.cableuncle.util.Constants;
import com.example.mscomputers.cableuncle.util.Util;
import com.example.mscomputers.cableuncle.view.ConfirmDialog;
import com.example.mscomputers.cableuncle.view.CustomDialog;
import com.example.mscomputers.cableuncle.view.DialogButtonListener;
import com.example.mscomputers.cableuncle.view.EditTextDialog;
import com.example.mscomputers.cableuncle.view.EnterPaymentDialog;
import com.madept.core.activity.MAdeptActivity;
import com.madept.core.net.MAdeptErrorResponse;
import com.madept.core.net.MAdeptRequest;
import com.madept.core.net.MAdeptResponse;
import com.madept.core.net.MAdeptResponseListener;
import com.madept.core.prefs.MAdeptPrefs;
import com.madept.core.util.MAdeptUtil;

/**
 * Created by MS Computers on 1/7/2018.
 */
public class PayNow extends MAdeptActivity {
    TextView lcoName, customerName, subscriberId, noOfTv, mobileNumber, balanceAmount, basics, total;
    String subscriberIdd;
    PayNowModel payNowModelData;
    EditText remark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_now);

        lcoName = (TextView) findViewById(R.id.lcoName);

        customerName = (TextView) findViewById(R.id.customerName);
        subscriberId = (TextView) findViewById(R.id.subscriberId);
        noOfTv = (TextView) findViewById(R.id.noOfTv);
        mobileNumber = (TextView) findViewById(R.id.mobileNumber);
        balanceAmount = (TextView) findViewById(R.id.balanceAmount);
        basics = (TextView) findViewById(R.id.basics);
        total = (TextView) findViewById(R.id.total);
        remark = (EditText) findViewById(R.id.remark);

        subscriberIdd = getIntent().getStringExtra("subscriberId");


        launchPayNowRequest(subscriberIdd);
    }

    public void launchPayNowRequest(String subscriberId) {
        MAdeptPrefs.getInstance(PayNow.this).loadPrefs();
        MAdeptRequest req = new MAdeptRequest(Constants.PAYNOW_REQUEST, this, Constants.PAYNOW_URL, MAdeptRequest.METHOD_POST);
        req.setJSONParser(JSONParser.getInstance());
        req.addParam("lco", MAdeptPrefs.getInstance(PayNow.this).getPrefs(Constants.LCO));
        req.addParam("dev", subscriberId);

        req.processForData(new MAdeptResponseListener() {
            @Override
            public void onResponse(final MAdeptResponse resp) {
                PayNow.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (resp.isError())// Handle error code
                        {
                            if (resp instanceof MAdeptErrorResponse) {
                                MAdeptErrorResponse error = (MAdeptErrorResponse) resp;
                                MAdeptUtil.showToast(PayNow.this, error.getErrorMessage());
                            } else {
                                MAdeptUtil.showToast(PayNow.this, resp.dataModel.getErrorMessage());
                            }

                        } else {
                            payNowModelData = (PayNowModel) resp.dataModel;
                            if (payNowModelData != null) {
                                setData(payNowModelData);
                            }
                        }
                    }
                });

            }

        });

    }

    public void setData(PayNowModel model) {
        lcoName.setText("Lco Name: " + model.lcoName);
        customerName.setText(model.customerName);
        subscriberId.setText(model.subscriberName);
        noOfTv.setText(model.noOfTv);
        mobileNumber.setText(model.phone);
        balanceAmount.setText(model.balance);
        basics.setText(model.basic + "");
        total.setText(model.total + "");


    }

    public void changePhone(View v) {
        new EditTextDialog(PayNow.this, "Enter Number", new String[]{"OK"}, new DialogButtonListener() {
            @Override
            public void onButtonClicked(String text) {
                launchPhoneNumberUpdateRequest(text);
            }
        }).show();

    }

    public void launchPhoneNumberUpdateRequest(final String text) {
        MAdeptPrefs.getInstance(PayNow.this).loadPrefs();

        MAdeptRequest req = new MAdeptRequest(Constants.UPDATE_PHONE_RQUEST, this, Constants.UPDATE_PHONE_URL, MAdeptRequest.METHOD_POST);
        req.setJSONParser(JSONParser.getInstance());
        req.addParam("lco", MAdeptPrefs.getInstance(PayNow.this).getPrefs(Constants.LCO));
        req.addParam("dev_id", subscriberIdd);
        req.addParam("phone", text);

        req.processForData(new MAdeptResponseListener() {
            @Override
            public void onResponse(final MAdeptResponse resp) {
                PayNow.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (resp.isError())// Handle error code
                        {
                            if (resp instanceof MAdeptErrorResponse) {
                                MAdeptErrorResponse error = (MAdeptErrorResponse) resp;
                                MAdeptUtil.showToast(PayNow.this, error.getErrorMessage());
                            } else {
                                MAdeptUtil.showToast(PayNow.this, resp.dataModel.getErrorMessage());
                            }

                        } else {
                            final LoginModel data = (LoginModel) resp.dataModel;
                            if (data != null) {
                                mobileNumber.setText(text);
                                payNowModelData.phone = text;
                            }
                        }
                    }
                });

            }

        });


    }

    public void payNow(View v) {
        new EnterPaymentDialog(PayNow.this, "Enter Payment", new String[]{"OK", "Cancel"}, new DialogButtonListener() {

            @Override
            public void onButtonClicked(String text) {

                payNowModelData.amount = text;
                new ConfirmDialog(PayNow.this, "Confirm Payment", "Message", new String[]{"ok", "Cancel"}, new DialogButtonListener() {

                    @Override
                    public void onButtonClicked(String text) {
                        if (text.equalsIgnoreCase("ok")) {
                            submitPayment(payNowModelData.amount);
                        }
                    }
                }, payNowModelData).show();

            }
        }).show();
    }

    public void submitPayment(String amount) {
        MAdeptPrefs.getInstance(PayNow.this).loadPrefs();

        MAdeptRequest req = new MAdeptRequest(Constants.SUBMIT_PAYMENT, PayNow.this, Constants.SUBMIT_PAYMENT_URL, MAdeptRequest.METHOD_POST);
        req.setJSONParser(JSONParser.getInstance());
        req.addParam("lco", MAdeptPrefs.getInstance(PayNow.this).getPrefs(Constants.LCO));
        req.addParam("dev_id", subscriberIdd);
        req.addParam("payment", amount);
        req.addParam("get_id", MAdeptPrefs.getInstance(PayNow.this).getPrefs(Constants.UNIQUE_ID));
        req.addParam("total_bill", payNowModelData.total + "");
        req.addParam("pay_mode", "cash");
        req.addParam("cheque_no", "null");
        req.addParam("ifsc_code", "null");
        req.addParam("dis_amount", "null");
        req.addParam("other", "null");
        req.addParam("discount", "null");
        req.addParam("due_date", Util.getDateandTime());

        String remarkString = remark.getEditableText().toString();
        if (remarkString.equalsIgnoreCase("")) {
            remarkString = "No remark";
        }
        req.addParam("remark", remarkString);
        req.addParam("account_no", "null");

        req.processForData(new MAdeptResponseListener() {
            @Override
            public void onResponse(final MAdeptResponse resp) {
                PayNow.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (resp.isError())// Handle error code
                        {
                            if (resp instanceof MAdeptErrorResponse) {
                                MAdeptErrorResponse error = (MAdeptErrorResponse) resp;
                                MAdeptUtil.showToast(PayNow.this, error.getErrorMessage());
                            } else {
                                MAdeptUtil.showToast(PayNow.this, resp.dataModel.getErrorMessage());
                            }

                        } else {
                            final PayNowModel data = (PayNowModel) resp.dataModel;
                            if (data != null) {
                                payNowModelData = data;
                                new CustomDialog(PayNow.this, "Alert", "Do you want to print slip?", new String[]{"OK", "Cancel"}, new DialogButtonListener() {
                                    @Override
                                    public void onButtonClicked(String text) {
                                        if (text.equalsIgnoreCase("OK")) {
                                            new CustomDialog(PayNow.this, "Printing", "Select Machine", new String[]{"AEM", "MAESTRO"}, new DialogButtonListener() {
                                                @Override
                                                public void onButtonClicked(String text) {
                                                    if (text.equalsIgnoreCase("AEM")) {

                                                        Intent intent = new Intent(PayNow.this, PrintActivity.class);
                                                        intent.putExtra("payNowModelData", payNowModelData);
                                                        startActivity(intent);
                                                    } else if (text.equalsIgnoreCase("MAESTRO")) {
                                                        Intent intent = new Intent(PayNow.this, Maestro.class);
                                                        intent.putExtra("payNowModelData", payNowModelData);
                                                        startActivity(intent);
                                                    }
                                                }
                                            }).show();
                                        }else{
                                            finish();
                                        }
                                    }
                                }).show();

                            }
                        }
                    }
                });

            }

        });


    }


    public void sendSMS(String phoneNo, String msg) {
        try {
            String subject = msg;
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, subject, null, null);
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public void eMail(String mailCustId, String text) {
        String msg = text;
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{mailCustId, Prefs.getInstance().email});
        //d i.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.mail_subject));
        i.putExtra(Intent.EXTRA_TEXT, msg);
        startActivity(i);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(PayNow.this, Payment.class);
        startActivity(i);
        finish();
    }
}
