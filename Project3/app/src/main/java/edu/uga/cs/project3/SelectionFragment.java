package edu.uga.cs.project3;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * Used as the home / splash screen. Click a button to see the countries list and info.
 */
public class SelectionFragment extends Fragment {

    private static final String TAG = "Project3SelectionFragmt";

    private boolean showTwoFragments = false; // not needed
    private boolean overview = true; // i dont think i need this

    public SelectionFragment() {} //default constructor

    public static SelectionFragment newInstance() {
        SelectionFragment fragment = new SelectionFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        Log.d( TAG, "onCreateView()");

        return inflater.inflate(R.layout.fragment_choice, container, false);

    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState ) {

        Log.d( TAG, "onActivityCreated()" );

        super.onViewCreated(view, savedInstanceState);

        Button viewCountry = getView().findViewById(R.id.button);

        View detailsFrame = getActivity().findViewById(R.id.countryInfo);
        showTwoFragments = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        if (showTwoFragments) {
            // If in landscape mode, repalces the countryInfoFragment with a new one for the
            // homepage
            int imageNumber = 22;

            CountryInfoFragment imager =
                    (CountryInfoFragment) getParentFragmentManager().findFragmentById(R.id.countryInfo);

            imager = CountryInfoFragment.newInstance(imageNumber);

            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.countryInfo, imager);

            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

            fragmentTransaction.commit();

        }

        viewCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.countryList, new CountryListFragment());
                fragmentTransaction.commit();
            }
        });
    }



}
