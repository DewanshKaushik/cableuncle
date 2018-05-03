package com.example.mscomputers.cableuncle.adp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.mscomputers.cableuncle.LastTransactions;
import com.example.mscomputers.cableuncle.R;
import com.example.mscomputers.cableuncle.model.LastTransactionModel;
import com.example.mscomputers.cableuncle.model.UserModel;

import java.util.ArrayList;

/**
 * Created by MS Computers on 12/24/2017.
 */
public class UserAdapter extends ArrayAdapter<LastTransactionModel> {

    Context context;
    ArrayList<LastTransactionModel> list;


    public UserAdapter(Context context, ArrayList<LastTransactionModel> list) {
        super(context, R.layout.last_transaction_row, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layout = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layout.inflate(R.layout.last_transaction_row, parent, false);
        LastTransactionModel model = list.get(position);
        TextView receiptNumber = (TextView) v.findViewById(R.id.receiptNumber);
        TextView subscriberId = (TextView) v.findViewById(R.id.subscriberId);
        TextView name = (TextView) v.findViewById(R.id.name);
        TextView date = (TextView) v.findViewById(R.id.date);
        TextView time = (TextView) v.findViewById(R.id.time);
        TextView paymentMode = (TextView) v.findViewById(R.id.paymentMode);
        TextView paidAmount = (TextView) v.findViewById(R.id.paidAmount);
        TextView balanceAmount = (TextView) v.findViewById(R.id.balanceAmount);
        Button printButton = (Button) v.findViewById(R.id.printButton);


        receiptNumber.setText(model.invoice);
        subscriberId.setText(model.comp_id);
        name.setText(model.customer_name);
        date.setText(model.date);
        time.setText(model.date);
        paymentMode.setText(model.pay_mode);
        paidAmount.setText(model.total_amount);
        balanceAmount.setText(model.panding_amount);

        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LastTransactions)context).printBill(position);
            }
        });


        return v;

    }
}
