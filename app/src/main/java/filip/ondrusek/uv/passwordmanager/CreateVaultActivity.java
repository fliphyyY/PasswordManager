package filip.ondrusek.uv.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;

import net.sqlcipher.database.SQLiteDatabase;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class CreateVaultActivity extends AppCompatActivity {
    private EditText editTextMasterPassword, editTextMasterPasswordAgain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vault);
        editTextMasterPassword = findViewById(R.id.editTextMasterPassword);
        editTextMasterPasswordAgain = findViewById(R.id.editTextMasterPasswordAgain);
        editTextMasterPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });
        editTextMasterPasswordAgain.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });
        /*String password = "123456";
        VaultDbHelper vaultDbHelper = new VaultDbHelper(getApplicationContext());
        SQLiteDatabase database = vaultDbHelper.getWritableDatabase("123456");
        vaultDbHelper.onDelete(database);*/
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}