package com.example.mscomputers.cableuncle.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mscomputers.cableuncle.R;

/**
 * Created by MS Computers on 3/27/2018.
 */

public class EditTextDialog extends Dialog {

    DialogButtonListener listener;
    String alert;
    //  String[] buttonNames;
    TextView alertText;
    // Button[] buttons = new Button[2];
    Button firstButton;
    Context ctx;
    EditText messageEditText;

    public EditTextDialog(Context context, String alert, String[] buttonNames,
                          DialogButtonListener listener) {

        super(context);
        this.ctx = context;
        this.listener = listener;
        this.alert = alert;
        //  this.buttonNames = buttonNames;
        //  this.setCancelable(false);
        getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // setContentView(R.layout.custom_dialog);
        setContentView(R.layout.edittext_dialog);


        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.CENTER_HORIZONTAL);

        //  buttons[0] = (Button) findViewById(R.id.firstButton);
        firstButton = (Button) findViewById(R.id.firstButton);


        alertText = (TextView) findViewById(R.id.alertText);
        messageEditText = (EditText) findViewById(R.id.messageEditText);


        alertText.setText(alert);
        firstButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String text = messageEditText.getText().toString();
                final String finalString = text;
                if (finalString.equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), "Please enter the value", Toast.LENGTH_SHORT).show();
                    return;
                }
                dismiss();
                if (listener != null)
                    listener.onButtonClicked(finalString);

            }
        });
        /*
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
        *//*

    }*/

    }
}