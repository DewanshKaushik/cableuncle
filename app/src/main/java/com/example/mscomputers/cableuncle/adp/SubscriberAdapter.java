package com.example.mscomputers.cableuncle.adp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mscomputers.cableuncle.R;
import com.example.mscomputers.cableuncle.model.SubscriberModel;

import java.util.ArrayList;

/**
 * Created by MS Computers on 12/24/2017.
 */
public class SubscriberAdapter extends ArrayAdapter<SubscriberModel> {


    Context context;
    ArrayList<SubscriberModel> list;


    public SubscriberAdapter(Context context, ArrayList<SubscriberModel> list) {
        super(context, R.layout.subscriber_row, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layout = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layout.inflate(R.layout.subscriber_row, parent, false);
        SubscriberModel model = list.get(position);
        TextView receiptNumber = (TextView) v.findViewById(R.id.receiptNumber);
        TextView paymentMode= (TextView) v.findViewById(R.id.paymentMode);
        TextView paymentDate= (TextView) v.findViewById(R.id.paymentDate);
        TextView amount= (TextView) v.findViewById(R.id.amount);

        receiptNumber.setText(model.receiptNumber);
        paymentMode.setText(model.paymentMode);
        paymentDate.setText(model.paymentDate);
        amount.setText(model.amount);

        return v;

    }

}
