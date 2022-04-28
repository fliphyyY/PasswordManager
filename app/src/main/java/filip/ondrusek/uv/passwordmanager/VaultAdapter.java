package filip.ondrusek.uv.passwordmanager;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import net.sqlcipher.database.SQLiteDatabase;

public class VaultAdapter extends RecyclerView.Adapter<VaultAdapter.VaultViewHolder> {
    private Context mContext;
    public Cursor vaultCursor;
    private String masterPassword;
    private VaultDbHelper vaultDbHelper;
    private View.OnClickListener onItemClickListener;

    public VaultAdapter(Context context, Cursor cursor, String masterPassword) {
        this.mContext = context;
        this.vaultCursor = cursor;
        this.masterPassword = masterPassword;
        vaultDbHelper = new VaultDbHelper(mContext.getApplicationContext());

    }
    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }

    public class VaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        public TextView textView;
        public ImageButton imageButton;
        public VaultViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textviewVaultList);
            imageButton = itemView.findViewById(R.id.imageButton);
            imageButton.setOnClickListener(this);
            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
        }

        @Override
        public void onClick(View v) {
            showPopupMenu(v);
        }

        private void showPopupMenu(View view) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_popup_delete:
                    Cursor cursor = getVaultCursor();
                    cursor.moveToPosition(getAdapterPosition());
                    String id = vaultCursor.getString(vaultCursor.getColumnIndexOrThrow(VaultContract.VaultEntry._ID));
                    vaultDbHelper.deleteItem(masterPassword,Integer.valueOf(id));
                    setVaultCursor(getVaultItems());
                    notifyItemRemoved(getAdapterPosition());
                    return true;
                default:
                    return false;
            }
        }
    }

    @NonNull
    @Override
    public VaultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.vault_recycler_list

                , parent, false);
        return new VaultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VaultViewHolder holder, int position) {
        if(!vaultCursor.moveToPosition(position)) {
            return;
        }

        String code ="name is: " + vaultCursor.getString(vaultCursor.getColumnIndexOrThrow(VaultContract.VaultEntry.COLUMN_NAME_NAME));
        holder.textView.setText(code);
    }

    @Override
    public int getItemCount() {
        return vaultCursor.getCount();
    }
    public Cursor getVaultCursor() { return vaultCursor; }

    public void setVaultCursor(Cursor newCursor)
    {
        this.vaultCursor = newCursor;
    }

    private Cursor getVaultItems() {
        SQLiteDatabase db = vaultDbHelper.getDatabase(masterPassword);
        String selectQuery = "SELECT _id, name FROM vault";
        String[] selectionArgs = new String[]{};
        Cursor c = db.rawQuery(selectQuery, selectionArgs );
        return c;
    }
}
