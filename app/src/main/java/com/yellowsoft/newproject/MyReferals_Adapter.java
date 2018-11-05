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

public class MyReferals_Adapter extends RecyclerView.Adapter<MyReferals_Adapter.MyViewHolder> {
	Context context;
	ArrayList<MyReferalsData> items;

	public MyReferals_Adapter(Context context, ArrayList<MyReferalsData> items){
		this.context=context;
		this.items=items;
	}
	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewtype){
		View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.myreferals_item,parent,false);
		MyViewHolder myViewHolder=new MyViewHolder(v);

		return myViewHolder;
	}
	@Override
	public void onBindViewHolder(MyViewHolder holder,final int position){

		holder.referalids.setText(items.get(position).referalid);
		holder.status.setText(items.get(position).status);
		holder.transdetails.setText(items.get(position).transdetails);
		holder.name.setText(items.get(position).referredby+" "+items.get(position).date);

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

		TextView referalids,status,transdetails,name;

		public MyViewHolder(View itemView){
			super(itemView);

			referalids = (TextView)itemView.findViewById(R.id.referalid_myreferals);
			status = (TextView)itemView.findViewById(R.id.status_myreferals);
			transdetails = (TextView)itemView.findViewById(R.id.trans_details_myreferals);
			name = (TextView)itemView.findViewById(R.id.referredbyname_myreferals);
		}
	}
}
