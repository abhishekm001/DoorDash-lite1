package com.doordash.utils;

import android.content.Context;
import android.graphics.Typeface;

public class FontManager {

    public static Typeface getTypeface(Context context, String fontTypefaceName) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/" + fontTypefaceName + ".ttf");
    }

}
