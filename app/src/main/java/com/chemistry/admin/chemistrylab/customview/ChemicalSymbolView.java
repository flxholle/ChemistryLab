package com.chemistry.admin.chemistrylab.customview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by Admin on 8/18/2016.
 */
public class ChemicalSymbolView extends AppCompatTextView {

    public ChemicalSymbolView(Context context) {
        super(context);
    }

    public ChemicalSymbolView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChemicalSymbolView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSymbol(CharSequence rawSymbolString) {//Only for index < 10
        setText(rawSymbolString, BufferType.SPANNABLE);
    }
}
