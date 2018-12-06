package com.example.skyserver.skycableweb.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.skyserver.skycableweb.R;
import com.example.skyserver.skycableweb.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ListProductAdapter extends ArrayAdapter<Product> implements Filterable {
    private Context mContext;
    private List<Product> mProductList;
    private List<Product> origPlanetList;
    private PlanetFilter filter;

    public ListProductAdapter(@NonNull Context mContext, List<Product> productList) {
        super(mContext, R.layout.main_list, productList);
        this.mContext = mContext;
        this.mProductList = new ArrayList<Product>();
        this.mProductList.addAll(productList);
        this.origPlanetList = mProductList;
        getFilter();
    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Nullable
    @Override
    public Product getItem(int position) {
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
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.main_list, null);
            // Now we can fill the layout with the right value
            TextView tvName = (TextView) v.findViewById(R.id.tv_product_name);
            TextView tvMobile = (TextView) v.findViewById(R.id.tvmobile1);
            TextView tvSetop = (TextView) v.findViewById(R.id.tvsetop1);
            TextView tvpbal = (TextView) v.findViewById(R.id.tvprebal);
//            TextView tvactdact = (TextView) v.findViewById(R.id.tvadc);
            TextView tvbiln = (TextView) v.findViewById(R.id.tvbillno1);
            TextView tvarea = (TextView) v.findViewById(R.id.tvarealist);
            TextView tvregno = (TextView) v.findViewById(R.id.tvregno);

            holder.custname = tvName;
            holder.mobile = tvMobile;
            holder.setup = tvSetop;
            holder.pbal = tvpbal;
//            holder.actdact = tvactdact;
            holder.billno = tvbiln;
            holder.area = tvarea;
            holder.regno = tvregno;

//            if (mProductList.get(position).getADcust().equals("0")) {
//                tvactdact.setText("DC");
//                tvactdact.setTextColor(Color.RED);
//            } else {
//                tvactdact.setText("AC");
//                tvactdact.setTextColor(Color.parseColor("#ff669900"));
//            }

            v.setTag(holder);
        } else
            holder = (PlanetHolder) v.getTag();

        Product planet = mProductList.get(position);
        holder.custname.setText(planet.getName());
        holder.mobile.setText("" + planet.getMobile());
        holder.setup.setText("" + planet.getSetop());
        holder.pbal.setText("" + planet.getPayableamt());
//        holder.actdact.setText("" + planet.getADcust());
        holder.billno.setText("" + planet.getBno());
        holder.area.setText("" + planet.getArea());
        holder.regno.setText("" + planet.getId());

//        if (planet.getADcust().toString().equals("0.0")) {
//            holder.actdact.setText("Deactive");
//            holder.actdact.setTextColor(Color.RED);
//        } else {
//            holder.actdact.setText("Active");
//            holder.actdact.setTextColor(Color.parseColor("#ff669900"));
//        }

        return v;
    }

    public static class PlanetHolder {
        public TextView custname;
        public TextView mobile;
        public TextView setup;
        public TextView pbal;
        public TextView actdact;
        public TextView billno;
        public TextView area;
        public TextView regno;

    }

    public void resetData() {
        mProductList = origPlanetList;
    }


    @Override
    public Filter getFilter() {
        if (filter == null)
            filter = new PlanetFilter();

        return filter;
    }

    private class PlanetFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = origPlanetList;
                results.count = origPlanetList.size();
            } else {
                String prefixString = constraint.toString().toLowerCase();
                List<Product> nPlanetList = new ArrayList<Product>();
                List<Product> nPlanetListlocal = new ArrayList<Product>();
                nPlanetListlocal.addAll(origPlanetList);
                final int count = nPlanetListlocal.size();
                for (int i = 0; i < count; i++) {
                    final Product item = nPlanetListlocal.get(i);
                    final String itemName = item.getName()
                            .toLowerCase();
                    if (itemName.contains(prefixString)) {
                        nPlanetList.add(item);
                    } else {
                    }
//                    final String itemMobile = item.getMobile()
//                            .toLowerCase();
//                    if (itemMobile.contains(prefixString)) {
//                        nPlanetList.add(item);
//                    } else {
//                    }
                    final String itemArea = item.getArea()
                            .toLowerCase();
                    if (itemArea.contains(prefixString)) {
                        nPlanetList.add(item);
                    } else {
                    }

                    final String itemRegno = item.getId();
                    if (itemRegno.contains(prefixString)) {
                        nPlanetList.add(item);
                    } else {
                    }


//                String constraintString = constraint.toString().toLowerCase();
//                // We perform filtering operation
//                List<Product> nPlanetList = new ArrayList<Product>();
//
//                for (Product p : mProductList) {
//                    if (p.getName().toUpperCase().startsWith(constraint.toString().toUpperCase()))
//                        nPlanetList.add(p);
//                    if (p.getMobile().toUpperCase().startsWith(constraint.toString().toUpperCase()))
//                        nPlanetList.add(p);
//                    if (p.getSetop().toUpperCase().startsWith(constraint.toString().toUpperCase()))
//                        nPlanetList.add(p);
//                    if (p.getArea().toUpperCase().startsWith(constraint.toString().toUpperCase()))
//                        nPlanetList.add(p);
//                    if (p.getName().contains(constraintString)) {
//                        nPlanetList.add(p);
//                    }
                }

                results.values = nPlanetList;
                results.count = nPlanetList.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // Now we have to inform the adapter about the new list filtered
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                mProductList = (List<Product>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}
