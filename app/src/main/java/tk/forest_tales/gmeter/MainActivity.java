package tk.forest_tales.gmeter;

import android.content.Intent;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements FirstValuesFragment.OnMeterSelectedListener {

    private WebView wv=null;

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
                FragmentManager fm = getSupportFragmentManager();
                if(fm.findFragmentByTag(Prefs.FRAGMENT_TAG) == null){
                    Log.d("meter","Settings null");
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.fragment_container, new Prefs(), Prefs.FRAGMENT_TAG);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                return(true);

            case R.id.report:
                Log.d("meter","Print");

                List<Meter> meters = ((App)getApplication())
                        .getDaoSession()
                        .getMeterDao()
                        .queryBuilder()
                        .orderAsc(MeterDao.Properties.Number, MeterDao.Properties.Id)
                        .build()
                        .list();

                new PrinterService(this, new ReportData(meters) ).print();
                return(true);

        }

        return(super.onOptionsItemSelected(item));
    }

}
