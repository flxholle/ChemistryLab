package com.chemistry.admin.chemistrylab.customview.laboratory_instrument.holder_instrument;

import android.content.Context;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.text.SpannableString;

import com.chemistry.admin.chemistrylab.R;
import com.chemistry.admin.chemistrylab.database.DatabaseManager;

/**
 * Created by Admin on 9/6/2016.
 */
public class Flask extends LaboratoryHolderInstrument {
    public final int FLASK_STANDARD_WIDTH;
    public final int FLASK_STANDARD_HEIGHT;
    private static final String TAG = "Flask";
    public final String NAME = getContext().getString(R.string.flask);
    private static Point[] arrPoint;
    private static Path instrumentPath;

    public Flask(Context context, int widthView, int heightView) {
        super(context, widthView, heightView);
        FLASK_STANDARD_WIDTH = getFlaskStandardWidth(context);
        FLASK_STANDARD_HEIGHT = getFlaskStandardHeight(context);
    }

    public static int getFlaskStandardWidth(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.contained_space_width) + 2 * getStrokeWidth(context);
    }

    public static int getFlaskStandardHeight(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.contained_space_height) + 2 * getStrokeWidth(context);
    }

    @Override
    public LaboratoryHolderInstrument getClone() {
        //No default substance, just empty
        return new Flask(getContext(), FLASK_STANDARD_WIDTH, FLASK_STANDARD_HEIGHT);
    }

    @Override
    protected void createPath(int spaceWidth, int spaceHeight) {
        if (instrumentPath == null) {
            instrumentPath = new Path();
            float bottleNeckWidth = spaceWidth / 3.125f;//64
            float bottleNeckHeight = spaceHeight / 3;//>100
            float angle = (float) Math.toDegrees(Math.acos(bottleNeckWidth / spaceWidth));
            float startAngle = 180 + angle;
            float sweepAngle = -(startAngle + angle);
            instrumentPath.moveTo((spaceWidth - bottleNeckWidth) / 2, 0);
            instrumentPath.lineTo((spaceWidth - bottleNeckWidth) / 2, bottleNeckHeight);
            instrumentPath.arcTo(new RectF(0, bottleNeckHeight, spaceWidth, spaceHeight),
                                startAngle, sweepAngle);
            instrumentPath.lineTo((spaceWidth - bottleNeckWidth) / 2 + bottleNeckWidth, 0);

            arrPoint = DatabaseManager.getInstance(getContext())
                    .getArrayPointOf(DatabaseManager.FLASK_MAP_VERTICAL_TABLE_NAME);
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
        return DatabaseManager.FLASK_MAP_HORIZONTAL_TABLE_NAME;
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
