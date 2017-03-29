package tk.forest_tales.gmeter;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.greendao.query.Query;

import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllMeterValuesFragment extends Fragment {

    final static String METER_ID = "meterId";
    final static String METER_NUMBER = "meterNumber";

    private MeterValueDao meterValueDao;
    private Query<MeterValue> meterValuesQuery;
    private MeterValuesAdapter meterValuesAdapter;

    public AllMeterValuesFragment(){}

    MeterValuesAdapter.MeterValuesClickListener meterValuesClickListener = new MeterValuesAdapter.MeterValuesClickListener() {
        @Override
        public void onMeterValueLongClick(final int position) {

            new AlertDialog.Builder(getActivity())
                    .setTitle("Delete Meter Value")
                    .setMessage("Sure?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(
                            android.R.string.yes,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                    MeterValue meterValue = meterValuesAdapter.getMeterValue(position);
                                    Long meterValueId = meterValue.getId();
                                    meterValueDao.deleteByKey(meterValueId);
                                    refreshMeterValues();

                                    Log.d("DaoExample", "Deleted");
                                    Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                                }
                            })
                    .setNegativeButton(android.R.string.no, null)
                    .show();

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        setHasOptionsMenu(true);

        meterValuesAdapter = new MeterValuesAdapter(meterValuesClickListener);

        DaoSession daoSession = ((App) getActivity().getApplication()).getDaoSession();
        meterValueDao = daoSession.getMeterValueDao();

        Long meterId = getArguments().getLong(METER_ID);

        meterValuesQuery = meterValueDao.queryBuilder()
                .where(MeterValueDao.Properties.MeterId.eq(meterId))
                .orderDesc(MeterValueDao.Properties.Date)
                .build();

        refreshMeterValues();

    }

    private void refreshMeterValues() {
        List<MeterValue> meterValues = meterValuesQuery.list();
        meterValuesAdapter.setMeterValues( meterValues );
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View result=inflater.inflate(R.layout.fragment_all_meter_values, parent, false);
        RecyclerView recyclerView = (RecyclerView)result.findViewById(R.id.allValues);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(meterValuesAdapter);

        String meterNumber = getArguments().getString(METER_NUMBER);
        TextView title = (TextView)result.findViewById(R.id.meterValueTitle);
        title.setText( String.format("Values for %s", meterNumber) );

        return result;
    }

}
