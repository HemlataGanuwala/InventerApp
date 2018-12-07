package com.example.skyserver.skycableweb;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skyserver.skycableweb.adapter.ListDatewiseAdapter;
import com.example.skyserver.skycableweb.model.DatewiseProduct;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DateListActivity extends AppCompatActivity {
    ServiceHandler shh;
    String custnm,setupbox,billno,payableamt,mob,paid,bal,paydt,paydt1,adcust,paidamt1,agentnm;
    String paydate;
    TextView textViewdate,textViewtotcust,textViewtotamt;
    Button buttonshow;
    String path,CDay,CMonth,cmonth,cyear;
    View view;
    int id,year,month,day;
    private DatePickerDialog dialog;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    List<DatewiseProduct>mPlanetlist = new ArrayList<DatewiseProduct>();
    ListDatewiseAdapter adapter;
    ListView listView;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_list);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        path = globalVariable.getconstr();

        textViewdate = findViewById(R.id.tvdatewise);
        textViewtotcust = findViewById(R.id.tvtotcust);
        textViewtotamt = findViewById(R.id.tvtotamtday);
        buttonshow =findViewById(R.id.btnshow);
        listView = findViewById(R.id.lvdatewiselist);
        Submitdata();
        Display();

        textViewdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(DateListActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,dateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                if(Integer.toString(day).length() == 1)
                {
                    CDay = "0" + day;
                }
                else
                {
                    CDay =  String.valueOf(day);
                }
                if(Integer.toString(month).length() == 1)
                {
                    CMonth = "0" + month;
                }
                else
                {
                    CMonth =  String.valueOf(month);
                }
//                currdate = CDay + "/" + CMonth + "/" + year;
//                textViewcdate.setText(currdate);
                paydate = CDay + "/" + CMonth + "/" + year;
                textViewdate.setText(paydate);
            }
        };

        buttonshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlanetlist.clear();
                new getDatewiselistData().execute();
            }
        });
    }

    public void Submitdata() {

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);

        if (month == 0) {
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

    }

    public void Display()
    {
        Intent inn= getIntent();
        Bundle bg1 = inn.getExtras();
        if(bg1!=null) {
            agentnm = (String) bg1.get("a1");
        }

    }

    class getDatewiselistData extends AsyncTask<Void, Void, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress=new ProgressDialog(DateListActivity.this);
            progress.setMessage("Loading...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            shh = new ServiceHandler();
            String url = path + "Registration/DailyCollectionReportAgent";
            Log.d("Url: ", "> " + url);

            try{
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("PaymentDate1",paydate));
                params2.add(new BasicNameValuePair("PaymentDate2",paydate));
                params2.add(new BasicNameValuePair("Bmonth",cmonth));
                params2.add(new BasicNameValuePair("Byear",cyear));
                params2.add(new BasicNameValuePair("AgentName",agentnm));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST , params2);

                if (jsonStr != null) {
                    JSONObject c1 = new JSONObject(jsonStr);
                    JSONArray classArray = c1.getJSONArray("Response");
                    //JSONArray jsonarry = new JSONArray(jsonStr);
                    for (int i = 0; i < classArray.length(); i++) {
                        JSONObject a1 = classArray.getJSONObject(i);

                        custnm = a1.getString("CustName");
                        setupbox = a1.getString("SetupBox_Details");
                        billno = a1.getString("Bid");
                        payableamt = a1.getString("OldBal");
                        paid = a1.getString("PaymentAmount1");
                        paydt = a1.getString("PaymentDate1");
                        paydt1 = a1.getString("PaymentDate2");
                        paidamt1 = a1.getString("PaymentAmount2");
                        // montch = a1.getString("RegUsername");

                        DatewiseProduct product = new DatewiseProduct(custnm,setupbox,billno,paid,paydt);
                        mPlanetlist.add(product);

                    }


                }
                else
                {
                    Toast.makeText(DateListActivity.this, "Data Not Available", Toast.LENGTH_LONG).show();
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

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter = new ListDatewiseAdapter(DateListActivity.this,mPlanetlist);
                    listView.setAdapter(adapter);
                }
            });

            int ti = adapter.getCount();
            double totalqty = 0;
            double totalamt = 0;
            for (int i=0;i < ti;i++)
            {
                double amtlist = Double.parseDouble(mPlanetlist.get(i).getDPaidAmt());
                totalamt = (totalamt + amtlist);
            }
            textViewtotcust.setText(Integer.toString(ti));
            textViewtotamt.setText(Double.toString(totalamt));

        }
    }
}
