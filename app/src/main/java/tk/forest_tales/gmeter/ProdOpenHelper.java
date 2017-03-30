package tk.forest_tales.gmeter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.greenrobot.greendao.database.Database;

public class ProdOpenHelper extends DaoMaster.OpenHelper {
    public ProdOpenHelper(Context context, String name) {
        super(context, name);
    }

    public ProdOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onCreate(Database db) {
        Log.i("DB", "Creating tables");

        db.execSQL("CREATE TABLE meters(_id integer primary key autoincrement, number TEXT NOT NULL, name TEXT NOT NULL);");
        db.execSQL("CREATE TABLE meter_values(_id integer primary key autoincrement, meter_id INTEGER NOT NULL, date TEXT NOT NULL, value REAL NOT NULL);");
        db.execSQL("CREATE INDEX meter_values_meter_id on meter_values(meter_id);");
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        Log.i("DB", "Upgrading schema from version " + oldVersion + " to " + newVersion);
    }
}