package tk.forest_tales.gmeter;

import java.util.ArrayList;
import java.util.List;

public class GraphData {

    GraphSeries series = null;

    GraphData(String name, String number, List<MeterValue> meterValues){

        series = new GraphSeries();

        series.setTitle(String.format("%s (%s)", name, number));

        Double prevValue = 0.0;
        List<MeterValue> temp = new ArrayList<MeterValue>();

        for(MeterValue mv: meterValues){
            temp.add( new MeterValue(null, mv.getDate(), mv.getValue() - prevValue, 0) );
            prevValue = mv.getValue();
        }
        series.setPoints(temp);
    }

    public GraphSeries getSeries() {
        return series;
    }
}
