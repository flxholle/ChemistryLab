package com.chemistry.admin.chemistrylab.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.chemistry.admin.chemistrylab.R;
import com.chemistry.admin.chemistrylab.adapter.ListHintAdapter;

import java.util.Locale;

import im.delight.android.webview.AdvancedWebView;

/**
 * Created by Admin on 10/13/2016.
 */

public class SearchFragment extends Fragment implements View.OnClickListener, TextWatcher, AdapterView.OnItemClickListener {
    private EditText searchBar;
    private ListView listHint;
    private ListHintAdapter adapter;
    private Button mButton;
    private AdvancedWebView webView;
    private ProgressBar loadingBar;
    private ImageButton buttonSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_fragment, container, false);
        searchBar = rootView.findViewById(R.id.edt_search_bar);
        searchBar.addTextChangedListener(this);
        listHint = rootView.findViewById(R.id.list_hint);
        listHint.setOnItemClickListener(this);
        adapter = new ListHintAdapter(getActivity());
        listHint.setAdapter(adapter);
        buttonSearch = rootView.findViewById(R.id.btn_search);
        buttonSearch.setOnClickListener(this);

        webView = rootView.findViewById(R.id.web_view);
        webView.setMixedContentAllowed(true);
        webView.setDesktopMode(false);
        AdvancedWebView.Listener listener = new AdvancedWebView.Listener() {
            @Override
            public void onPageStarted(String url, Bitmap favicon) {
                loadingBar.setVisibility(View.VISIBLE);
                listHint.setVisibility(View.INVISIBLE);
                buttonSearch.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onPageFinished(String url) {
                if (webView.getVisibility() == View.INVISIBLE) {
                    webView.setVisibility(View.VISIBLE);
                }
                loadingBar.setVisibility(View.INVISIBLE);
                buttonSearch.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageError(int errorCode, String description, String failingUrl) {

            }

            @Override
            public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {
                if (AdvancedWebView.handleDownload(requireContext(), url, suggestedFilename)) {
                    // download successfully handled
                    Toast.makeText(requireContext(), R.string.download_succeeded, Toast.LENGTH_LONG).show();
                } else {
                    // download couldn't be handled because user has disabled download manager app on the device
                    Toast.makeText(requireContext(), R.string.download_failed, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onExternalPageRequest(String url) {

            }
        };
        webView.setListener(requireActivity(), listener);

        loadingBar = rootView.findViewById(R.id.loading_bar);

        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mButton.getBackground().setLevel(0);
        searchBar.setText("");
        adapter.clearListItems();
        listHint.setVisibility(View.INVISIBLE);
    }

    public void setmButton(Button mButton) {
        this.mButton = mButton;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_search) {
            adapter.clearListItems();
            String content = searchBar.getText().toString().toLowerCase(Locale.getDefault());
            String languageCode = Locale.getDefault().getLanguage();
            webView.loadUrl("https://" + languageCode + ".wikipedia.org/wiki/" + content);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListHintAdapter adapter = (ListHintAdapter) parent.getAdapter();
        String content = adapter.getItem(position).getName();
        searchBar.setText(content);
        adapter.clearListItems();
        listHint.setVisibility(View.INVISIBLE);
        buttonSearch.performClick();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String lowerText = s.toString().toLowerCase(Locale.getDefault());
        if (lowerText.isEmpty()) {
            listHint.setVisibility(View.INVISIBLE);
        } else {
            listHint.setVisibility(View.VISIBLE);
        }
        adapter.search(s.toString());
    }
}
