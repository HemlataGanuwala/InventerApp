package com.example.skyserver.skycableweb;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        globalVariable.setconstr("http://192.168.0.112:8011/api/");
//        globalVariable.setconstr("http://omi.skyvisioncables.com/api/");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                SplashActivity.this.finish();
            }
        },2000);// 4000 =4 seconds
    }


}
