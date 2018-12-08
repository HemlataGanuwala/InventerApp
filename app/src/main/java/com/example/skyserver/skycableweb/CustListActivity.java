package com.example.skyserver.skycableweb;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skyserver.skycableweb.adapter.PlanetCustlistAdapter;
import com.example.skyserver.skycableweb.model.Product1;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CustListActivity extends AppCompatActivity {

    private ListView lvPaydet;
    private PlanetCustlistAdapter adapter;
    private List<Product1> mProductList = new ArrayList<Product1>();
    TextView textViewcustid,textViewcustnm,textViewcustsetup,textViewcustmobile,textViewcustbillno,textViewcustpaydt,textViewcustpaid,textViewcustbal;
    String custid,custnm,setupbox,mob;
    ServiceHandler shh;
    String path,cmonth,pay1,pay2,paydt1,paydt2,bno,bal,compid,agentnm;
    int cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_list);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        path = globalVariable.getconstr();

        textViewcustnm = (TextView)findViewById(R.id.tvpaycustnm);
        textViewcustsetup = (TextView)findViewById(R.id.tvpaysetbox);
        textViewcustmobile = (TextView)findViewById(R.id.tvpaymobile);
        lvPaydet = (ListView)findViewById(R.id.lvpaylist);

        Display();
    }

   public void Display()
   {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
            custid = (String) bundle.get("a1");
            custnm = (String) bundle.get("a2");
            setupbox = (String) bundle.get("a3");
            mob = (String) bundle.get("a4");
            agentnm = (String) bundle.get("a5");
//            compid = (String) bundle.get("a5");

            textViewcustnm.setText(custnm);
            textViewcustsetup.setText(setupbox);
            textViewcustmobile.setText(mob);
        }

       new FetchactList().execute();

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

            String url =  path + "Registration/PaidDetailsList";

            Log.d("Url: ", "> " + url);

            try {
                // Making a request to url and getting response
                //smonth = "MAY";
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("CustId", custid));
                params2.add(new BasicNameValuePair("AgentName", agentnm));
//                params2.add(new BasicNameValuePair("CompanyId", compid));

//                Log.d(cmonth, "here is");

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, params2);

                if (jsonStr != null)
                {
                    JSONObject c1 = new JSONObject(jsonStr);
                    JSONArray classArray = c1.getJSONArray("Response");

                    for (int i = 0; i < classArray.length(); i++)
                    {
                        JSONObject a1 = classArray.getJSONObject(i);
                        cid = a1.getInt("CustId");
                        bno = a1.getString("Bid");
                        cmonth = a1.getString("Bmonth");
                        pay1 = a1.getString("PaymentAmount1");
                        pay2 = a1.getString("PaymentAmount2");
                        bal = a1.getString("OldBal");
                        paydt1 = a1.getString("PaymentDate1");
                        paydt2 = a1.getString("PaymentDate2");

                        Product1 product = new Product1(bno,cmonth,pay1,pay2,paydt1,paydt2,bal);
                        mProductList.add(product);

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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter = new PlanetCustlistAdapter( mProductList,CustListActivity.this);
                    lvPaydet.setAdapter(adapter);
                }
            });
        }


    }
}
