package filip.ondrusek.uv.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.regex.*;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class CreateVaultActivity extends AppCompatActivity {
    private EditText masterPassword, masterPasswordAgain;
    private TextInputLayout textInputLayoutCreate;
    /*private String regex = "^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[@#$%^&+=])"
            + "(?=\\S+$).{8,20}$";*/
    String regex = "^(?=.*[0-9])";
            /*+ "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[@#$%^&+=])"
            + "(?=\\S+$).{8,20}$";*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vault);
        masterPassword = findViewById(R.id.masterPasswordCreate);
        masterPasswordAgain = findViewById(R.id.masterPasswordCreateAgain);
        textInputLayoutCreate = findViewById(R.id.textInputLayoutCreate);
        textInputLayoutCreate.setErrorEnabled(true);
        masterPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });
        masterPasswordAgain.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });
        /*String password = "123456";
        VaultDbHelper vaultDbHelper = new VaultDbHelper(getApplicationContext());
        SQLiteDatabase database = vaultDbHelper.getWritableDatabase("123456");
        vaultDbHelper.onDelete(database);*/
        masterPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                    textInputLayoutCreate.getEditText().addTextChangedListener(new TextWatcher() {
                        // ...
                        @Override
                        public void onTextChanged(CharSequence text, int start, int count, int after) {
                            if (text.length() > 0 && text.length() <= 8) {
                                textInputLayoutCreate.setError(getString(R.string.min_length));
                                textInputLayoutCreate.setErrorEnabled(true);
                            } else if(!isValidPassword(text.toString())) {
                                textInputLayoutCreate.setError(getString(R.string.special_password));
                                textInputLayoutCreate.setErrorEnabled(true);
                            }
                            else {
                                textInputLayoutCreate.setErrorEnabled(false);
                            }
                        }
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count,
                                                      int after) {
                            // TODO Auto-generated method stub
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }

         });
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void setupFloatingLabelError() {
        textInputLayoutCreate.getEditText().addTextChangedListener(new TextWatcher() {
            // ...
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (text.length() > 0 && text.length() <= 4) {
                    textInputLayoutCreate.setError(getString(R.string.app_name));
                    textInputLayoutCreate.setErrorEnabled(true);
                } else {
                    textInputLayoutCreate.setErrorEnabled(false);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private boolean isValidPassword(String password)
    {
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(password);
        boolean check = m.find();
        return check;
    }
}