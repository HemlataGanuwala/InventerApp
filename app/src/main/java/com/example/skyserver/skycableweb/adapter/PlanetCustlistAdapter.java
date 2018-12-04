package com.example.skyserver.skycableweb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.skyserver.skycableweb.R;
import com.example.skyserver.skycableweb.model.Product1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SkyVision on 09/07/2017.
 */

public class PlanetCustlistAdapter extends ArrayAdapter<Product1> implements Filterable
{

    private List<Product1> planetsList;
    private Context context;
   // private PlanetFilter filter;
    private List<Product1> origPlanetList;
    CheckBox cb;

    public PlanetCustlistAdapter(List<Product1> planetList, Context ctx) {
        super(ctx, R.layout.pay_list, planetList);
        this.context = ctx;
        this.planetsList = new ArrayList<Product1>();
        this.planetsList.addAll(planetList);

        this.origPlanetList = planetList;
        getFilter();
    }
    @Override
    public int getCount() {
        return planetsList.size();
    }
    @Override
    public Product1 getItem(int position) {
        return planetsList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        PlanetHolder holder = new PlanetHolder();

        // First let's verify the convertView is not null
        if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.pay_list, null);
            // Now we can fill the layout with the right values
            TextView tvbill = (TextView) v.findViewById(R.id.tvpaybillno);
            TextView tvcmonth = (TextView) v.findViewById(R.id.tvcmonth);
            TextView tvpaid1 = (TextView) v.findViewById(R.id.tvpaypaid);
            TextView tvpaid2 = (TextView) v.findViewById(R.id.tvpaypaidt);
            TextView tvpaydt1 = (TextView) v.findViewById(R.id.tvpaypaydt);
            TextView tvpaydt2 = (TextView) v.findViewById(R.id.tvpaypaydt2);
            TextView tvbal = (TextView) v.findViewById(R.id.tvpaybal);

            holder.billno = tvbill;
            holder.cmonth = tvcmonth;
            holder.paid1 = tvpaid1;
            holder.paid2 = tvpaid2;
            holder.paydt1 = tvpaydt1;
            holder.paydt2 = tvpaydt2;
            holder.bal = tvbal;

            //holder.checkBox = chk;
            v.setTag(holder);
        }
        else
            holder = (PlanetHolder) v.getTag();

        Product1 planet = planetsList.get(position);
       // holder.checkBox.setTag(planet);
        holder.billno.setText(planet.getBillno());
        holder.cmonth.setText("" + planet.getCmonth());
        holder.paid1.setText("" + planet.getPaid1());
        holder.paid2.setText("" + planet.getPaid2());
        holder.paydt1.setText("" + planet.getPaydt1());
        holder.paydt2.setText("" + planet.getPaiddt2());
        holder.bal.setText("" + planet.getBalance());



        return v;
    }

    public void resetData() {
        planetsList = origPlanetList;
    }


	/* *********************************
	 * We use the holder pattern
	 * It makes the view faster and avoid finding the component
	 * **********************************/

     public static class PlanetHolder {
        public TextView billno;
        public TextView cmonth;
        public TextView paid1;
         public TextView paid2;
         public TextView paydt1;
         public TextView paydt2;
         public TextView bal;

//        public CheckBox checkBox;
//         public CheckBox getCheckBox() {
//             return checkBox;
//         }
//         public void setCheckBox(CheckBox checkBox) {
//             this.checkBox = checkBox;
//         }
     }



	/*
	 * We create our filter
	 */


}
