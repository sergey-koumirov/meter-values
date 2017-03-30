package tk.forest_tales.gmeter;


import android.app.Application;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

import tk.forest_tales.gmeter.DaoMaster.DevOpenHelper;

public class App extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();


        QueryBuilder.LOG_SQL = true;

        ProdOpenHelper helper = new ProdOpenHelper(this, "meters-values-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
