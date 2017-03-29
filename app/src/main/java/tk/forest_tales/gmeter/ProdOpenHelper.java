package tk.forest_tales.gmeter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.greenrobot.greendao.database.Database;

import tk.forest_tales.gmeter.DaoMaster;

/** WARNING: Drops all table on Upgrade! Use only during development. */
public class ProdOpenHelper extends DaoMaster.OpenHelper {
    public ProdOpenHelper(Context context, String name) {
        super(context, name);
    }

    public ProdOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
        onCreate(db);
    }
}