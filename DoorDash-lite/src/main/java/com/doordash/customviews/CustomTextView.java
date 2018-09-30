package com.doordash.customviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.doordash.lite.R;
import com.doordash.utils.CommonUtils;
import com.doordash.utils.FontManager;

@SuppressLint("AppCompatCustomView")
public class CustomTextView extends TextView {

    public CustomTextView(Context context) {
        super(context);
        init(null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {

            try {
                TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTextView);

                if (a != null) {
                    String fontName = a.getString(R.styleable.CustomTextView_customFont);


                    if (!CommonUtils.isNullOrEmpty(fontName)) {
                        setTypeface(FontManager.getTypeface(getContext(), fontName));
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
