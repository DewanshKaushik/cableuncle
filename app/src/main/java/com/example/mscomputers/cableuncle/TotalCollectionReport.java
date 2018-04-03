package com.example.mscomputers.cableuncle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.madept.core.activity.MAdeptActivity;

/**
 * Created by MS Computers on 12/16/2017.
 */
public class TotalCollectionReport extends MAdeptActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.total_collection_report);
    }

    public void goToNext(View v) {
        Intent i = new Intent(this,LastTransactions.class);
        startActivity(i);
      /*  Intent i = new Intent(this, PrintActivity.class);
        startActivity(i);*/
    }

}
