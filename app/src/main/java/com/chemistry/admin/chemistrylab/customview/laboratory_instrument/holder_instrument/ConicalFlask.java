package com.chemistry.admin.chemistrylab.customview.laboratory_instrument.holder_instrument;

import android.content.Context;
import android.graphics.Path;
import android.graphics.Point;
import android.text.SpannableString;

import com.chemistry.admin.chemistrylab.R;
import com.chemistry.admin.chemistrylab.database.LaboratoryDatabaseManager;

/**
 * Created by Admin on 9/7/2016.
 */
public class ConicalFlask extends LaboratoryHolderInstrument {
    public final int CONICAL_FLASK_STANDARD_WIDTH;
    public final int CONICAL_FLASK_STANDARD_HEIGHT;
    private static final String TAG = "ConicalFlask";
    public final String NAME = getContext().getString(R.string.conical_flask);
    private static Point[] arrPoint;
    private static Path instrumentPath;

    public ConicalFlask(Context context, int widthView, int heightView) {
        super(context, widthView, heightView);
        CONICAL_FLASK_STANDARD_WIDTH = getConicalFlaskStandardWidth(context);
        CONICAL_FLASK_STANDARD_HEIGHT = getConicalFlaskStandardHeight(context);
    }

    public static int getConicalFlaskStandardWidth(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.contained_space_width) + 2 * getStrokeWidth(context);
    }

    public static int getConicalFlaskStandardHeight(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.contained_space_height) + 2 * getStrokeWidth(context);
    }

    @Override
    public LaboratoryHolderInstrument getClone() {
        //No default substance, just empty
        return new ConicalFlask(getContext(), CONICAL_FLASK_STANDARD_WIDTH, CONICAL_FLASK_STANDARD_HEIGHT);
    }

    @Override
    protected void createPath(int spaceWidth, int spaceHeight) {
        if (instrumentPath == null) {
            instrumentPath = new Path();
            int bottleNeckWidth = (int) (spaceWidth / 3.125f);//64
            int bottleNeckHeight = spaceHeight / 3;
            int roundBottomCornerDiameter = spaceWidth / 10;
            instrumentPath.moveTo((spaceWidth - bottleNeckWidth) / 2, 0);
            instrumentPath.lineTo((spaceWidth - bottleNeckWidth) / 2, bottleNeckHeight);
            instrumentPath.quadTo(0, spaceHeight - roundBottomCornerDiameter / 3, roundBottomCornerDiameter / 2, spaceHeight);
            instrumentPath.lineTo(spaceWidth - roundBottomCornerDiameter / 2, spaceHeight);
            instrumentPath.quadTo(spaceWidth, spaceHeight - roundBottomCornerDiameter / 3, (spaceWidth - bottleNeckWidth) / 2 + bottleNeckWidth, bottleNeckHeight);
            instrumentPath.lineTo((spaceWidth - bottleNeckWidth) / 2 + bottleNeckWidth, 0);

            arrPoint = LaboratoryDatabaseManager.getInstance(getContext()).getArrayPointOf(LaboratoryDatabaseManager.FLASK_MAP_VERTICAL_TABLE_NAME);
        }
    }

    @Override
    public SpannableString getName() {
        return SpannableString.valueOf(NAME);
    }

    @Override
    public int getContainedSpaceHeight() {
        return getResources().getDimensionPixelOffset(R.dimen.contained_space_height);
    }

    @Override
    public int getContainedSpaceWidth() {
        return getResources().getDimensionPixelOffset(R.dimen.contained_space_width);
    }

    @Override
    public String getTableName() {
        return LaboratoryDatabaseManager.CONICAL_FLASK_MAP_HORIZONTAL_TABLE_NAME;
    }

    @Override
    public Point[] getArrayPoint() {
        return arrPoint;
    }

    @Override
    public Path getInstrumentPath() {
        return instrumentPath;
    }
}
