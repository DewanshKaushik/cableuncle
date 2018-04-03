package com.example.mscomputers.cableuncle.adp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mscomputers.cableuncle.R;
import com.example.mscomputers.cableuncle.model.UserModel;

import java.util.ArrayList;

/**
 * Created by MS Computers on 12/24/2017.
 */
public class UserAdapter extends ArrayAdapter<UserModel> {

    Context context;
    ArrayList<UserModel> list;


    public UserAdapter(Context context, ArrayList<UserModel> list) {
        super(context, R.layout.complaint_list_row, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layout = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layout.inflate(R.layout.last_transaction_row, parent, false);
        UserModel model = list.get(position);
        TextView receiptNumber = (TextView) v.findViewById(R.id.receiptNumber);
        TextView subscriberId = (TextView) v.findViewById(R.id.subscriberId);
        TextView name = (TextView) v.findViewById(R.id.name);
        TextView date = (TextView) v.findViewById(R.id.date);
        TextView time = (TextView) v.findViewById(R.id.time);
        TextView paymentMode = (TextView) v.findViewById(R.id.paymentMode);
        TextView paidAmount = (TextView) v.findViewById(R.id.paidAmount);
        TextView balanceAmount = (TextView) v.findViewById(R.id.balanceAmount);

        subscriberId.setText(model.id);
       // subscriberId.setText(model.subscriberName);

        return v;

    }
}
