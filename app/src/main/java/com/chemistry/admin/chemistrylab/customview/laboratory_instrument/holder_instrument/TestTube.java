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
public class TestTube extends LaboratoryHolderInstrument {
    public final int TEST_TUBE_STANDARD_WIDTH;
    public final int TEST_TUBE_STANDARD_HEIGHT;
    private static final String TAG = "TestTube";
    public final String NAME = getContext().getString(R.string.test_tube);
    private static Point[] arrPoint;
    private static Path instrumentPath;

    public TestTube(Context context, int widthView, int heightView) {
        super(context, widthView, heightView);
        TEST_TUBE_STANDARD_WIDTH = getTestTubetStandardWidth(context);
        TEST_TUBE_STANDARD_HEIGHT = getTestTubeStandardHeight(context);
    }

    public static int getTestTubetStandardWidth(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.contained_space_width_test_tube) + 2 * getStrokeWidth(context);
    }

    public static int getTestTubeStandardHeight(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.contained_space_height) + 2 * getStrokeWidth(context);
    }

    @Override
    public LaboratoryHolderInstrument getClone() {
        //No default substance, just empty
        return new TestTube(getContext(), TEST_TUBE_STANDARD_WIDTH, TEST_TUBE_STANDARD_HEIGHT);
    }

    @Override
    protected void createPath(int spaceWidth, int spaceHeight) {
        if (instrumentPath == null) {
            instrumentPath = new Path();
            instrumentPath.moveTo(0, 0);
            instrumentPath.lineTo(0, spaceHeight - spaceWidth);
            instrumentPath.arcTo(new RectF(0,
                                            spaceHeight - spaceWidth,
                                            spaceWidth,
                                            spaceHeight),
                                180, -180);
            instrumentPath.lineTo(spaceWidth, 0);

            arrPoint = DatabaseManager.getInstance(getContext())
                    .getArrayPointOf(DatabaseManager.TEST_TUBE_MAP_VERTICAL_TABLE_NAME);
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
        return getResources().getDimensionPixelOffset(R.dimen.contained_space_width_test_tube);
    }

    @Override
    public String getTableName() {
        return DatabaseManager.TEST_TUBE_MAP_HORIZONTAL_TABLE_NAME;
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
