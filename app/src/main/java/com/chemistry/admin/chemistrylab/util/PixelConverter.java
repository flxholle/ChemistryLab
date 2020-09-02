package com.chemistry.admin.chemistrylab.util;

import android.content.Context;

import com.chemistry.admin.chemistrylab.R;

/**
 * Created by Admin on 9/4/2016.
 */
public class PixelConverter {
//    public static final int PIXEL_SQUARE_PER_ML = 120;// 1 ml = 120 px^2

    public static double calculateHeightByVolume(Context context, double volume, double widthSpace){
        return volume * context.getResources().getDimensionPixelOffset(R.dimen.pixel_square_per_ml) / widthSpace;
    }

    public static double calculateVolumeByHeight(Context context, double height, double widthSpace){
        return height * widthSpace * 1.0/ context.getResources().getDimensionPixelOffset(R.dimen.pixel_square_per_ml);
    }
}
