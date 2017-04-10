package tk.forest_tales.gmeter;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import org.greenrobot.greendao.query.Query;

import java.util.List;


public class GraphsFragment extends Fragment {

    public static String FRAGMENT_TAG = "Graphs";

    private GraphData data = null;
    BarGraphSeries<DataPoint> series = null;

    public GraphsFragment(){}

    public void setData(GraphData data) {
        this.data = data;

        DataPoint[] dataPoints = new DataPoint[data.getSeries().getPoints().size()];

        int index = 0;
        for(MeterValue mv: data.getSeries().getPoints()){
            dataPoints[index] = new DataPoint(index, mv.getValue());
            index++;
        }

        series = new BarGraphSeries<>(dataPoints);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View result=inflater.inflate(R.layout.fragment_graphs, parent, false);

        GraphView graph = (GraphView)result.findViewById(R.id.graph);

        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

//        graph.getViewport().setMaxX(10.0);
//        graph.getViewport().setMaxY(13.0);
//        graph.getViewport().setMinX(0.0);
//        graph.getViewport().setMinX(0.0);
//        graph.getViewport().setXAxisBoundsManual(true);
//        graph.getViewport().setYAxisBoundsManual(true);


        graph.addSeries(series);
        series.setTitle(data.getSeries().getTitle());
        series.setSpacing(50);
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);

        return result;
    }


}
