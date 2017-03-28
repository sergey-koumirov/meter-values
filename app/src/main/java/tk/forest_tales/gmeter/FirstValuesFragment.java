package tk.forest_tales.gmeter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class FirstValuesFragment extends ListFragment {



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

        setListAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_activated_1, DataManager.Headlines));
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
    public void onListItemClick(ListView l, View v, int position, long id) {
        mCallback.onMeterSelected(13);
    }



}
