package com.yellowsoft.newproject;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by sriven on 5/6/2016.
 */
public class MenuItem implements Serializable {
    String title,title_ar;
    int icon;

    MenuItem(String title, String title_ar, int id){
        this.title=title;
        this.title_ar=title_ar;
       // this.id = id;
        this.icon = id;
    }


    public String getTitle(Context context) {
      //  if(Settings.get_user_language(context).equals("ar"))
        //    return title_ar;
        //else
            return  title;
    }




}
