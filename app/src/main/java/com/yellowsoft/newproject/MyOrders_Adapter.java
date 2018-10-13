package com.yellowsoft.newproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sriven on 6/1/2018.
 */

public class MyOrders_Adapter extends RecyclerView.Adapter<MyOrders_Adapter.MyViewHolder> {
	Context context;
	ArrayList<MyOrdersData> items;

	public MyOrders_Adapter(Context context, ArrayList<MyOrdersData> items){
		this.context=context;
		this.items=items;
	}
	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewtype){
		View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_item,parent,false);
		MyViewHolder myViewHolder=new MyViewHolder(v);

		return myViewHolder;
	}
	@Override
	public void onBindViewHolder(MyViewHolder holder,final int position){

		holder.product_tittle.setText(items.get(position).tittle);
		holder.address_myorders_tv.setText(items.get(position).address);
		holder.order_ids.setText(items.get(position).order_id);
		holder.price_tv_myorders.setText(items.get(position).price);
		holder.quantity_tv_myorders.setText(items.get(position).quantity);

		holder.myorders_status.setText(items.get(position).status);

		if(!items.get(position).images.equals(""))
		Picasso.get().load(items.get(position).images).into(holder.order_img);

		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
			/*	Intent intent;
				intent =  new Intent(context, Order_type.class);
				intent.putExtra("service",items.get(position));
				context.startActivity(intent);*/

			}

		});


	}
	public int getItemCount(){
		return items.size();
	}

	public  class MyViewHolder extends RecyclerView.ViewHolder
	{

		ImageView order_img;
		TextView address_myorders_tv,order_ids,quantity_tv_myorders,price_tv_myorders,product_tittle,myorders_status;
		public MyViewHolder(View itemView){
			super(itemView);

			product_tittle = (TextView) itemView.findViewById(R.id.producttitle_tv);
			order_ids = (TextView)itemView.findViewById(R.id.orderid_tv_myorders);
			quantity_tv_myorders = (TextView)itemView.findViewById(R.id.quantity_tv_myorders);
			price_tv_myorders = (TextView)itemView.findViewById(R.id.price_tv_myorders);
			address_myorders_tv=(TextView)itemView.findViewById(R.id.address_myorders_tv);
			order_img=(ImageView)itemView.findViewById(R.id.order_img);
			myorders_status = (TextView) itemView.findViewById(R.id.ordersstatus_tv);
		}
	}
}
