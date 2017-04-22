package tk.forest_tales.gmeter;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;

public class MainActivity extends AppCompatActivity
        implements FirstValuesFragment.OnMeterSelectedListener,
        DatePickerDialog.OnDateSetListener {

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
        args.putLong(App.METER_ID, meter.getId());
        args.putString(App.METER_NUMBER, meter.getNumber());
        args.putString(App.METER_NAME, meter.getName());
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
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.fragment_container, new Prefs(), Prefs.FRAGMENT_TAG);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                return(true);

            case R.id.report:
                MonthYearPickerDialog pd = new MonthYearPickerDialog();
                pd.setListener(this);
                pd.show(getSupportFragmentManager(), "MonthYearPickerDialog");
                return(true);
        }

        return(super.onOptionsItemSelected(item));
    }

    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
        new PrinterService(this, ReportData.getWithPreparedData(this, year, monthOfYear)).print();
    };

}
