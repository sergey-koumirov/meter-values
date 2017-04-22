package tk.forest_tales.gmeter;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class GraphData {

    GraphSeries series = null;

    GraphData(String name, String number, List<MeterValue> meterValues){

        series = new GraphSeries();

        series.setTitle(String.format("%s (%s)", name, number));

        Double prevValue = 0.0;
        int index = 0;
        if(meterValues.size()>0){
            prevValue = meterValues.get(0).getValue();
        }
        if(meterValues.size()>12){
            index=1;
        }

        List<MeterValue> temp = new ArrayList<MeterValue>();

        for(int i=index; i<meterValues.size(); i++){
            temp.add( new MeterValue(null, meterValues.get(i).getDate(), meterValues.get(i).getValue() - prevValue, 0) );
            prevValue = meterValues.get(i).getValue();
        }
        series.setPoints(temp);
    }

    public GraphSeries getSeries() {
        return series;
    }
}
