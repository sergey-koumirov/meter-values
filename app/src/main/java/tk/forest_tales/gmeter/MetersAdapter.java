package tk.forest_tales.gmeter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MetersAdapter extends RecyclerView.Adapter<MetersAdapter.MeterViewHolder> {

    private MeterClickListener clickListener;
    private List<Meter> dataset;

    public interface MeterClickListener {
        void onMeterClick(int position);
    }

    static class MeterViewHolder extends RecyclerView.ViewHolder {

        public TextView number;

        public MeterViewHolder(View itemView, final MeterClickListener clickListener) {
            super(itemView);
            number = (TextView) itemView.findViewById(R.id.meterNumber);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        clickListener.onMeterClick(getAdapterPosition());
                    }
                }
            });
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
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
