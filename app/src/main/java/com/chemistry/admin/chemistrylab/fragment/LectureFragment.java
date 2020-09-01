package com.chemistry.admin.chemistrylab.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chemistry.admin.chemistrylab.R;
import com.chemistry.admin.chemistrylab.activity.DocumentActivity;
import com.chemistry.admin.chemistrylab.adapter.LectureAdapter;

/**
 * Created by Admin on 10/19/2016.
 */

public class LectureFragment extends Fragment implements AdapterView.OnItemClickListener {
    public static final String KEY_PDF_PATH = "KEY_PDF_PATH";
    public static final String LITERATURE_DOWNLOAD_LINK = "https://github.com/asdoi/ChemistryLab/raw/reduce_size/literature/";

    private static final String credits = "Chemistry 2e\n" +
            "\n" +
            "Senior Contributing Authors:\n" +
            "Paul Flowers, University of North Carolina at Pembroke\n" +
            "Klaus Theopold, University of Delaware\n" +
            "Richard Langley, Stephen F. Austin State University\n" +
            "William R. Robinson, PhD\n" +
            "\n" +
            "Contributing Authors:\n" +
            "Don Frantz, Wilfrid Laurier University\n" +
            "Paul Hooker, Westminster College\n" +
            "George Kaminski, Worcester Polytechnic Institute\n" +
            "Jennifer Look, Mercer University\n" +
            "Carol Martinez, Central New Mexico Community College\n" +
            "Andrew Eklund, Alfred University\n" +
            "Mark Blaser, Shasta College\n" +
            "Tom Sorensen, University of Wisconsin-Milwaukee\n" +
            "Allison Soult, University of Kentucky\n" +
            "Troy Milliken, Jackson State University\n" +
            "Vicki Moravec, Trine University\n" +
            "Jason Powell, Ferrum College\n" +
            "Emad El-Giar, University of Louisiana at Monroe\n" +
            "Simon Bott, University of Houston\n" +
            "Don Carpenetti, Craven Community College\n" +
            "\n" +
            "(c) Copyright: openstax\n" +
            "by OpenStax is licensed under Creative Commons Attribution License v4.0\n" +
            "\n" +
            "Link: https://openstax.org/details/books/chemistry-2e\n" +
            "\n" +
            "Edits: The book was cut into some smaller pdfs.\n" +
            "\n" +
            "More Information:\n" +
            "\n" +
            "Publish Date:\n" +
            "Feb 14, 2019\n" +
            "\n" +
            "Web Version Last Updated:\n" +
            "Jan 09, 2020\n" +
            "\n" +
            "Hardcover:\n" +
            "ISBN-10: 1-947172-62-X\n" +
            "ISBN-13: 978-1-947172-62-3\n" +
            "\n" +
            "Paperback:\n" +
            "ISBN-13: 978-1-59399-578-2\n" +
            "\n" +
            "Digital:\n" +
            "ISBN-10: 1-947172-61-1\n" +
            "ISBN-13: 978-1-947172-61-6";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.lecture_fragment, container, false);
        ListView listViewLecture = rootView.findViewById(R.id.list_view_lecture);
        listViewLecture.setOnItemClickListener(this);
        listViewLecture.setAdapter(new LectureAdapter(getActivity()));

        rootView.findViewById(R.id.credits).setOnClickListener((view) -> {
            new AlertDialog.Builder(requireContext())
                    .setPositiveButton(R.string.okay, (d, w) -> {
                        d.dismiss();
                    })
                    .setTitle(R.string.credits)
                    .setMessage(credits)
                    .show();
            view.setVisibility(View.GONE);
        });

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String path = ((LectureAdapter)parent.getAdapter()).getItem(position).getPath();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
        startActivity(browserIntent);
    }
}
