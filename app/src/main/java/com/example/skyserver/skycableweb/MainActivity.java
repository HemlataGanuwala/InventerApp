package com.example.skyserver.skycableweb;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.skyserver.skycableweb.adapter.ListProductAdapter;
import com.example.skyserver.skycableweb.model.Product;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //private List<Product1> mPlanetlist = new ArrayList<Product1>();
     List<Product> mPlanetlist = new ArrayList<Product>();
    ListProductAdapter adapter;
    String id,agentnm,cmonth,cyear,custnm,path,spincompany,mob,setupbox,billno,dtfrom,payableamt,dtto,paid,bal,paydt,adcust,paydt1,paidamt1,area,paystatus,montch,sbillno;

    ServiceHandler shh;
    ListView listView;
    int success = 1;
    EditText editTextsearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        path = globalVariable.getconstr();
        editTextsearch = (EditText)findViewById(R.id.etsearch);

        Display();

        listView = (ListView)findViewById(R.id.listview_product);


        editTextsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("Text ["+s+"] - Start ["+start+"] - Before ["+before+"] - Count ["+count+"]");
                if (count < before) {
                    // We're deleting char so we need to reset the adapter data
                    adapter.resetData();
                }


                adapter.getFilter().filter(s.toString());

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product planet1 = adapter.getItem(i);
                ListProductAdapter.PlanetHolder viewHolder = ( ListProductAdapter.PlanetHolder) view.getTag();
                int ti = 0;

                ti = listView.getAdapter().getCount();

                for (int i1 = 0; i1 < ti; i1++)
                {
                    sbillno = (String) planet1.getBno();
                }
                Intent intent = new Intent(MainActivity.this,AgentPayActivity.class);
                intent.putExtra("a4",sbillno);
                startActivity(intent);

            }
        });

    }



    public void Display()
    {
        Intent inn= getIntent();
        Bundle bg1 = inn.getExtras();
        if(bg1!=null) {
            agentnm = (String) bg1.get("a1");
            cmonth = (String) bg1.get("a2");
            cyear = (String) bg1.get("a3");
            spincompany = (String) bg1.get("a4");
        }
        mPlanetlist.clear();
        new getCustlistData().execute();
    }

    class getCustlistData extends AsyncTask<Void, Void, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            shh = new ServiceHandler();
            String url = path + "Registration/BillDetailsList";
            Log.d("Url: ", "> " + url);

            try{
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("AgentName",agentnm));
                params2.add(new BasicNameValuePair("Bmonth",cmonth));
                params2.add(new BasicNameValuePair("Byear",cyear));
//                params2.add(new BasicNameValuePair("CompanyId",spincompany));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST , params2);

                if (jsonStr != null) {
                    JSONObject c1 = new JSONObject(jsonStr);
                    JSONArray classArray = c1.getJSONArray("Response");
                    //JSONArray jsonarry = new JSONArray(jsonStr);
                    for (int i = 0; i < classArray.length(); i++) {
                        JSONObject a1 = classArray.getJSONObject(i);

                        id = a1.getString("CustId");
                        custnm = a1.getString("CustName");
                        setupbox = a1.getString("SetupBox_Details");
                        billno = a1.getString("Bid");
                        area = a1.getString("Area");
                        payableamt = a1.getString("Monthcharge");
                        mob = a1.getString("MobileNo");
                        //dtto = a1.getString("RegPassword");
                        paid = a1.getString("PaymentAmount1");
                        bal = a1.getString("Balance");
                        paydt = a1.getString("PaymentDate1");
//                        adcust = a1.getString("AccNoBox");
                        paydt1 = a1.getString("PaymentDate2");
                        paidamt1 = a1.getString("PaymentAmount2");
                       // montch = a1.getString("RegUsername");

                        Product product = new Product(id,custnm,setupbox,billno,cmonth,payableamt,mob,paid,bal,paydt,paydt1,paidamt1,area,paystatus,cyear);
                        mPlanetlist.add(product);


                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Data Not Available", Toast.LENGTH_LONG).show();
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
                    adapter = new ListProductAdapter(MainActivity.this,mPlanetlist);
                    listView.setAdapter(adapter);
                    Toast.makeText(getApplicationContext(), "Total number of Items are:" + listView.getAdapter().getCount() , Toast.LENGTH_LONG).show();

                }
            });

        }
    }
}
