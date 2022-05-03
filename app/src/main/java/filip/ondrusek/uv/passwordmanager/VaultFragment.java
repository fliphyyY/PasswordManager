package filip.ondrusek.uv.passwordmanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.sqlcipher.database.SQLiteDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VaultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VaultFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private VaultAdapter vaultAdapter;
    private VaultDbHelper vaultDbHelper;
    private String masterPassword;
    private Cursor vaultCursor;
    private VaultModel vaultModel;
    private final View.OnClickListener onItemClickListener = view -> {
        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
        int position = viewHolder.getAdapterPosition();
        Cursor allItems = getVaultItems();
        allItems.moveToPosition(position);
        String id = getItemId(allItems);
        Intent intent = new Intent(getActivity(), VaultItemDetails.class);
        Cursor vaultItem = getItemById(id);
        createItemObject(vaultItem);
        allItems.close();
        Bundle b = new Bundle();
        b.putSerializable("vaultModel", vaultModel);
        intent.putExtras(b);
        b.putSerializable("masterPassword", masterPassword);
        intent.putExtras(b);
        startActivity(intent);
    };

    public VaultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VaultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VaultFragment newInstance(String param1, String param2) {
        VaultFragment fragment = new VaultFragment();
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
        view = inflater.inflate(R.layout.fragment_vault, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_vault);
        masterPassword = getArguments().getString("masterPassword");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        vaultDbHelper = new VaultDbHelper(getContext());
        vaultCursor = getVaultItems();
        vaultAdapter = new VaultAdapter(getContext(), vaultCursor, masterPassword);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(vaultAdapter);
        vaultAdapter.setOnItemClickListener(onItemClickListener);
        return view;
    }

    public Cursor getVaultItems() {
        SQLiteDatabase db = vaultDbHelper.getDatabase(masterPassword);
        String selectQuery = "SELECT _id, name FROM vault";
        String[] selectionArgs = new String[]{};
        Cursor c = db.rawQuery(selectQuery, selectionArgs );
        return c;
    }

    public Cursor getItemById(String id) {
        SQLiteDatabase db = vaultDbHelper.getDatabase(masterPassword);
        String selectQuery = "SELECT * FROM vault WHERE _id = ?";
        String[] selectionArgs = new String[]{id};
        Cursor c = db.rawQuery(selectQuery, selectionArgs);
        return c;
    }

    private void createItemObject(Cursor cursor) {
        try {
            cursor.moveToFirst();
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(VaultContract.VaultEntry.COLUMN_NAME_NAME));
                String username = cursor.getString(cursor.getColumnIndexOrThrow(VaultContract.VaultEntry.COLUMN_NAME_USERNAME));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(VaultContract.VaultEntry.COLUMN_NAME_PASSWORD));
                String url = cursor.getString(cursor.getColumnIndexOrThrow(VaultContract.VaultEntry.COLUMN_NAME_URL));
                String notes = cursor.getString(cursor.getColumnIndexOrThrow(VaultContract.VaultEntry.COLUMN_NAME_NOTES));
                vaultModel = new VaultModel(name, username, password, url, notes);
            } while (cursor.moveToNext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getItemId(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(VaultContract.VaultEntry._ID));
    }

    public VaultModel getVaultModel()
    {
        return vaultModel;
    }
}