package filip.ondrusek.uv.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.security.SecureRandom;

public class EditItemActivity extends AppCompatActivity {
    private VaultModel vaultModel;
    private String masterPassword;
    private EditText name, username, password, url;
    private TextView cancelButton, saveButton;
    private TextInputEditText notes;
    private String id;
    private VaultDbHelper vaultDbHelper;
    private ImageView checkPassword, generatePassword;
    private PwnedPasswordHandling pwnedPasswordHandling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_edit_item);
        vaultModel = (VaultModel) getIntent().getSerializableExtra("vaultModel");
        masterPassword = (String) getIntent().getSerializableExtra("masterPassword");
        id = (String) getIntent().getSerializableExtra("id");
        cancelButton = findViewById(R.id.cancel_buttonEdit);
        saveButton = findViewById(R.id.saveEdit);
        name = findViewById(R.id.nameEdit);
        username = findViewById(R.id.usernameEdit);
        password = findViewById(R.id.passwordEdit);
        url = findViewById(R.id.urlEdit);
        notes = findViewById(R.id.notesEdit);
        vaultDbHelper = new VaultDbHelper(getApplicationContext());
        checkPassword = findViewById(R.id.checkPasswordEdit);
        generatePassword = findViewById(R.id.generatePasswordEdit);
        pwnedPasswordHandling =  new PwnedPasswordHandling(getSupportFragmentManager(), getApplicationContext());

        cancelButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(this, NavigationActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("masterPassword", masterPassword);
            intent.putExtras(b);
            startActivity(intent);
            finish();
        });

        saveButton.setOnClickListener(view -> {
            if(isNameEmpty(view)) {
                openEmptyNameDialog();
            }
            else {
                updateItem(view);
                Intent intent = new Intent(EditItemActivity.this, NavigationActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("masterPassword", masterPassword);
                intent.putExtras(b);
                startActivity(intent);
                showToast(getResources().getString(R.string.item_updated));
            }
        });

        checkPassword.setOnClickListener(view -> {
            boolean internetConnection = false;
            String passwordTextView = this.password.getText().toString();
            if(passwordTextView.length() > 0) {
                try {
                    internetConnection = isConnected();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(internetConnection) {

                    pwnedPasswordHandling.hashPassword(passwordTextView);
                } else {
                    showToast(getResources().getString(R.string.no_internet));
                }
            }
            else {
                showToast(getResources().getString(R.string.empty_password));
            }
        });

        generatePassword.setOnClickListener(view -> {
            char[] possibleCharacters = (new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?")).toCharArray();
            password.setText(RandomStringUtils.random( 16, 0, possibleCharacters.length-1, false, false, possibleCharacters, new SecureRandom()));

        });

        name.setText(vaultModel.getName());
        username.setText(vaultModel.getUsername());
        password.setText(vaultModel.getPassword());
        url.setText(vaultModel.getUrl());
        notes.setText(vaultModel.getNotes());
    }

    private void openEmptyNameDialog()
    {
        EmptyNameDialog emptyNameDialog = new EmptyNameDialog(getResources().getString(R.string.error_dialog), getResources().getString(R.string.empty_name_text));
        emptyNameDialog.show(getSupportFragmentManager(), "empty_name");
    }

    private boolean isNameEmpty(View view)
    {
        return this.name.getText().length() == 0;
    }

    private void updateItem(View view)
    {
        String name = this.name.getText().toString();
        String username = this.username.getText().toString();
        String password = this.password.getText().toString();
        String url = this.url.getText().toString();
        String notes = this.notes.getText().toString();

        ContentValues itemValues = new ContentValues();
        itemValues.put(VaultContract.VaultEntry.COLUMN_NAME_NAME, name);
        itemValues.put(VaultContract.VaultEntry.COLUMN_NAME_USERNAME, username);
        itemValues.put(VaultContract.VaultEntry.COLUMN_NAME_PASSWORD, password);
        itemValues.put(VaultContract.VaultEntry.COLUMN_NAME_URL, url);
        itemValues.put(VaultContract.VaultEntry.COLUMN_NAME_NOTES, notes);
        vaultDbHelper.updateItem(masterPassword, itemValues, id);
    }

    private void showToast(String text)
    {
        View layout = LayoutInflater.from(getApplicationContext()).inflate(R.layout.toast_layout, null);
        TextView toastText = layout.findViewById(R.id.toast_text);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0,600);
        toast.setDuration(Toast.LENGTH_LONG);
        toastText.setText(text);
        toast.setView(layout);
        toast.show();
    }

    @Override
    public void onBackPressed() { }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        Intent intent = new Intent(EditItemActivity.this, AuthenticationActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean isConnected() throws InterruptedException, IOException {
        String command = "ping -c 1 google.com";
        return Runtime.getRuntime().exec(command).waitFor() == 0;
    }
}