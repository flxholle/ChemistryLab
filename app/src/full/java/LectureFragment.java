package com.chemistry.admin.chemistrylab.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.chemistry.admin.chemistrylab.R;
import com.chemistry.admin.chemistrylab.activity.DocumentActivity;
import com.chemistry.admin.chemistrylab.adapter.LectureAdapter;

/**
 * Created by Admin on 10/19/2016.
 */

public class LectureFragment extends LectureFragmentMain implements AdapterView.OnItemClickListener {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String path = ((LectureAdapter) parent.getAdapter()).getItem(position).getPath();
        Bundle sendBundle = new Bundle();
        sendBundle.putString(KEY_PDF_PATH, path);
        LectureContentViewFragment lectureContentViewFragment = new LectureContentViewFragment();
        lectureContentViewFragment.setArguments(sendBundle);
        DocumentActivity documentActivity = (DocumentActivity) getActivity();
        documentActivity.getSupportFragmentManager().beginTransaction()
                .hide(this)
                .add(R.id.ll_main, lectureContentViewFragment)
                .show(lectureContentViewFragment).addToBackStack("BACK_TO_LECTURE_LIST")
                .commit();
//        ((DocumentActivity)getActivity()).showFragment(lectureContentViewFragment);

    }
}
