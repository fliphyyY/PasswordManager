package filip.ondrusek.uv.passwordmanager;

import static filip.ondrusek.uv.passwordmanager.CreateVaultActivity.MAX_LENGTH;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;

import net.sqlcipher.database.*;

import java.util.Objects;

public class AuthenticationActivity extends AppCompatActivity {
    private EditText masterPassword;
    private TextInputLayout textInputLayoutAuthentication;
    private Button logInButton;
    private SQLiteDatabase database ;

    @Override
    protected void onCreate(Bundle savedInstanceState) throws SQLiteException{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_activity);
        masterPassword = findViewById(R.id.masterPasswordAuthentication);
        logInButton = findViewById(R.id.logInButton);
        textInputLayoutAuthentication = findViewById(R.id.textInputLayoutAuthentication);
        logInButton.setEnabled(false);
        database = null;
        //getApplicationContext().deleteDatabase("VaultDB.db");
        masterPassword.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(view);
            }

            Objects.requireNonNull(textInputLayoutAuthentication.getEditText()).addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence text, int start, int count, int after) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    logInButton.setEnabled(s.length() >= MAX_LENGTH);
                }
            });
        });

        logInButton.setOnClickListener(v -> {
            unlockDb();
            if(database != null && database.isOpen()) {
                database.close();
                Intent intent = new Intent(AuthenticationActivity.this, NavigationActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("masterPassword", masterPassword.getText().toString());
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });
    }

    private void unlockDb() throws SQLiteException {
        try {
            VaultDbHelper vaultDbHelper = new VaultDbHelper(getApplicationContext());
            database = vaultDbHelper.getWritableDatabase(masterPassword.getText().toString());
        } catch (SQLiteException sqLiteException) {
            Toast.makeText(getApplicationContext(), "Wrong password!", Toast.LENGTH_LONG).show();
        }
    }
    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}