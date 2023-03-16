package edu.uga.cs.project3;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Adds an actionbar and it's functions to the app in portrait mode.
 */
public class CountryInfoActivity extends AppCompatActivity {

    private static final String TAG = "Project3"; // for debugging

    @Override
    protected void onCreate( Bundle savedInstanceState ) {

        Log.d( TAG, "CountryInfoActivity.onCreate()" );

        super.onCreate( savedInstanceState );

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled( true );

        if( getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ) {
            Log.d( TAG, "CountryInfoActivity.onCreate(): in landscape mode; returning" );
            finish();
            return;
        }

        Log.d(TAG, "CountryInfoActivity.onCreate(): in portrait mode; replacing fragments");

        CountryInfoFragment countryInfoFragment = new CountryInfoFragment();
        Log.d( TAG, "CountryInfoActivity.onCreate(): countryInfoFragment: " + countryInfoFragment);

        countryInfoFragment.setArguments( getIntent().getExtras() );

        getSupportFragmentManager().beginTransaction().replace( android.R.id.content, countryInfoFragment).commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if( id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected( item );
    }

}
