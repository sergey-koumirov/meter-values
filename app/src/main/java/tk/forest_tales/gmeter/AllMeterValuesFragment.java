package tk.forest_tales.gmeter;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.greendao.query.Query;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllMeterValuesFragment extends Fragment {

    private static final int REQUEST_METER_VALUE = 1;

    final static String METER_ID = "meterId";
    final static String METER_NUMBER = "meterNumber";
    final static String METER_NAME = "meterName";

    private MeterValueDao meterValueDao;
    private Query<MeterValue> meterValuesQuery;
    private Query<MeterValue> meterValuesGraphQueryAsc;
    private MeterValuesAdapter meterValuesAdapter;
    private Long meterId = null;
    String meterNumber = "";
    String meterName = "";

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

        meterId = getArguments().getLong(METER_ID);

        meterValuesQuery = meterValueDao.queryBuilder()
                .where(MeterValueDao.Properties.MeterId.eq(meterId))
                .orderDesc(MeterValueDao.Properties.Date)
                .build();

        meterValuesGraphQueryAsc = meterValueDao.queryBuilder()
                .where(MeterValueDao.Properties.MeterId.eq(meterId))
                .orderAsc(MeterValueDao.Properties.Date)
                .limit(13)
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

        meterNumber = getArguments().getString(METER_NUMBER);
        meterName = getArguments().getString(METER_NAME);
        TextView title = (TextView)result.findViewById(R.id.meterValueTitle);
        title.setText( String.format("Values for %s (%s)", meterName, meterNumber) );

        return result;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actions_all_meter_values, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add_meter_value:
                AddMeterValueDialog dialog = new AddMeterValueDialog();
                dialog.setTargetFragment(this,REQUEST_METER_VALUE);
                dialog.show(getFragmentManager(), dialog.getClass().getName());
                return(true);

            case R.id.graphs:
                FragmentManager fm = getFragmentManager();

                if(fm.findFragmentByTag(GraphsFragment.FRAGMENT_TAG) == null){
                    FragmentTransaction transaction = fm.beginTransaction();

                    GraphsFragment gf = new GraphsFragment();
                    gf.setData(new GraphData(meterName, meterNumber, meterValuesGraphQueryAsc.list()));

                    transaction.replace(R.id.fragment_container, gf, GraphsFragment.FRAGMENT_TAG);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }

                return(true);

        }
        return(super.onOptionsItemSelected(item));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_METER_VALUE:
                    String date = data.getStringExtra(AddMeterValueDialog.NEW_METER_VALUE_DATE);
                    Double value = data.getDoubleExtra(AddMeterValueDialog.NEW_METER_VALUE_VALUE, 0);

                    MeterValue meterValue = new MeterValue();
                    meterValue.setMeterId(meterId);
                    meterValue.setDate(date);
                    meterValue.setValue(value);
                    meterValueDao.insert(meterValue);

                    refreshMeterValues();

                    break;
            }
        }
    }

}
