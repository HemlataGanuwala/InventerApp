package com.example.skyserver.skycableweb;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skyserver.skycableweb.model.Product;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgentPayActivity extends AppCompatActivity {
    TextView textViewid, textViewcname, textViewpaidv, textViewbilldate2, textViewmonth, textViewdtto, textViewbillno, textViewbilldt, textViewprebal, textViewbal, textViewcmon, editTextpaidamt1, textViewbilldt1, textViewpaid2, textViewpaid, textViews, textViewpaidamt, textViewmonthchar;
    EditText editTextpaidamt, editTextsetupbox, editTextmobile;
    Button buttonpay, buttonpaydetails, buttonbackup, buttonplus, buttonpaycall;
    Integer paid1, paid2, payable;
    double prebal;
    private List<Product> mProductList;
    ServiceHandler shh;
    String path, custid, custname, setup, bno, cmonth, payableamt, mobile, dtto, monthch;
    int Status,pay1, pay2;
    String pamt, paidamt1, paidamt2, paydt1, paydt2, balance, bal;
    String dtr1;
    String dtr2;
    String msgs,mobileno,setupboxno,settime,settime1,companyid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_pay);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        path = globalVariable.getconstr();

        textViewid = (TextView) findViewById(R.id.tvcustidpay);
        textViewcname = (TextView) findViewById(R.id.tvname);
        editTextsetupbox = (EditText) findViewById(R.id.tvsetup);
        editTextmobile = (EditText) findViewById(R.id.tvmob);
        textViewmonth = (TextView) findViewById(R.id.tvcmonth);
        // textViewdtto = (TextView)findViewById(R.id.tvtodt);
        textViewbillno = (TextView) findViewById(R.id.tvopbillno);
        textViewbilldt = (TextView) findViewById(R.id.tvopbilldate);
        textViewbilldt1 = (TextView) findViewById(R.id.tvopbilldate2);
        textViewprebal = (TextView) findViewById(R.id.tvopprebal);
        editTextpaidamt = (EditText) findViewById(R.id.tvoppaidamt);
        textViewbal = (TextView) findViewById(R.id.tvopbalamt);
        textViewcmon = (TextView) findViewById(R.id.tvcmonth);
        buttonpay = (Button) findViewById(R.id.btnpay);
        // buttonbackup = (Button)findViewById(R.id.btnbackup);
        textViewpaid2 = (TextView) findViewById(R.id.tvpaidamt2);
        buttonpaydetails = (Button) findViewById(R.id.btncall);
        buttonplus = (Button) findViewById(R.id.btnupdate);
        buttonpaycall = (Button) findViewById(R.id.btnpaycall);
        textViewpaid = (TextView) findViewById(R.id.tvpaidamt);
        editTextpaidamt1 = (EditText) findViewById(R.id.tvoppaidamt2);
        textViewpaidv = (TextView) findViewById(R.id.tvpaidd);
        textViews = (TextView) findViewById(R.id.tvs);
        textViewbilldate2 = (TextView) findViewById(R.id.tvbilldate2);

        Display();

        Date d = new Date();
        CharSequence g = DateFormat.format("dd/MM/yyyy", d.getTime());
        textViewbilldt.setText(g);

//        Date dt = new Date();
//        CharSequence t = DateFormat.format("hh:mm:ss a", d.getTime());
//        settime = t.toString();

