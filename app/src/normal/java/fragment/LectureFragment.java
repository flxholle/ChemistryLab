package com.chemistry.admin.chemistrylab.fragment;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;

import com.chemistry.admin.chemistrylab.adapter.LectureAdapter;

/**
 * Created by Admin on 10/19/2016.
 */

public class LectureFragment extends LectureFragmentMain implements AdapterView.OnItemClickListener {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String path = ((LectureAdapter) parent.getAdapter()).getItem(position).getPath();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(LITERATURE_DOWNLOAD_LINK + path));
        startActivity(browserIntent);
    }
}