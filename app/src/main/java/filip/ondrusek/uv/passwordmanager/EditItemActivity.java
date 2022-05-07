package filip.ondrusek.uv.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class EditItemActivity extends AppCompatActivity {
    private VaultModel vaultModel;
    private String masterPassword;
    private EditText name, username, password, url;
    private TextView cancelButton;
    private TextInputEditText notes;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        vaultModel = (VaultModel) getIntent().getSerializableExtra("vaultModel");
        masterPassword = (String) getIntent().getSerializableExtra("masterPassword");
        id = (String) getIntent().getSerializableExtra("id");
        cancelButton = findViewById(R.id.cancel_buttonEdit);
        name = findViewById(R.id.nameEdit);
        username = findViewById(R.id.usernameEdit);
        password = findViewById(R.id.passwordEdit);
        url = findViewById(R.id.urlEdit);
        notes = findViewById(R.id.notesEdit);

        cancelButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(this, NavigationActivity.class);
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
    }
}