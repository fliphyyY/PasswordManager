package filip.ondrusek.uv.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class VaultItemDetails extends AppCompatActivity {
    private VaultModel vaultModel;
    private TextView cancelButton;
    private String masterPassword;
    private EditText name, username, password, url;
    private TextInputEditText notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vault_item_details);
        vaultModel = (VaultModel) getIntent().getSerializableExtra("vaultModel");
        masterPassword = (String) getIntent().getSerializableExtra("masterPassword");
        cancelButton = findViewById(R.id.cancel_button);
        name = findViewById(R.id.nameDetail);
        username = findViewById(R.id.usernameDetail);
        password = findViewById(R.id.passwordDetail);
        url = findViewById(R.id.urlDetail);
        notes = findViewById(R.id.notesDetail);

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
    }


}