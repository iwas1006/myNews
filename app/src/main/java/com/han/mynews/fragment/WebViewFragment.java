package com.han.mynews.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.han.mynews.R;
import com.han.mynews.extend.MovableFloatingActionButton;

public class WebViewFragment extends Fragment {

    public static WebView webView;

    public WebViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_webview, container, false);

        String url = getArguments().getString("url");

        webView = (WebView) v.findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl("about:blank");
        webView.clearFormData();
        webView.clearHistory();

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.loadUrl(url);

        MovableFloatingActionButton snsBtn = (MovableFloatingActionButton) v.findViewById(R.id.snsBtn);
        snsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClipboardManager clipboardManager = (ClipboardManager)getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("label", webView.getOriginalUrl());
                clipboardManager.setPrimaryClip(clipData);

                Snackbar.make(view, "share sns : " + webView.getOriginalUrl(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

}
