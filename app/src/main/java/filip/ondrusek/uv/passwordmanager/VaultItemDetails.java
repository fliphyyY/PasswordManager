package filip.ondrusek.uv.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.content.ClipboardManager;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

public class VaultItemDetails extends AppCompatActivity {
    private VaultModel vaultModel;
    private TextView cancelButton;
    private String masterPassword;
    private EditText name, username, password, url;
    private TextInputEditText notes;
    private ImageView checkPassword, copyPassword;
    private boolean internetConnection;
    private PwnedPasswordHandling pwnedPasswordHandling;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_vault_item_details);
        vaultModel = (VaultModel) getIntent().getSerializableExtra("vaultModel");
        masterPassword = (String) getIntent().getSerializableExtra("masterPassword");
        cancelButton = findViewById(R.id.cancel_button);
        name = findViewById(R.id.nameDetail);
        username = findViewById(R.id.usernameDetail);
        password = findViewById(R.id.passwordDetail);
        url = findViewById(R.id.urlDetail);
        notes = findViewById(R.id.notesDetail);
        checkPassword = findViewById(R.id.checkPasswordDetail);
        copyPassword = findViewById(R.id.copyPassword);
        pwnedPasswordHandling =  new PwnedPasswordHandling(getSupportFragmentManager(), getApplicationContext());


        cancelButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(VaultItemDetails.this, NavigationActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("masterPassword", masterPassword);
            intent.putExtras(b);
            startActivity(intent);
            finish();
        });
        name.setText(vaultModel.getName());
        username.setText(vaultModel.getUsername());
        password.setText(vaultModel.getPassword());
        url.setText(vaultModel.getUrl());
        notes.setText(vaultModel.getNotes());

        checkPassword.setOnClickListener(view -> {
            String passwordTextView = this.password.getText().toString();
            if(passwordTextView.length() > 0) {
                try {
                    isConnected();
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

        copyPassword.setOnClickListener(view -> {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("passwordClip", password.getText());
            clipboardManager.setPrimaryClip(clipData);
            showToast(getResources().getString(R.string.copy_password));
        });
    }

    private void showToast(String text)
    {
        LayoutInflater layoutInflater = getLayoutInflater();
        View layout = layoutInflater.inflate(R.layout.toast_layout, findViewById(R.id.toast_root));
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
        Intent intent = new Intent(VaultItemDetails.this, AuthenticationActivity.class);
        startActivity(intent);
        finish();
    }

    private void isConnected() throws InterruptedException, IOException {

        String command = "ping -c 1 google.com";
        internetConnection = Runtime.getRuntime().exec(command).waitFor() == 0;
    }
}