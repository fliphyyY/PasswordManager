package filip.ondrusek.uv.passwordmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static boolean databaseExists(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        SQLiteDatabase.loadLibs(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                createUnlockVault();
            }
        }, 100);
    }

    private void createUnlockVault() {
        if (databaseExists(getApplicationContext(), "VaultDB.db")) {
            Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(MainActivity.this, CreateVaultActivity.class);
            startActivity(intent);
            finish();
        }
    }
}