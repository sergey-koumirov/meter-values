package tk.forest_tales.gmeter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;

import org.greenrobot.greendao.query.Query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by sergey.koumirov on 31/03/2017.
 */

public class ReportData {

    List<Meter> meters = null;
    SharedPreferences prefs = null;
    String periodName = "";

    ReportData(String _periodName, List<Meter> _meters, SharedPreferences _prefs){
        meters = _meters;
        prefs = _prefs;
        periodName = _periodName;
    }

    List<Meter> getMeters(){
        return meters;
    }

    String getName(){
        return prefs.getString("name","No Name");
    }

    String getAddress(){
        return prefs.getString("address","No Address");
    }

    String getPeriodName(){
        return periodName;
    }

    public static ReportData getWithPreparedData(AppCompatActivity context){
        DaoSession daoSession = ((App)context.getApplication()).getDaoSession();

        DateFormat df = new SimpleDateFormat("yyyy-MM");
        Date currMonth = Calendar.getInstance().getTime();

        Calendar prev = Calendar.getInstance();
        prev.add(Calendar.MONTH,-1);
        Date prevMonth = prev.getTime();
        String currMonthStr = df.format(currMonth);
        String prevMonthStr = df.format(prevMonth);
        String periodName = new SimpleDateFormat("yyyy LLLL").format(currMonth);

        List<Meter> meters = daoSession.getMeterDao()
                .queryBuilder()
                .orderAsc(MeterDao.Properties.Number, MeterDao.Properties.Id)
                .build()
                .list();

        Query<MeterValue> lastValueQuery = daoSession.getMeterValueDao().queryBuilder()
                .where(MeterValueDao.Properties.MeterId.eq(-1), MeterValueDao.Properties.Date.like(currMonthStr+"-%"))
                .orderDesc(MeterValueDao.Properties.Date, MeterValueDao.Properties.Id)
                .limit(1)
                .build();

        Query<MeterValue> prevValueQuery = daoSession.getMeterValueDao().queryBuilder()
                .where(MeterValueDao.Properties.MeterId.eq(-1), MeterValueDao.Properties.Date.like(prevMonthStr+"-%"))
                .orderDesc(MeterValueDao.Properties.Date, MeterValueDao.Properties.Id)
                .limit(1)
                .build();

        Meter.setLastValues(meters, lastValueQuery);
        Meter.setPrevValues(meters, prevValueQuery);

        return new ReportData(
                periodName,
                meters,
                PreferenceManager.getDefaultSharedPreferences(context)
        );
    }


}
