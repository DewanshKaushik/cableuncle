package com.example.mscomputers.cableuncle;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mscomputers.cableuncle.util.Constants;
import com.madept.core.activity.MAdeptActivity;
import com.madept.core.prefs.MAdeptPrefs;

public class Splash extends MAdeptActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              /*  Intent i = new Intent(Splash.this, Login.class);
                startActivity(i);*/
                String isLogin = MAdeptPrefs.getInstance(Splash.this).getPrefs(Constants.PREFS_IS_LOGIN);
                if (isLogin != null && isLogin.equalsIgnoreCase("true")) {
                    Intent i = new Intent(Splash.this, Dashboard.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(Splash.this, Login.class);
                    startActivity(i);
                }
                finish();

            }
        }, 500);
    }


}
