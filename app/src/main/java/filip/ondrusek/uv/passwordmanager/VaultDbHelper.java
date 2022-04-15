package filip.ondrusek.uv.passwordmanager;

import static filip.ondrusek.uv.passwordmanager.VaultContract.SQL_CREATE_ENTRIES;
import static filip.ondrusek.uv.passwordmanager.VaultContract.SQL_DELETE_ENTRIES;

import android.content.Context;
import net.sqlcipher.database.SQLiteOpenHelper;
import net.sqlcipher.database.SQLiteDatabase;

public class VaultDbHelper extends SQLiteOpenHelper {
    private static VaultDbHelper instance;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "VaultDB.db";
    public static final String PASS_PHRASE = "123456";

    public VaultDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDelete(SQLiteDatabase db) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
