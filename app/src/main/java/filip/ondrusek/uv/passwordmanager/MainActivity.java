package filip.ondrusek.uv.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteDatabase.loadLibs(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                createUnlockVault();
            }
        }, 100);
    }

    private void createUnlockVault()
    {
        if(databaseExists(getApplicationContext(), "VaultDB.db"))
        {
            Intent intent = new Intent(MainActivity.this, AuthenticationActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(MainActivity.this, CreateVaultActivity.class);
            startActivity(intent);
            finish();
        }

    }
    private static boolean databaseExists(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }
}