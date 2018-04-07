package com.example.mscomputers.cableuncle;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    TextView lcoName, customerName, subscriberId, noOfTv, mobileNumber, balanceAmount, basics, total,paidAmount;
    String subscriberIdd;
    PayNowModel payNowModelData;
    LinearLayout bankLayout,chequeLayout;
    EditText remark,addnAmount,bankName,chequeNumber;
    RadioGroup radioGroup;
    int index;
    String paymentMode="";
    RadioButton cash;

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
        addnAmount= (EditText) findViewById(R.id.addnAmount);
        bankName= (EditText) findViewById(R.id.bankName);
        chequeNumber= (EditText) findViewById(R.id.chequeNumber);
        radioGroup= (RadioGroup) findViewById(R.id.radioGroup);

        paidAmount= (TextView) findViewById(R.id.paidAmount);
        bankLayout= (LinearLayout) findViewById(R.id.bankLayout);
        chequeLayout= (LinearLayout) findViewById(R.id.chequeLayout);
        cash= (RadioButton) findViewById(R.id.cash);

        cash.setChecked(true);
        paymentMode="cash";

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(i);
                index = radioGroup.indexOfChild(radioButton);


                if (index == 0) {
                    paymentMode="cash";
                    bankLayout.setVisibility(View.GONE);
                    bankName.setText("");
                    chequeNumber.setText("");
                    chequeLayout.setVisibility(View.GONE);
                   // radioButton.setChecked(false);
                } else if (index == 1) {
                    paymentMode="cheque";
                    bankLayout.setVisibility(View.VISIBLE);
                    chequeLayout.setVisibility(View.VISIBLE);
                    //radioButton.setChecked(false);

                }
            }
        });


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

    String paidAmountString="";
    String bankNameString="";
    String chequeNumberString="";

    public void payNow(View v) {
        if(paymentMode.equalsIgnoreCase("cash")){
            paidAmountString= paidAmount.getEditableText().toString();
            if (paidAmountString.equalsIgnoreCase("")) {
                MAdeptUtil.showToast(PayNow.this,"Please Enter Paid Amount");
                return;
            }

        }else{
            paidAmountString= paidAmount.getEditableText().toString();
            bankNameString= bankName.getEditableText().toString();
            chequeNumberString=chequeNumber.getEditableText().toString();
            if (paidAmountString.equalsIgnoreCase("")) {
                MAdeptUtil.showToast(PayNow.this,"Please Enter Paid Amount");
                return;
            }else if(bankNameString.equalsIgnoreCase("")){
                MAdeptUtil.showToast(PayNow.this,"Please Enter Bank Name");
                return;
            }else if(chequeNumberString.equalsIgnoreCase("")){
                MAdeptUtil.showToast(PayNow.this,"Please Enter Cheque Number");
                return;
            }
        }
        payNowModelData.amount = paidAmountString;

        new ConfirmDialog(PayNow.this, "Confirm Payment", "Message", new String[]{"OK", "Cancel"}, new DialogButtonListener() {

            @Override
            public void onButtonClicked(String text) {
                if (text.equalsIgnoreCase("ok")) {
                    getFields(paidAmountString);
                }
            }
        }, payNowModelData).show();

/*        new EnterPaymentDialog(PayNow.this, "Enter Payment", new String[]{"OK", "Cancel"}, new DialogButtonListener() {

            @Override
            public void onButtonClicked(String text) {

                payNowModelData.amount = text;
                new ConfirmDialog(PayNow.this, "Confirm Payment", "Message", new String[]{"ok", "Cancel"}, new DialogButtonListener() {

                    @Override
                    public void onButtonClicked(String text) {
                        if (text.equalsIgnoreCase("ok")) {
                            getFields();
                        }
                    }
                }, payNowModelData).show();

            }
        }).show();*/
    }

    public void getFields(String paidAmountString){
        String remarkString = remark.getEditableText().toString();
        if (remarkString.equalsIgnoreCase("")) {
            remarkString = "No remark";
        }
        String addnAmountString = addnAmount.getEditableText().toString();
        if (addnAmountString.equalsIgnoreCase("")) {
            addnAmountString = "0";
        }
        String bankNameString = bankName.getEditableText().toString();
        if (bankNameString.equalsIgnoreCase("")) {
            bankNameString = "Not Available";
        }
        String chequeNumberString= chequeNumber.getEditableText().toString();
        if (chequeNumberString.equalsIgnoreCase("")) {
            chequeNumberString = "0";
        }

        submitPayment(remarkString,addnAmountString,bankNameString,chequeNumberString,paidAmountString);

    }

    public void submitPayment(String remarkString,String addnAmountString,String bankNameString,String chequeNumberString,String amount) {
        MAdeptPrefs.getInstance(PayNow.this).loadPrefs();

        MAdeptRequest req = new MAdeptRequest(Constants.SUBMIT_PAYMENT, PayNow.this, Constants.SUBMIT_PAYMENT_URL, MAdeptRequest.METHOD_POST);
        req.setJSONParser(JSONParser.getInstance());
        req.addParam("lco", MAdeptPrefs.getInstance(PayNow.this).getPrefs(Constants.LCO));
        Log.e("paynow",MAdeptPrefs.getInstance(PayNow.this).getPrefs(Constants.LCO));

        req.addParam("dev_id", subscriberIdd);
        Log.e("paynow",subscriberIdd);

        req.addParam("payment", amount);
        Log.e("paynow",amount);

        req.addParam("get_id", MAdeptPrefs.getInstance(PayNow.this).getPrefs(Constants.UNIQUE_ID));
        Log.e("paynow",MAdeptPrefs.getInstance(PayNow.this).getPrefs(Constants.UNIQUE_ID));

        req.addParam("total_bill", payNowModelData.total + "");
        Log.e("paynow",payNowModelData.total + "");

        req.addParam("pay_mode", paymentMode);
        Log.e("paynow",paymentMode);

        req.addParam("cheque_no", chequeNumberString);
        Log.e("paynow",chequeNumberString);

        req.addParam("ifsc_code", bankNameString);
        Log.e("paynow",bankNameString);

        req.addParam("dis_amount", "null");
        Log.e("paynow","null");

        req.addParam("other", addnAmountString);
        Log.e("paynow",addnAmountString);

        req.addParam("discount", "null");
        Log.e("paynow","null");

        req.addParam("due_date", Util.getDateandTime());
        Log.e("paynow",Util.getDateandTime());

        req.addParam("remark", remarkString);
        Log.e("paynow",remarkString);

        req.addParam("account_no", "null");
        Log.e("paynow","null");

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
                                                        finish();
                                                    } else if (text.equalsIgnoreCase("MAESTRO")) {
                                                        Intent intent = new Intent(PayNow.this, Maestro.class);
                                                        intent.putExtra("payNowModelData", payNowModelData);
                                                        startActivity(intent);
                                                        finish();
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

/*
* Rai.abhishek727@gmail.com
Pwd-Lotopo@3393*/
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
