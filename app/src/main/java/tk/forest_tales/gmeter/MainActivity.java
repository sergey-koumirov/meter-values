package tk.forest_tales.gmeter;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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

}
