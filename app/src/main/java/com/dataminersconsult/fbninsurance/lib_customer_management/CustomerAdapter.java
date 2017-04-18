package com.dataminersconsult.fbninsurance.lib_customer_management;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dataminersconsult.fbninsurance.R;

import java.util.ArrayList;

public class CustomerAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CustomerList> customerLists;

    public CustomerAdapter (Context context, ArrayList<CustomerList> customerLists) {
        this.context = context;
        this.customerLists = customerLists;
    }

    @Override
    public int getCount() {
        return customerLists.size();
    }

    @Override
    public Object getItem(int position) {
        return customerLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.activity_cma_listview, null);

            viewHolder = new ViewHolder();

            viewHolder.fullname = (TextView) view.findViewById(R.id.cma_label_title);
            viewHolder.email = (TextView) view.findViewById(R.id.cma_label_email);
            viewHolder.occupation = (TextView) view.findViewById(R.id.cma_label_occupation);
            viewHolder.edit = (TextView) view.findViewById(R.id.cma_label_edit);
            viewHolder.photo = (ImageView) view.findViewById(R.id.cma_image_view);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        CustomerList customerList = (CustomerList) getItem(position);

        viewHolder.fullname.setText(customerList.getFullname());
        viewHolder.email.setText(customerList.getEmail());
        viewHolder.occupation.setText(customerList.getOccupation());
//        viewHolder.occupation.setText(customerList.getImage());

        Bitmap bm = customerList.getBm();

        if (bm != null) {
            viewHolder.photo.setImageBitmap(customerList.getBm());
        }

        return view;
    }

    private class ViewHolder {
        TextView fullname;
        TextView email;
        TextView occupation;
        TextView edit;
        ImageView photo;
    }
}
