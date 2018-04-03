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


public class CustomDialog extends Dialog {
    DialogButtonListener listener;
    String message, alert;
    String[] buttonNames;
    TextView messageTextview, alertText;
    ImageView closeDialogButton;
    Button[] buttons = new Button[2];
    Context ctx;

    public CustomDialog(Context context, String alert, String message, String[] buttonNames,
                        DialogButtonListener listener) {

        super(context);
        this.ctx = context;
        this.listener = listener;
        this.message = message;
        this.alert = alert;
        this.buttonNames = buttonNames;
        this.setCancelable(false);
        getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.custom_dialog);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.CENTER_HORIZONTAL);

        buttons[0] = (Button) findViewById(R.id.firstButton);
        buttons[1] = (Button) findViewById(R.id.secondButton);
        messageTextview = (TextView) findViewById(R.id.messageTextview);
        alertText = (TextView) findViewById(R.id.alertText);

        messageTextview.setText(message);
        alertText.setText(alert);
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

