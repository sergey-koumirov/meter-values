package tk.forest_tales.gmeter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;


public class GraphsFragment extends Fragment {

    public static String FRAGMENT_TAG = "Graphs";

    private ColumnChartData data;

    private GraphData rawData;

    private ColumnChartView chart;

    public GraphsFragment(){}

    public void setData(GraphData rawData) {
        this.rawData = rawData;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View result=inflater.inflate(R.layout.fragment_graphs, parent, false);
        chart = (ColumnChartView)result.findViewById(R.id.chart);
        generateDefaultData();
        return result;
    }


    private void generateDefaultData() {

        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        List<AxisValue> xLabelValues = new ArrayList<AxisValue>();
        int color = ChartUtils.COLOR_GREEN;

        float index = 0.0f;

        for(MeterValue mv: rawData.getSeries().getPoints()){
            values = new ArrayList<SubcolumnValue>();

            SubcolumnValue value = new SubcolumnValue();
            value.setColor(color);
            value.setValue(new Float(mv.getValue()));
            value.setLabel(mv.getDate().substring(0,7));

            AxisValue axisValue = new AxisValue(index);
            axisValue.setLabel(mv.getDate().substring(0,7));
            xLabelValues.add(axisValue);

            values.add(value);
            Column column = new Column(values);
            column.setHasLabels(false);
            column.setHasLabelsOnlyForSelected(false);
            columns.add(column);
            index++;
        }

        data = new ColumnChartData(columns);

        Axis axisX = new Axis();
        axisX.setName("Month");
        axisX.setHasTiltedLabels(false);
        axisX.setTextSize(10);
        axisX.setValues(xLabelValues);

        Axis axisY = new Axis().setHasLines(true);
        axisY.setName("Value");


        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);

        chart.setColumnChartData(data);
    }


}
