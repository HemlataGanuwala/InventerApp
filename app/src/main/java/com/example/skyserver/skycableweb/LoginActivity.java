package com.example.skyserver.skycableweb;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    EditText editTextuser,editTextpass;
    Button buttonlogin;
    String path,user,pass,user1,pass1,cmonth,cyear,spincomp,spincompany,Response;
    ServiceHandler shh;
    int flag=1;
    private DrawerLayout drawerLayout;
    private ProgressDialog progress;
    private CheckBox mcheck;
    private SharedPreferences preferences;
    private static final String PREFS_NAME = "PrefsFile";
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        path = globalVariable.getconstr();

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

       // spinner = (Spinner) findViewById(R.id.spincompany);
//        List<String> list = new ArrayList<String>();
//        list.add("1");
//        list.add("2");
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, list);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(dataAdapter);

        editTextuser = (EditText)findViewById(R.id.etuser);
        editTextpass = (EditText)findViewById(R.id.etpass);
        buttonlogin = (Button) findViewById(R.id.btnlogin);
        mcheck = (CheckBox)findViewById(R.id.chkrememberme);

        Currmont();
        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLogin();
            }
        });

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        isInternetOn();

        getPreferencedata();
    }

    public void isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            // if connected with internet
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    LoginActivity.this.startActivity(new Intent(LoginActivity.this,LoginActivity.class));
//                    LoginActivity.this.finish();
//                }
//            },2000);// 4000 =4 seconds

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            Toast.makeText(this, "Internet Connection Not Connected ", Toast.LENGTH_LONG).show();



        }

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbarmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.listdate:
                Intent intent = new Intent(LoginActivity.this,DateListActivity.class);
                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void popupMessage()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void getPreferencedata()
    {
        SharedPreferences sp = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        if (sp.contains("pref_name"))
        {
            String u = sp.getString("pref_name","not found");
            editTextuser.setText(u.toString());
        }
        if (sp.contains("pref_pass"))
        {
            String p = sp.getString("pref_pass","not found");
            editTextpass.setText(p.toString());
        }
        if (sp.contains("pref_check"))
        {
            Boolean c = sp.getBoolean("pref_check",false);
            mcheck.setChecked(c);
        }
    }

    public void getLogin()
    {
        user = editTextuser.getText().toString().toUpperCase();
        pass = editTextpass.getText().toString();
        spincomp = spinner.getSelectedItem().toString();

        new getloginData().execute();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent = null;
        if(item.getItemId() == R.id.nav_collection)
        {
            intent = new Intent(this,DateListActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.nav_about)
        {
            intent = new Intent(this,AboutUsActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.nav_logout)
        {
            SharedPreferences SM = getSharedPreferences("userrecord", 0);
            SharedPreferences.Editor edit = SM.edit();
            edit.putBoolean("userlogin", false);
            edit.commit();
            popupMessage();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



    class getloginData extends AsyncTask<Void, Void, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress=new ProgressDialog(LoginActivity.this);
            progress.setMessage("Loading...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            shh = new ServiceHandler();
            String url = path + "Registration/AgentLogin";
            Log.d("Url: ", "> " + url);

            try{
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("AgentName",user));
                params2.add(new BasicNameValuePair("Password",pass));
//                params2.add(new BasicNameValuePair("CompanyId",spincomp));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST , params2);

                if (jsonStr != null) {
                    JSONObject c1 = new JSONObject(jsonStr);
                    JSONArray classArray = c1.getJSONArray("Response");
                    //JSONArray jsonarry = new JSONArray(jsonStr);
                    Response = (c1.getString("Response"));
                    if (Response != null)
                    {
                        for (int i = 0; i < classArray.length(); i++) {
                            JSONObject a1 = classArray.getJSONObject(i);
                            user1 = a1.getString("AgentName");
                            pass1 = a1.getString("Password");
                        }
                        if(classArray.length() == 0)
                        {
                            flag = 0;
                        }
                        else
                        {
                            flag = 1;
                            if (mcheck.isChecked())
                            {
                                Boolean boolIscheck = mcheck.isChecked();
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("pref_name",editTextuser.getText().toString());
                                editor.putString("pref_pass",editTextpass.getText().toString());
                                editor.putBoolean("pref_check",boolIscheck);
                                editor.apply();
                            }
                            else
                            {
                                preferences.edit().clear().apply();
                            }
                        }
                    }

                    else
                    {
                        Toast.makeText(getBaseContext(), "Invalid AgentName Or Password", Toast.LENGTH_LONG).show();
                    }


                }
                else
                {
                    //Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progress.dismiss();

            if (flag == 1)
            {
                Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("a1",user);
                intent.putExtra("a2",cmonth);
                intent.putExtra("a3",cyear);
                intent.putExtra("a4",spincomp);
                startActivity(intent);
                //finish();
            }
            else {
                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
            }
        }
    }


    public void Currmont()
    {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        //for everyone
        if (month == 12) {
            cmonth = "DEC";
            cyear = String.valueOf(year);
        }
        if (month == 1) {
            cmonth = "JAN";
            cyear = String.valueOf(year);
        }
        if (month == 2) {
            cmonth = "FEB";
            cyear = String.valueOf(year);
        }
        if (month == 3) {
            cmonth = "MAR";
            cyear = String.valueOf(year);
        }
        if (month == 4) {
            cmonth = "APR";
            cyear = String.valueOf(year);
        }
        if (month == 5) {
            cmonth = "MAY";
            cyear = String.valueOf(year);
        }
        if (month == 6) {
            cmonth = "JUN";
            cyear = String.valueOf(year);
        }
        if (month == 7) {
            cmonth = "JUL";
            cyear = String.valueOf(year);
        }
        if (month == 8) {
            cmonth = "AUG";
            cyear = String.valueOf(year);
        }
        if (month == 9) {
            cmonth = "SEP";
            cyear = String.valueOf(year);
        }
        if (month == 10) {
            cmonth = "OCT";
            cyear = String.valueOf(year);
        }
        if (month == 11) {
            cmonth = "NOV";
            cyear = String.valueOf(year);
        }

        //For Rokade

//        if (month == 11) {
//            cmonth = "DEC";
//            cyear = String.valueOf(year);
//        }
//        if (month == 0) {
//            cmonth = "JAN";
//            cyear = String.valueOf(year);
//        }
//        if (month == 1) {
//            cmonth = "FEB";
//            cyear = String.valueOf(year);
//        }
//        if (month == 2) {
//            cmonth = "MAR";
//            cyear = String.valueOf(year);
//        }
//        if (month == 3) {
//            cmonth = "APR";
//            cyear = String.valueOf(year);
//        }
//        if (month == 4) {
//            cmonth = "MAY";
//            cyear = String.valueOf(year);
//        }
//        if (month == 5) {
//            cmonth = "JUN";
//            cyear = String.valueOf(year);
//        }
//        if (month == 6) {
//            cmonth = "JUL";
//            cyear = String.valueOf(year);
//        }
//        if (month == 7) {
//            cmonth = "AUG";
//            cyear = String.valueOf(year);
//        }
//        if (month == 8) {
//            cmonth = "SEP";
//            cyear = String.valueOf(year);
//        }
//        if (month == 9) {
//            cmonth = "OCT";
//            cyear = String.valueOf(year);
//        }
//        if (month == 10) {
//            cmonth = "NOV";
//            cyear = String.valueOf(year);
//        }

    }
}
