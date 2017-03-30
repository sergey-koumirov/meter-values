package tk.forest_tales.gmeter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.greenrobot.greendao.query.Query;

import java.util.List;


public class FirstValuesFragment extends Fragment {

    private static final int REQUEST_METER_NUMBER = 1;

    private MeterDao meterDao;
    private Query<Meter> metersQuery;
    private MetersAdapter metersAdapter;

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

        metersQuery = meterDao.queryBuilder().orderAsc(MeterDao.Properties.Number).build();
        updateMeters();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View result=inflater.inflate(R.layout.fragment_first_values, parent, false);
        RecyclerView recyclerView = (RecyclerView)result.findViewById(R.id.firstValues);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(metersAdapter);

        return(result);
    }



    private void updateMeters() {
        List<Meter> meters = metersQuery.list();
        metersAdapter.setMeters( meters );
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
                    updateMeters();

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
                                    updateMeters();
                                    Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                                }
                            })
                    .setNegativeButton(android.R.string.no, null)
                    .show();

        }
    };

}
