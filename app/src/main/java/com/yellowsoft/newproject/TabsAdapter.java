package com.yellowsoft.newproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by mac on 3/18/17.
 */

public class TabsAdapter extends FragmentStatePagerAdapter {




    public TabsAdapter(FragmentManager fm, HomeActivity activity) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {


    switch (position){


        case 0:{
            HomeFragment demoFragment = HomeFragment.newInstance(position);

            return demoFragment;

        }
        case 1:{
            TrackFragment trackFragment = TrackFragment.newInstance(position);

            return trackFragment;
        }

        case 2:{
            ProductsFragment productsFragment = ProductsFragment.newInstance(position);

            return  productsFragment;

        }

        case 3:{
            SchemeFragment schemeFragment = SchemeFragment.newInstance(position);
            return schemeFragment;
        }

        case 4:{
            MyAccountFragment myAccountFragment = MyAccountFragment.newInstance(position);
            return myAccountFragment;

        }

        default: {
            HomeFragment demoFragment = HomeFragment.newInstance(position);

            return demoFragment;
        }


    }

    }

    @Override
    public int getCount() {
        return 5;
    }
}