//        settime1 = t.toString();

        new FetchactList().execute();

        editTextpaidamt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                prebal = Double.parseDouble(textViewprebal.getText().toString());

                if (editTextpaidamt1.length() == 0) {
                    paid2 = 0;

                } else {
                    paid2 = Integer.parseInt(editTextpaidamt1.getText().toString());
                    Date d = new Date();
                    CharSequence t = DateFormat.format("hh:mm:ss a", d.getTime());
                    settime = t.toString();
                }
                if (s.toString().equals("")) {
                    return;
                } else {
                    paid1 = Integer.parseInt(s.toString());
                }
                paid1 = Integer.parseInt(s.toString());

                calc((int) prebal, paid1, paid2);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        buttonplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateMobileSetupData();
            }
        });

        buttonpaycall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call To Customer
                mobile = editTextmobile.getText().toString();
                //mobile = "8983141186";
                if (mobile.length() != 0) {
                    // TODO Auto-generated method stub
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + mobile));
                    if (ActivityCompat.checkSelfPermission(AgentPayActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(callIntent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Enter a valid emergency contact",Toast.LENGTH_SHORT).show();
                }
            }
        });

        editTextpaidamt1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                prebal = Double.parseDouble(textViewprebal.getText().toString());

                if(editTextpaidamt.length()==0)
                {
                    paid1 = 0;

                }
                else
                {
                    paid1 = Integer.parseInt(editTextpaidamt.getText().toString());
                    Date d = new Date();
                    CharSequence t = DateFormat.format("hh:mm:ss a", d.getTime());
                    settime1 = t.toString();
                }
                if (s.toString().equals(""))
                {
                    return;
                }
                else
                {
                    paid2 = Integer.parseInt(s.toString());
                }
                paid2 = Integer.parseInt(s.toString());
                calc((int) prebal,paid1,paid2);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        buttonpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaidData();
            }
        });

        buttonpaydetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AgentPayActivity.this,CustListActivity.class);
                intent.putExtra("a1",custid);
                intent.putExtra("a2",custname);
                intent.putExtra("a3",setup);
                intent.putExtra("a4",mobile);
                intent.putExtra("a5",companyid);
                startActivity(intent);
            }
        });
    }

    public void calc(int preb,int pa1,int pa2)
    {
        int patot = pa1 + pa2;
        int pb;
        pb = (preb - patot);
        textViewbal.setText(String.valueOf(pb));

    }

    public void Display()
    {
        //textViewmonthchar = (TextView)findViewById(R.id.tvmontchar);

        Intent inn= getIntent();
        Bundle bg1 = inn.getExtras();
        if(bg1!=null) {
            bno = (String) bg1.get("a4");
        }
    }

    class FetchactList extends AsyncTask<String, String, String>
    {
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params)
        {
            shh = new ServiceHandler();

            String url =  path + "Android/Billnolist";

            Log.d("Url: ", "> " + url);

            try {
                // Making a request to url and getting response
                //smonth = "MAY";
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("BillNo", bno));

//                Log.d(cmonth, "here is");

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, params2);

                if (jsonStr != null)
                {
                    JSONObject c1 = new JSONObject(jsonStr);
                    JSONArray classArray = c1.getJSONArray("Response");

                    for (int i = 0; i < classArray.length(); i++)
                    {
                        JSONObject a1 = classArray.getJSONObject(i);

                        custid = a1.getString("CustomerID");
                        custname = a1.getString("CustomerName");
                        setup = a1.getString("SetupBoxBill");
                        bno = a1.getString("BillNo");
                        cmonth = a1.getString("CMonth");
                        payableamt = a1.getString("PayableAmt");
                        mobile = a1.getString("Mbno1");
                        pay1 = a1.getInt("PaidAmt");
                        pay2 = a1.getInt("PaidAmt1");
                        bal = a1.getString("Balance");
                        settime = a1.getString("PayTime");
                        settime1 = a1.getString("PayTime1");
                        companyid = a1.getString("CompanyId");
                    }

                } else {
                    Toast.makeText(getBaseContext(), "Data Not Available", Toast.LENGTH_LONG).show();
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
            textViewid.setText(custid);
            textViewcname.setText(custname);
            editTextmobile.setText(mobile);
            editTextsetupbox.setText(setup);
            textViewbillno.setText(bno);
            textViewbal.setText(bal);
            textViewprebal.setText(payableamt);
            textViewmonth.setText(cmonth);
            if(pay1 == 0.0)
            {
                editTextpaidamt.setText("0");
            }
            else {
                editTextpaidamt.setText(String.valueOf(pay1));
            }
            if(pay2 == 0.0)
            {
                editTextpaidamt1.setText("0");
            }
            else {
                editTextpaidamt1.setText(String.valueOf(pay2));
            }
        }


    }

    public void PaidData()
    {

        String mt = editTextpaidamt.getText().toString();
        if(mt == "0")
        {
            dtr1 = "";
        }
        else
        {
            dtr1 = textViewbilldt.getText().toString();
        }
       String mt1 = editTextpaidamt1.getText().toString();
        if(mt1.equals("0"))
        {
            dtr2 = "";
        }
        else
        {
            dtr2 = textViewbilldt.getText().toString();
        }
//        msgs = "Dear Customer \n Date :" + dtr1 + "Paid :" + editTextpaidamt.getText().toString() + " rs. Bal :" + textViewbal.getText().toString() + " rs." + " Thanks For Payment \nMCN Cable Network";
//                mobile = "8983141186";
////                mobile = "8421205670";
       // mobile = "0";
        // mobile = textViewmobile.getText().toString();
        bno = textViewbillno.getText().toString();
       // pamt = textViewprebal.getText().toString();
        paidamt1 = editTextpaidamt.getText().toString();
        paidamt2 = editTextpaidamt1.getText().toString();
//        paydt1 = textViewbilldt.getText().toString();
//        paydt2 = textViewbilldt1.getText().toString();
        balance = textViewbal.getText().toString();

        new  GetPaidData().execute();
    }

    public class GetPaidData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute()
        {
            // TODO Auto-generated method stub
            super.onPreExecute();

            //progressBar.setVisibility(View.VISIBLE);
            //GPlusProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            shh = new ServiceHandler();

            //String url = "http://sanjurokde.skyvisioncables.com/Complaint/SetComplaintand";
            String url = path + "Android/PaidUpdate";

            Log.d("Url: ", "> " + url);

            try {
                // Making a request to url and getting response

                List<NameValuePair> para = new ArrayList<>();
               // para.add(new BasicNameValuePair("CustBal", balance));
                para.add(new BasicNameValuePair("PaidAmt", paidamt1));
                para.add(new BasicNameValuePair("PaidAmt1", paidamt2));
                para.add(new BasicNameValuePair("PayDate", dtr1));
                para.add(new BasicNameValuePair("PayDate1", dtr2));
                para.add(new BasicNameValuePair("Balance", balance));
                para.add(new BasicNameValuePair("BillNo", bno));
                if (paidamt1.toString().equals("0")) {
                }
                else
                {
                    para.add(new BasicNameValuePair("PayTime", settime));
                }
                if (paidamt2.toString().equals("0")) {
                }
                else
                {
                    para.add(new BasicNameValuePair("PayTime1", settime1));
                }

                //Log.d(senrollNo, "here is");

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, para);
                if (jsonStr != null) {
                    JSONObject jObj = new JSONObject(jsonStr);
                    String msg = jObj.getString("Message");
                    Status = Integer.parseInt(jObj.getString("Status"));

                    if (Status == 1)
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AgentPayActivity.this, "Paid Successfully", Toast.LENGTH_LONG).show();

                            }
                        });

                    }
                    else { Toast.makeText(AgentPayActivity.this, "Data not Registered", Toast.LENGTH_LONG).show();}

                } else {
                    Toast.makeText(AgentPayActivity.this, "Data not Found", Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ServiceHandler", "Json Error ");
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            //progressBar.setVisibility(View.INVISIBLE);
            new GetPaidRegData().execute();


        }
    }

    public class GetPaidRegData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute()
        {
            // TODO Auto-generated method stub
            super.onPreExecute();

            //progressBar.setVisibility(View.VISIBLE);
            //GPlusProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            shh = new ServiceHandler();

            //String url = "http://sanjurokde.skyvisioncables.com/Complaint/SetComplaintand";
            String url = path + "Android/PaidRegUpdate";

            Log.d("Url: ", "> " + url);

            try {
                // Making a request to url and getting response

                List<NameValuePair> para = new ArrayList<>();
                 para.add(new BasicNameValuePair("CustBal", balance));
                para.add(new BasicNameValuePair("CustomerID", custid));


                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, para);
                if (jsonStr != null) {
                    JSONObject jObj = new JSONObject(jsonStr);
                    String msg = jObj.getString("Message");
                    Status = Integer.parseInt(jObj.getString("Status"));

                } else {
                    Toast.makeText(AgentPayActivity.this, "Data not Found", Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ServiceHandler", "Json Error ");
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            //progressBar.setVisibility(View.INVISIBLE);


        }
    }

    public void UpdateMobileSetupData()
    {
        mobileno = editTextmobile.getText().toString().trim();
        setupboxno = editTextsetupbox.getText().toString().trim();

        new GetMobileSetRegData().execute();
    }

    public class GetMobileSetRegData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute()
        {
            // TODO Auto-generated method stub
            super.onPreExecute();

            //progressBar.setVisibility(View.VISIBLE);
            //GPlusProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            shh = new ServiceHandler();

            //String url = "http://sanjurokde.skyvisioncables.com/Complaint/SetComplaintand";
            String url = path + "Android/PaidMobileSetUpdate";

            Log.d("Url: ", "> " + url);

            try {
                // Making a request to url and getting response

                List<NameValuePair> para = new ArrayList<>();
                para.add(new BasicNameValuePair("Mbno1", mobileno));
                para.add(new BasicNameValuePair("SetupNoReg", setupboxno));
                para.add(new BasicNameValuePair("CustomerID", custid));


                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, para);
                if (jsonStr != null) {
                    JSONObject jObj = new JSONObject(jsonStr);
                    String msg = jObj.getString("Message");
                    Status = Integer.parseInt(jObj.getString("Status"));

                } else {
                    Toast.makeText(AgentPayActivity.this, "Data not Found", Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ServiceHandler", "Json Error ");
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (Status == 1)
            {
                Toast.makeText(AgentPayActivity.this, "Update Successfully", Toast.LENGTH_LONG).show();
            }
            //progressBar.setVisibility(View.INVISIBLE);


        }
    }
}
