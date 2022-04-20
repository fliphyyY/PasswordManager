package filip.ondrusek.uv.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.material.textfield.TextInputLayout;

public class CreateVaultActivity extends AppCompatActivity {
    static final int MAX_LENGTH = 8;
    private EditText masterPassword, masterPasswordAgain;
    private TextInputLayout textInputLayoutCreate, textInputLayoutCreateAgain;
    private Button createVaultButton;
    private boolean passwordValidationCorrect;
    private boolean samePasswords;

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
        masterPasswordAgain.setEnabled(false);
        passwordValidationCorrect = false;
        samePasswords = false;
        masterPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
                Objects.requireNonNull(textInputLayoutCreate.getEditText()).addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence text, int start, int count, int after) {
                        passwordValidation(text, textInputLayoutCreate, masterPasswordAgain);
                    }
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        if(s.toString().equals(masterPasswordAgain.getText().toString()) && s.length() > 0 && !textInputLayoutCreate.isErrorEnabled())
                        {
                            samePasswords = true;
                        } else {
                            samePasswords = false;
                        }
                        if(!textInputLayoutCreate.isErrorEnabled() && !passwordValidationCorrect) {
                            masterPasswordAgain.setEnabled(true);
                            passwordValidationCorrect = true;
                        } else if(textInputLayoutCreate.isErrorEnabled() && !(textInputLayoutCreate.getError().toString().equals(getString(R.string.match_passwords)))) {
                            masterPasswordAgain.setEnabled(false);
                            createVaultButton.setEnabled(false);
                        }
                        else if(samePasswords) {
                            masterPasswordAgain.setEnabled(true);
                            createVaultButton.setEnabled(true);
                            hideKeyboard(v);
                        }
                       else {
                            createVaultButton.setEnabled(false);
                            if(!passwordValidationCorrect) {
                                masterPasswordAgain.setEnabled(false);
                            }
                            else {
                                masterPasswordAgain.setEnabled(true);
                            }
                        }
                        if(textInputLayoutCreateAgain.isErrorEnabled()) {
                            textInputLayoutCreateAgain.setError(null);
                        }
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
                        passwordValidation(text, textInputLayoutCreateAgain, masterPassword);
                    }
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(textInputLayoutCreate.isErrorEnabled() && textInputLayoutCreate.getError() != null) {
                            if((textInputLayoutCreate.getError().toString().equals(getString(R.string.match_passwords)))) {
                                textInputLayoutCreate.setErrorEnabled(false);
                        }
                        }
                        if(s.toString().equals(masterPassword.getText().toString()) && !textInputLayoutCreate.isErrorEnabled()) {
                            createVaultButton.setEnabled(true);
                            hideKeyboard(v);
                        } else {
                            createVaultButton.setEnabled(false);
                        }

                        if(textInputLayoutCreate.isErrorEnabled())
                        {
                            textInputLayoutCreate.setError(null);
                        }

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

    private void passwordValidation(CharSequence text, TextInputLayout textInputLayout, EditText editText) {
        if(text.length() == 0){
            textInputLayout.setError(getString(R.string.no_passwords));
            textInputLayout.setErrorEnabled(true);
        }
        else if(isValidPassword(text.toString(), Pattern.compile("[a-z ]"))){
            textInputLayout.setError(getString(R.string.letter_lower_password));
            textInputLayout.setErrorEnabled(true);
        }
        else if(isValidPassword(text.toString(), Pattern.compile("[0-9 ]"))){
            textInputLayout.setError(getString(R.string.number_password));
            textInputLayout.setErrorEnabled(true);
        }
        else if(isValidPassword(text.toString(), Pattern.compile("[A-Z ]"))){
            textInputLayout.setError(getString(R.string.letter_upper_password));
            textInputLayout.setErrorEnabled(true);
        }
        else if(isValidPassword(text.toString(), Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE))) {
            textInputLayout.setError(getString(R.string.special_password));
            textInputLayout.setErrorEnabled(true);
        }
        else if(text.toString().contains(" ")){
            textInputLayout.setError(getString(R.string.space_password));
            textInputLayout.setErrorEnabled(true);
        }
        else if (text.length() > 0 && text.length() < MAX_LENGTH) {
            textInputLayout.setError(getString(R.string.min_length));
            textInputLayout.setErrorEnabled(true);
        }
        else if (!text.toString().equals(editText.getText().toString()) && !(editText.getText().toString().trim().length() == 0)) {
            textInputLayout.setError(getString(R.string.match_passwords));
            textInputLayout.setErrorEnabled(true);
        }
        else {
            textInputLayout.setErrorEnabled(false);
        }
    }

    private boolean isValidPassword(String password, Pattern pattern)
    {
        Matcher m = pattern.matcher(password);
        return !m.find();
    }
}