package filip.ondrusek.uv.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import filip.ondrusek.uv.passwordmanager.databinding.ActivityMainBinding;
import filip.ondrusek.uv.passwordmanager.databinding.ActivityNavigationBinding;

public class NavigationActivity extends AppCompatActivity {

    ActivityNavigationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new VaultFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.vault:
                    replaceFragment(new VaultFragment());
                    break;
                case R.id.add:
                    replaceFragment(new AddItemFragment());
                    break;
                case R.id.settings:
                    replaceFragment(new SettingsFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}