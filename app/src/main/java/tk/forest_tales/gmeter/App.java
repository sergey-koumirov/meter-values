package tk.forest_tales.gmeter;


import android.app.Application;
import android.os.Environment;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

import java.io.File;

import tk.forest_tales.gmeter.DaoMaster.DevOpenHelper;

public class App extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        QueryBuilder.LOG_SQL = true;

        File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/MeterValues", "meter-values.db");
        path.getParentFile().mkdirs();

        ProdOpenHelper helper = new ProdOpenHelper(
                this,
                path.getAbsolutePath()
        );
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
