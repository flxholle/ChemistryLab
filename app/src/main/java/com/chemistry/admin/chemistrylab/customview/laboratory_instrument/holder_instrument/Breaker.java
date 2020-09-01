package com.chemistry.admin.chemistrylab.customview.laboratory_instrument.holder_instrument;

import android.content.Context;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.text.SpannableString;

import com.chemistry.admin.chemistrylab.R;
import com.chemistry.admin.chemistrylab.chemical.liquid.Liquid;
import com.chemistry.admin.chemistrylab.database.DatabaseManager;

/**
 * Created by Admin on 8/22/2016.
 */
public class Breaker extends LaboratoryHolderInstrument {
    public final int BREAKER_STANDARD_WIDTH;
    public final int BREAKER_STANDARD_HEIGHT;
    private static final String TAG = "Breaker";
    public final String NAME = getContext().getString(R.string.breaker);
    private static Point[] arrPoint;
    private static Path instrumentPath;

    public Breaker(Context context, int widthView, int heightView) {
        super(context, widthView, heightView);
        BREAKER_STANDARD_HEIGHT = getBreakerStandardHeight(context);
        BREAKER_STANDARD_WIDTH = getBreakerStandardWidth(context);
    }

    public static int getBreakerStandardWidth(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.contained_space_width) + context.getResources().getDimensionPixelOffset(R.dimen.ten_dp) + 2 * getStrokeWidth(context);
    }

    public static int getBreakerStandardHeight(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.contained_space_height) + 2 * getStrokeWidth(context);
    }

    @Override
    public Breaker getClone() {
        Breaker copy = new Breaker(getContext(), BREAKER_STANDARD_WIDTH, BREAKER_STANDARD_HEIGHT);
        if (getDefaultSubstance() != null) {
            copy.addSubstance(getDefaultSubstance().getClone());
        }
        return copy;
    }

    @Override
    protected void createPath(int spaceWidth, int spaceHeight) {
        if (instrumentPath == null) {
            int roundTopCornerDiameter = (spaceWidth - 10) / 10;
            int roundBottomCornerDiameter = (spaceWidth - 10) / 5;
            instrumentPath = new Path();
            instrumentPath.moveTo(0, 0);
            instrumentPath.arcTo(new RectF(-roundTopCornerDiameter / 2,
                            0,
                            -roundTopCornerDiameter / 2 + roundTopCornerDiameter,
                            0 + roundTopCornerDiameter),
                    270, 90);
            instrumentPath.arcTo(new RectF(roundTopCornerDiameter / 2,
                            spaceHeight - roundBottomCornerDiameter,
                            roundTopCornerDiameter / 2 + roundBottomCornerDiameter,
                            spaceHeight - roundBottomCornerDiameter + roundBottomCornerDiameter),
                    180, -90);
            instrumentPath.arcTo(new RectF(spaceWidth - roundBottomCornerDiameter,
                            spaceHeight - roundBottomCornerDiameter,
                            spaceWidth - roundBottomCornerDiameter + roundBottomCornerDiameter,
                            spaceHeight - roundBottomCornerDiameter + roundBottomCornerDiameter),
                    90, -90);
            instrumentPath.lineTo(spaceWidth, 0);

//            DatabaseManager.getInstance(getContext()).insertToDataBase(DatabaseManager.BREAKER_MAP_VERTICAL_TABLE_NAME,PointAnalyzer.analyzePointVertical(instrumentPath));
            arrPoint = DatabaseManager.getInstance(getContext())
                    .getArrayPointOf(DatabaseManager.BREAKER_MAP_VERTICAL_TABLE_NAME);
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
    public Liquid getDefaultSubstance() {
        return getLiquidManager().getSubstance(0);
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
        return DatabaseManager.BREAKER_MAP_HORIZONTAL_TABLE_NAME;
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
