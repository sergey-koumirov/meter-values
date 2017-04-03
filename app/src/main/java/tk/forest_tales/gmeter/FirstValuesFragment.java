package tk.forest_tales.gmeter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.greendao.query.CountQuery;
import org.greenrobot.greendao.query.Query;

import java.util.List;


public class FirstValuesFragment extends Fragment {

    private static final int REQUEST_METER_NUMBER = 1;

    private MeterDao meterDao;
    private Query<Meter> metersQuery;
    private Query<MeterValue> lastValueQuery;
    private CountQuery<MeterValue> countValueQuery;
    private MetersAdapter metersAdapter;
    private SharedPreferences prefs;

    OnMeterSelectedListener mCallback;
    public interface OnMeterSelectedListener {
        void onMeterSelected(Meter meter);
    }

    public FirstValuesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        setHasOptionsMenu(true);

        metersAdapter = new MetersAdapter(meterClickListener);

        DaoSession daoSession = ((App) getActivity().getApplication()).getDaoSession();
        meterDao = daoSession.getMeterDao();

        metersQuery = meterDao.queryBuilder()
                .orderAsc(MeterDao.Properties.Number, MeterDao.Properties.Id)
                .build();

        lastValueQuery = daoSession.getMeterValueDao().queryBuilder()
                .where(MeterValueDao.Properties.MeterId.eq(-1))
                .orderDesc(MeterValueDao.Properties.Date, MeterValueDao.Properties.Id)
                .limit(1)
                .build();

        countValueQuery = daoSession.getMeterValueDao().queryBuilder()
                .where(MeterValueDao.Properties.MeterId.eq(-1))
                .buildCount();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View result=inflater.inflate(R.layout.fragment_first_values, parent, false);
        RecyclerView recyclerView = (RecyclerView)result.findViewById(R.id.firstValues);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(metersAdapter);

        prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        refreshMeters();

        return(result);
    }



    private void refreshMeters() {
        List<Meter> meters = metersQuery.list();
        Meter.setLastValues(meters, lastValueQuery);
        metersAdapter.setMeters(meters, prefs);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnMeterSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnMeterSelectedListener");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actions_first_values, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add_meter:
                Log.d("meter","Add Meter");

                AddMeterDialog dialog = new AddMeterDialog();
                dialog.setTargetFragment(this,REQUEST_METER_NUMBER);
                dialog.show(getFragmentManager(), dialog.getClass().getName());

                return(true);
            case R.id.graphs:
                Log.d("meter","Graphs");
                return(true);

        }
        return(super.onOptionsItemSelected(item));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_METER_NUMBER:
                    String meterNumber = data.getStringExtra(AddMeterDialog.NEW_METER_NUMBER);
                    String meterName = data.getStringExtra(AddMeterDialog.NEW_METER_NAME);

                    Meter meter = new Meter();
                    meter.setNumber(meterNumber);
                    meter.setName(meterName);
                    meterDao.insert(meter);
                    refreshMeters();

                    break;
            }
        }
    }

    MetersAdapter.MeterClickListener meterClickListener = new MetersAdapter.MeterClickListener() {
        @Override
        public void onMeterClick(int position) {
            Meter meter = metersAdapter.getMeter(position);
            mCallback.onMeterSelected(meter);
        }

        @Override
        public void onMeterLongClick(final int position) {
            Meter meter = metersAdapter.getMeter(position);
            countValueQuery.setParameter(0,meter.getId());
            Long cnt = countValueQuery.count();

            if(cnt==0){
                new AlertDialog.Builder(getActivity())
                        .setTitle("Delete Meter")
                        .setMessage("Sure?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(
                                android.R.string.yes,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        Meter meter = metersAdapter.getMeter(position);
                                        Long meterId = meter.getId();
                                        meterDao.deleteByKey(meterId);

                                        refreshMeters();
                                        Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }else{
                Toast.makeText(getActivity(), "Has values. Can't delete.", Toast.LENGTH_SHORT).show();
            }



        }
    };

}
