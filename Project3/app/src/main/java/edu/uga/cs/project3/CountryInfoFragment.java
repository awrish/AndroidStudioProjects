package edu.uga.cs.project3;

import android.app.ActionBar;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Layout;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.InputStream;

/**
 * Provides a Fragment used for showing the country's information in the
 * countryInfo fragment container.
 */
public class CountryInfoFragment extends Fragment {

    private static final String TAG = "Project3"; //for debugging

    public CountryInfoFragment() {} //default constructor

    public static CountryInfoFragment newInstance(int versionIndex) {
        Log.d(TAG, "CountryInfoFragment.newInstance(): versionIndex: " + versionIndex);

        CountryInfoFragment fragment = new CountryInfoFragment();
        Log.d(TAG, "CountryInfoFragment.newInstance(): fragment: " + fragment);

        Bundle args = new Bundle();
        args.putInt("versionIndex", versionIndex);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "CountryInfoFragment.onCreateView()");

        //layout inside the countryInfo Fragment
        //giving scrollView a child of LinearLayout allows use to be able to put multiple
        //children like imageView and textView into it in the space of one

        ScrollView scroller = new ScrollView(getActivity());
        LinearLayout parent = new LinearLayout(getActivity());

        parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        parent.setOrientation(LinearLayout.VERTICAL);

        TextView textView = new TextView(getActivity());
        ImageView imageView = new ImageView(getActivity());

        parent.addView(imageView);
        parent.addView(textView);

        scroller.addView(parent);

        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getActivity().getResources().getDisplayMetrics());
        textView.setPadding(padding, padding, padding, padding);

        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);

        //reading the file and setting text and image
        int selection = getArguments().getInt("versionIndex", 0);

        Resources res = getResources();

        InputStream in_s = null;

        //based on selected Item, updates the textView and Image in the countryInfo fragment
        switch (selection) {
            case 0:
                in_s = res.openRawResource(R.raw.spain_overview);
                imageView.setImageResource(R.drawable.spain_flag);
                break;
            case 1:
                in_s = res.openRawResource(R.raw.italy_overview);
                imageView.setImageResource(R.drawable.venice);
                break;
            case 2:
                in_s = res.openRawResource(R.raw.scotland_overview);
                imageView.setImageResource(R.drawable.scotland);
                break;

            case 3:
                in_s = res.openRawResource(R.raw.belgium_overview);
                imageView.setImageResource(R.drawable.belgium_flag);
                break;

            case 4:
                in_s = res.openRawResource(R.raw.norway_overview);
                imageView.setImageResource(R.drawable.norway);
                break;

            case 22: // added for the Landscape portion of the splash screen
                imageView.setImageResource(R.drawable.travel);
                in_s = res.openRawResource(R.raw.homepage);
        }

        try {
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            textView.setText(new String(b));
        } catch (IOException e) {
            e.printStackTrace();
        }

        textView.setMovementMethod(new ScrollingMovementMethod());
        Linkify.addLinks(textView, Linkify.WEB_URLS);

        return scroller;
    }

    //returns the version index of the CountryInfoFragment
    public int getShownVersionIndex() {
        return getArguments().getInt("versionIndex", 0 );
    }

    }



