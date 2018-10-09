package com.yellowsoft.newproject;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by mac on 6/27/18.
 */

public class TypeFactory {

    private String A_ROUND= "fonts/gothamroundedbook.otf";
    private String A_LIGHT="fonts/gothamroundedlight.otf";
    private String A_REGULAR= "fonts/Roboto-Regular.ttf";
    private String A_MEDIUM= "fonts/gothamroundedmedium.otf";

    Typeface ambleBold;
    Typeface ambleLight;
    Typeface ambleRegular;
    Typeface ambleMedium;

    public TypeFactory(Context context){
        ambleBold = Typeface.createFromAsset(context.getAssets(),A_ROUND);
        ambleLight = Typeface.createFromAsset(context.getAssets(),A_LIGHT);
        ambleRegular = Typeface.createFromAsset(context.getAssets(),A_REGULAR);
        ambleMedium = Typeface.createFromAsset(context.getAssets(),A_MEDIUM);
    }

}
