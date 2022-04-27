package filip.ondrusek.uv.passwordmanager;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import filip.ondrusek.uv.passwordmanager.databinding.ActivityNavigationBinding;

public class NavigationActivity extends AppCompatActivity {

    private ActivityNavigationBinding binding;
    private String masterPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        masterPassword = (String) getIntent().getSerializableExtra("masterPassword");
        Bundle bundleVault = new Bundle();
        bundleVault.putString("masterPassword", masterPassword);
        VaultFragment vaultFragment = new VaultFragment();
        vaultFragment.setArguments(bundleVault);
        replaceFragment(vaultFragment);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.vault:
                    Bundle bundleVaultSwitch = new Bundle();
                    bundleVaultSwitch.putString("masterPassword", masterPassword);
                    VaultFragment vaultFragmentSwitch = new VaultFragment();
                    vaultFragmentSwitch.setArguments(bundleVaultSwitch);
                    replaceFragment(vaultFragmentSwitch);
                    break;
                case R.id.add:
                    Bundle bundleAdd = new Bundle();
                    bundleAdd.putString("masterPassword", masterPassword);
                    AddItemFragment addItemFragment = new AddItemFragment();
                    addItemFragment.setArguments(bundleAdd);
                    replaceFragment(addItemFragment);
                    break;
                case R.id.settings:
                    replaceFragment(new SettingsFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}