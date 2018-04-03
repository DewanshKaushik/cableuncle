package com.example.mscomputers.cableuncle.adp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mscomputers.cableuncle.R;
import com.example.mscomputers.cableuncle.model.ResolvedModel;
import com.madept.core.model.MAdeptModel;

import java.util.ArrayList;

/**
 * Created by MS Computers on 12/23/2017.
 */
public class ResolvedComplaintAdapter extends ArrayAdapter<ResolvedModel> {

    Context context;
    ArrayList<ResolvedModel> list;


    public ResolvedComplaintAdapter(Context context, ArrayList<ResolvedModel> list) {
        super(context, R.layout.complaint_list_row, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layout = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layout.inflate(R.layout.complaint_list_row, parent, false);
        ResolvedModel model = list.get(position);
        TextView complaintNumber = (TextView) v.findViewById(R.id.complaintNumber);

        complaintNumber.setText(model.complaintNumber);
        return v;

    }
}
