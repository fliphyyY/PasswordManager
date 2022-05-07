package filip.ondrusek.uv.passwordmanager;

import android.content.ContentValues;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import filip.ondrusek.uv.passwordmanager.databinding.ActivityNavigationBinding;

public class AddItemFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText name, username, password, url;
    private TextInputEditText notes;
    private View view;
    private TextView saveButton;
    private VaultDbHelper vaultDbHelper;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String masterPassword;
    private BottomNavigationView bottomNavigationView;


    public AddItemFragment() {
        // Required empty public constructor
    }

    public static AddItemFragment newInstance(String param1, String param2) {
        AddItemFragment fragment = new AddItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_item, container, false);
        name = view.findViewById(R.id.nameAdd);
        username = view.findViewById(R.id.usernameAdd);
        password = view.findViewById(R.id.passwordAdd);
        url = view.findViewById(R.id.urlAdd);
        notes = view.findViewById(R.id.notesAdd);
        saveButton = view.findViewById(R.id.save);
        vaultDbHelper = new VaultDbHelper(getContext());
        bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
        masterPassword = getArguments().getString("masterPassword");
        saveButton.setOnClickListener(view -> {
            if(isNameEmpty(view)) {
                openEmptyNameDialog();
            }
            else {
                insertNewItem(view);
                Bundle bundleVaultChange = new Bundle();
                bundleVaultChange.putString("masterPassword", masterPassword);
                VaultFragment vaultFragmentChange = new VaultFragment();
                vaultFragmentChange.setArguments(bundleVaultChange);
                replaceFragment(vaultFragmentChange);
                bottomNavigationView.setSelectedItemId(R.id.vault);
                showToast();
            }
        });
        return view;
    }

    private void insertNewItem(View view)
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
        vaultDbHelper.insertItem(itemValues, masterPassword);
    }

    public void openEmptyNameDialog()
    {
        EmptyNameDialog emptyNameDialog = new EmptyNameDialog();
        emptyNameDialog.show(getActivity().getSupportFragmentManager(), "empty name");
    }

    private boolean isNameEmpty(View view)
    {
        if(this.name.getText().length() == 0) {
            return true;
        }
        return false;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void showToast()
    {
        LayoutInflater layoutInflater = getLayoutInflater();
        View layout = layoutInflater.inflate(R.layout.toast_layout, (ViewGroup) view.findViewById(R.id.toast_root));
        TextView toastText = layout.findViewById(R.id.toast_text);
        Toast toast = new Toast(getContext());
        toast.setGravity(Gravity.CENTER, 0,600);
        toast.setDuration(Toast.LENGTH_LONG);
        toastText.setText("New item created.");
        toast.setView(layout);
        toast.show();
    }

}