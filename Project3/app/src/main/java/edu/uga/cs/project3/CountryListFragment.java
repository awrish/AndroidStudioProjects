package edu.uga.cs.project3;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

/**
 * List Fragment portion for the layout
 */
public class CountryListFragment extends ListFragment {

    //For debugging
    private static final String TAG = "Project3";

    private final String[] countryList = {
            "Spain",
            "Italy",
            "Scotland",
            "Belgium",
            "Norway"
    };


    boolean twoFragmentsActivity; //checking if in landscape

    int versionIndex = 0; //which country is selected

    public CountryListFragment() {} //default constructor

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {

        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "CountryListFragment.onActivityCreated(): savedInstanceState: " + savedInstanceState);

        setListAdapter( new ArrayAdapter<>( getActivity(), android.R.layout.simple_list_item_activated_1, countryList));

        View detailsFrame = getActivity().findViewById(R.id.countryInfo);
        Log.d(TAG, "CountryListFragment.onActivityCreated(): detailsFrame: " + detailsFrame);

        twoFragmentsActivity = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        if (savedInstanceState != null) {
            versionIndex = savedInstanceState.getInt("countrySelection", 0);
            Log.d( TAG, "CountryListFragment.onActivityCreated(): restored versionIndex: " + versionIndex );
        }

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        getListView().setItemChecked(versionIndex, true);

        if (twoFragmentsActivity) {
            showCountryInfo(versionIndex);
            getListView().smoothScrollToPosition(versionIndex);
        }


    }

    //Displays the information on the country selected within a CountryInfoFragment
    //Variates depending on the landscape as well
    @Override
    public void onListItemClick( ListView l, View v, int position, long id ) {

        showCountryInfo( position );
    }

    @Override
    public void onSaveInstanceState( Bundle outState ) {
        super.onSaveInstanceState(outState);

        // save the list index selection
        outState.putInt( "countrySelection", versionIndex);
        Log.d( TAG, "CountryListFragment.onSaveInstanceState(): saved versionIndex: " + versionIndex );
    }

    // Shows info of the country selected in the listFragment
    // if in landscape, updates the countryInfo
    // if in portrait launches new intent
    void showCountryInfo( int versionIndex ) {
        this.versionIndex = versionIndex;

        if (twoFragmentsActivity) { //if in landscape mode
            getListView().setItemChecked(versionIndex, true);

            CountryInfoFragment details =
                    (CountryInfoFragment) getParentFragmentManager().findFragmentById(R.id.countryInfo);

            Log.d(TAG, "CountryListFragment.showCountryInfo(): details: " + details);

            if (details == null || details.getShownVersionIndex() != versionIndex) {

                details = CountryInfoFragment.newInstance(versionIndex);

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.countryInfo, details);

                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

                fragmentTransaction.commit();
            }
        }
        else { //If in portrait mode
            Intent intent = new Intent();
            intent.setClass( getActivity(), CountryInfoActivity.class);
            intent.putExtra( "versionIndex", versionIndex);

            startActivity(intent);
        }
    }

}
