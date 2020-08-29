package com.chemistry.admin.chemistrylab.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.chemistry.admin.chemistrylab.R;
import com.chemistry.admin.chemistrylab.fragment.CategoryFragment;

/**
 * Created by Admin on 10/13/2016.
 */

public class DocumentActivity extends AppCompatActivity {
    private static final String TAG = "DocumentActivity";
    private CategoryFragment categoryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.document_activity);
        initViews();
    }

    private void initViews() {
        categoryFragment = new CategoryFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.ll_main, categoryFragment)
                .commit();
    }

    public void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .hide(categoryFragment)
                .setCustomAnimations(android.R.animator.fade_in,
                                    android.R.animator.fade_out,
                                    android.R.animator.fade_in,
                                    android.R.animator.fade_out)
                .add(R.id.ll_main, fragment).addToBackStack("BACK_TO_CATEGORY")
                .show(fragment)
                .commit();
    }
}
