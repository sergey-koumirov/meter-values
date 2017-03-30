package tk.forest_tales.gmeter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceGroup;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity
        implements FirstValuesFragment.OnMeterSelectedListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            FirstValuesFragment frg = new FirstValuesFragment();
            frg.setArguments( getIntent().getExtras() );
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, frg)
                    .commit();

        }
    }

    public void onMeterSelected(Meter meter){
        AllMeterValuesFragment af = new AllMeterValuesFragment();
        Bundle args = new Bundle();
        args.putLong(AllMeterValuesFragment.METER_ID, meter.getId());
        args.putString(AllMeterValuesFragment.METER_NUMBER, meter.getNumber());
        args.putString(AllMeterValuesFragment.METER_NAME, meter.getName());
        af.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, af);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return(super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.settings:
                Log.d("meter","Settings");

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new Prefs());
                transaction.addToBackStack(null);
                transaction.commit();

                return(true);
        }
        return(super.onOptionsItemSelected(item));
    }


    public static class Prefs extends PreferenceFragmentCompat implements
            SharedPreferences.OnSharedPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);
        }

        @Override
        public void onResume() {
            super.onResume();
            for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); ++i) {
                Preference preference = getPreferenceScreen().getPreference(i);
                if (preference instanceof PreferenceGroup) {
                    PreferenceGroup preferenceGroup = (PreferenceGroup) preference;
                    for (int j = 0; j < preferenceGroup.getPreferenceCount(); ++j) {
                        Preference singlePref = preferenceGroup.getPreference(j);
                        updatePreference(singlePref, singlePref.getKey());
                    }
                } else {
                    updatePreference(preference, preference.getKey());
                }
            }
        }

        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            updatePreference(findPreference(key), key);
        }

        private void updatePreference(Preference p, String key) {
            if (p instanceof EditTextPreference) {
                EditTextPreference editTextPref = (EditTextPreference) p;
                p.setSummary(editTextPref.getText());
            }
        }


    }
}
