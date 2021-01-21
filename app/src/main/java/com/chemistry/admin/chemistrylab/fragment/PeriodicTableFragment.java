package com.chemistry.admin.chemistrylab.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.chemistry.admin.chemistrylab.R;
import com.chemistry.admin.chemistrylab.database.ReactionsDatabaseManager;
import com.chemistry.admin.chemistrylab.tooltip.ElementToolTip;
import com.chemistry.admin.chemistrylab.util.SymbolConverter;
import com.michael.easydialog.EasyDialog;

/**
 * Created by Admin on 10/14/2016.
 */
public class PeriodicTableFragment extends Fragment implements EasyDialog.OnEasyDialogDismissed, View.OnClickListener {
    private static final String TAG = "PTRagment";
    private ElementToolTip toolTip;
    private int fragmentWidth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.periodic_table_layout, container, false);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                fragmentWidth = rootView.getWidth();
                rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        Context context = getActivity();
        String[] arrElementName = ReactionsDatabaseManager.getInstance(context).getAllElementSymbol();
        String packageName = context.getPackageName();
        Resources res = context.getResources();
        for (String anElementName : arrElementName) {
            rootView.findViewById(res.getIdentifier("btn_" + anElementName, "id", packageName))
                    .setOnClickListener(this);
        }
        toolTip = new ElementToolTip(context);
        return rootView;
    }

    @Override
    public void onDismissed() {
        View contentView = toolTip.getRootView();
        ((LinearLayout) contentView.getParent()).removeView(contentView);
    }

    @Override
    public void onClick(View v) {
        Context context = getActivity();
        Button button = (Button) v;
        ElementItem item = ReactionsDatabaseManager.getInstance(context).getElement((button.getText().toString()));

        toolTip.setData(item);

        int easyDialogOrient;
        int widthDialog = toolTip.getRootView().getMeasuredWidth();
        int heightDialog = toolTip.getRootView().getMeasuredHeight();
        int xView = (int) v.getX();
        int yView = (int) v.getY();
        easyDialogOrient = EasyDialog.GRAVITY_TOP;
        if (yView - heightDialog < 0) {
            if (xView + widthDialog > fragmentWidth) {
                easyDialogOrient = EasyDialog.GRAVITY_LEFT;
            } else if (xView - widthDialog < 0) {
                easyDialogOrient = EasyDialog.GRAVITY_RIGHT;
            } else {
                easyDialogOrient = EasyDialog.GRAVITY_BOTTOM;
            }
        }

        new EasyDialog(context)
                .setLayout(toolTip.getRootView())
                .setLocationByAttachedView(v)
                .setGravity(easyDialogOrient)
                .setMatchParent(false)
                .setBackgroundColor(Color.WHITE)
                .setOnEasyDialogDismissed(this)
                .setAnimationAlphaShow(400, 0.3f, 1.0f)
                .setAnimationTranslationShow(EasyDialog.DIRECTION_Y, 300, -100, 0)
                .setAnimationAlphaDismiss(300, 1.0f, 0.0f)
                .show();
    }

    public static class ElementItem {
        private final String name;
        private final double atomicMass;
        private final int atomicNumber;
        private final String symbol;
        private final int boiling;
        private final int melting;
        private final int groups;
        private final String electronConfig;
        private final double electronicGravity;
        private final String oxidationStates;

        public ElementItem(String name,
                           String symbol,
                           int boiling,
                           int melting,
                           int groups,
                           double atomicMass,
                           int atomicNumber,
                           String electronConfig,
                           double electronicGravity,
                           String oxidationStates) {
            this.name = name;
            this.symbol = symbol;
            this.boiling = boiling;
            this.melting = melting;
            this.groups = groups;
            this.atomicMass = atomicMass;
            this.atomicNumber = atomicNumber;
            this.electronConfig = electronConfig;
            this.electronicGravity = electronicGravity;
            this.oxidationStates = oxidationStates;
        }

        public String getName() {
            return name;
        }

        public double getAtomicMass() {
            return atomicMass;
        }

        public int getAtomicNumber() {
            return atomicNumber;
        }

        public String getSymbol() {
            return symbol;
        }

        public CharSequence getElectronConfig() {
            return SymbolConverter.getElectronConfig(electronConfig);
        }

        public double getElectronicGravity() {
            return electronicGravity;
        }

        public String getOxidationStates() {
            return oxidationStates;
        }


    }
}
