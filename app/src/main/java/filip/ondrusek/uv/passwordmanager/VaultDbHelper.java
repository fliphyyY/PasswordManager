package filip.ondrusek.uv.passwordmanager;

import static filip.ondrusek.uv.passwordmanager.VaultContract.SQL_CREATE_ENTRIES;
import static filip.ondrusek.uv.passwordmanager.VaultContract.SQL_DELETE_ENTRIES;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteException;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.io.File;

public class VaultDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "VaultDB.db";
    private final static String TAG = "VaultDbHelper";
    private static VaultDbHelper instance;
    private final Context context;

    public VaultDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
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

    public void insertItem(ContentValues vaultValues, String masterPassword)
    {
        SQLiteDatabase db = this.getDatabase(masterPassword);
        db.insert(VaultContract.VaultEntry.TABLE_NAME, null, vaultValues);
    }

    public void deleteItem(String masterPassword, String id)
    {
        SQLiteDatabase db = this.getDatabase(masterPassword);
        db.delete(VaultContract.VaultEntry.TABLE_NAME, "_id = ?",new String[]{id});
    }

    public void updateItem(String masterPassword, ContentValues vaultValues, String id)
    {
        SQLiteDatabase db = this.getDatabase(masterPassword);
        db.update(VaultContract.VaultEntry.TABLE_NAME, vaultValues, "_id=?",new String[]{id});
    }


    public SQLiteDatabase getDatabase(String masterPassword)
    {
        SQLiteDatabase.loadLibs(context);
        SQLiteDatabase db = null;
        File databaseFile = context.getDatabasePath("VaultDB.db");
        try {
            db = SQLiteDatabase.openOrCreateDatabase(databaseFile, masterPassword, null );
        } catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());
        }
        return db;
    }
}
