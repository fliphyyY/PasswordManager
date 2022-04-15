package filip.ondrusek.uv.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;

import net.sqlcipher.database.SQLiteDatabase;
import android.os.Bundle;

public class CreateVaultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vault);
        /*String password = "123456";
        VaultDbHelper vaultDbHelper = new VaultDbHelper(getApplicationContext());
        SQLiteDatabase database = vaultDbHelper.getWritableDatabase("123456");
        vaultDbHelper.onDelete(database);*/

    }
}