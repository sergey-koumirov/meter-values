package tk.forest_tales.gmeter;


import android.app.Application;

import org.greenrobot.greendao.database.Database;
import tk.forest_tales.gmeter.DaoMaster.DevOpenHelper;

public class App extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DevOpenHelper helper = new DevOpenHelper(this, "meters-values-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
