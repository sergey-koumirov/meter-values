package tk.forest_tales.gmeter;

import java.util.List;

public class GraphSeries {

    private String title = "";

    private List<MeterValue> points = null;

    public List<MeterValue> getPoints() {
        return points;
    }

    public void setPoints(List<MeterValue> points) {
        this.points = points;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
