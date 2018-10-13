package com.yellowsoft.newproject;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sriven on 6/1/2018.
 */

public class SlidingImageAdapter extends PagerAdapter{
ArrayList<SlidingImage_Data> images;
Context context;

	public SlidingImageAdapter(Context context, ArrayList<SlidingImage_Data> images){
	this.context=context;
	this.images=images;
	}
	@Override
	public int getCount(){
	return images.size();
	}
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		do {
			context = this.context; //Using just "this" doesn't work either.
		} while (context==null);

		//	context = SlidingPageAdapter.this.context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.slidingimage_item, container,false);

		ImageView imageView = (ImageView)view.findViewById(R.id.viewpagerimage);

		//	textView.setText(colorName.get(position));

		Picasso.get().load(images.get(position).image_url).placeholder(R.drawable.place_holder).into(imageView);

		//	linearLayout.setBackgroundColor(color.get(position));

		ViewPager viewPager = (ViewPager) container;
		viewPager.addView(view, 0);

		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		ViewPager viewPager = (ViewPager) container;
		View view = (View) object;
		viewPager.removeView(view);
	}

}
