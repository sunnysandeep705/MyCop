package com.yellowsoft.newproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MenuAdapter_MyAccount extends BaseAdapter {
    Context context;
    ArrayList<MenuItem> menuItems;




    private static LayoutInflater inflater=null;
    public MenuAdapter_MyAccount(Context mainActivity, ArrayList<MenuItem> menuItems) {
        // TODO Auto-generated constructor stubcontext=mainActivity;
        this.context = mainActivity;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.menuItems = menuItems;

    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return menuItems.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    public class Holder
    {
        TextView cur_symbol,cur_name;
        ImageView country_flag;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        Holder holder=new Holder();

        View rowView;

        rowView = inflater.inflate(R.layout.menu_item_myaccount, null);




        holder.cur_symbol=(TextView) rowView.findViewById(R.id.menu_title);
        holder.cur_symbol.setText(menuItems.get(position).title);

        //holder.cur_name=(TextView)rowView.findViewById(R.id.menu_iem2);
       // holder.cur_name.setText(menuItems.get(position).title_ar);


        holder.country_flag = (ImageView) rowView.findViewById(R.id.menu_icon_home);
        holder.country_flag.setImageResource(menuItems.get(position).icon);

        //Picasso.with(context).load(categories.get(position).icon).into(holder.country_flag);


        return rowView;
        
    }




}