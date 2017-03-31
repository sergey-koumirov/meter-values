package tk.forest_tales.gmeter;

import android.graphics.Color;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sergey.koumirov on 31/03/2017.
 */

public class CheckService {

    private Integer checkDay;
    private Calendar nextCheck;
    private Calendar today;

    CheckService(Integer _checkDay){
        checkDay = _checkDay;

        today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY,23);
        today.set(Calendar.MINUTE,59);
        today.set(Calendar.SECOND,59);

        nextCheck = Calendar.getInstance();
        if( today.get(Calendar.DAY_OF_MONTH) <= checkDay ){
            nextCheck.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH), checkDay, 23, 59, 59);
        }else{
            if(Calendar.MONTH == Calendar.DECEMBER){
                nextCheck.set(today.get(Calendar.YEAR)+1, Calendar.JANUARY, checkDay, 23, 59, 59);
            }else{
                nextCheck.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH)+1, checkDay, 23, 59, 59);
            }
        }

    }

    public Integer daysBeforeNextCheck(){
        long diff = nextCheck.getTimeInMillis() - today.getTimeInMillis();
        return new Double(diff / (24.0 * 60.0 * 60.0 * 1000.0) ).intValue();
    }

    public Integer daysSinceLastCheck(MeterValue lastValue){
        if(lastValue==null){
            return Integer.MAX_VALUE;
        }else{
            try{
                Date lastDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(lastValue.getDate()+" 23:59:59");
                long diff = today.getTimeInMillis() - lastDate.getTime();
                return new Double(diff / (24.0 * 60.0 * 60.0 * 1000.0) ).intValue();
            }catch(ParseException e){
                return Integer.MAX_VALUE;
            }
        }
    }

    public int statusColor(MeterValue mv){
        if( daysSinceLastCheck(mv)>35 ){
            return 0xFFFFCCCC;
        }else if(daysSinceLastCheck(mv)>=20 && daysSinceLastCheck(mv)<=35 && daysBeforeNextCheck()<=5 ){
            return 0xFFFFDDDD;
        }else{
            return 0xFFFFFFFF;
        }
    }

}
