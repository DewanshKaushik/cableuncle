package com.example.mscomputers.cableuncle.view;

/**
 * Created by User on 9/7/2016.
 */

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mscomputers.cableuncle.R;
import com.example.mscomputers.cableuncle.model.PayNowModel;


public class ConfirmDialog extends Dialog {
    DialogButtonListener listener;
    String message, alert;
    String[] buttonNames;
    TextView messageTextview, alertText;
    TextView subscriberId, subscriberName, mobileNumber, amount,paymentMode;
    ImageView closeDialogButton;
    Button[] buttons = new Button[2];
    Context ctx;
    PayNowModel model;


    public ConfirmDialog(Context context, String alert, String message, String[] buttonNames,
                         DialogButtonListener listener, PayNowModel model) {

        super(context);
        this.ctx = context;
        this.listener = listener;
        this.message = message;
        this.alert = alert;
        this.buttonNames = buttonNames;
        this.model = model;
        this.setCancelable(false);
        getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.confirm_payment_dialog);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.CENTER_HORIZONTAL);

        buttons[0] = (Button) findViewById(R.id.firstButton);
        buttons[1] = (Button) findViewById(R.id.secondButton);

        subscriberId = (TextView) findViewById(R.id.subscriberId);
        subscriberName = (TextView) findViewById(R.id.subscriberName);
        mobileNumber = (TextView) findViewById(R.id.mobileNumber);
        amount = (TextView) findViewById(R.id.amount);

        paymentMode = (TextView) findViewById(R.id.paymentMode);

        subscriberId.setText(model.subscriberName);
        subscriberName.setText(model.customerName);
        mobileNumber.setText(model.phone);
        amount.setText(model.amount);
        paymentMode.setText(model.payment_mode);

       /* messageTextview = (TextView) findViewById(R.id.messageTextview);
        alertText = (TextView) findViewById(R.id.alertText);

        messageTextview.setText(message);
        alertText.setText(alert);*/
        for (int i = 0; i < buttonNames.length; i++) {
            final int j = i;
            buttons[i].setVisibility(View.VISIBLE);
            buttons[i].setText(buttonNames[i]);

            buttons[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dismiss();
                    if (listener != null)
                        listener.onButtonClicked(buttonNames[j]);

                }
            });

        }
    }

}

