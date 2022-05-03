package filip.ondrusek.uv.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class VaultItemDetails extends AppCompatActivity {
    private VaultModel vaultModel;
    private TextView cancelButton;
    private String masterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vault_item_details);
        vaultModel = (VaultModel) getIntent().getSerializableExtra("vaultModel");
        masterPassword = (String) getIntent().getSerializableExtra("masterPassword");
        cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(view -> {
            Intent intent = new Intent(VaultItemDetails.this, NavigationActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("masterPassword", masterPassword);
            intent.putExtras(b);
            startActivity(intent);
            finish();
        });

    }
}