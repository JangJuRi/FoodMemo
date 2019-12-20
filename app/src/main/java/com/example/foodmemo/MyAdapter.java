package com.example.foodmemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<FoodList> data;
    private int layout;

    public MyAdapter(Context context, int layout, ArrayList<FoodList> data) {
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(layout,parent,false);
        }
        FoodList foodList = data.get(position);

        ImageView list_pic = (ImageView)convertView.findViewById(R.id.list_pic);
        if(foodList.getPic()!=null) list_pic.setImageBitmap(getImage(foodList.getPic()));
        else list_pic.setImageResource(R.drawable.img_sample);

        TextView list_name = (TextView)convertView.findViewById(R.id.list_name);
        list_name.setText(foodList.getName());

        TextView list_address = (TextView)convertView.findViewById(R.id.list_address);
        list_address.setText(foodList.getAddress());

        TextView list_type = (TextView)convertView.findViewById(R.id.list_type);
        list_type.setText(foodList.getTypeT());

        TextView list_region = (TextView)convertView.findViewById(R.id.list_region);
        list_region.setText(foodList.getRegionT());

        return convertView;
    }

    private Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}