package com.chemistry.admin.chemistrylab.tooltip;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chemistry.admin.chemistrylab.R;
import com.chemistry.admin.chemistrylab.adapter.ListSubstancesPreviewAdapter;
import com.chemistry.admin.chemistrylab.chemical.Substance;
import com.chemistry.admin.chemistrylab.database.DatabaseManager;

/**
 * Created by Admin on 10/12/2016.
 */
public class PreviewTip extends RelativeLayout implements SeekBar.OnSeekBarChangeListener {
    private static final String TAG = "PreviewTip";
    private TextView textSubstanceName;
    private TextView textSubstanceM;
    private TextView textSubstanceDensity;
    private TextView textSubstanceMole;
    private SeekBar seekBarMole;
    private final ListSubstancesPreviewAdapter adapter;
    private Substance baseSubstance;


    public PreviewTip(Context context, ListSubstancesPreviewAdapter adapter) {
        super(context);
        this.adapter = adapter;
        initView(context);
        setLayoutParams(new LayoutParams(getResources().getDimensionPixelOffset(R.dimen.preview_tip_width), RelativeLayout.LayoutParams.WRAP_CONTENT));
    }

    private void initView(Context context) {
        View rootView = View.inflate(context, R.layout.substance_preview_tip, this);
        textSubstanceName = rootView.findViewById(R.id.txt_substance_name);
        textSubstanceM = rootView.findViewById(R.id.txt_M);
        textSubstanceDensity = rootView.findViewById(R.id.txt_density);
        textSubstanceMole = rootView.findViewById(R.id.txt_mole);
        seekBarMole = rootView.findViewById(R.id.seek_bar_mole);
        seekBarMole.setOnSeekBarChangeListener(this);
    }

    public void setSubstance(Substance substance) {
        this.baseSubstance = substance;
        int maxMole = (int) substance.getMaxMoleInHolder() * 100;
        seekBarMole.setMax(maxMole - 1);
        int mole = (int) Math.round(substance.getMole() * 100);
        seekBarMole.setProgress(mole - 1);

        textSubstanceName.setText(substance.getName());
        textSubstanceM.setText(String.valueOf(substance.getM()));
        textSubstanceDensity.setText(String.valueOf(substance.getDensity()));
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        textSubstanceMole.setText(String.valueOf((i + 1) * 1.0 / 100));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        double moleChanged = (seekBar.getProgress() + 1) * 1.0 / 100 - baseSubstance.getMole();
        if(moleChanged > 0){
            baseSubstance.addAmount(getContext(), moleChanged);
        }else {
            baseSubstance.reduceAmount(getContext(), -moleChanged);
        }
        baseSubstance.getTip().update();
        DatabaseManager.getInstance(getContext()).updateWeightOrVolumeOf(baseSubstance);
        adapter.notifyDataSetChanged();
    }
}
