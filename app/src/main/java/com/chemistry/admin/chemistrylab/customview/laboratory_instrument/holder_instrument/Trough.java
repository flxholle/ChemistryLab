package com.chemistry.admin.chemistrylab.customview.laboratory_instrument.holder_instrument;

import android.content.Context;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.text.SpannableString;

import com.chemistry.admin.chemistrylab.R;
import com.chemistry.admin.chemistrylab.database.LaboratoryDatabaseManager;

/**
 * Created by Admin on 9/7/2016.
 */
public class Trough extends LaboratoryHolderInstrument {
    public final int TROUGH_STANDARD_WIDTH;
    public final int TROUGH_STANDARD_HEIGHT;
    private static final String TAG = "Trough";
    public final String NAME = getContext().getString(R.string.trough);
    private static Point[] arrPoint;
    private static Path instrumentPath;

    public Trough(Context context, int widthView, int heightView) {
        super(context, widthView, heightView);
        TROUGH_STANDARD_WIDTH = getTroughStandardWidth(context);
        TROUGH_STANDARD_HEIGHT = getTroughStandardHeight(context);
    }

    public static int getTroughStandardWidth(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.contained_space_width_trough) + 2 * getStrokeWidth(context);
    }

    public static int getTroughStandardHeight(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.contained_space_height) + 2 * getStrokeWidth(context);
    }

    @Override
    public Trough getClone() {
        return new Trough(getContext(), TROUGH_STANDARD_WIDTH, TROUGH_STANDARD_HEIGHT);
    }

    @Override
    protected void createPath(int spaceWidth, int spaceHeight) {
        if (instrumentPath == null) {
            int roundBottomCornerDiameter = spaceHeight / 5;
            instrumentPath = new Path();
            instrumentPath.moveTo(0, 0);
            instrumentPath.lineTo(0, spaceHeight - roundBottomCornerDiameter);
            instrumentPath.arcTo(new RectF(0,
                                            spaceHeight - roundBottomCornerDiameter,
                                            roundBottomCornerDiameter,
                                            spaceHeight - roundBottomCornerDiameter + roundBottomCornerDiameter),
                                180, -90);
            instrumentPath.arcTo(new RectF(spaceWidth - roundBottomCornerDiameter,
                                            spaceHeight - roundBottomCornerDiameter,
                                            spaceWidth - roundBottomCornerDiameter + roundBottomCornerDiameter,
                                            spaceHeight - roundBottomCornerDiameter + roundBottomCornerDiameter),
                                90, -90);
            instrumentPath.lineTo(spaceWidth, 0);

//          LaboratoryDatabaseManager.getInstance(getContext()).insertToDataBase(LaboratoryDatabaseManager.BREAKER_MAP_VERTICAL_TABLE_NAME,PointAnalyzer.analyzePointVertical(instrumentPath));
            arrPoint = LaboratoryDatabaseManager.getInstance(getContext())
                    .getArrayPointOf(LaboratoryDatabaseManager.TROUGH_MAP_VERTICAL_TABLE_NAME);
        }
    }

    @Override
    public SpannableString getName() {
        if (getDefaultSubstance() != null) {
            return getDefaultSubstance().getConvertSymbol();
        }
        return SpannableString.valueOf(NAME);
    }

    @Override
    public int getContainedSpaceHeight() {
        return getResources().getDimensionPixelOffset(R.dimen.contained_space_height);
    }

    @Override
    public int getContainedSpaceWidth() {
        return getResources().getDimensionPixelOffset(R.dimen.contained_space_width_trough);
    }

    @Override
    public String getTableName() {
        return LaboratoryDatabaseManager.TROUGH_MAP_HORIZONTAL_TABLE_NAME;
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
