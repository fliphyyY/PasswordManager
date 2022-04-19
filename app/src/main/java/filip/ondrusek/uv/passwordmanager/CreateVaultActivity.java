package filip.ondrusek.uv.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.Objects;
import java.util.regex.*;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class CreateVaultActivity extends AppCompatActivity {
    static final int MAX_LENGTH = 8;
    private EditText masterPassword, masterPasswordAgain;
    private TextInputLayout textInputLayoutCreate, textInputLayoutCreateAgain;
    private String masterPasswordInput, masterPasswordInputAgain;
    private Button createVaultButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vault);
        masterPassword = findViewById(R.id.masterPasswordCreate);
        masterPasswordAgain = findViewById(R.id.masterPasswordCreateAgain);
        textInputLayoutCreate = findViewById(R.id.textInputLayoutCreate);
        textInputLayoutCreateAgain = findViewById(R.id.textInputLayoutCreateAgain);
        createVaultButton = findViewById(R.id.createButton);
        textInputLayoutCreate.setErrorEnabled(true);
        textInputLayoutCreateAgain.setErrorEnabled(true);
        createVaultButton.setEnabled(false);
        masterPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
                Objects.requireNonNull(textInputLayoutCreate.getEditText()).addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence text, int start, int count, int after) {
                        passwordValidation(text);
                    }
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        masterPasswordInput = s.toString();
                    }
                });

        });

        createVaultButton.setOnClickListener(v -> {
        });

        masterPasswordAgain.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }

                Objects.requireNonNull(textInputLayoutCreateAgain.getEditText()).addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence text, int start, int count, int after) {
                        if(!text.toString().equals(masterPasswordInput))
                        {
                            textInputLayoutCreateAgain.setError(getString(R.string.match_passwords));
                            textInputLayoutCreateAgain.setErrorEnabled(true);
                        } else {
                            textInputLayoutCreateAgain.setErrorEnabled(false);
                        }
                    }
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        masterPasswordInputAgain = s.toString();
                    }
                });
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

    private void passwordValidation(CharSequence text) {
        if(isValidPassword(text.toString(), Pattern.compile("[a-z ]"))){
            textInputLayoutCreate.setError(getString(R.string.letter_lower_password));
            textInputLayoutCreate.setErrorEnabled(true);
        }
        else if(isValidPassword(text.toString(), Pattern.compile("[0-9 ]"))){
            textInputLayoutCreate.setError(getString(R.string.number_password));
            textInputLayoutCreate.setErrorEnabled(true);
        }
        else if(isValidPassword(text.toString(), Pattern.compile("[A-Z ]"))){
            textInputLayoutCreate.setError(getString(R.string.letter_upper_password));
            textInputLayoutCreate.setErrorEnabled(true);
        }
        else if(isValidPassword(text.toString(), Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE))) {
            textInputLayoutCreate.setError(getString(R.string.special_password));
            textInputLayoutCreate.setErrorEnabled(true);
        }
        else if(text.toString().contains(" ")){
            textInputLayoutCreate.setError(getString(R.string.space_password));
            textInputLayoutCreate.setErrorEnabled(true);
        }
        else if (text.length() > 0 && text.length() <= MAX_LENGTH) {
            textInputLayoutCreate.setError(getString(R.string.min_length));
            textInputLayoutCreate.setErrorEnabled(true);
        }
        else {
            textInputLayoutCreate.setErrorEnabled(false);
        }
    }

    private boolean isValidPassword(String password, Pattern pattern)
    {
        Matcher m = pattern.matcher(password);
        return !m.find();
    }
}