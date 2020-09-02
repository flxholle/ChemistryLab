package com.chemistry.admin.chemistrylab.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chemistry.admin.chemistrylab.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FitPolicy;

/**
 * Created by Admin on 10/19/2016.
 */

public class LectureContentViewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.lecture_content_view_fragment, container, false);
        Bundle receiverBundle = getArguments();
        PDFView pdfView = rootView.findViewById(R.id.pdf_view);
        pdfView.fromAsset(receiverBundle.getString(LectureFragmentMain.KEY_PDF_PATH))
                .defaultPage(1)
                .enableSwipe(true)
                .enableDoubletap(true)
                .pageFitPolicy(FitPolicy.WIDTH) // mode to fit pages in the view
                .landscapeOrientation(true)
                .load();
        pdfView.zoomTo(3.0f);
        return rootView;
    }
}
