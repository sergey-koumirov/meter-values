package tk.forest_tales.gmeter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MeterValuesAdapter extends RecyclerView.Adapter<MeterValuesAdapter.MeterValueViewHolder> {

    private MeterValuesClickListener clickListener;
    private List<MeterValue> dataset;

    public interface MeterValuesClickListener {
        void onMeterValueLongClick(int position);
    }

    static class MeterValueViewHolder extends RecyclerView.ViewHolder {

        public TextView date;
        public TextView value;

        public MeterValueViewHolder(View itemView, final MeterValuesClickListener eventsListener) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.meterValueDate);
            value = (TextView) itemView.findViewById(R.id.meterValueValue);

            itemView.setOnLongClickListener(
                    new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            if (eventsListener != null) {
                                eventsListener.onMeterValueLongClick(getAdapterPosition());
                                return true;
                            }
                            return false;
                        }
                    }
            );
        }
    }

    public MeterValuesAdapter(MeterValuesClickListener clickListener) {
        this.clickListener = clickListener;
        this.dataset = new ArrayList<MeterValue>();
    }

    public void setMeterValues(@NonNull List<MeterValue> meterValues) {
        dataset = meterValues;
        notifyDataSetChanged();
    }

    public MeterValue getMeterValue(int position) {
        return dataset.get(position);
    }

    @Override
    public MeterValuesAdapter.MeterValueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meter_value, parent, false);
        return new MeterValueViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(MeterValuesAdapter.MeterValueViewHolder holder, int position) {
        MeterValue meterValue = dataset.get(position);
        holder.date.setText(meterValue.getDate());
        holder.value.setText(meterValue.getValue().toString());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
