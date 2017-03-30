package tk.forest_tales.gmeter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

public class MetersAdapter extends RecyclerView.Adapter<MetersAdapter.MeterViewHolder> {

    private MeterClickListener clickListener;
    private List<Meter> dataset;

    public interface MeterClickListener {
        void onMeterClick(int position);
        void onMeterLongClick(int position);
    }

    static class MeterViewHolder extends RecyclerView.ViewHolder {

        public TextView number;
        public TextView name;
        public TextView lastDate;
        public TextView lastValue;

        public MeterViewHolder(View itemView, final MeterClickListener eventsListener) {
            super(itemView);
            number = (TextView) itemView.findViewById(R.id.meterNumber);
            name = (TextView) itemView.findViewById(R.id.meterName);
            lastDate = (TextView) itemView.findViewById(R.id.lastDate);
            lastValue = (TextView) itemView.findViewById(R.id.lastValue);

            itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (eventsListener != null) {
                                eventsListener.onMeterClick(getAdapterPosition());
                            }
                        }
                    }
            );

            itemView.setOnLongClickListener(
                    new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            if (eventsListener != null) {
                                eventsListener.onMeterLongClick(getAdapterPosition());
                                return true;
                            }
                            return false;
                        }
                    }
            );
        }
    }

    public MetersAdapter(MeterClickListener clickListener) {
        this.clickListener = clickListener;
        this.dataset = new ArrayList<Meter>();
    }

    public void setMeters(@NonNull List<Meter> meters) {
        dataset = meters;
        notifyDataSetChanged();
    }

    public Meter getMeter(int position) {
        return dataset.get(position);
    }

    @Override
    public MetersAdapter.MeterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meter, parent, false);
        return new MeterViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(MetersAdapter.MeterViewHolder holder, int position) {
        Meter meter = dataset.get(position);
        holder.number.setText(meter.getNumber());
        holder.name.setText(meter.getName());
        holder.lastDate.setText(meter.getLastValue().getDate());
        holder.lastValue.setText(meter.getLastValue().getValue().toString());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
