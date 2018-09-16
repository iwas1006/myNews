package com.han.mynews.fragment;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.ag.floatingactionmenu.OptionsFabLayout;
import com.han.mynews.R;
import com.han.mynews.activity.MainActivity;
import com.han.mynews.db.DBHelper;
import com.han.mynews.extend.MovableFloatingActionButton;
import com.han.mynews.task.BookImageTask;
import com.han.mynews.task.BookMarkTask;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WebViewFragment extends Fragment {

    public static WebView webView;

    private OptionsFabLayout fabWithOptions;

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

        initFabActionMenu(v);

      /*  MovableFloatingActionButton snsBtn = (MovableFloatingActionButton) v.findViewById(R.id.snsBtn);
        snsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClipboardManager clipboardManager = (ClipboardManager)getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("label", webView.getOriginalUrl());
                clipboardManager.setPrimaryClip(clipData);

                Snackbar.make(view, "share sns : " + webView.getOriginalUrl(), Snackbar.LENGTH_LONG).setAction("Action", null).show();

                new BookMarkTask(getContext()).execute(webView.getOriginalUrl());

            }
        });*/

       /* final ImageView icon = new ImageView(v.getContext()); // Create an icon
        icon.setImageResource(R.drawable.ic_action_new_light);

        final ImageView fabIconNew = new ImageView(v.getContext());
        fabIconNew.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_new_light, null));
        final FloatingActionButton rightLowerButton = new FloatingActionButton.Builder(getActivity())
                .setContentView(fabIconNew)
                .build();

        rightLowerButton.setOnTouchListener(new View.OnTouchListener() {
            private final static float CLICK_DRAG_TOLERANCE = 10; // Often, there will be a slight, unintentional, drag when the user taps the FAB, so we need to account for this.

            private float downRawX, downRawY;
            private float dX, dY;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent){

                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {

                    downRawX = motionEvent.getRawX();
                    downRawY = motionEvent.getRawY();
                    dX = view.getX() - downRawX;
                    dY = view.getY() - downRawY;

                    return true; // Consumed

                } else if (action == MotionEvent.ACTION_MOVE) {

                    int viewWidth = view.getWidth();
                    int viewHeight = view.getHeight();

                    View viewParent = (View)view.getParent();
                    int parentWidth = viewParent.getWidth();
                    int parentHeight = viewParent.getHeight();

                    float newX = motionEvent.getRawX() + dX;
                    newX = Math.max(0, newX); // Don't allow the FAB past the left hand side of the parent
                    newX = Math.min(parentWidth - viewWidth, newX); // Don't allow the FAB past the right hand side of the parent

                    float newY = motionEvent.getRawY() + dY;
                    newY = Math.max(0, newY); // Don't allow the FAB past the top of the parent
                    newY = Math.min(parentHeight - viewHeight, newY); // Don't allow the FAB past the bottom of the parent

                    view.animate()
                            .x(newX)
                            .y(newY)
                            .setDuration(0)
                            .start();

                    return true; // Consumed

                } else if (action == MotionEvent.ACTION_UP) {

                    float upRawX = motionEvent.getRawX();
                    float upRawY = motionEvent.getRawY();

                    float upDX = upRawX - downRawX;
                    float upDY = upRawY - downRawY;

                    if (Math.abs(upDX) < CLICK_DRAG_TOLERANCE && Math.abs(upDY) < CLICK_DRAG_TOLERANCE) { // A click
                        return view.performClick();
                    }
                    else { // A drag
                        return true; // Consumed
                    }

                } else {
                    return view.onTouchEvent(motionEvent);
                }
            }
        });

        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(getActivity());
        ImageView rlIcon1 = new ImageView(v.getContext());
        ImageView rlIcon2 = new ImageView(v.getContext());
        ImageView rlIcon3 = new ImageView(v.getContext());
        ImageView rlIcon4 = new ImageView(v.getContext());


        rlIcon1.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_chat_light));
        rlIcon2.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_camera_light));
        rlIcon3.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_video_light));
        rlIcon4.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_place_light));

        //clipboard
        rlIcon1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboardManager = (ClipboardManager)getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("label", webView.getOriginalUrl());
                    clipboardManager.setPrimaryClip(clipData);
                    Snackbar.make(v, "to clipboard : " + webView.getOriginalUrl(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
        });

        //bookmark
        rlIcon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BookMarkTask(getContext()).execute(webView.getOriginalUrl());
                Snackbar.make(v, "saved to bookmark : " + webView.getOriginalUrl(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        //snackbar
        rlIcon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "clicked : " + webView.getOriginalUrl(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        //all
        rlIcon4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager)getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("label", webView.getOriginalUrl());
                clipboardManager.setPrimaryClip(clipData);

                Snackbar.make(v, "share sns : " + webView.getOriginalUrl(), Snackbar.LENGTH_LONG).setAction("Action", null).show();

                new BookMarkTask(getContext()).execute(webView.getOriginalUrl());
            }
        });


        // Build the menu with default options: light theme, 90 degrees, 72dp radius.
        // Set 4 default SubActionButtons
        final FloatingActionMenu rightLowerMenu = new FloatingActionMenu.Builder(getActivity())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon1).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon2).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon3).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon4).build())
                .attachTo(rightLowerButton)
                .build();

        // Listen menu open and close events to animate the button content view
        rightLowerMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees clockwise
                fabIconNew.setRotation(0);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
                animation.start();
            }

            @Override
            public void onMenuClosed(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees counter-clockwise
                fabIconNew.setRotation(45);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
                animation.start();
            }


        });*/


        // Inflate the layout for this fragment
        return v;
    }

    private void initFabActionMenu(View v) {
        final View view = v;
        fabWithOptions = (OptionsFabLayout) view.findViewById(R.id.fab_l);

        //Set mini fab's colors.
        fabWithOptions.setMiniFabsColors (
                R.color.colorPrimary,
                R.color.green_fab);

        //Set main fab clicklistener.
        fabWithOptions.setMainFabOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fabWithOptions.isOptionsMenuOpened())
                    fabWithOptions.closeOptionsMenu();

                //Toast.makeText(getActivity(), "Main fab clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        //Set mini fabs clicklisteners.
        fabWithOptions.setMiniFabSelectedListener(new OptionsFabLayout.OnMiniFabSelectedListener() {
            @Override
            public void onMiniFabSelected(MenuItem fabItem) {
                switch (fabItem.getItemId()) {
                    case R.id.fab_link:
                        ClipboardManager clipboardManager = (ClipboardManager)getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
                        ClipData clipData = ClipData.newPlainText("label", webView.getOriginalUrl());
                        clipboardManager.setPrimaryClip(clipData);

                        Snackbar.make(view, "saved to clipboard : " + webView.getOriginalUrl(), Snackbar.LENGTH_LONG).setAction("Action", null).show();

                        break;
                    case R.id.fab_add:

                        new BookMarkTask(getContext()).execute(webView.getOriginalUrl());
                        Snackbar.make(view, "saved to bookmark : " + webView.getOriginalUrl(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        break;
                    default:
                        break;
                }
            }
        });

      /*  fabWithOptions.setOnTouchListener(new OptionsFabLayout.OnTouchListener() {
            private final static float CLICK_DRAG_TOLERANCE = 10; // Often, there will be a slight, unintentional, drag when the user taps the FAB, so we need to account for this.

            private float downRawX, downRawY;
            private float dX, dY;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent){

                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {

                    downRawX = motionEvent.getRawX();
                    downRawY = motionEvent.getRawY();
                    dX = view.getX() - downRawX;
                    dY = view.getY() - downRawY;

                    return true; // Consumed

                } else if (action == MotionEvent.ACTION_MOVE) {

                    int viewWidth = view.getWidth();
                    int viewHeight = view.getHeight();

                    View viewParent = (View)view.getParent();
                    int parentWidth = viewParent.getWidth();
                    int parentHeight = viewParent.getHeight();

                    float newX = motionEvent.getRawX() + dX;
                    newX = Math.max(0, newX); // Don't allow the FAB past the left hand side of the parent
                    newX = Math.min(parentWidth - viewWidth, newX); // Don't allow the FAB past the right hand side of the parent

                    float newY = motionEvent.getRawY() + dY;
                    newY = Math.max(0, newY); // Don't allow the FAB past the top of the parent
                    newY = Math.min(parentHeight - viewHeight, newY); // Don't allow the FAB past the bottom of the parent

                    view.animate()
                            .x(newX)
                            .y(newY)
                            .setDuration(0)
                            .start();

                    return true; // Consumed

                } else if (action == MotionEvent.ACTION_UP) {

                    float upRawX = motionEvent.getRawX();
                    float upRawY = motionEvent.getRawY();

                    float upDX = upRawX - downRawX;
                    float upDY = upRawY - downRawY;

                    if (Math.abs(upDX) < CLICK_DRAG_TOLERANCE && Math.abs(upDY) < CLICK_DRAG_TOLERANCE) { // A click
                        return view.performClick();
                    }
                    else { // A drag
                        return true; // Consumed
                    }

                } else {
                    return view.onTouchEvent(motionEvent);
                }
            }
        });*/
    }
}
