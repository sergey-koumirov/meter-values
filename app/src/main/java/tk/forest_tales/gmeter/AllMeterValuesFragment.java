package tk.forest_tales.gmeter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllMeterValuesFragment extends Fragment {

    final static String METER_ID = "meterId";

    public AllMeterValuesFragment() {
        // Required empty public constructor
        Log.d("meter", Double.toString( Math.random() ) );
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_meter_values, container, false);
    }

}
