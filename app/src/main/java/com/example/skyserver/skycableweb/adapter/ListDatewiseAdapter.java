package com.example.skyserver.skycableweb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.skyserver.skycableweb.R;
import com.example.skyserver.skycableweb.model.DatewiseProduct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NgocTri on 11/7/2015.
 */
public class ListDatewiseAdapter extends BaseAdapter {
    private Context mContext;
    private List<DatewiseProduct> mProductList;

    public ListDatewiseAdapter(Context mContext, List<DatewiseProduct> productList) {
        this.mContext = mContext;
        this.mProductList = new ArrayList<DatewiseProduct>();
        this.mProductList.addAll(productList);
    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public DatewiseProduct getItem(int position) {
        return mProductList.get(position);
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
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.day_list, null);
            TextView tvdname = (TextView)v.findViewById(R.id.tvdayname);
            TextView tvdbill = (TextView)v.findViewById(R.id.tvdaybillno);
            TextView tvdpay = (TextView)v.findViewById(R.id.tvdaypayamt);

            holder.custnm = tvdname;
            holder.bill = tvdbill;
            holder.pay = tvdpay;

            v.setTag(holder);
        }
        else
            holder = (PlanetHolder) v.getTag();

        DatewiseProduct planet = mProductList.get(position);
        // holder.checkBox.setTag(planet);
        holder.custnm.setText(planet.getDname());
        holder.bill.setText("" + planet.getDBillno());
        holder.pay.setText("" + planet.getDPaidAmt());



        return v;
    }

    public static class PlanetHolder {
        public TextView custnm;
        public TextView bill;
        public TextView pay;

    }
}
