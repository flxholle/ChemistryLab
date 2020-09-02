package com.chemistry.admin.chemistrylab.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Admin on 8/10/2016.
 */
public class LaboratoryDatabaseManager {
    public static final String TAG = "LaboratoryDatabase";
    private static final String APP_DATA_PATH = Environment.getDataDirectory().getPath() + "/data/com.chemistry.admin.chemistrylab/";

    private static final String DATABASE_FOLDER_NAME = "database";
    private static final String DATABASE_NAME = "laboratory.db";
    private static final String DATABASE_DATA_PATH = Environment.getDataDirectory().getPath() + "/data/com.chemistry.admin.chemistrylab/" + DATABASE_FOLDER_NAME + "/" + DATABASE_NAME;

    public static final String BREAKER_MAP_VERTICAL_TABLE_NAME = "breaker_map_vertical";
    public static final String BREAKER_MAP_HORIZONTAL_TABLE_NAME = "breaker_map_horizontal";
    public static final String JAR_MAP_VERTICAL_TABLE_NAME = "jar_map_vertical";
    public static final String JAR_MAP_HORIZONTAL_TABLE_NAME = "jar_map_horizontal";
    public static final String GAS_BOTTLE_MAP_VERTICAL_TABLE_NAME = "gas_bottle_map_vertical";
    public static final String GAS_BOTTLE_MAP_HORIZONTAL_TABLE_NAME = "gas_bottle_map_horizontal";
    public static final String FLASK_MAP_VERTICAL_TABLE_NAME = "flask_map_vertical";
    public static final String FLASK_MAP_HORIZONTAL_TABLE_NAME = "flask_map_horizontal";
    public static final String TEST_TUBE_MAP_VERTICAL_TABLE_NAME = "test_tube_map_vertical";
    public static final String TEST_TUBE_MAP_HORIZONTAL_TABLE_NAME = "test_tube_map_horizontal";
    public static final String TROUGH_MAP_VERTICAL_TABLE_NAME = "trough_map_vertical";
    public static final String TROUGH_MAP_HORIZONTAL_TABLE_NAME = "trough_map_horizontal";
    public static final String CONICAL_FLASK_MAP_VERTICAL_TABLE_NAME = "conical_flask_map_vertical";
    public static final String CONICAL_FLASK_MAP_HORIZONTAL_TABLE_NAME = "conical_flask_map_horizontal";
    public static final String KEY_X = "x";
    public static final String KEY_X_START = "xStart";
    public static final String KEY_X_END = "xEnd";
    public static final String KEY_Y = "y";

    private final Context context;
    private SQLiteDatabase database;
    public static LaboratoryDatabaseManager instance;

    public static LaboratoryDatabaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new LaboratoryDatabaseManager(context);
            return instance;
        }
        return instance;
    }

    private LaboratoryDatabaseManager(Context context) {
        this.context = context;
        copyDataToInternalStorage(DATABASE_FOLDER_NAME, DATABASE_NAME);
    }

    private void copyDataToInternalStorage(String folderName, String fileName) {
        if (!folderName.isEmpty()) {
            File file = new File(APP_DATA_PATH + folderName + "/");
            file.mkdir();
        }
        File fileDatabase = new File(APP_DATA_PATH + folderName + "/" + fileName);
        if (fileDatabase.exists()) {
            return;
        }
        try {
            DataInputStream inputStream = new DataInputStream(context.getAssets().open(folderName + "/" + fileName));
            DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(fileDatabase));
            byte[] buff = new byte[1024];
            int length;
            while ((length = inputStream.read(buff)) != -1) {
                outputStream.write(buff, 0, length);
            }
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openDatabase() {
        if (database == null || !database.isOpen()) {
            database = SQLiteDatabase.openDatabase(DATABASE_DATA_PATH, null, SQLiteDatabase.OPEN_READWRITE);
        }
    }

    private void closeDatabase() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

    //Length of Array is Width of Instrument IN PX!
    public Point[] getArrayPointOf(String tableName) {
        openDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + tableName, null);
        Point[] result = new Point[cursor.getCount()];
        int i = 0;
        cursor.moveToFirst();
        int xStartColumnIndex = cursor.getColumnIndex(KEY_X_START);
        int xEndColumnIndex = cursor.getColumnIndex(KEY_X_END);
        while (!cursor.isAfterLast()) {
            result[i++] = new Point(cursor.getInt(xStartColumnIndex),
                    cursor.getInt(xEndColumnIndex));
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return convertArrayPointResolution(result);
    }

    private Point[] convertArrayPointResolution(Point[] points) {
        int lengthDp = points.length;
        int lenthPx = (int) convertDpToPixel(lengthDp);
        float factor = ((float) lenthPx) / lengthDp;
        int factorInt = Math.round(factor);
        ArrayList<Point> pointsPx = new ArrayList<>();
        for (int i = 1; i <= lengthDp; i++) {
            for (int j = 0; j < factorInt; j++) {
                pointsPx.add(points[i - 1]);
            }
        }
        int pointsPxSize = pointsPx.size();
        for (int i = pointsPxSize; i >= lenthPx; i--) {
            pointsPx.remove(lenthPx);
        }
        return pointsPx.toArray(new Point[]{});
    }

    public int getYByX(String mapHorizontalTableName, int x) {
        openDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + mapHorizontalTableName +
                " WHERE " + KEY_X + " = " + x, null);
        if (cursor.getCount() != 1) {
            Log.e(TAG, "method getYByX(): database data error, value: " + x);
            return -1;
        }
        cursor.moveToFirst();
        int result = cursor.getInt(cursor.getColumnIndex(KEY_Y));
        cursor.close();
        closeDatabase();
        return result;
    }
//    public int[] getXByY(String mapVerticalTableName, int y) {
//        openDatabase();
//        Cursor cursor = database.rawQuery("SELECT * FROM " + mapVerticalTableName + " WHERE " + KEY_Y + " = " + y, null);
//        if (cursor.getCount() != 1) {
//            Log.e(TAG, "method getXByY(): database data error");
//            return null;
//        }
//        cursor.moveToFirst();
//        int result[] = new int[2];
//        int xColumnIndex = cursor.getColumnIndex(KEY_X);
//        result[0] = cursor.getInt(xColumnIndex);
//        cursor.moveToNext();
//        result[1] = cursor.getInt(xColumnIndex);
//        cursor.close();
//        closeDatabase();
//        return result;
//    }
//
//    public void insertToDataBase(String tableName, Point point[]) {
//        openDatabase();
//        for (Point aPoint : point) {
////            database.execSQL("INSERT INTO " + tableName + " (" + KEY_X + ", " + KEY_Y + ") VALUES (" + aPoint.x + ", " + aPoint.y + ")", null);
//            ContentValues value = new ContentValues();
////            value.put(KEY_X,aPoint.x);
////            value.put(KEY_Y,aPoint.y);
//            value.put("xStart", aPoint.x);
//            value.put("xEnd", aPoint.y);
//            database.insert(tableName, null, value);
////            if (result != -1) {
////                Log.i(TAG, "Insert completed");
////            } else {
////                Log.i(TAG, "Insert error");
////            }
//        }
//        closeDatabase();
//    }

    private float convertDpToPixel(float dp) {
        return convertDpToPixel(dp, context);
    }

    private float convertPixelsToDp(float px) {
        return convertPixelsToDp(px, context);
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
