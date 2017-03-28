package tk.forest_tales.gmeter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import org.greenrobot.greendao.query.Query;

import java.util.List;


public class FirstValuesFragment extends Fragment {

    private MeterDao meterDao;
    private Query<Meter> metersQuery;
    private MetersAdapter metersAdapter;

    OnMeterSelectedListener mCallback;
    public interface OnMeterSelectedListener {
        public void onMeterSelected(int meterId);
    }

    public FirstValuesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View result=inflater.inflate(R.layout.fragment_first_values, parent, false);

        RecyclerView recyclerView = (RecyclerView)result.findViewById(R.id.firstValues);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        metersAdapter = new MetersAdapter(meterClickListener);
        recyclerView.setAdapter(metersAdapter);


        DaoSession daoSession = ((App) getActivity().getApplication()).getDaoSession();
        meterDao = daoSession.getMeterDao();

        metersQuery = meterDao.queryBuilder().orderAsc(MeterDao.Properties.Number).build();
        updateMeters();

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
            throw new ClassCastException(context.toString() + " must implement OnHeadlineSelectedListener");
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

                Meter meter = new Meter();
                meter.setNumber("777.899.2");
                meterDao.insert(meter);
                updateMeters();

                return(true);
            case R.id.graphs:
                Log.d("meter","Graphs");
                return(true);

        }
        return(super.onOptionsItemSelected(item));
    }

    MetersAdapter.MeterClickListener meterClickListener = new MetersAdapter.MeterClickListener() {
        @Override
        public void onMeterClick(int position) {
            Meter note = metersAdapter.getMeter(position);
            Long noteId = note.getId();

            meterDao.deleteByKey(noteId);
            Log.d("DaoExample", "Deleted note, ID: " + noteId);

            updateMeters();
        }
    };

}
