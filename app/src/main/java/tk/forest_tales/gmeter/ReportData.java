package tk.forest_tales.gmeter;

import java.util.List;

/**
 * Created by sergey.koumirov on 31/03/2017.
 */

public class ReportData {

    List<Meter> meters = null;

    ReportData(List<Meter> _meters){
        meters = _meters;
    }

    List<Meter> getMeters(){
        return meters;
    }

}
