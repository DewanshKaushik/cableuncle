package com.example.mscomputers.cableuncle.adp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.example.mscomputers.cableuncle.PayNow;
import com.example.mscomputers.cableuncle.R;
import com.example.mscomputers.cableuncle.model.PaymentModel;

import java.util.ArrayList;


public class PaymentAdapter extends ArrayAdapter<PaymentModel> {

    Activity context;
    ArrayList<PaymentModel> list;


    public PaymentAdapter(Activity context, ArrayList<PaymentModel> list) {
        super(context, R.layout.complaint_list_row, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layout = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layout.inflate(R.layout.payment_list_row, parent, false);
        final PaymentModel model = list.get(position);
        TextView subscriberId = (TextView) v.findViewById(R.id.subscriberId);
        TextView subscriberName = (TextView) v.findViewById(R.id.subscriberName);


        subscriberId.setText(model.compId);
        subscriberName.setText(model.name);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PayNow.class);
                i.putExtra("subscriberId", model.compId);
                context.startActivity(i);
                context.finish();
            }
        });

        return v;

    }

}
