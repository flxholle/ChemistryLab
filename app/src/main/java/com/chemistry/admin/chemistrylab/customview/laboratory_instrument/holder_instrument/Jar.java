package com.chemistry.admin.chemistrylab.customview.laboratory_instrument.holder_instrument;

import android.content.Context;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.text.SpannableString;

import com.chemistry.admin.chemistrylab.R;
import com.chemistry.admin.chemistrylab.chemical.solid.Solid;
import com.chemistry.admin.chemistrylab.database.DatabaseManager;

/**
 * Created by Admin on 8/26/2016.
 */
public class Jar extends LaboratoryHolderInstrument {
    public final int JAR_STANDARD_WIDTH;
    public final int JAR_STANDARD_HEIGHT;
    private static final String TAG = "Jar";
    public final String NAME = getContext().getString(R.string.jar);
    private static Point[] arrPoint;
    private static Path instrumentPath;


    public Jar(Context context, int widthView, int heightView) {
        super(context, widthView, heightView);
        JAR_STANDARD_WIDTH = getJarStandardWidth(context);
        JAR_STANDARD_HEIGHT = getJarStandardHeight(context);
    }

    public static int getJarStandardWidth(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.contained_space_width) + 2 * getStrokeWidth(context);
    }

    public static int getJarStandardHeight(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.contained_space_height) + 2 * getStrokeWidth(context);
    }

    @Override
    public LaboratoryHolderInstrument getClone() {
        Jar copy = new Jar(getContext(), JAR_STANDARD_WIDTH, JAR_STANDARD_HEIGHT);
        if (getDefaultSubstance() != null) {
            copy.addSubstance(getDefaultSubstance().getClone());
        }
        return copy;
    }

    @Override
    protected void createPath(int spaceWidth, int spaceHeight) {
        if (instrumentPath == null) {
            instrumentPath = new Path();
            float bottleNeckWidth = spaceWidth / 2;
            float bottleNeckHeight = spaceHeight / 12;
            float roundBottomCornerDiameter = spaceWidth / 5;

            instrumentPath.moveTo((spaceWidth - bottleNeckWidth) / 2, 0);
            instrumentPath.lineTo((spaceWidth - bottleNeckWidth) / 2, bottleNeckHeight);
            instrumentPath.arcTo(new RectF(0,
                                            bottleNeckHeight,
                                            (spaceWidth - bottleNeckWidth) / 2 * 2,
                                            bottleNeckHeight + (spaceWidth - bottleNeckWidth) / 2 * 2),
                                270, -90);
            instrumentPath.arcTo(new RectF(0,
                                            spaceHeight - roundBottomCornerDiameter,
                                            0 + roundBottomCornerDiameter,
                                            spaceHeight - roundBottomCornerDiameter + roundBottomCornerDiameter),
                                180, -90);
            instrumentPath.arcTo(new RectF(spaceWidth - roundBottomCornerDiameter,
                                            spaceHeight - roundBottomCornerDiameter,
                                            spaceWidth - roundBottomCornerDiameter + roundBottomCornerDiameter,
                                            spaceHeight - roundBottomCornerDiameter + roundBottomCornerDiameter),
                                90, -90);
            instrumentPath.arcTo(new RectF(bottleNeckWidth,
                                            bottleNeckHeight,
                                            (spaceWidth - bottleNeckWidth) / 2 * 2 + bottleNeckWidth,
                                            bottleNeckHeight + (spaceWidth - bottleNeckWidth) / 2 * 2),
                                0, -90);
            instrumentPath.lineTo((spaceWidth - bottleNeckWidth) / 2 + bottleNeckWidth, 0);

//          DatabaseManager.getInstance(getContext()).insertToDataBase(DatabaseManager.JAR_MAP_VERTICAL_TABLE_NAME,PointAnalyzer.analyzePointVertical(instrumentPath));
            arrPoint = DatabaseManager.getInstance(getContext())
                    .getArrayPointOf(DatabaseManager.JAR_MAP_VERTICAL_TABLE_NAME);
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
    public Solid getDefaultSubstance() {
        return getSolidManager().getSubstance(0);
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
        return DatabaseManager.JAR_MAP_HORIZONTAL_TABLE_NAME;
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
